/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;
import Modelos.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Karen
 */
public class Persistencia {
    private final Path carpeta;
    private final Path archivoUsuarios;
    private final Path archivoTorneos;
    private final Gson gson;

    public Persistencia() {
        String dir = System.getenv("DATA_DIR");
        if (dir == null || dir.isBlank()) {
            dir = "data";
        }
        carpeta = Paths.get(dir).toAbsolutePath();
        archivoUsuarios = carpeta.resolve("usuarios.json");
        archivoTorneos = carpeta.resolve("torneos.json");

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (f, t, c) -> new JsonPrimitive(f.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (j, t, c) -> LocalDate.parse(j.getAsString()))
                .registerTypeAdapter(Usuario.class, new AdaptadorPoli<Usuario>("Modelos"))
                .create();
    }

    // ---------- Usuarios ----------
    public List<Usuario> cargarUsuarios() {
        return leer(archivoUsuarios, new TypeToken<List<Usuario>>() {}.getType());
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        escribir(archivoUsuarios, usuarios, new TypeToken<List<Usuario>>() {}.getType());
    }

    // ---------- Torneos ----------
    public List<Torneo> cargarTorneos() {
        return leer(archivoTorneos, new TypeToken<List<Torneo>>() {}.getType());
    }

    public void guardarTorneos(List<Torneo> torneos) {
        escribir(archivoTorneos, torneos, new TypeToken<List<Torneo>>() {}.getType());
    }

    // ---------- Utilidades ----------
    private <T> List<T> leer(Path archivo, Type tipoLista) {
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(archivo, StandardCharsets.UTF_8);
            if (json.isBlank()) {
                return new ArrayList<>();
            }
            List<T> lista = gson.fromJson(json, tipoLista);
            return lista == null ? new ArrayList<>() : lista;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer " + archivo, e);
        }
    }

    private void escribir(Path archivo, Object datos, Type tipo) {
        try {
            Files.createDirectories(carpeta);
            Files.writeString(archivo, gson.toJson(datos, tipo), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo escribir " + archivo, e);
        }
    }
}
