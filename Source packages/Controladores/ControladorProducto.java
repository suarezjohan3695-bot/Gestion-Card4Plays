/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorProducto {

    private List<Producto> productos;

    public ControladorProducto() {
        this.productos = new ArrayList<>();
    }

    public Producto buscarProducto(String id) {

        if (productos.isEmpty()) {
            return null;
        }
        for (Producto p : productos) {
            if (p.getIdProducto().equals(id)) {
                return p;
            }

        }
        return null;
    }

    public void agregarProducto(Producto p) {
        Producto aux = buscarProducto(p.getIdProducto());
        if (aux == null) {
            productos.add(p);
        } else {
            System.out.println("Ya existe");

        }
    }

}
