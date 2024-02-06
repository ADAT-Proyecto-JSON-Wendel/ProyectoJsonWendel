package adat_proyecto_json_wendel.gestion.bbdd.sql.gestionFicherosSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

// Clase con métodos para leer ficheros SQL y ejecutar las sentencias en él.
public class LeerSQL {

    /**
     * Ejecuta las sentencias SQL contenidas en un archivo.
     *
     * @param conn        La conexión a la base de datos.
     * @param rutaFichero La ruta del archivo que contiene las sentencias SQL.
     * @return true si se ejecutan las sentencias correctamente, false de lo
     *         contrario.
     */
    public static boolean ejecutarSentenciasFicheroSQL(Connection conn, String rutaFichero) {
        if (conn != null) {
            try (BufferedReader bf = new BufferedReader(new FileReader(rutaFichero))) {
                StringBuilder query = new StringBuilder();
                String linea;
                while ((linea = bf.readLine()) != null) {
                    if (!linea.trim().isEmpty() && !linea.trim().startsWith("--")) {
                        query.append(linea.trim());

                        // Si la línea termina con ';', ejecutar la sentencia
                        if (linea.trim().endsWith(";")) {
                            ejecutarSentencia(conn, query.toString());
                            // Reiniciar StringBuilder
                            query.setLength(0);
                        }
                    }
                }
            } catch (Exception e) {
                // Manejo de excepciones en caso de error al ejecutar las sentencias
                e.printStackTrace();
                // En caso de cualquier excepcion devolver false.
                return false;
            }
            // Devolver true en caso de que se ejecuten todas las sentencias correctamente
            return true;
        } else {
            System.out.println("Erro. La conexión no puede ser null.");
        }
        // Si la conexión es null devolver false
        return false;
    }

    /**
     * Ejecuta una sentencia SQL en la base de datos.
     * 
     * @param conn  La conexión a la base de datos.
     * @param query La sentencia SQL a ejecutar.
     */
    public static void ejecutarSentencia(Connection conn, String query) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            // Manejo de excepciones en caso de error al ejecutar la sentencia
            System.out.println("Error al ejecutar la sentencia SQL: " + query);
            e.printStackTrace();
        }
    }

}
