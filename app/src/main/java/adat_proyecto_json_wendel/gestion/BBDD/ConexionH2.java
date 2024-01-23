package adat_proyecto_json_wendel.gestion.BBDD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexionH2 {

    public static Connection conn;

    // Datos de conexión a la base de datos
    //private static final String URL = "jdbc:mysql://localhost:3306/nombre_base_datos";
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USUARIO = "";
    private static final String CONTRASENA = "";

    public static void main(String[] args) {
        connectar();

        crearTablas(conn);
        insertarDatosPrueba(conn);
        seleccionarDatosPrueba(conn);

        cerrarConexion();
    }

    public static void crearTablas(Connection conexion) {
        try {
            Statement statement = conexion.createStatement();

            // Crear tabla Concellos
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Concellos (" +
                    "idConcello INT PRIMARY KEY," +
                    "nome VARCHAR(255))");

            // Crear tabla Predicciones
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Predicciones (" +
                    "idPrediccion INT PRIMARY KEY," +
                    "idConcello INT," +
                    "dataPredicion VARCHAR(255)," +
                    "nivelAviso INT," +
                    "tMax INT," +
                    "tMin INT," +
                    "uvMax INT," +
                    "FOREIGN KEY (idConcello) REFERENCES Concellos(idConcello))");

            // Crear tabla Cielo
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Cielo (" +
                    "idCielo INT PRIMARY KEY," +
                    "idPrediccion INT," +
                    "manha INT," +
                    "tarde INT," +
                    "noite INT," +
                    "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

            // Crear tabla ProbabilidadChoiva
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ProbabilidadChoiva (" +
                    "idProbabilidadChoiva INT PRIMARY KEY," +
                    "idPrediccion INT," +
                    "manha INT," +
                    "tarde INT," +
                    "noite INT," +
                    "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

            // Crear tabla TemperaturasFranxa
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS TemperaturasFranxa (" +
                    "idTemperaturasFranxa INT PRIMARY KEY," +
                    "idPrediccion INT," +
                    "manha INT," +
                    "tarde INT," +
                    "noite INT," +
                    "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

            // Crear tabla Vento
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Vento (" +
                    "idVento INT PRIMARY KEY," +
                    "idPrediccion INT," +
                    "manha INT," +
                    "tarde INT," +
                    "noite INT," +
                    "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

            System.out.println("Tablas creadas correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear las tablas.");
            e.printStackTrace();
        }
    }


    public static void insertarDatosPrueba(Connection conexion) {
    try {
        String insertConcello = "INSERT INTO Concellos (idConcello, nome) VALUES (?, ?)";
        String insertPrediccion = "INSERT INTO Predicciones (idPrediccion, idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertCielo = "INSERT INTO Cielo (idCielo, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
        String insertProbabilidadChoiva = "INSERT INTO ProbabilidadChoiva (idProbabilidadChoiva, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
        String insertTemperaturasFranxa = "INSERT INTO TemperaturasFranxa (idTemperaturasFranxa, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
        String insertVento = "INSERT INTO Vento (idVento, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmtConcello = conexion.prepareStatement(insertConcello);
             PreparedStatement stmtPrediccion = conexion.prepareStatement(insertPrediccion);
             PreparedStatement stmtCielo = conexion.prepareStatement(insertCielo);
             PreparedStatement stmtProbabilidadChoiva = conexion.prepareStatement(insertProbabilidadChoiva);
             PreparedStatement stmtTemperaturasFranxa = conexion.prepareStatement(insertTemperaturasFranxa);
             PreparedStatement stmtVento = conexion.prepareStatement(insertVento)) {

            // Insertar datos de prueba
            stmtConcello.setInt(1, 1);
            stmtConcello.setString(2, "Santiago de Compostela");
            stmtConcello.executeUpdate();

            stmtPrediccion.setInt(1, 1);
            stmtPrediccion.setInt(2, 1);
            stmtPrediccion.setString(3, "2024-01-24");
            stmtPrediccion.setInt(4, 2);
            stmtPrediccion.setInt(5, 20);
            stmtPrediccion.setInt(6, 10);
            stmtPrediccion.setInt(7, 5);
            stmtPrediccion.executeUpdate();

            stmtCielo.setInt(1, 1);
            stmtCielo.setInt(2, 1);
            stmtCielo.setInt(3, 1);
            stmtCielo.setInt(4, 2);
            stmtCielo.setInt(5, 3);
            stmtCielo.executeUpdate();

            stmtProbabilidadChoiva.setInt(1, 1);
            stmtProbabilidadChoiva.setInt(2, 1);
            stmtProbabilidadChoiva.setInt(3, 20);
            stmtProbabilidadChoiva.setInt(4, 30);
            stmtProbabilidadChoiva.setInt(5, 10);
            stmtProbabilidadChoiva.executeUpdate();

            stmtTemperaturasFranxa.setInt(1, 1);
            stmtTemperaturasFranxa.setInt(2, 1);
            stmtTemperaturasFranxa.setInt(3, 15);
            stmtTemperaturasFranxa.setInt(4, 18);
            stmtTemperaturasFranxa.setInt(5, 12);
            stmtTemperaturasFranxa.executeUpdate();

            stmtVento.setInt(1, 1);
            stmtVento.setInt(2, 1);
            stmtVento.setInt(3, 10);
            stmtVento.setInt(4, 15);
            stmtVento.setInt(5, 5);
            stmtVento.executeUpdate();

            System.out.println("Datos de prueba insertados correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar datos de prueba.");
            e.printStackTrace();
        }
    } catch (Exception e) {
        System.out.println("Error al preparar las sentencias.");
        e.printStackTrace();
    }
}



    public static void insertarDatosPrueba2(Connection conexion) {
        try {
            Statement statement = conexion.createStatement();
    
            // Insertar datos de prueba
            statement.executeUpdate("INSERT INTO Concellos (idConcello, nome) VALUES (1, 'Santiago de Compostela')");
            statement.executeUpdate("INSERT INTO Predicciones (idPrediccion, idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) " +
                    "VALUES (1, 1, '2024-01-24', 2, 20, 10, 5)");
    
            // Insertar datos relacionados con la predicción
            statement.executeUpdate("INSERT INTO Cielo (idCielo, idPrediccion, manha, tarde, noite) VALUES (1, 1, 1, 2, 3)");
            statement.executeUpdate("INSERT INTO ProbabilidadChoiva (idProbabilidadChoiva, idPrediccion, manha, tarde, noite) VALUES (1, 1, 20, 30, 10)");
            statement.executeUpdate("INSERT INTO TemperaturasFranxa (idTemperaturasFranxa, idPrediccion, manha, tarde, noite) VALUES (1, 1, 15, 18, 12)");
            statement.executeUpdate("INSERT INTO Vento (idVento, idPrediccion, manha, tarde, noite) VALUES (1, 1, 10, 15, 5)");
    
            System.out.println("Datos de prueba insertados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar datos de prueba.");
            e.printStackTrace();
        }
    }
    

    public static void seleccionarDatosPrueba(Connection conexion) {
        try {
            Statement statement = conexion.createStatement();

            // Seleccionar datos de prueba
            System.out.println("Datos de la tabla Concellos:");
            printResultSet(statement.executeQuery("SELECT * FROM Concellos"));

            System.out.println("Datos de la tabla Predicciones:");
            printResultSet(statement.executeQuery("SELECT * FROM Predicciones"));

            // Seleccionar datos relacionados con la predicción
            System.out.println("Datos de la tabla Cielo:");
            printResultSet(statement.executeQuery("SELECT * FROM Cielo WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla ProbabilidadChoiva:");
            printResultSet(statement.executeQuery("SELECT * FROM ProbabilidadChoiva WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla TemperaturasFranxa:");
            printResultSet(statement.executeQuery("SELECT * FROM TemperaturasFranxa WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla Vento:");
            printResultSet(statement.executeQuery("SELECT * FROM Vento WHERE idPrediccion = 1"));

        } catch (SQLException e) {
            System.out.println("Error al seleccionar datos de prueba.");
            e.printStackTrace();
        }
    }

     private static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
        resultSet.close();
    }


    public static void connectar(){
        conn = obtenerConexion();
    }


    // Método para obtener la conexión a la base de datos
    public static Connection obtenerConexion() {
        Connection conexion = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("org.h2.Driver");

            // Obtener la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            System.out.println("Conexión exitosa a la base de datos");
        } catch (Exception e) {
            System.out.println("No se encontró el driver de MySQL");
            e.printStackTrace();
        } 

        return conexion;
    }

    // Método para cerrar la conexión a la base de datos
    public static void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión");
            e.printStackTrace();
        }
    }


}
