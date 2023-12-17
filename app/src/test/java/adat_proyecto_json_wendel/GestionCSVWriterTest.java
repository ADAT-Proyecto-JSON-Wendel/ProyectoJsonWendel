package adat_proyecto_json_wendel;

import org.junit.jupiter.api.Test;

import adat_proyecto_json_wendel.gestion.gestionCSV.GestionCSVWriter;
import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.model.PrediccionConcello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class GestionCSVWriterTest {

    @Test
    void testObtenerDatosConcello() {
        PrediccionConcello prediccionConcello = new PrediccionConcello();

        List<String[]> datos = GestionCSVWriter.obtenerDatosConcello(prediccionConcello);

        assertNotNull(datos);
        assertFalse(datos.isEmpty());
    }

    @Test
    void testGetDatosPrediccionesEnCSV() {
        List<PrediccionConcello> predicciones = new ArrayList<>();

        String rutaPaqueteGestionJSON = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/dataJson/";
        String rutaDescripcionesPredicciones = rutaPaqueteGestionJSON + "descripciones.json";

        DescripcionParser descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);

        List<String[]> datos = GestionCSVWriter.getDatosPrediccionesEnCSV(predicciones, descripcionParser);

        assertNotNull(datos);
        assertFalse(datos.isEmpty());
    }

}
