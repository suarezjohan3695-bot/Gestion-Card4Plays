package Controladores;

import Datos.Persistencia;
import Modelos.Cliente;
import Modelos.Torneo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TorneoControlador {
    private final Persistencia persistencia;
    private List<Torneo> listaTorneos;

    public TorneoControlador(Persistencia persistencia) {
    this.persistencia = persistencia;
    this.listaTorneos = new ArrayList<>(persistencia.cargarTorneos());
}

    public void crearTorneo(String idTorneo, String nombre, LocalDate fecha, int maxParticipantes) {
    listaTorneos.add(new Torneo(idTorneo, nombre, fecha, maxParticipantes));
    persistencia.guardarTorneos(listaTorneos);
}

    public Torneo buscarTorneo(String idTorneo){
        for (Torneo torneo : listaTorneos){
            if (torneo.getIdTorneo().equalsIgnoreCase(idTorneo)){
                return torneo;
            }
        }
        return null;
    }

    public boolean eliminarTorneo(String idTorneo) {
    Torneo torneo = buscarTorneo(idTorneo);
    if (torneo != null) {
        listaTorneos.remove(torneo);
        persistencia.guardarTorneos(listaTorneos);
        return true;
    }
    return false;
}


    public boolean inscribirClienteTorneo(String idTorneo, Cliente cliente) {
    Torneo torneo = buscarTorneo(idTorneo);
    if (torneo != null && torneo.isActivo()) {
        boolean ok = torneo.agregarParticipante(cliente);
        if (ok) {
            persistencia.guardarTorneos(listaTorneos);
        }
        return ok;
    }
    return false;
    }
    
    public List<Torneo> listarTorneos() {
        return this.listaTorneos;
    }

    public List<Torneo> getListaTorneos() {
        return listaTorneos;
    }


}
