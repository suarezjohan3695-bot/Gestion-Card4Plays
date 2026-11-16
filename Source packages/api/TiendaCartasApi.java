/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import Controladores.UsuarioControlador;
import Modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import spark.Response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


public class TiendaCartasApi {

    private static final Gson gson = new Gson();
    private static final UsuarioControlador usuarioControlador = new UsuarioControlador();

    public static void iniciar() {
        port(4567);
        configurar();
        rutasUsuarios();

        System.out.println("API corriendo en http://localhost:4567");
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
    }

    private static void rutasUsuarios() {

        get("/clientes", (req, res) -> {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (Usuario u : usuarioControlador.listarClientes()) {
                lista.add(vista(u));
            }
            return gson.toJson(lista);
        });

        get("/clientes/:id", (req, res) -> {
            Usuario u = usuarioControlador.buscarCliente(req.params(":id"));
            if (u == null) {
                return error(res, 404, "Cliente no encontrado");
            }
            return gson.toJson(vista(u));
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
            return gson.toJson(vista(nuevo));
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
            return gson.toJson(vista(actual));
        });

        delete("/clientes/:id", (req, res) -> {
            if (!usuarioControlador.eliminar(req.params(":id"))) {
                return error(res, 404, "Cliente no encontrado");
            }
            return gson.toJson(Map.of("mensaje", "Cliente eliminado"));
        });
    }

    // ---------- Auxiliares  ----------

    private static String error(Response res, int codigo, String mensaje) {
        res.status(codigo);
        return gson.toJson(Map.of("error", mensaje));
    }

    private static Map<String, Object> vista(Usuario u) {
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
}
