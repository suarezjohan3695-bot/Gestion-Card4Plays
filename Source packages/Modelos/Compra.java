/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author morde
 */
public class Compra {

    private String idCompra;
    private LocalDate fechaCompra;
    private List<DetalleCompra> comprados;

    public Compra(String idCompra, LocalDate fechaCompra, List<DetalleCompra> comprados) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.comprados = comprados;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public List<DetalleCompra> getComprados() {
        return comprados;
    }

    public void setComprados(List<DetalleCompra> comprados) {
        this.comprados = comprados;
    }
    
}
