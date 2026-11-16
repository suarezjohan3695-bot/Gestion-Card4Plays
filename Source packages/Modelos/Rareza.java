/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author morde
 */
public enum Rareza {
    NORMAL(0.5),
    POCOCOMUN(0.25),
    RARO(0.15),
    SUPERRARO(0.07),
    ULTRARARO(0.03);

    private final double probabilidad;

    Rareza(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

}
