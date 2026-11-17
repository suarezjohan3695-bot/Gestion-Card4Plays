package Modelos;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    //Cambiar cuando exista la clase compra
    private List<String> idCompra = new ArrayList<>();

    public Cliente(String id, String nombre, String contrasena) {
        super(id, nombre, contrasena, Rol.CLIENTE);
    }

    public boolean inscribirseTorneo(Torneo torneo) {
        if (torneo == null) {
            return false;
        }
        return torneo.agregarParticipante(this);
    }

    public List<String> getIdCompra() {
        return idCompra;
    }
}
