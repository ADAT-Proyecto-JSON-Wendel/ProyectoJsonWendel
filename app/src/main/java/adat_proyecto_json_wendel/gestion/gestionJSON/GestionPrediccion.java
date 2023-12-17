package adat_proyecto_json_wendel.gestion.gestionJSON;

import com.google.gson.Gson;
import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.PrediccionWrapper;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.Vento;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

// Clase que gestiona la obtención y visualización de predicciones meteorológicas.
public class GestionPrediccion {

    private final DescripcionParser descripcionParser;

    /**
     * Constructor que recibe un objeto DescripcionParser o crea uno nuevo si es nulo.
     *
     * @param descripcionParser El objeto DescripcionParser para obtener descripciones.
     */
    public GestionPrediccion(DescripcionParser descripcionParser) {
        if (descripcionParser != null && descripcionParser instanceof DescripcionParser) {
            this.descripcionParser = descripcionParser;
        } else {
            String rutaDescripcionesPredicciones = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/descripciones.json";
            this.descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);
        }
    }

     /**
     * Obtiene la predicción de un concello a partir de una URL.
     *
     * @param url La URL que proporciona la información de la predicción.
     * @return Un objeto PrediccionConcello con la información de la predicción o null si hay un error.
     */
    public PrediccionConcello obtenerPrediccion(String url) {
        try {
            // Verificar si la URL es válida
            if (!isValidURL(url)) {
                System.err.println("URL no válida.");
                return null;
            }
            try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
                Gson gson = new Gson();
                PrediccionWrapper wrapper = gson.fromJson(reader, PrediccionWrapper.class);
                return wrapper.getPredConcello();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si una URL es válida.
     *
     * @param url La URL a verificar.
     * @return true si la URL es válida, false en caso contrario.
     */
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | java.net.URISyntaxException e) {
            return false;
        }
    }

    /**
     * Muestra en la consola los datos de la predicción para un concello.
     *
     * @param prediccion La predicción del concello a mostrar.
     */
    public void mostrarDatosPrediccion(PrediccionConcello prediccion) {
        try {
            System.out.println("ID do Concello: " + prediccion.getIdConcello());
            System.out.println("Nome do Concello: " + prediccion.getNome());

            List<DiaPrediccion> listaPredicciones = prediccion.getListaPredDiaConcello();
            for (DiaPrediccion dia : listaPredicciones) {
                System.out.println("Data Predicion: " + dia.getDataPredicion());
                System.out.println("Nivel de Aviso: " + dia.getNivelAviso());

                // Mostrar datos Cielo
                Cielo cielo = dia.getCeo();
                System.out.println("Estado do Ceo pola manha: " + cielo.getManha());
                System.out.println("Estado do Ceo pola tarde: " + cielo.getTarde());
                System.out.println("Estado do Ceo pola noite: " + cielo.getNoite());

                // Mostrar probabilidad de choiva
                ProbabilidadChoiva pchoiva = dia.getPchoiva();
                System.out.println("Probabilidade de Choiva (manha): " + pchoiva.getManha() + "%");
                System.out.println("Probabilidade de Choiva (tarde): " + pchoiva.getTarde() + "%");
                System.out.println("Probabilidade de Choiva (noite): " + pchoiva.getNoite() + "%");

                // Mostrar temperatura máxima y mínima
                System.out.println("Temperatura Maxima: " + dia.gettMax() + "°C");
                System.out.println("Temperatura Minima: " + dia.gettMin() + "°C");

                // Mostrar índice ultravioleta máximo
                System.out.println("Indice Ultravioleta Maximo: " + dia.getUvMax());

                // Mostrar información del viento
                Vento vento = dia.getVento();
                System.out.println("Vento (manha): " + obtenerDescripcionCeo(vento.getManha()));
                System.out.println("Vento (tarde): " + obtenerDescripcionVento(vento.getTarde()));
                System.out.println("Vento (noite): " + obtenerDescripcionVento(vento.getNoite()));

                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la descripción asociada al código de estado del cielo
     *
     * @param codigo Código de estado del cielo.
     * @return Descripción correspondiente o "Dato descoñecido" si no se encuentra.
     */
    private String obtenerDescripcionCeo(int codigo) {
        return descripcionParser.obtenerDescripcion("ceo", String.valueOf(codigo));
    }

    /**
     * Obtiene la descripción asociada al código de estado del viento
     *
     * @param codigo Código de estado del viento.
     * @return Descripción correspondiente o "Dato descoñecido" si no se encuentra.
     */
    private String obtenerDescripcionVento(int codigo) {
        return descripcionParser.obtenerDescripcion("vento", String.valueOf(codigo));
    }

}
