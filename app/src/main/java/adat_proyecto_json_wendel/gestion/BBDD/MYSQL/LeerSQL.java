package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class LeerSQL {

    public static boolean EjecutarSentenciasFicheroSQL(Connection conn, String rutaFichero) {
        if (rutaFichero.trim() != null) {
            try (BufferedReader bf = new BufferedReader(new FileReader(rutaFichero))) {
                StringBuffer query = new StringBuffer();
                String linea;
                while ((linea = bf.readLine()) != null) {
                    if (!linea.trim().isEmpty() && !linea.trim().startsWith("--")) {
                        query.append(linea.trim());
                    }
                    if (linea.trim().endsWith(";")) {
                        EjecutarSentencia(conn, query.toString());
                        // Reiniciar StringBuffer
                        query.setLength(0);
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static void EjecutarSentencia(Connection conn, String query) {
        try (Statement statement = conn.prepareStatement(query)) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al ejecutar al ejecutar la sentencia SQL: " + query);
            // e.printStackTrace();
        }
    }

}
