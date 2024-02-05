package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionMYSQL {

        private static final String userName = "root";
        // private static final String password = "abc123.";
        private static final String password = "abc123.";
        private static final String dbms = "mysql";
        private static final String serverName = "127.0.0.1";
        private static final String portNumber = "3306";
        public static final String dbName = "prediccionconcellos";
        private static final String URL = "jdbc:mariadb://localhost:" + portNumber + "/" + dbName;
        private static final String URL2 = "jdbc:mysql://localhost:" + portNumber;

        private static Connection conn = null;

        // Método para obtener la conexión a la base de datos
        public Connection getConexion() {
                if (conn == null) {
                        try {
                                // Cargar el driver de MySQL
                                Class.forName("com.mysql.cj.jdbc.Driver"); //Class.forName("com.mysql.cj.jdbc.Driver");

                                // Obtener la conexión
                                conn = DriverManager.getConnection(URL2, userName, password);
                                System.out.println("Conexión exitosa a la base de datos");
                        } catch (Exception e) {
                                System.out.println("No se encontró el driver de MySQL");
                                e.printStackTrace();
                        }
                }
                return conn;
        }

        public static Connection obtenerConexion() {
                try {
                        Class.forName("org.mariadb.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection(URL2, userName, password);
                        return conexion;
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        // Método para cerrar la conexión a la base de datos
        public void cerrarConexion() {
                try {
                        // Comprobamos si la conexion no es nula y la cerramos.
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

        public void setConnection() throws SQLException {
                this.conn = getConnection(
                                this.userName,
                                this.password,
                                this.dbms,
                                this.serverName,
                                this.portNumber,
                                this.dbName);
        }

        public Connection getConnection() {
                try {
                        if (conn != null) {
                                return conn;
                        }
                        Properties connectionProps = new Properties();
                        connectionProps.put("user", userName);
                        connectionProps.put("password", password);
                        if (dbms.equals("mysql")) {
                                conn = DriverManager.getConnection(
                                                "jdbc:" + dbms + "://" +
                                                                serverName +
                                                                ":" + portNumber + "/" + dbName,
                                                connectionProps);
                        } else if (dbms.equals("derby")) {
                                conn = DriverManager.getConnection(
                                                "jdbc:" + dbms + ":" +
                                                                dbName +
                                                                ";create=true",
                                                connectionProps);
                        }
                        System.out.println("Conexion establecida en la base de datos MYSQL.\n\tServidor: " + URL);
                } catch (Exception e) {
                        System.out.println("Error al establecer la conexión.");
                        e.printStackTrace();
                }
                return conn;

        };

        public static Connection getConnection(
                        String userName,
                        String password,
                        String dbms,
                        String serverName,
                        String portNumber,
                        String dbName) throws SQLException {

                if (ConexionMYSQL.conn != null) {
                        return conn;
                }
                Properties connectionProps = new Properties();
                connectionProps.put("user", userName);
                connectionProps.put("password", password);

                if (dbms.equals("mysql")) {
                        conn = DriverManager.getConnection(
                                        "jdbc:" + dbms + "://" +
                                                        serverName +
                                                        ":" + portNumber + "/",
                                        connectionProps);
                } else if (dbms.equals("derby")) {
                        conn = DriverManager.getConnection(
                                        "jdbc:" + dbms + ":" +
                                                        dbName +
                                                        ";create=true",
                                        connectionProps);
                }
                System.out.println("Conexion establecida.");
                return conn;
        };

}