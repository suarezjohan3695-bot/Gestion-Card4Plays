/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Datos.Persistencia;
import Modelos.Booster;
import Modelos.Inventario;
import Modelos.Producto;
import Modelos.Rareza;
import Modelos.Single;
import Modelos.TasaCambio;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author morde
 */
public class ControladorInventario {

    private static final Random random = new Random();
    private Inventario inventario;
    private final Persistencia persistencia;

    public ControladorInventario(Persistencia persistencia) {
        this.persistencia = persistencia;
        this.inventario = new Inventario();
        for (Producto p : persistencia.cargarProductos()) {
            inventario.getGeneral().add(p);
        }
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

    public boolean agregarProducto(Producto p) {
        if (buscarProducto(p.getIdProducto()) != null) {
            return false;
        }
        inventario.getGeneral().add(p);
        guardar();
        return true;
    }

    public List<Single> cartas() {
        List<Single> cartas = new ArrayList<>();
        if (inventario.getGeneral().isEmpty()) {
            return null;
        }
        for (Producto p : inventario.getGeneral()) {
            if (p instanceof Single) {
                Single s = (Single) p;
                cartas.add(s);
            }
        }
        return cartas;
    }

    public Rareza rarezaAzar() {
        double n = random.nextDouble();
        double acumulado = 0.0;
        for (Rareza r : Rareza.values()) {
            acumulado += r.getProbabilidad();
            if (n < acumulado) {
                return r;
            }
        }
        return Rareza.ULTRARARO;

    }

    private List<Single> deRareza(List<Single> existentes, Rareza rareza) {
        List<Single> resultado = new ArrayList<>();
        for (Single s : existentes) {
            if (s.getRareza() == rareza) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public Single alAzar(List<Single> existentes) {
        Rareza[] rarezas = Rareza.values();
        int indiceRareza = rarezaAzar().ordinal();

        for (int i = indiceRareza; i >= 0; i--) {
            List<Single> candidatas = deRareza(existentes, rarezas[i]);
            if (!candidatas.isEmpty()) {
                return candidatas.get(random.nextInt(candidatas.size()));
            }
        }
        for (int i = indiceRareza + 1; i < rarezas.length; i++) {
            List<Single> candidatas = deRareza(existentes, rarezas[i]);
            if (!candidatas.isEmpty()) {
                return candidatas.get(random.nextInt(candidatas.size()));
            }
        }
        return null;
    }

    public List<Single> contenidoBooster() {
        List<Single> existencias = cartas();
        if (existencias.isEmpty()) {
            System.out.println("No hay existencias de cartas");
            return null;
        }
        List<Single> contenidoBooster = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Single s = alAzar(existencias);
            contenidoBooster.add(s);
        }
        return contenidoBooster();
    }

    public Booster crearBooster(Booster booster) {
        
        return null;
    }

    public List<Producto> listarProductos() {
        return inventario.getGeneral();
    }

    public void guardar() {
        persistencia.guardarProductos(inventario.getGeneral());
    }

    public List<Producto> getInventario() {
        return inventario.getGeneral();
    }

}
