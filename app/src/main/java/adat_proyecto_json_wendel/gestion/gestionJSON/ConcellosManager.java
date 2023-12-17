package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;

public class ConcellosManager {
    private JsonObject data;

    public ConcellosManager(String ruta) {
        cargarDatos(ruta);
    }

    private void cargarDatos(String ruta) {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(ruta);
            data = gson.fromJson(fileReader, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int obtenerIdConcelloPorNombre(String nombreConcello) {
        JsonArray provincias = data.getAsJsonArray("provincias");

        for (int i = 0; i < provincias.size(); i++) {
            JsonObject provincia = provincias.get(i).getAsJsonObject();
            JsonArray concellos = provincia.getAsJsonArray("concellos");

            for (int j = 0; j < concellos.size(); j++) {
                JsonObject concello = concellos.get(j).getAsJsonObject();
                if (nombreConcello.equals(concello.get("nome").getAsString())) {
                    return concello.get("idConcello").getAsInt();
                }
            }
        }

        return -1;
    }

}
