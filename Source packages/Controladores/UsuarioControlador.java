package Controladores;

import Datos.Persistencia;
import Modelos.Administrador;
import Modelos.Cliente;
import Modelos.Rol;
import Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioControlador {

    private final Persistencia persistencia;
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioControlador(Persistencia persistencia) {
        
        this.persistencia = persistencia;
        usuarios.addAll(persistencia.cargarUsuarios());

        boolean hayAdmin = false;
        for (Usuario u : usuarios) {
            if (u.getTipoUsuario() == Rol.ADMINISTRADOR) {
                hayAdmin = true;
            }
        }
        if (!hayAdmin) {
            usuarios.add(new Administrador("ADM-001", "admin", "admin123"));
            guardar();
        }
    }

    private void guardar() {
        persistencia.guardarUsuarios(usuarios);
    }

    

    public boolean datosValidos(String nombre, String contrasena) {
        return nombre != null && !nombre.isBlank()
                && contrasena != null && !contrasena.isBlank();
    }


    public Usuario buscarPorNombre(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                return u;
            }
        }
        return null;
    }


    public Cliente addCliente(String nombre, String contrasena) {
        Cliente c = new Cliente(UUID.randomUUID().toString(), nombre.trim(), contrasena.trim());
        usuarios.add(c);
        guardar();
        return c;
    }


    public List<Usuario> listarClientes() {
        List<Usuario> clientes = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getTipoUsuario() == Rol.CLIENTE) {
                clientes.add(u);
            }
        }
        return clientes;
    }


    public Usuario buscarCliente(String id) {
        for (Usuario u : usuarios) {
            if (u.getTipoUsuario() == Rol.CLIENTE && u.getIdUsuario().equals(id)) {
                return u;
            }
        }
        return null;
    }


    public void actualizar(Usuario u, String nombre, String contrasena) {
        if (nombre != null) {
            u.setNombre(nombre.trim());
        }
        if (contrasena != null) {
            u.setContrasena(contrasena.trim());
        }
        guardar();
    }


    public boolean eliminar(String id) {
        boolean borrado = usuarios.removeIf(u -> u.getTipoUsuario() == Rol.CLIENTE
            && u.getIdUsuario().equals(id));
        if (borrado) {
            guardar();
        }
        return borrado;
    }

    public Usuario autentificar(String nombre, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.autenticar(nombre, contrasena)) {
                return u;
            }
        }
        return null;
    }
}
