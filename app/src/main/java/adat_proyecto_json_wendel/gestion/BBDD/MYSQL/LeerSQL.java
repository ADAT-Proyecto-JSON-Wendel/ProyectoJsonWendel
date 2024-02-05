package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class LeerSQL {

    public static boolean ejecutarSentenciasFicheroSQL(Connection conn, String rutaFichero) {
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
    }

    public static void ejecutarSentencia(Connection conn, String query) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al ejecutar la sentencia SQL: " + query);
            e.printStackTrace();
        }
    }
}
