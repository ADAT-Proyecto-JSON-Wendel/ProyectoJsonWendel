package adat_proyecto_json_wendel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import adat_proyecto_json_wendel.gestion.bbdd.gestionPrediccionesBBDD.GestionPredicciones;
import adat_proyecto_json_wendel.gestion.bbdd.h2.ConexionH2;
import adat_proyecto_json_wendel.gestion.bbdd.mysql.ConexionMYSQL;
import adat_proyecto_json_wendel.gestion.bbdd.sql.gestionFicherosSQL.LeerSQL;
import adat_proyecto_json_wendel.gestion.gestionCSV.GestionCSVWriter;
import adat_proyecto_json_wendel.gestion.gestionJSON.ConcellosParser;
import adat_proyecto_json_wendel.gestion.gestionJSON.DescripcionParser;
import adat_proyecto_json_wendel.gestion.gestionJSON.GestionPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.util.Metodos;

public class App {

    public static int SALIR = 18;

    // Strings con los datos que voy a utilizar, como por ejemplo las rutas
    // -------------
    // Rutas ficheros con datos JSON, ruta común
    public static String rutaPaqueteGestionJSON = "app/src/main/java/adat_proyecto_json_wendel/gestion/gestionJSON/dataJson/";
    // Nombre fichero Json con valores de las descripciones
    public static String rutaDescripcionesPredicciones = rutaPaqueteGestionJSON + "descripciones.json";
    // Nombre fichero Json con la lista de id de los concellos
    public static String rutaListaIdConcellos = rutaPaqueteGestionJSON + "listaIdConcellos.json";
    // Nombre fichero para guardar el CSV
    public static String nombreFicheroCSV = "25-11-2023-galicia.csv";
    // Ruta base de la peticion a la api, despues de idConc= tendremos que indicar
    // el id de un concello
    public static String peticionApiBase = "https://servizos.meteogalicia.gal/mgrss/predicion/jsonPredConcellos.action?idConc=";
    // Ruta que va al final de la peticion con el parametro del idioma en gallego
    public static String peticionApiLocale = "&request_locale=gl";
    // -----------------------------------------------------------

    // Instanciar los PARSER de los JSON -------------------
    // Instanciar el parser con la ruta del JSON de las descripciones
    public static DescripcionParser descripcionParser = new DescripcionParser(rutaDescripcionesPredicciones);

    // Instanciar la clase de GestionPrediccion para obtener las predicciones de una
    // peticion a la API
    public static GestionPrediccion gestion = new GestionPrediccion(descripcionParser);

    // Devuelve mapa con las ciudades principales en las que voy a realizar las
    // peticiones a la api, en este mapa estan guardados los id de esas ciudades
    public static Map<String, String> ciudadesImportantes = Metodos.getMapCiudadesPrincipales();

    // Creo una lista de PrediccionConcello
    public static List<PrediccionConcello> listaPrediccionesCiudadesImportantes = null;

    public static Connection connH2;
    public static Connection connMYSQL;

    public static final String rutaCrearTablasMYSQL = "app\\src\\main\\java\\adat_proyecto_json_wendel\\gestion\\BBDD\\SQL\\CrearTablas.sql";
    public static final String rutaCrearTablasH2 = "app\\src\\main\\java\\adat_proyecto_json_wendel\\gestion\\BBDD\\SQL\\CrearTablasH2.sql";
    public static final String rutaEliminarDatosTablasMYSQL = "app\\src\\main\\java\\adat_proyecto_json_wendel\\gestion\\BBDD\\SQL\\EliminarDatosTablas.sql";
    public static final String rutaInsertarDatosPruebaMYSQL = "app\\src\\main\\java\\adat_proyecto_json_wendel\\gestion\\BBDD\\SQL\\InsertarDatosPrueba.sql";

