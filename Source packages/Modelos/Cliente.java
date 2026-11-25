package Modelos;


public class Cliente extends Usuario {

    private Inventario pertenencias;

    public Cliente(String id, String nombre, String contrasena) {
        super(id, nombre, contrasena, Rol.CLIENTE);
        this.pertenencias = new Inventario();
    }

    public boolean inscribirseTorneo(Torneo torneo) {
        if (torneo == null) {
            return false;
        }
        return torneo.agregarParticipante(this);
    }

    public Inventario getPertenencias() {
        return pertenencias;
    }

}
