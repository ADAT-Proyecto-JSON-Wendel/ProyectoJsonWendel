package adat_proyecto_json_wendel;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DescripcionParserTest {

    private DescripcionParser descripcionParser;

    @BeforeEach
    void setUp() {
        String rutaPaqueteGestionJSON = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/dataJson/";
        String rutaDescripcionesPredicciones = rutaPaqueteGestionJSON + "descripciones.json";
        descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);
    }

    @Test
    void obtenerDescripcionNoExistente() {
        String categoria = "ceo";
        String codigoNoExistente = "999";
        String descripcion = descripcionParser.obtenerDescripcion(categoria, codigoNoExistente);
        assertEquals(null, descripcion);
    }

    @Test
    void obtenerDescripcionCategoriaNoExistente() {
        String categoriaNoExistente = "categoria_inexistente";
        String codigo = "1";
        String descripcion = descripcionParser.obtenerDescripcion(categoriaNoExistente, codigo);
        assertEquals(null, descripcion);
    }

    @Test
    void obtenerDescripcionParaCategoriaNull() {
        String codigo = "1";
        String descripcion = descripcionParser.obtenerDescripcion(null, codigo);
        assertEquals("Categoria incorrecta.", descripcion);
    }

    @Test
    void obtenerDescripcionParaCodigoNull() {
        String categoria = "ceo";
        String descripcion = descripcionParser.obtenerDescripcion(categoria, null);
        assertEquals("Codigo incorrecto.", descripcion);
    }
}
