/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author morde
 */
public class DetalleCompra {

    private Producto producto;
    private int cantidad;

    public DetalleCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;

    }

    public double calcularSubtotal() {
        return producto.getPrecioCOP() * cantidad;
    }

}
