package Modelos;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    private List<Compra> HistorialCompra;
    private Inventario pertenencias;

    public Cliente(String id, String nombre, String contrasena) {
        super(id, nombre, contrasena, Rol.CLIENTE);
        this.HistorialCompra = new ArrayList<>();
        this.pertenencias = new Inventario();
    }

    public boolean inscribirseTorneo(Torneo torneo) {
        if (torneo == null) {
            return false;
        }
        return torneo.agregarParticipante(this);
    }

    public List<Compra> getHistorial() {
        return HistorialCompra;
    }

    public Inventario getPertenencias() {
        return pertenencias;
    }

}
