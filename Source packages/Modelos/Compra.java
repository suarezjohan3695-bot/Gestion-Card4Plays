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
    private LocalDate fechaCompra;
    private List<Producto> comprados;

    public Compra(String idCompra, LocalDate fechaCompra, List<Producto> comprados) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.comprados = comprados;
    }
}
