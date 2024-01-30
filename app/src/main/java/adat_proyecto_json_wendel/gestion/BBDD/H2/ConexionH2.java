package adat_proyecto_json_wendel.gestion.BBDD.H2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionH2 {

    public static Connection conn;

    // Datos de conexión a la base de datos
    // private static final String URL =
    // "jdbc:mysql://localhost:3306/nombre_base_datos";
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USUARIO = "";
    private static final String CONTRASENA = "";


    // Método para obtener la conexión a la base de datos
    public Connection obtenerConexion() {
        if (conn == null) {
            try {
                // Cargar el driver de MySQL
                Class.forName("org.h2.Driver");

                // Obtener la conexión
                conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

                System.out.println("Conexión exitosa a la base de datos");
            } catch (Exception e) {
                System.out.println("No se encontró el driver de MySQL");
                e.printStackTrace();
            }
        }
        return conn;
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Conexión cerrada correctamente");
            } else {
                System.out.println("La conexión ya estaba cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }

}
