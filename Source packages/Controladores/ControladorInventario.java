/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Inventario;
import Modelos.Producto;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorInventario {

    private Inventario inventario;

    public ControladorInventario() {
        this.inventario = new Inventario();
    }

    public Producto buscarProducto(String id) {
        List<Producto> productos = inventario.getGeneral();

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
        
        List<Producto> productos = inventario.getGeneral();

        Producto aux = buscarProducto(p.getIdProducto());
        if (aux == null) {
            productos.add(p);
        } else {
            System.out.println("Ya existe");

        }
    }

    public String ListarProductos() {
        StringBuilder sb = new StringBuilder();
        List<Producto> prod = inventario.getGeneral();
        sb.append("------Productos------");
        for (Producto p : prod) {
            sb.append("\n");
            sb.append("ID producto: ").append(p.getIdProducto());
            sb.append("Nombre: ").append(p.getNombre());
            sb.append("Precio USD: ").append(p.getPrecioUSD());

        }
        sb.append("----------------");
        return sb.toString();

    }

}
