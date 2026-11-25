/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;


import Datos.Persistencia;
import Modelos.Inventario;
import Modelos.Producto;
import Modelos.TasaCambio;
import java.util.List;

/**
 *
 * @author morde
 */
public class ControladorInventario {

    private Inventario inventario;
    private final Persistencia persistencia;

    public ControladorInventario(Persistencia persistencia, TasaCambio tasa) {
    this.persistencia = persistencia;
    this.inventario = new Inventario();
    for (Producto p : persistencia.cargarProductos()) {
        p.setTasaCambio(tasa); 
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
    
    public List<Producto> listarProductos() {
        return inventario.getGeneral();
    }

    public void guardar() {
        persistencia.guardarProductos(inventario.getGeneral());
    }
    public List<Producto> getInventario(){
        return inventario.getGeneral();
    }

}
