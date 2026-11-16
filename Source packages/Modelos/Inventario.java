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
public class Inventario {
    
    private List<Producto> general;
    public Inventario(){
        this.general = new ArrayList<>();
    }

    public List<Producto> getGeneral() {
        return general;
    }
    
}
