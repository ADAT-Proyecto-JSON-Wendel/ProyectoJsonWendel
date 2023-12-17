package adat_proyecto_json_wendel.gestion.gestionCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


import com.opencsv.CSVWriter;

import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;

public class CSVWriterExample {
    

     public static List<String[]> obtenerDatosConcello(PrediccionConcello prediccion) {
        List<String[]> datos = new ArrayList<String[]>();

        // Agrega encabezados al principio
        datos.add(new String[]{"Fecha", "EstadoCeoManha", "EstadoCeoTarde", "EstadoCeoNoite", "NivelAviso",
                "ProbChoivaManha", "ProbChoivaTarde", "ProbChoivaNoite", "VentoManha", "VentoTarde", "VentoNoite",
                "TMax", "TMin", "UVMax"});

        // Agrega datos de prediccion
        for (DiaPrediccion dia : prediccion.getListaPredDiaConcello()) {
            String[] fila = new String[]{
                    dia.getDataPredicion(),
                    String.valueOf(dia.getCeo().getManha()),
                    String.valueOf(dia.getCeo().getTarde()),
                    String.valueOf(dia.getCeo().getNoite()),
                    String.valueOf(dia.getNivelAviso()),
                    String.valueOf(dia.getPchoiva().getManha()),
                    String.valueOf(dia.getPchoiva().getTarde()),
                    String.valueOf(dia.getPchoiva().getNoite()),
                    String.valueOf(dia.getVento().getManha()),
                    String.valueOf(dia.getVento().getTarde()),
                    String.valueOf(dia.getVento().getNoite()),
                    String.valueOf(dia.gettMax()),
                    String.valueOf(dia.gettMin()),
                    String.valueOf(dia.getUvMax())
            };
            datos.add(fila);
        }

        return datos;
    }

    public static List<String[]> getDatosPrediccionesEnCSV(List<PrediccionConcello> predicciones, DescripcionParser descripcionParser) {
        List<String[]> datos = new ArrayList<>();
    
        // Escribir datos de cada predicción
        for (PrediccionConcello prediccion : predicciones) {
            String concello = prediccion.getNome();
    
            for (DiaPrediccion dia : prediccion.getListaPredDiaConcello()) {
                String fecha = dia.getDataPredicion();
                String tMax = String.valueOf(dia.gettMax());
                String tMin = String.valueOf(dia.gettMin());
                String uvMax = String.valueOf(dia.getUvMax());
    
                // Determinar si es de día o de noche
                boolean esDeDia = !fecha.contains("T21:00:00") && !fecha.contains("T00:00:00");
    
                String estadoCeoManana = esDeDia ?
                        descripcionParser.obtenerDescripcion("ceo", String.valueOf(dia.getCeo().getManha())) :
                        descripcionParser.obtenerDescripcion("ceo", String.valueOf(dia.getCeo().getNoite()));
    
                String direccionVientoManana = esDeDia ?
                        descripcionParser.obtenerDescripcion("vento", String.valueOf(dia.getVento().getManha())) :
                        descripcionParser.obtenerDescripcion("vento", String.valueOf(dia.getVento().getNoite()));
    
                String intensidadVientoManana = descripcionParser.obtenerDescripcion("vento", dia.getVento().getManha().toString());
    
                String[] datosDia = {concello, fecha, tMax, tMin, uvMax, estadoCeoManana, direccionVientoManana, intensidadVientoManana};
                datos.add(datosDia);
            }
        }
    
        return datos;
    }
    
    public static void crearCSV(String nombreArchivo, List<String[]> datos){

        String rutaCompleta = "app\\src\\main\\java\\adat_proyecto_json_wendel\\outputCSV\\" + nombreArchivo;

        // Escribir en el archivo CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaCompleta))) {
            // Escribir las líneas en el archivo
            writer.writeAll(datos);

            System.out.println("Datos escritos correctamente en el archivo: " + rutaCompleta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