    public static int OpcionesMenu(Scanner sc) {
        int opcion = -1;
        System.out.println("1 - Conectar BBDD H2.");
        System.out.println("2 - Crear Tablas en BBDD H2.");
        System.out.println("3 - Insertar datos de prueba en la BBDD H2.");
        System.out.println("4 - Cerrar conexion BBDD H2.");
        System.out.println("5 - Por cada ciudad realizamos una peticion.");
        System.out.println("6 - Guardar los datos de las 7 peticiones en el fichero CSV.");
        System.out.println("7 - Mostrar datos Predicciones principales ciudades desde memoria");
        System.out.println("8 - Guardar Predicciones en la BBDD H2");
        System.out.println("9 - Mostrar datos por pantalla de BBDD H2");
        System.out.println("10 - Conexion BBDD MYSQL");
        System.out.println("11 - Cerrar conexion MYSQL");
        System.out.println("12 - Insertar concello prueba");
        System.out.println("13 - Crear Tablas en BBDD MYSQL");
        System.out.println("14 - Insertar datos pruebas en BBDD MYSQL");
        System.out.println("15 - Eliminar datos de todas las tablas.");
        System.out.println("16 - Insertar datos Predicciones desde cache a BBDD MYSQL.");
        System.out.println("17 - Mostrar datos tablas BBDD MYSQL.");
        System.out.println("18 - SALIR.");
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opcion;
    }

