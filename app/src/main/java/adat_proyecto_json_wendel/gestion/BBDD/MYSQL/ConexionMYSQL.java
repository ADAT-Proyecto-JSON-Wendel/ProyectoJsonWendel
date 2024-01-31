package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionMYSQL {

        private static final String userName = "root";
        // private static final String password = "abc123.";
        private static final String password = "";
        private static final String dbms = "mysql";
        private static final String serverName = "127.0.0.1";
        private static final String portNumber = "3306";
        private static final String dbName = "prediccionconcellos";
        private static final String URL = "jdbc:mariadb://localhost:" + portNumber + "/" + dbName;

        private static Connection conn = null;

        public static Connection obtenerConexion() {
                try {
                        Class.forName("org.mariadb.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection(URL, userName, password);
                        return conexion;
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public static Connection cerrarConexion() {
                try {
                        if (conn != null && !conn.isClosed()) {
                                conn.close();
                                System.out.println("Se ha cerrado la conexion con MYSQL.");
                        }
                } catch (Exception e) {
                        System.out.println("Error al cerrar la conexion.");
                        e.printStackTrace();
                }
                return conn;
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

        public Connection getConnection() throws SQLException {
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