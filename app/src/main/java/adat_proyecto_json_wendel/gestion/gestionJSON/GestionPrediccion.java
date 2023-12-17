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
import java.net.URL;
import java.util.List;

public class GestionPrediccion {

    private final DescripcionParser descripcionParser;

    public GestionPrediccion(DescripcionParser descripcionParser) {
        if(descripcionParser != null && descripcionParser instanceof DescripcionParser){
            this.descripcionParser = descripcionParser;
        }else{
            String rutaDescripcionesPredicciones = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/descripciones.json";
            this.descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);
        }
    }

    public PrediccionConcello obtenerPrediccion(String url) {
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            Gson gson = new Gson();
            PrediccionWrapper wrapper = gson.fromJson(reader, PrediccionWrapper.class);
            return wrapper.getPredConcello();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void mostrarDatosPrediccion(PrediccionConcello prediccion) {
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

            System.out.println("-----"); // Separador entre cada día
        }

    }

    private String obtenerDescripcionCeo(int codigo) {
        return descripcionParser.obtenerDescripcion("ceo", String.valueOf(codigo));
    }

    private String obtenerDescripcionVento(int codigo) {
        return descripcionParser.obtenerDescripcion("vento", String.valueOf(codigo));
    }

}