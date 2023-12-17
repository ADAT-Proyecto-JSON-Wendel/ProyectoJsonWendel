package adat_proyecto_json_wendel.gestion.gestionCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVWriter;

import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;

public class GestionCSVWriter {

    /**
     * Obtiene los datos de predicción de un concello y los organiza en una lista de
     * arrays de cadenas.
     *
     * @param prediccion La predicción del concello de la que se obtendrán los
     *                   datos.
     * @return Una lista de arrays de cadenas que contiene los datos organizados,
     *         incluyendo encabezados.
     */
    public static List<String[]> obtenerDatosConcello(PrediccionConcello prediccion) {
        List<String[]> datos = new ArrayList<String[]>();

        // Agrega encabezados al principio
        datos.add(new String[] { "Fecha", "EstadoCeoManha", "EstadoCeoTarde", "EstadoCeoNoite", "NivelAviso",
                "ProbChoivaManha", "ProbChoivaTarde", "ProbChoivaNoite", "VentoManha", "VentoTarde", "VentoNoite",
                "TMax", "TMin", "UVMax" });

        // Agrega datos de predicción
        try {
            // Agrega datos de prediccion
            for (DiaPrediccion dia : prediccion.getListaPredDiaConcello()) {
                String[] fila = new String[] {
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
        } catch (Exception e) {
            // Manejar la excepción aquí, ya sea registrándola o tomando alguna acción
            // específica.
            e.printStackTrace(); // Esto imprimirá el seguimiento de la pila, pero podrías manejarlo de otra
                                 // manera.
        }

        return datos;
    }

    /**
     * Obtiene los datos de predicciones de varios concellos en formato CSV.
     *
     * @param predicciones      Lista de predicciones de concellos.
     * @param descripcionParser Objeto utilizado para obtener descripciones según
     *                          códigos de ceo y viento.
     * @return Una lista de arrays de cadenas en formato CSV que contiene los datos
     *         de predicciones.
     */
    public static List<String[]> getDatosPrediccionesEnCSV(List<PrediccionConcello> predicciones,
            DescripcionParser descripcionParser) {
        List<String[]> datos = new ArrayList<>();

        // Agregar cabecera
        datos.add(new String[] { "concello", "fecha", "tMax", "tMin", "uvMax", "estadoCeoManana",
                "direccionVientoManana", "intensidadVientoManana" });

        // Escribir datos de cada predicción
        try {
            for (PrediccionConcello prediccion : predicciones) {
                String concello = prediccion.getNome();

                for (DiaPrediccion dia : prediccion.getListaPredDiaConcello()) {
                    String fecha = dia.getDataPredicion();
                    String tMax = String.valueOf(dia.gettMax());
                    String tMin = String.valueOf(dia.gettMin());
                    String uvMax = String.valueOf(dia.getUvMax());

                    // Determinar si es de dia o noche
                    Boolean esDeDia = esDeDia(fecha);

                    // Obtener descripción del estado del cielo y dirección/intensidad del viento

                    String estadoCeoManana = esDeDia
                            ? descripcionParser.obtenerDescripcion("ceo", String.valueOf(dia.getCeo().getManha()))
                            : descripcionParser.obtenerDescripcion("ceo", String.valueOf(dia.getCeo().getNoite()));

                    String direccionVientoManana = esDeDia
                            ? descripcionParser.obtenerDescripcion("vento", String.valueOf(dia.getVento().getManha()))
                            : descripcionParser.obtenerDescripcion("vento", String.valueOf(dia.getVento().getNoite()));

                    String intensidadVientoManana = descripcionParser.obtenerDescripcion("vento",
                            dia.getVento().getManha().toString());

                    // Crear array con los datos del día y agregarlo a la lista de datos

                    String[] datosDia = { concello, fecha, tMax, tMin, uvMax, estadoCeoManana, direccionVientoManana,
                            intensidadVientoManana };
                    datos.add(datosDia);
                }
            }
        } catch (Exception e) {
            // Manejar la excepción aquí, ya sea registrándola o tomando alguna acción
            // específica.
            e.printStackTrace(); // Esto imprimirá el seguimiento de la pila, pero podrías manejarlo de otra
                                 // manera.
        }

        return datos;
    }

    /**
     * Crea un archivo CSV con los datos proporcionados.
     *
     * @param nombreArchivo Nombre del archivo CSV a crear.
     * @param datos         Lista de arrays de cadenas que contiene los datos a
     *                      escribir en el archivo.
     */
    public static void crearCSV(String nombreArchivo, List<String[]> datos) {

        // Ruta completa del archivo CSV
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

    /**
     * Determina si la hora proporcionada corresponde al período diurno.
     *
     * @param fecha La fecha y hora en formato "yyyy-MM-dd'T'HH:mm:ss".
     * @return {@code true} si es de día, {@code false} si es de noche.
     */
    public static boolean esDeDia(String fecha) {
        // Formato de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Obtener la hora de la fecha proporcionada
        LocalDateTime dateTime = LocalDateTime.parse(fecha, formatter);
        LocalTime hora = dateTime.toLocalTime();

        // Determinar si es de día (entre las 07:00:00 y las 21:00:00)
        boolean esDeDia = hora.isAfter(LocalTime.parse("07:00:00")) && hora.isBefore(LocalTime.parse("21:00:00"));

        return esDeDia;
    }

}
