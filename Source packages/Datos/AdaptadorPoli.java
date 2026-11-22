/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 *
 * @author Karen
 */
public class AdaptadorPoli<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private final String paquete;

    public AdaptadorPoli(String paquete) {
        this.paquete = paquete;
    }

    @Override
    public JsonElement serialize(T src, Type tipo, JsonSerializationContext ctx) {
        JsonObject obj = ctx.serialize(src, src.getClass()).getAsJsonObject();
        obj.addProperty("tipo", src.getClass().getSimpleName());
        return obj;
    }

    @Override
    public T deserialize(JsonElement json, Type tipo, JsonDeserializationContext ctx) {
        String nombre = json.getAsJsonObject().get("tipo").getAsString();
        try {
            return ctx.deserialize(json, Class.forName(paquete + "." + nombre));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Tipo desconocido: " + nombre, e);
        }
    }
}
