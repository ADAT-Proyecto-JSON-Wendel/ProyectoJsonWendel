package adat_proyecto_json_wendel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.gestion.gestionJSON.GestionPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;

class GestionPrediccionTest {

    private GestionPrediccion gestionPrediccion;

    @BeforeEach
    void setUp() {
        String rutaDescripciones = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/dataJson/descripciones.json";
        DescripcionParser descripcionParser = new DescripcionParser(rutaDescripciones);
        gestionPrediccion = new GestionPrediccion(descripcionParser);
    }

    @Test
    void obtenerPrediccionConUrlInvalidaDeberiaRetornarNull() {
        // URL inválida a propósito
        String urlInvalida = "https://ejemplo.com/prediccionInvalida";
        PrediccionConcello prediccion = gestionPrediccion.obtenerPrediccion(urlInvalida);
        assertNull(prediccion);
    }


}
