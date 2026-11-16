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
