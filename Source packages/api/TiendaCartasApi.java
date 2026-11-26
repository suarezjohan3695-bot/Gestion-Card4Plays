/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import Controladores.ControladorCompra;
import Controladores.ControladorInventario;
import Controladores.TorneoControlador;
import Controladores.UsuarioControlador;
import Datos.Persistencia;
import Modelos.Accesorio;
import Modelos.Booster;
import Modelos.Cliente;
import Modelos.Inventario;
import Modelos.Producto;
import Modelos.ProductoSellado;
import Modelos.Rareza;
import Modelos.Rol;
import Modelos.Single;
import Modelos.TasaCambio;
import Modelos.Torneo;
import Modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import spark.Route;

import static spark.Spark.*;

public class TiendaCartasApi {

    private static final Gson gson = new Gson();
    private static final Persistencia persistencia = new Persistencia();
    private static final UsuarioControlador usuarioControlador = new UsuarioControlador(persistencia);
    private static final TorneoControlador torneoControlador = new TorneoControlador(persistencia);
    private static final ControladorInventario controladorInventario = new ControladorInventario(persistencia);
    private static final ControladorCompra controladorCompra = new ControladorCompra(persistencia);

    public static void iniciar() {
        int puerto = obtenerPuerto();
        port(puerto);
        configurar();
        rutasUsuarios();
        rutasTorneos();
        rutasProductos();

        System.out.println("API corriendo en http://localhost:" + puerto);
    }

    private static int obtenerPuerto() {
        String puerto = System.getenv("PORT");
        if (puerto != null) {
            return Integer.parseInt(puerto);

        }
        return 4567;
    }

    private static void configurar() {
        before((req, res) -> res.type("application/json"));
        notFound((req, res) -> {
            res.type("application/json");
            return gson.toJson(Map.of("error", "Ruta no encontrada"));
        });
        exception(Exception.class, (e, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body(gson.toJson(Map.of("error", "Error interno: " + e.getMessage())));
        });

        Filter soloAdmin = (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u == null) {
                halt(401, gson.toJson(Map.of("error", "Se requiere autenticación con credenciales válidas")));
            }
            if (u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                halt(403, gson.toJson(Map.of("error", "Acción reservada al administrador")));
            }
        };
        before("/clientes", soloAdmin);
        before("/clientes/*", soloAdmin);

