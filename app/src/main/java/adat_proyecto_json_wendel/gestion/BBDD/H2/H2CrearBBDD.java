package adat_proyecto_json_wendel.gestion.BBDD.H2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// Clase para crear las tablas en la base de datos H2
public class H2CrearBBDD {

        public static void crearTablas(Connection conexion) {
                try {
                        Statement statement = conexion.createStatement();

                        // Crear tabla Concellos
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Concellos (" +
                                        "idConcello INT PRIMARY KEY," +
                                        "nome VARCHAR(255))");

                        // Crear tabla Predicciones
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Predicciones (" +
                                        "idPrediccion INT AUTO_INCREMENT PRIMARY KEY," +
                                        "idConcello INT," +
                                        "dataPredicion VARCHAR(255)," +
                                        "nivelAviso INT," +
                                        "tMax INT," +
                                        "tMin INT," +
                                        "uvMax INT," +
                                        "FOREIGN KEY (idConcello) REFERENCES Concellos(idConcello))");

                        // Crear tabla Cielo
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Cielo (" +
                                        "idCielo INT AUTO_INCREMENT PRIMARY KEY," +
                                        "idPrediccion INT," +
                                        "manha INT," +
                                        "tarde INT," +
                                        "noite INT," +
                                        "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

                        // Crear tabla ProbabilidadChoiva
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS ProbabilidadChoiva (" +
                                        "idProbabilidadChoiva INT AUTO_INCREMENT PRIMARY KEY," +
                                        "idPrediccion INT," +
                                        "manha INT," +
                                        "tarde INT," +
                                        "noite INT," +
                                        "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

                        // Crear tabla TemperaturasFranxa
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS TemperaturasFranxa (" +
                                        "idTemperaturasFranxa INT AUTO_INCREMENT PRIMARY KEY," +
                                        "idPrediccion INT," +
                                        "manha INT," +
                                        "tarde INT," +
                                        "noite INT," +
                                        "FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion))");

                        // Crear tabla Vento
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Vento (" +
                                        "idVento INT AUTO_INCREMENT PRIMARY KEY," +
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
}
