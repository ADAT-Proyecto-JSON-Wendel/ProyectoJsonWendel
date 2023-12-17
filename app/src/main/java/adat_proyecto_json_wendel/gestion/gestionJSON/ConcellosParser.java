package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;

/**
 * Clase que parsea un archivo JSON con información sobre provincias y concellos.
 */
public class ConcellosParser {
    private JsonObject data;

    /**
     * Constructor que carga los datos desde un archivo JSON.
     *
     * @param ruta La ruta al archivo JSON.
     */
    public ConcellosParser(String ruta) {
        cargarDatos(ruta);
    }

    /**
     * Carga los datos desde un archivo JSON utilizando la biblioteca Gson.
     *
     * @param ruta La ruta al archivo JSON.
     */
    private void cargarDatos(String ruta) {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(ruta);
            data = gson.fromJson(fileReader, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un concello por su nombre.
     *
     * @param nombreConcello El nombre del concello.
     * @return El ID del concello o -1 si no se encuentra.
     */
    public int obtenerIdConcelloPorNombre(String nombreConcello) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