        before("/torneos", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u == null) {
                halt(401, gson.toJson(Map.of("error", "Se requiere autenticación con credenciales válidas")));
            }
            if (req.requestMethod().equalsIgnoreCase("POST") && u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                halt(403, gson.toJson(Map.of("error", "Acción reservada al administrador")));
            }
        });

        before("/torneos/*", (req, res) -> {
            if (usuarioDeLaPeticion(req) == null) {
                halt(401, gson.toJson(Map.of("error", "Se requiere autenticación con credenciales válidas")));
            }
        });

    }

    private static void rutasUsuarios() {

        get("/clientes", (req, res) -> {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (Usuario u : usuarioControlador.listarClientes()) {
                lista.add(vistaUsuario(u));
            }
            return gson.toJson(lista);
        });

        get("/clientes/:id", (req, res) -> {
            Usuario u = usuarioControlador.buscarCliente(req.params(":id"));
            if (u == null) {
                return error(res, 404, "Cliente no encontrado");
            }
            return gson.toJson(vistaUsuario(u));
        });

        post("/clientes", (req, res) -> {
            JsonObject body = leerBody(req.body());
            if (body == null || !tieneTexto(body, "nombre") || !tieneTexto(body, "contrasena")) {
                return error(res, 400, "Los campos 'nombre' y 'contrasena' son obligatorios");
            }
            String nombre = body.get("nombre").getAsString();
            String contrasena = body.get("contrasena").getAsString();

            if (usuarioControlador.buscarPorNombre(nombre.trim()) != null) {
                return error(res, 409, "Ya existe un usuario con ese nombre");
            }
            Usuario nuevo = usuarioControlador.addCliente(nombre, contrasena);
            res.status(201);
            return gson.toJson(vistaUsuario(nuevo));
        });

        patch("/clientes/:id", (req, res) -> {
            Usuario actual = usuarioControlador.buscarCliente(req.params(":id"));
            if (actual == null) {
                return error(res, 404, "Cliente no encontrado");
            }
            JsonObject body = leerBody(req.body());
            if (body == null) {
                return error(res, 400, "JSON inválido");
            }
            String nombre = null;
            String contrasena = null;

            if (body.has("nombre")) {
                if (!tieneTexto(body, "nombre")) {
                    return error(res, 400, "El campo 'nombre' no puede estar vacío");
                }
                nombre = body.get("nombre").getAsString();
                Usuario otro = usuarioControlador.buscarPorNombre(nombre.trim());
                if (otro != null && !otro.getIdUsuario().equals(actual.getIdUsuario())) {
                    return error(res, 409, "Ya existe un usuario con ese nombre");
                }
            }
            if (body.has("contrasena")) {
                if (!tieneTexto(body, "contrasena")) {
                    return error(res, 400, "El campo 'contrasena' no puede estar vacío");
                }
                contrasena = body.get("contrasena").getAsString();
            }
            usuarioControlador.actualizar(actual, nombre, contrasena);
            return gson.toJson(vistaUsuario(actual));
        });

        delete("/clientes/:id", (req, res) -> {
            if (!usuarioControlador.eliminar(req.params(":id"))) {
                return error(res, 404, "Cliente no encontrado");
            }
            return gson.toJson(Map.of("mensaje", "Cliente eliminado"));
        });
    }

    private static void rutasTorneos() {

        get("/torneos", (req, res) -> {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (Torneo t : torneoControlador.listarTorneos()) {
                lista.add(vistaTorneo(t));
            }
            return gson.toJson(lista);
        });

        get("/torneos/:id", (req, res) -> {
            Torneo t = torneoControlador.buscarTorneo(req.params(":id"));
            if (t == null) {
                return error(res, 404, "Torneo no encontrado");
            }
            return gson.toJson(vistaTorneo(t));
        });

        post("/torneos", (req, res) -> {
            JsonObject body = leerBody(req.body());
            if (body == null || !tieneTexto(body, "idTorneo") || !tieneTexto(body, "nombre")
                    || !tieneTexto(body, "fecha") || !body.has("maxParticipantes")) {
                return error(res, 400, "Faltan datos obligatorios para crear el torneo");
            }

            String idTorneo = body.get("idTorneo").getAsString().trim();
            String nombre = body.get("nombre").getAsString().trim();
            LocalDate fecha;
            int maxParticipantes;

            try {
                fecha = LocalDate.parse(body.get("fecha").getAsString());
                maxParticipantes = body.get("maxParticipantes").getAsInt();
            } catch (Exception e) {
                return error(res, 400, "Fecha inválida (use YYYY-MM-DD) o maxParticipantes no numérico");
            }

            if (maxParticipantes <= 0) {
                return error(res, 400, "maxParticipantes debe ser mayor que cero");
            }
            if (torneoControlador.buscarTorneo(idTorneo) != null) {
                return error(res, 409, "Ya existe un torneo con ese ID");
            }

            torneoControlador.crearTorneo(idTorneo, nombre, fecha, maxParticipantes);
            res.status(201);
            return gson.toJson(Map.of("mensaje", "Torneo creado exitosamente"));
        });

        post("/torneos/:id/inscribirse", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u.getTipoUsuario() != Rol.CLIENTE) {
                return error(res, 403, "Solo los clientes pueden inscribirse a los torneos");
            }

            Torneo torneo = torneoControlador.buscarTorneo(req.params(":id"));
            if (torneo == null) {
                return error(res, 404, "Torneo no encontrado");
            }
            if (!torneo.isActivo()) {
                return error(res, 400, "El torneo ya ha finalizado o no está activo");
            }

            boolean exito = torneoControlador.inscribirClienteTorneo(torneo.getIdTorneo(), (Cliente) u);
            if (!exito) {
                return error(res, 409, "No fue posible la inscripción: torneo sin cupo o el cliente ya está inscrito");
            }

            return gson.toJson(Map.of("mensaje", "Inscripción al torneo realizada con éxito"));
        });
    }

    public static void rutasProductos() {
        get("/Productos", (req, res) -> {
            List<Producto> prod = controladorInventario.getInventario();
            if (!prod.isEmpty()) {
                return gson.toJson(vistaProductos(controladorInventario.getInventario()));
            }
            return ("Inventario vacio");
        });
        get("/Productos/:id", (req, res) -> {

            Usuario u = usuarioControlador.buscarCliente(":id");
            if (u != null) {
                return error(res, 404, "Usuario no encontrado");
            } else if (u.getTipoUsuario() == Rol.CLIENTE) {
                Cliente c = (Cliente) u;
                if (c.getPertenencias() == null) {
                    return gson.toJson("Sin pertenencias aun");
                }
                res.status(200);
                return gson.toJson(c.getPertenencias());
            }
            return error(res, 401, "Admin no autorizado");

        });
        get("/Productos/:idUsuario/abrirBooster/:idProducto", (req, res) -> {
            Usuario u = usuarioControlador.buscarCliente(":idUsuario");
            if (u == null) {
                return error(res, 404, "Usuario no encontrado");

            } else if (u.getTipoUsuario() != Rol.CLIENTE) {
                return error(res, 401, "Admin no autorizado");
            }
            Cliente c = (Cliente) u;
            Inventario ip = c.getPertenencias();
            if (ip.getGeneral().isEmpty()) {
                return ("Tu inventario esta vacio");
            }
            Booster b = controladorInventario.buscarBooster(ip, ":idProducto");
            if (b == null) {
                return error(res, 404, "Este booster no se encuentra en tu inventario");

            }
            List<Single> contenido = b.getContenido();
            ip.getGeneral().remove(b);
            res.status(200);
            return gson.toJson(contenido);

        });

        post("/Productos/Single", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u == null) {
                return error(res, 400, "Usuario no identificado");
            }
            if (u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                return error(res, 403, "Accion reservada para administradores");
            }
            Single s = gson.fromJson(req.body(), Single.class);

            if (s.getIdProducto() == null) {
                return error(res, 400, "Falta el 'IdProducto'");
            }
            if (s.getNombre() == null) {
                return error(res, 400, "Pon el 'nombre' del producto");
            }
            if (s.getPrecioUSD() == 0) {
                return error(res, 400, "Cual es el valor de 'precioUSD'?");
            }
            if (s.getRareza() == null) {
                return error(res, 400, "La carta debe de tener una 'rareza'");
            }
            if (!controladorInventario.agregarProducto(s)) {
                return error(res, 409, "Ya existe un producto con este idProducto");
            }
            res.status(201);

            return gson.toJson(s);
        });

        post("/Productos/Booster", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                return error(res, 403, "Accion reservada para administradores");
            }
            Producto p = gson.fromJson(req.body(), Producto.class);
            List<Single> contenido = controladorInventario.contenidoBooster();
            Booster b = new Booster(contenido, p.getIdProducto(), p.getNombre(),
                    p.getPrecioUSD());
            if (b.getIdProducto() == null) {
                return error(res, 400, "Falta el 'IdProducto'");
            }
            if (b.getNombre() == null) {
                return error(res, 400, "Pon el 'nombre' del producto");
            }
            if (b.getPrecioUSD() == 0) {
                return error(res, 400, "Cual es el valor de 'precioUSD'?");
            }
            if (b.getContenido() == null || b.getContenido().size() < 10) {
                return error(res, 400, "No hay cartas suficientes para crear un booster");
            }
            if (!controladorInventario.agregarProducto(b)) {
                return error(res, 409, "Ya existe un producto con este idProducto");
            }
            res.status(201);

            return gson.toJson(vistaBooster(b));
        });
        post("/Productos/Accesorio", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                return error(res, 403, "Accion reservada para administradores");
            }
            Accesorio a = gson.fromJson(req.body(), Accesorio.class);

            if (a.getIdProducto() == null) {
                return error(res, 400, "Falta el 'IdProducto'");
            }
            if (a.getNombre() == null) {
                return error(res, 400, "Pon el 'nombre' del producto");
            }
            if (a.getPrecioUSD() == 0) {
                return error(res, 400, "Cual es el valor de 'precioUSD'?");
            }

            if (!controladorInventario.agregarProducto(a)) {
                return error(res, 409, "Ya existe un producto con este idProducto");
            }
            res.status(201);

            return gson.toJson(a);
        });
        post("/Productos/ProductoSellado", (req, res) -> {
            Usuario u = usuarioDeLaPeticion(req);
            if (u.getTipoUsuario() != Rol.ADMINISTRADOR) {
                return error(res, 403, "Accion reservada para administradores");
            }
            ProductoSellado ps = gson.fromJson(req.body(), ProductoSellado.class);

            if (ps.getIdProducto() == null) {
                return error(res, 400, "Falta el 'IdProducto'");
            }
            if (ps.getNombre() == null) {
                return error(res, 400, "Pon el 'nombre' del producto");
            }
            if (ps.getPrecioUSD() == 0) {
                return error(res, 400, "Cual es el valor de 'precioUSD'?");
            }

            if (!controladorInventario.agregarProducto(ps)) {
                return error(res, 409, "Ya existe un producto con este idProducto");
            }
            res.status(201);

            return gson.toJson(ps);
        });

    }

    // ---------- Auxiliares  ----------
    private static String error(Response res, int codigo, String mensaje) {
        res.status(codigo);
        return gson.toJson(Map.of("error", mensaje));
    }

    private static Map<String, Object> vistaUsuario(Usuario u) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("idUsuario", u.getIdUsuario());
        m.put("nombre", u.getNombre());
        m.put("tipoUsuario", u.getTipoUsuario());
        return m;
    }

    private static JsonObject leerBody(String body) {
        try {
            return gson.fromJson(body, JsonObject.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    private static boolean tieneTexto(JsonObject body, String campo) {
        return body.has(campo) && !body.get(campo).isJsonNull()
                && !body.get(campo).getAsString().isBlank();
    }

    private static Usuario usuarioDeLaPeticion(Request req) {
        String header = req.headers("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            return null;
        }
        try {
            String texto = new String(Base64.getDecoder().decode(header.substring(6)), StandardCharsets.UTF_8);
            String[] partes = texto.split(":", 2);
            if (partes.length != 2) {
                return null;
            }
            return usuarioControlador.autentificar(partes[0], partes[1]);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static Map<String, Object> vistaTorneo(Torneo t) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("idTorneo", t.getIdTorneo());
        m.put("nombre", t.getNombre());
        m.put("fecha", t.getFecha().toString());
        m.put("maxParticipantes", t.getMaxParticipantes());
        m.put("activo", t.isActivo());
        return m;
    }

    private static Map<String, Object> vistaBooster(Booster b) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("contenido", "cerrado");
        m.put("idProducto", b.getIdProducto());
        m.put("nombre", b.getNombre());
        m.put("precioUSD", b.getPrecioUSD());
        m.put("precioCOP", b.getPrecioCOP());
        return m;
    }

    private static List<Object> vistaProductos(List<Producto> general) {
        List<Object> respuesta = new ArrayList<>();
        for (Producto p : general) {
            if (p instanceof Booster) {
                Booster b = (Booster) p;
                Map<String, Object> m = vistaBooster(b);
                respuesta.add(m);

            } else {
                respuesta.add(p);
            }
        }
        return respuesta;

    }
}
