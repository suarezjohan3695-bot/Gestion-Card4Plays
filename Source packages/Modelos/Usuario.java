package Modelos;

public abstract class Usuario {
    private String idUsuario;
    private String nombre;
    private String contrasena;
    private Rol tipoUsuario;

    protected Usuario(String idUsuario, String nombre,String contrasena, Rol tipoUsuario){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    public boolean auntentificar(String nombre, String contrasena){
        return this.nombre.equals(nombre) && this.contrasena.equals(contrasena);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getTipoUsuario() {
        return tipoUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
