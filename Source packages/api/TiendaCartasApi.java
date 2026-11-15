/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static spark.Spark.*;

      

/**
 *
 * @author morde
 */
public class TiendaCartasApi {
    private static final Gson gson = new Gson();
    
    public static void iniciar(){
        port(4800);
        configurar();
        
        
    }

    private static void configurar() {
        before((req,res) -> res.type("application/json"));
        
    }
    
}
