/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author morde
 */
public class Single extends Producto {

    private Rareza rareza;

    public Single(String idCompra, String nombre, double precioUSD,
            Rareza rareza) throws Exception {
        super(idCompra, nombre, precioUSD);
        this.rareza = rareza;
    }

}
