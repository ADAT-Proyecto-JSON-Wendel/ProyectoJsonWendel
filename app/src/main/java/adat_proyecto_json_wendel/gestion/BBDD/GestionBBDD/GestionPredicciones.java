package adat_proyecto_json_wendel.gestion.BBDD.GestionBBDD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import adat_proyecto_json_wendel.gestion.BBDD.H2.H2GestionPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;

// Clase para gestionar las predicciones en una base de datos
public class GestionPredicciones {

    // Método para mostrar predicciones pasando una conexión y una lista de
    // predicciones
    public static void MostrarPrediccionesBBDDLista(List<PrediccionConcello> predicciones, Connection conn) {
        if (conn == null) {
            System.out.println("Error. La conexión no puede ser null para mostrar la lista de predicciones.");
        }
        if (predicciones != null) {
            try {
                for (PrediccionConcello pr : predicciones) {
                    if (pr != null) {
                        // Por cada predicción llamamos al método que muesta los datos pasando la
                        // conexión y el id del Concello.
                        H2GestionPrediccion.MostrarDatosTablasPorIdConcello(conn, pr.getIdConcello());
                    }
                    try {
                        // Realizamos una espera en el hilo para no ejecutar las sentencias tan rápido.
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Error al pausar el hilo.");
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error en la lectura de las predicciones.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error. La lista de predicciones no puede ser null.");
        }
    }

    // Seleccionar una base de datos en una conexión.
    public static boolean seleccionarBaseDeDatos(Connection conn, String dbName) {
        if (conn != null) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("USE " + dbName);
                System.out.println("Base de datos seleccionada: " + dbName);
                return true;
            } catch (SQLException e) {
                System.out.println("Error al seleccionar la base de datos: " + dbName);
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Error. La conexion no puede ser null.");
        }
        return false;
    }

}
