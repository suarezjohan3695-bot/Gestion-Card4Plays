/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author morde
 */
public class Producto {

    private String idProducto;
    private String nombre;
    private double precioUSD;
    private double precioCOP;
    private TasaCambio tasaCambio;

    public Producto(String idProducto, String nombre, double precioUSD, TasaCambio tasaCambio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioUSD = precioUSD;
        this.precioCOP = 0; // Se le dara valor cuando se integre api externa por un medio de un metodo
        this.tasaCambio = tasaCambio;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUSD() {
        return precioUSD;
    }

    public void setPrecioUSD(double precioUSD) {
        this.precioUSD = precioUSD;
    }

    public double getPrecioCOP() {
        return precioCOP;
    }

    public void setPrecioCOP(double precioCOP) {
        this.precioCOP = precioCOP;
    }

    public TasaCambio getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(TasaCambio tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

}
