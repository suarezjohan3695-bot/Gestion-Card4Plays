/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

/**
 *
 * @author morde
 */
public class TasaCambio {

    private static double TASA_DEFECTO = leerTasaDefecto();
    private double cop;

    public TasaCambio() throws Exception {
        this.cop = extraerCOP();
    }

    public static double leerTasaDefecto() {
        String ENV = System.getenv("TASA_DEFECTO");
        if (ENV == null || ENV.isBlank()) {
            return 3500;
        }
        try {
            return Double.parseDouble(ENV);
        } catch (NumberFormatException e) {
            System.out.println("TASA_DEFECTO invalida: " + ENV + ". Se usa 3900.0");
            return 3500;
        }
    }

    public static String TasaCambioAPI() throws Exception {

        String url = "https://v6.exchangerate-api.com/v6/3f8f6e86eb1bc0ec14f3f8f0/latest/USD";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(3)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        return body;

    }

    public static double extraerCOP() throws Exception {

        
        try {
            String json = TasaCambioAPI();
            int inicio = json.indexOf("\"COP\":") + 6;
            int fin = json.indexOf(",", inicio);
            String COP = json.substring(inicio, fin);
            System.out.println("API funciono correctamente");
            return Double.parseDouble(COP);
        } catch (Exception e) {
            System.out.println("La API no respondio: se obtiene tasa por defecto: " + TASA_DEFECTO);
            return TASA_DEFECTO;
        }
    }

    public double getCop() {
        return this.cop;

    }
}
