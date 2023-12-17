package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class DescripcionParser {

    private final Map<String, Map<String, String>> descripciones;

    public DescripcionParser(String filePath) {
        this.descripciones = cargarDescripciones(filePath);
    }

    private Map<String, Map<String, String>> cargarDescripciones(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String obtenerDescripcion(String categoria, String codigo) {
        Map<String, String> categoriaMap = descripciones.get(categoria);
        if (categoriaMap != null) {
            return categoriaMap.getOrDefault(codigo, "Dato descoñecido");
        }
        return "Categoria Descoñecida";
    }
}
