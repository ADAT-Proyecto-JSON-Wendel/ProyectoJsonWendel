package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

/**
 * Clase que parsea un archivo JSON con descripciones para diferentes categorías y códigos.
 */
public class DescripcionParser {

    private final Map<String, Map<String, String>> descripciones;

    /**
     * Constructor que carga las descripciones desde un archivo JSON.
     *
     * @param filePath La ruta al archivo JSON.
     */
    public DescripcionParser(String filePath) {
        this.descripciones = cargarDescripciones(filePath);
    }

    /**
     * Carga las descripciones desde un archivo JSON utilizando la biblioteca Gson.
     *
     * @param filePath La ruta al archivo JSON.
     * @return Un mapa que contiene las descripciones por categoría y código.
     */
    private Map<String, Map<String, String>> cargarDescripciones(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene una descripción para una categoría y código específicos.
     *
     * @param categoria La categoría para la que se desea obtener la descripción.
     * @param codigo    El código para el que se desea obtener la descripción.
     * @return La descripción correspondiente o un mensaje predeterminado si no se encuentra.
     */
    public String obtenerDescripcion(String categoria, String codigo) {
        // Obtiene el mapa de descripciones para la categoría dada
        Map<String, String> categoriaMap = descripciones.get(categoria);
        // Verifica si el mapa existe
        if (categoriaMap != null) {
            // Obtiene la descripción correspondiente al código, o devuelve "Dato descoñecido" si no existe
            return categoriaMap.getOrDefault(codigo, "Dato descoñecido");
        }
        // Si la categoría no existe en el mapa, devuelve "Categoria Descoñecida"
        return "Categoria Descoñecida";
    }
}
