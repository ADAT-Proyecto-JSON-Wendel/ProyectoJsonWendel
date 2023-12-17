/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package adat_proyecto_json_wendel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import adat_proyecto_json_wendel.gestion.gestionCSV.GestionCSVWriter;
import adat_proyecto_json_wendel.gestion.gestionJSON.ConcellosParser;
import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.gestion.gestionJSON.GestionPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.util.Metodos;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        // Strings con los datos que voy a utilizar, como por ejemplo las rutas
        // -------------
        // Rutas ficheros con datos JSON, ruta común
        String rutaPaqueteGestionJSON = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/dataJson/";
        // Nombre fichero Json con valores de las descripciones
        String rutaDescripcionesPredicciones = rutaPaqueteGestionJSON + "descripciones.json";
        // Nombre fichero Json con la lista de id de los concellos
        String rutaListaIdConcellos = rutaPaqueteGestionJSON + "listaIdConcellos.json";
        // Nombre fichero para guardar el CSV
        String nombreFicheroCSV = "25-11-2023-galicia.csv";
        // Ruta base de la peticion a la api, despues de idConc= tendremos que indicar el id de un concello
        String peticionApiBase = "https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=";
        // Ruta que va al final de la peticion con el parametro del idioma en gallego
        String peticionApiLocale = "&request_locale=gl";
        // -----------------------------------------------------------

        // Instanciar los PARSER de los JSON -------------------
        // Instanciar el parser con la ruta del JSON de las descripciones
        DescripcionParser descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);

        // Parser para obtener los id de los concellos
        ConcellosParser manager = new ConcellosParser(rutaListaIdConcellos);
        // ------------------------------------------------------

        // Instanciar la clase de GestionPrediccion para obtener las predicciones de una
        // peticion a la API
        GestionPrediccion gestion = new GestionPrediccion(descripcionParser);

        // Crear lista de Predicciones, realizar peticiones y guardar los datos en la
        // Lista ------------
        // Creo una lista de PrediccionConcello
        List<PrediccionConcello> listaPrediccionesCiudadesImportantes = new ArrayList<>();

        // Devuelve mapa con las ciudades principales en las que voy a realizar las
        // peticiones a la api, en este mapa estan guardados los id de esas ciudades
        Map<String, String> ciudadesImportantes = Metodos.getMapCiudadesPrincipales();

        // Por cada ciudad realizamos una peticion, guardamos el objeto
        // PrediccionConcello y lo agregamos a una lista pera luego almacenar los datos
        // en el fichero CSV.
        for (Entry entry : ciudadesImportantes.entrySet()) {
            String urlCompleta = peticionApiBase + entry.getValue() + peticionApiLocale;
            PrediccionConcello prediccion = gestion.obtenerPrediccion(urlCompleta);
            if (prediccion != null) {
                listaPrediccionesCiudadesImportantes.add(prediccion);
                gestion.mostrarDatosPrediccion(prediccion);
            }
            try {
                // Realizo una espera de medio segundo entre cada petición para no saturar el
                // servidor, si se realizan muy rápido a veces me devuelve una HTTP Response 500
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // ----------------------------------------------------------------------------------------

        // Guardar los datos de las 7 peticiones en el fichero CSV
        guardarDatosEnCSV(listaPrediccionesCiudadesImportantes, descripcionParser, nombreFicheroCSV);

        // Leer datos de un Concello, esto lo he agregado como extra para obtener datos
        // de el concello que queramos, también he creado un JSON con toda la lista de
        // concellos disponibles y su correspondientes ID.
        // obtenerIdConcelloPorNombre(manager,"Baleira");

    }

    public static int obtenerIdConcelloPorNombre(ConcellosParser manager, String nombreConcello) {

        int idConcello = manager.obtenerIdConcelloPorNombre(nombreConcello);

        if (idConcello != -1) {
            System.out.println("El ID del concello '" + nombreConcello + "' es: " + idConcello);
        } else {
            System.out.println("No se encontró el concello '" + nombreConcello + "'.");
        }
        return idConcello;
    }

    /**
     * Guarda los datos de las predicciones de ciudades importantes en un archivo CSV.
     *
     * @param listaPrediccionesCiudadesImportantes Lista de predicciones de ciudades importantes.
     * @param descripcionParser Objeto utilizado para obtener descripciones según códigos de ceo y viento.
     * @param nombreFicheroCSV Nombre del archivo CSV donde se guardarán los datos.
     */
    public static void guardarDatosEnCSV(
            List<PrediccionConcello> listaPrediccionesCiudadesImportantes,
            DescripcionParser descripcionParser,
            String nombreFicheroCSV) {

        try {
            // Verificar si la lista de predicciones no es nula
            if (listaPrediccionesCiudadesImportantes != null) {

                // Obtener datos de la predicción en formato CSV
                List<String[]> datos = GestionCSVWriter.getDatosPrediccionesEnCSV(listaPrediccionesCiudadesImportantes, descripcionParser);

                // Escribir los datos en el fichero CSV
                GestionCSVWriter.crearCSV(nombreFicheroCSV, datos);

            } else {
                System.out.println("La predicción es nula.");
            }
        } catch (Exception e) {
            // Manejar la excepción
            e.printStackTrace();
        }
    }

}
