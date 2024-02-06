package adat_proyecto_json_wendel.gestion.bbdd.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionMYSQL {

        // Nombre de usuario para la conexión a la base de datos
        private static final String userName = "root";

        // Contraseña para la conexión a la base de datos
        //private static final String password = "abc123.";
        private static final String password = "";

        // Sistema de gestión de bases de datos (DBMS) que se utilizará (MySQL en este
        // caso)
        private static final String dbms = "mysql";

        // Dirección IP del servidor de la base de datos
        private static final String serverName = "127.0.0.1";

        // Número de puerto del servidor de la base de datos
        private static final String portNumber = "3306";

        // Nombre de la base de datos
        public static final String dbName = "prediccionconcellos";

        // URL para la conexión utilizando MariaDB
        private static final String URL = "jdbc:mariadb://localhost:" + portNumber + "/" + dbName;

        // URL para la conexión utilizando MySQL
        private static final String URL2 = "jdbc:mysql://localhost:" + portNumber;

        // Declaracion de variable estática que será utilizada para almacenar la conexión a la base de datos.
        private static Connection conn = null;

        /**
         * Obtiene una conexión a la base de datos.
         * 
         * @return La conexión a la base de datos.
         */
        public Connection getConexion() {
                if (conn == null) {
                        try {
                                // Cargar el driver de MySQL
                                Class.forName("com.mysql.cj.jdbc.Driver");

                                // Obtener la conexión
                                conn = DriverManager.getConnection(URL2, userName, password);
                                System.out.println("Conexión exitosa a la base de datos");
                        } catch (Exception e) {
                                // Si ocurre alguna excepción durante el proceso, se captura y se imprime un
                                // mensaje de error junto con la traza de la excepción.
                                System.out.println("No se encontró el driver de MySQL");
                                e.printStackTrace();
                        }
                }
                return conn;
        }

        /**
         * Obtiene una conexión a la base de datos.
         * 
         * @return La conexión a la base de datos.
         */
        public static Connection obtenerConexion() {
                try {
                        // Cargar el driver de MariaDB
                        Class.forName("org.mariadb.jdbc.Driver");

                        // Obtener la conexión utilizando la URL, el nombre de usuario y la contraseña
                        // proporcionados
                        Connection conexion = DriverManager.getConnection(URL2, userName, password);

                        // Devolver la conexión
                        return conexion;
                } catch (ClassNotFoundException e) {
                        // Si ocurre una ClassNotFoundException (no se encuentra la clase del driver),
                        // se imprime la traza de la excepción
                        e.printStackTrace();
                } catch (SQLException e) {
                        // Si ocurre una SQLException (error al conectar a la base de datos), se imprime
                        // la traza de la excepción
                        e.printStackTrace();
                }
                // Si se produce una excepción, se devuelve null
                return null;
        }

        /**
         * Cierra la conexión a la base de datos.
         */
        public void cerrarConexion() {
                try {
                        // Comprobamos si la conexión no es nula antes de cerrarla.
                        if (conn != null) {
                                // Cerramos la conexión.
                                conn.close();
                                System.out.println("Conexión cerrada correctamente");
                        } else {
                                // Si la conexión ya estaba cerrada, mostramos un mensaje indicándolo.
                                System.out.println("La conexión ya estaba cerrada.");
                        }
                } catch (SQLException e) {
                        // Si se produce una SQLException al intentar cerrar la conexión, mostramos la
                        // traza de la excepción.
                        System.out.println("Error al cerrar la conexión");
                        e.printStackTrace();
                }
        }

        /**
         * Establece la conexión a la base de datos utilizando los parámetros
         * proporcionados.
         *
         * @throws SQLException si se produce un error al establecer la conexión.
         */
        public void setConnection() throws SQLException {
                this.conn = getConnection(
                                this.userName,
                                this.password,
                                this.dbms,
                                this.serverName,
                                this.portNumber,
                                this.dbName);
        }

        /**
         * Obtiene una conexión a la base de datos utilizando los parámetros de conexión
         * proporcionados.
         * Si la conexión ya está establecida, simplemente la devuelve.
         *
         * @return La conexión a la base de datos.
         */
        public Connection getConnection() {
                try {
                        // Si la conexión ya está establecida, la devuelve.
                        if (conn != null) {
                                return conn;
                        }

                        // Configura las propiedades de la conexión.
                        Properties connectionProps = new Properties();
                        connectionProps.put("user", userName);
                        connectionProps.put("password", password);

                        // Intenta establecer la conexión según el tipo de base de datos.
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

                        // Mensaje de confirmación de la conexión.
                        System.out.println("Conexion establecida en la base de datos MYSQL.\n\tServidor: " + URL);
                } catch (Exception e) {
                        // En caso de error, muestra un mensaje de error y la traza del error.
                        System.out.println("Error al establecer la conexión.");
                        e.printStackTrace();
                }

                // Devuelve la conexión establecida o null en caso de error.
                return conn;
        }

        /**
         * Obtiene una conexión a la base de datos utilizando los parámetros de conexión
         * proporcionados.
         * Si la conexión ya está establecida, simplemente la devuelve.
         *
         * @param userName   Nombre de usuario para la conexión a la base de datos.
         * @param password   Contraseña para la conexión a la base de datos.
         * @param dbms       Tipo de sistema de gestión de bases de datos (MySQL o
         *                   Derby).
         * @param serverName Nombre del servidor de la base de datos.
         * @param portNumber Número de puerto para la conexión a la base de datos.
         * @param dbName     Nombre de la base de datos a la que se desea conectar.
         * @return La conexión a la base de datos.
         * @throws SQLException Si ocurre algún error al intentar establecer la
         *                      conexión.
         */
        public static Connection getConnection(
                        String userName,
                        String password,
                        String dbms,
                        String serverName,
                        String portNumber,
                        String dbName) throws SQLException {

                // Si la conexión ya está establecida, la devuelve.
                if (ConexionMYSQL.conn != null) {
                        return conn;
                }

                // Configura las propiedades de la conexión.
                Properties connectionProps = new Properties();
                connectionProps.put("user", userName);
                connectionProps.put("password", password);

                // Intenta establecer la conexión según el tipo de base de datos.
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

                // Mensaje de confirmación de la conexión.
                System.out.println("Conexion establecida.");

                // Devuelve la conexión establecida.
                return conn;
        }

}