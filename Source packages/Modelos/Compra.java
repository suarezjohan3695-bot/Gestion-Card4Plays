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
    private String idCliente;
    private LocalDate fechaCompra;
    private List<DetalleCompra> comprados;

    public Compra(String idCompra,String idCliente, List<DetalleCompra> comprados) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.fechaCompra = LocalDate.now();
        this.comprados = comprados;
    }
    
    public String getIdCliente() {        
        return idCliente;
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