    public static void Menu(int opcion, ConexionH2 conexionH2, ConexionMYSQL conexionMYSQL) {

        switch (opcion) {
            case 1: // Conectar BBDD H2.
                if (connH2 == null) {
                    connH2 = conexionH2.obtenerConexion();
                } else {
                    System.out.println("La conexión ya está establecida.");
                }
                System.out.println();
                break;
            case 2: // Crear tablas en la BBDD H2
                if (connH2 != null) {
                    if (LeerSQL.ejecutarSentenciasFicheroSQL(connH2, rutaCrearTablasMYSQL)) {
                        System.out.println("Tablas creadas correctamente.");
                    }
                } else {
                    System.out.println("Error. Primero debe de establecerse una conexión.");
                }
                System.out.println();
                break;
            case 3: // Insertar datos de prueba en la BBDD H2
                if (connH2 != null) {
                    GestionPredicciones.insertarDatosPrueba(connH2);
                    System.out.println("Datos de prueba insertados correctamente.");
                } else {
                    System.out.println("Error. Primero debe realizarse la conexion la BBDD de H2.");
                }
                System.out.println();
                break;
            case 4: // Cerrar conexion H2.
                if (connH2 == null) {
                    conexionH2.cerrarConexion();
                } else {
                    System.out.println("La conexión ya se encuentra cerrada.");
                }
                System.out.println();
                break;
            case 5: // Peticiones a la API con las principales ciudades
                listaPrediccionesCiudadesImportantes = RealizarPeticionesPredicciones();
                System.out.println();
                break;
            case 6: // Guardar los datos de las 7 peticiones en el fichero CSV
                if (listaPrediccionesCiudadesImportantes != null && descripcionParser != null
                        && nombreFicheroCSV != null) {
                    guardarDatosEnCSV(listaPrediccionesCiudadesImportantes, descripcionParser, nombreFicheroCSV);
                } else {
                    System.out.println("Primero hay que realizar las peticiones a la API.");
                }
                System.out.println();
                break;
            case 7: // Mostrar datos Predicciones principales ciudades
                if (listaPrediccionesCiudadesImportantes != null) {
                    MostrarDatosPredicciones(listaPrediccionesCiudadesImportantes);
                } else {
                    System.out.println("Primero hay que realizar las peticiones a la API.");
                }
                System.out.println();
                break;
            case 8: // Guardar Predicciones en la BBDD H2.
                if (listaPrediccionesCiudadesImportantes != null) {
                    GestionPredicciones.GuardarDatosPrediccionesEnBaseDeDatos(listaPrediccionesCiudadesImportantes, connH2);
                } else {
                    System.out.println("Primero hay que realizar las peticiones a la API.");
                }
                System.out.println();
                break;
            case 9: // Mostrar datos por pantalla de BBDD H2.
                GestionPredicciones.MostrarPrediccionesBBDDLista(listaPrediccionesCiudadesImportantes, connH2);
                System.out.println();
                break;
            case 10: // Obtener conexion MYSQL
                connMYSQL = conexionMYSQL.getConexion();
                System.out.println();
                break;
            case 11: // Cerrar conexion MYSQL
                conexionMYSQL.cerrarConexion();
                System.out.println();
                break;
            case 12: // Insertar Concello prueba
                GestionPredicciones.insertarConcello(1, "ourense", connMYSQL);
                System.out.println();
                break;
            case 13: // Crear Tablas en BBDD MYSQL
                if (LeerSQL.ejecutarSentenciasFicheroSQL(connMYSQL, rutaCrearTablasMYSQL)) {
                    System.out.println("Tablas creadas correctamente.");
                }
                System.out.println();
                break;
            case 14: // Insertar datos pruebas en BBDD MYSQL
                if (LeerSQL.ejecutarSentenciasFicheroSQL(connMYSQL, rutaInsertarDatosPruebaMYSQL)) {
                    System.out.println("Datos de prueba insertados correctamente.");
                }
                System.out.println();
                break;
            case 15: // Eliminar datos de todas las tablas.
                if (LeerSQL.ejecutarSentenciasFicheroSQL(connMYSQL, rutaEliminarDatosTablasMYSQL)) {
                    System.out.println("Datos de las tablas eliminados correctamente.");
                }
                System.out.println();
                break;
            case 16: // Insertar datos Predicciones desde cache a BBDD MYSQL
                // Seleccionar primero la BBDD, si existe y se ha seleccionado correctamente
                // insertamos las predicciones.
                if (GestionPredicciones.seleccionarBaseDeDatos(connMYSQL, ConexionMYSQL.dbName)) {
                    if (listaPrediccionesCiudadesImportantes != null) {
                        GestionPredicciones.GuardarDatosPrediccionesEnBaseDeDatos(listaPrediccionesCiudadesImportantes, connMYSQL);
                    } else {
                        System.out.println("Primero hay que realizar las peticiones a la API.");
                    }
                    System.out.println();
                }
                break;
            case 17: // Mostrar datos por pantalla de BBDD MYSQL.
                GestionPredicciones.MostrarPrediccionesBBDDLista(listaPrediccionesCiudadesImportantes, connMYSQL);
                System.out.println();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("null")
    public static List<PrediccionConcello> RealizarPeticionesPredicciones() {
        List<PrediccionConcello> listaPrediccionesCiudadesImportantes = null;
        try {
            // Por cada ciudad realizamos una peticion, guardamos el objeto
            // PrediccionConcello y lo agregamos a una lista pera luego almacenar los datos
            // en el fichero CSV.
            listaPrediccionesCiudadesImportantes = new ArrayList<PrediccionConcello>();
            for (@SuppressWarnings("rawtypes")
            Entry entry : ciudadesImportantes.entrySet()) {
                String urlCompleta = peticionApiBase + entry.getValue() + peticionApiLocale;
                PrediccionConcello prediccion = gestion.obtenerPrediccion(urlCompleta);
                if (prediccion != null) {
                    listaPrediccionesCiudadesImportantes.add(prediccion);
                }
                try {
                    // Realizo una espera de medio segundo entre cada petición para no saturar el
                    // servidor, si se realizan muy rápido a veces me devuelve una HTTP Response 500
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int idconcelloPrediccion = -1;
                try {
                    idconcelloPrediccion = prediccion.getIdConcello();
                } catch (Exception e) {
                }
                System.out.println("Agregada Prediccion con id de concello: " + idconcelloPrediccion);
            }
            System.out.println("Datos de predicciones guardados correctamente en memoria.");
        } catch (Exception e) {
            System.out.println("Error. Ha habido un error al obtener las Predicciones.");
        }

        return listaPrediccionesCiudadesImportantes;
    }


    /**
     * Muestra los datos de las predicciones contenidas en la lista.
     *
     * @param predicciones La lista de predicciones a mostrar.
     */
    public static void MostrarDatosPredicciones(List<PrediccionConcello> predicciones) {
        for (PrediccionConcello pr : predicciones) {
            if (pr != null) {
                // Llama al método que muestra los datos de la predicción
                gestion.mostrarDatosPrediccion(pr);
            }
            try {
                // Realiza una espera de medio segundo entre cada petición para no saturar el
                // servidor
                // Si se realizan muy rápido, a veces devuelve una HTTP Response 500
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // Instanciar clase con metodos de conexión a la base de datos H2
        ConexionH2 conexionH2 = new ConexionH2();

        // Instanciar clase conexion MYSQL
        ConexionMYSQL conexionMYSQL = new ConexionMYSQL();

        // Instancia de Scanner
        Scanner sc = new Scanner(System.in);

        // Bucle para mostrar menú y gestionar las opciones según el número introducido
        // por teclado
        int op = -1;
        while (op != SALIR) {
            op = OpcionesMenu(sc);
            Menu(op, conexionH2, conexionMYSQL);
        }

        // ----------------------------
        // Parser para obtener los id de los concellos, esto lo he agregado a mayores
        // para poder obtener la prediccion por un concello buscando por su nombre.
        // ConcellosParser concellosParser = new ConcellosParser(rutaListaIdConcellos);
        // ------------------------------------------------------

        // Leer datos de un Concello, esto lo he agregado como extra para obtener datos
        // de el concello que queramos, también he creado un JSON con toda la lista de
        // concellos disponibles y su correspondientes ID.
        // obtenerIdConcelloPorNombre(manager,"Baleira");

    }

    /**
     * Obtiene el ID del concello por su nombre utilizando el gestor de
     * ConcellosParser.
     *
     * @param manager        El gestor de ConcellosParser.
     * @param nombreConcello El nombre del concello del cual se desea obtener el ID.
     * @return El ID del concello si se encuentra, de lo contrario, retorna -1.
     */
    public static int obtenerIdConcelloPorNombre(ConcellosParser manager, String nombreConcello) {

        // Utiliza el gestor para obtener el ID del concello por su nombre
        int idConcello = manager.obtenerIdConcelloPorNombre(nombreConcello);

        // Imprime el resultado obtenido
        if (idConcello != -1) {
            System.out.println("El ID del concello '" + nombreConcello + "' es: " + idConcello);
        } else {
            System.out.println("No se encontró el concello '" + nombreConcello + "'.");
        }
        return idConcello;
    }

    /**
     * Guarda los datos de las predicciones de ciudades importantes en un archivo
     * CSV.
     *
     * @param listaPrediccionesCiudadesImportantes Lista de predicciones de ciudades
     *                                             importantes.
     * @param descripcionParser                    Objeto utilizado para obtener
     *                                             descripciones según códigos de
     *                                             ceo y viento.
     * @param nombreFicheroCSV                     Nombre del archivo CSV donde se
     *                                             guardarán los datos.
     */
    public static void guardarDatosEnCSV(
            List<PrediccionConcello> listaPrediccionesCiudadesImportantes,
            DescripcionParser descripcionParser,
            String nombreFicheroCSV) {

        try {
            // Verificar si la lista de predicciones no es nula
            if (listaPrediccionesCiudadesImportantes != null) {

                // Obtener datos de la predicción en formato CSV
                List<String[]> datos = GestionCSVWriter.getDatosPrediccionesEnCSV(listaPrediccionesCiudadesImportantes,
                        descripcionParser);

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
