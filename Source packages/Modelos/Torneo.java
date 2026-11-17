package Modelos;

import java.time.LocalDate;

public class Torneo {
    private String idTorneo;
    private String nombre;
    private LocalDate fecha;
    private int maxParticipantes;
    private Cliente[] participantes;
    private int contadorParticipantes;

    public Torneo(String idTorneo, String nombre, LocalDate fecha, int maxParticipantes) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.maxParticipantes = maxParticipantes;
        this.participantes = new Cliente[maxParticipantes];
        this.contadorParticipantes = 0;
    }

    public boolean isActivo(){
        LocalDate hoy = LocalDate.now();
        return hoy.isBefore(fecha) || hoy.isEqual(fecha);
    }

    public boolean agregarParticipante(Cliente cliente) {
        for (int i = 0; i < contadorParticipantes; i++) {
            if (participantes[i].getIdUsuario().equals(cliente.getIdUsuario())) {
                return false;
            }
        }

        if (contadorParticipantes < maxParticipantes) {
            participantes[contadorParticipantes] = cliente;
            contadorParticipantes++;
            return true;
        }
        return false;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public Cliente[] getParticipantes() {
        return participantes;
    }
}
