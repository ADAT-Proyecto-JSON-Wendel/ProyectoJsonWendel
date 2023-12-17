package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;

/**
 * Clase que parsea un archivo JSON con información sobre provincias y
 * concellos.
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
            // Obtener la lista de provincias desde el objeto JSON principal.
            JsonArray provincias = data.getAsJsonArray("provincias");

            // Iterar sobre las provincias.
            for (int i = 0; i < provincias.size(); i++) {
                // Obtener la provincia actual.
                JsonObject provincia = provincias.get(i).getAsJsonObject();

                // Obtener la lista de concellos (municipios) dentro de la provincia.
                JsonArray concellos = provincia.getAsJsonArray("concellos");

                // Iterar sobre los concellos.
                for (int j = 0; j < concellos.size(); j++) {
                    // Obtener el concello actual.
                    JsonObject concello = concellos.get(j).getAsJsonObject();

                    // Comparar el nombre del concello con el proporcionado.
                    if (nombreConcello.equals(concello.get("nome").getAsString())) {
                        // Si hay coincidencia, devolver el ID del concello.
                        return concello.get("idConcello").getAsInt();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Devolver -1 si no se encuentra el concello.
        return -1;
    }

}
