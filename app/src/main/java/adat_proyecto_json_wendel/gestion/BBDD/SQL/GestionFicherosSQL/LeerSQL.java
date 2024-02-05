package adat_proyecto_json_wendel.gestion.BBDD.SQL.GestionFicherosSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

// Clase con métodos para leer ficheros SQL y ejecutar las sentencias en él.
public class LeerSQL {

    // Método que dado un fichero con sentencias sql, va leyendo el fichero y ejecutando las sentencias sql secuencialmente.
    // Devuelve true si se han ejecutado todas correctamente y false en caso de que falle alguna.
    public static boolean ejecutarSentenciasFicheroSQL(Connection conn, String rutaFichero) {
        if(conn != null){
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
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            System.out.println("Erro. La conexión no puede ser null.");
        }
        return false;
    }

    // Método para ejecutar una sola consulta.
    public static void ejecutarSentencia(Connection conn, String query) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al ejecutar la sentencia SQL: " + query);
            e.printStackTrace();
        }
    }
}
