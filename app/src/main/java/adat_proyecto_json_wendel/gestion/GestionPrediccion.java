package adat_proyecto_json_wendel.gestion;

import com.google.gson.Gson;

import adat_proyecto_json_wendel.model.PrediccionConcello;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GestionPrediccion {

    public PrediccionConcello obtenerPrediccion(String url) {
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, PrediccionConcello.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción apropiadamente según tus necesidades
            return null;
        }
    }
}