package adat_proyecto_json_wendel.gestion.bbdd.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase para conectar con la base de datos H2 en memoria
public class ConexionH2 {

    public static Connection conn;

    // Datos de conexión a la base de datos
    // private static final String URL =
    // "jdbc:mysql://localhost:3306/nombre_base_datos";
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USUARIO = "";
    private static final String CONTRASENA = "";

    /**
     * Obtiene y devuelve una conexión a la base de datos.
     * Si la conexión ya está establecida, la devuelve; de lo contrario, intenta
     * establecerla.
     *
     * @return la conexión a la base de datos.
     */
    public Connection obtenerConexion() {
        // Verificar si la conexión ya está establecida
        if (conn == null) {
            try {
                // Cargar el driver de H2
                Class.forName("org.h2.Driver");

                // Intentar establecer la conexión utilizando la URL, el usuario y la contraseña
                conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

                // Imprimir mensaje de conexión exitosa
                System.out.println("Conexión exitosa a la base de datos");
            } catch (Exception e) {
                // Capturar y manejar cualquier excepción que pueda ocurrir durante el proceso
                System.out.println("No se encontró el driver de H2.");
                e.printStackTrace();
            }
        }
        // Devolver la conexión, ya sea la existente o la recién establecida
        return conn;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public void cerrarConexion() {
        try {
            // Comprobamos si la conexión no es nula antes de intentar cerrarla
            if (conn != null) {
                conn.close(); // Cerrar la conexión
                System.out.println("Conexión cerrada correctamente");
            } else {
                System.out.println("La conexión ya estaba cerrada.");
            }
        } catch (SQLException e) {
            // Si ocurre alguna excepción durante el proceso, se captura y se imprime un
            // mensaje de error junto con la traza de la excepción.
            System.out.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }

}
