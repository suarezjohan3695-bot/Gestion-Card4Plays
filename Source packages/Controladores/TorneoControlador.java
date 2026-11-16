package Controladores;

import Modelos.Cliente;
import Modelos.Torneo;
import org.eclipse.jetty.io.ssl.ALPNProcessor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TorneoControlador {
    private List<Torneo> listaTorneos;

    public TorneoControlador() {
        this.listaTorneos = new ArrayList<>();
    }

    public void crearTorneo(String idTorneo, String nombre, LocalDate fecha, int maxParticipantes){
        Torneo nuevoTorneo = new Torneo(idTorneo, nombre, fecha, maxParticipantes);
        listaTorneos.add(nuevoTorneo);
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
            return true;
        }
        return false;
    }


    public boolean inscribirClienteTorneo(String idTorneo, Cliente cliente){
        Torneo torneo = buscarTorneo(idTorneo);

        if (torneo != null){
            if (torneo.isActivo()){
                return torneo.agregarParticipante(cliente);
            }
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
