/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Compra;
import Modelos.Producto;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorCompra {
    
    private Compra compra;
    
    public ControladorCompra(String idCompra, LocalDate fechaCompra, List<Producto> comprados){
        this.compra = new Compra(idCompra, fechaCompra, comprados);
    }
}
