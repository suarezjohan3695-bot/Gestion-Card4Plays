/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Compra;
import Modelos.DetalleCompra;
import Modelos.Producto;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorCompra {

    private Compra compra;

    public ControladorCompra(String idCompra, LocalDate fechaCompra, List<DetalleCompra> comprados) {
        this.compra = new Compra(idCompra, fechaCompra, comprados);
    }

    public double calcularTotal() {
        if (compra.getComprados().isEmpty() || compra.getComprados() == null) {
            return 0;
        }
        double total = 0;
        for (DetalleCompra dc : compra.getComprados()) {
            total += dc.calcularSubtotal();
        }
        return total;

    }
}
