/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Datos.Persistencia;
import java.util.ArrayList;
import Modelos.Cliente;
import Modelos.Compra;
import Modelos.DetalleCompra;
import Modelos.Producto;
import Modelos.Usuario;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorCompra {

    private final Persistencia persistencia;
    private final List<Compra> compras;

    public ControladorCompra(Persistencia persistencia) {
    this.persistencia = persistencia;
    this.compras = new ArrayList<>(persistencia.cargarCompras());
}

    public Compra buscarCompraId(String id) {
        for (Compra c : compras) {
            if (c.getIdCompra().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public boolean crearCompra(Compra compra) {
        if (buscarCompraId(compra.getIdCompra()) != null) {
            return false;
        }
        compras.add(compra);
        persistencia.guardarCompras(compras);
        return true;
    }

    public double calcularTotal(Compra compra) {
        if (compra == null || compra.getComprados() == null || compra.getComprados().isEmpty()) {
            return 0;
        }
        double total = 0;
        for (DetalleCompra dc : compra.getComprados()) {
            total += dc.calcularSubtotal();
        }
        return total;

    }
    
    public List<Compra> historialDeCliente(String idCliente) {
        List<Compra> historial = new ArrayList<>();
        for (Compra c : compras) {
            if (c.getIdCliente().equals(idCliente)) {
                historial.add(c);
            }
        }
        return historial;
    }
}
