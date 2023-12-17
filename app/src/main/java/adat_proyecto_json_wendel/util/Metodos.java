package adat_proyecto_json_wendel.util;
import java.util.Map;
import java.util.HashMap;

public class Metodos {
    
    // Método para obtener un mapa de ciudades importantes con sus códigos asociados
    public static Map<String,String> getMapCiudadesPrincipales(){
        // Se crea un nuevo HashMap y se inicializa con valores predefinidos
        Map<String, String> ciudadesImportantes = new HashMap<String, String>() {
            {
                put("A Coruña", "15030");
                put("Ferrol", "15036");
                put("Lugo", "27028");
                put("Ourense", "32054");
                put("Pontevedra", "36038");
                put("Santiago de Compostela", "15078");
                put("Vigo", "36057");
            }
        };
        // Se devuelve el mapa creado
        return ciudadesImportantes;
    }
}
