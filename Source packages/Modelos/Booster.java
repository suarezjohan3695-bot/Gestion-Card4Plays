/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author morde
 */
public class Booster extends Producto {

    private List<Single> contenido;

    public Booster(List<Single> contenido, String idProducto, String nombre,
            double precioUSD){
        super(idProducto, nombre, precioUSD);
        this.contenido = contenido;
    }

    public List<Single> getContenido() {
        return contenido;
    }

}
