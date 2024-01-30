package adat_proyecto_json_wendel.gestion.BBDD.H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.TemperaturasFranxa;
import adat_proyecto_json_wendel.model.Vento;

public class H2GestionPrediccion {

    public static void insertarDatosPrediccion(Connection conexion, PrediccionConcello prediccion) {
        try {
            String insertConcello = "INSERT INTO Concellos (idConcello, nome) VALUES (?, ?)";
            String insertPrediccion = "INSERT INTO Predicciones (idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?, ?, ?, ?, ?, ?)";
            String insertCielo = "INSERT INTO Cielo (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertProbabilidadChoiva = "INSERT INTO ProbabilidadChoiva (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertTemperaturasFranxa = "INSERT INTO TemperaturasFranxa (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertVento = "INSERT INTO Vento (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmtConcello = conexion.prepareStatement(insertConcello);
                    PreparedStatement stmtPrediccion = conexion.prepareStatement(insertPrediccion,
                            Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtCielo = conexion.prepareStatement(insertCielo);
                    PreparedStatement stmtProbabilidadChoiva = conexion.prepareStatement(insertProbabilidadChoiva);
                    PreparedStatement stmtTemperaturasFranxa = conexion.prepareStatement(insertTemperaturasFranxa);
                    PreparedStatement stmtVento = conexion.prepareStatement(insertVento)) {

                // Insertar datos del concello
                stmtConcello.setInt(1, prediccion.getIdConcello());
                stmtConcello.setString(2, prediccion.getNome());
                stmtConcello.executeUpdate();

                List<DiaPrediccion> listaPredDiaConcello;
                listaPredDiaConcello = prediccion.getListaPredDiaConcello();

                listaPredDiaConcello.forEach(pred -> {

                    // Insertar datos de Predicciones
                    try {
                        stmtPrediccion.setInt(1, prediccion.getIdConcello());
                        String dataPrediccion = pred.getDataPredicion();
                        stmtPrediccion.setString(2, (dataPrediccion != null) ? dataPrediccion : "0000-00-00");
                        Integer nivelAviso = pred.getNivelAviso();
                        stmtPrediccion.setInt(3, (nivelAviso != null) ? nivelAviso.intValue() : -1);
                        Integer predTMax = pred.gettMax();
                        stmtPrediccion.setInt(4, (predTMax != null) ? predTMax.intValue() : -1);
                        Integer predTMin = pred.gettMin();
                        stmtPrediccion.setInt(5, (predTMin != null) ? predTMin.intValue() : -1);
                        Integer predUvMax = pred.getUvMax();
                        stmtPrediccion.setInt(6, (predUvMax != null) ? predUvMax.intValue() : -1);
                        stmtPrediccion.executeUpdate();

                        // Obtener la clave generada para Predicciones
                        try (ResultSet generatedPredKeys = stmtPrediccion.getGeneratedKeys()) {
                            if (generatedPredKeys.next()) {
                                int idPrediccion = generatedPredKeys.getInt(1);

                                // Insertar datos en Cielo
                                stmtCielo.setInt(1, idPrediccion);

                                Cielo ceo = pred.getCeo();
                                Integer ceoManha = (ceo != null) ? ceo.getManha() : null;
                                stmtCielo.setInt(2, (ceoManha != null) ? ceoManha.intValue() : -1);

                                Integer ceoTarde = (ceo != null) ? ceo.getTarde() : null;
                                stmtCielo.setInt(3, (ceoTarde != null) ? ceoTarde.intValue() : -1);

                                Integer ceoNoite = (ceo != null) ? ceo.getNoite() : null;
                                stmtCielo.setInt(4, (ceoNoite != null) ? ceoNoite.intValue() : -1);
                                stmtCielo.executeUpdate();

                                // Insertar datos en ProbabilidadChoiva
                                stmtProbabilidadChoiva.setInt(1, idPrediccion);

                                ProbabilidadChoiva probabilidadChoiva = pred.getPchoiva();

                                Integer pChoivaManha = (probabilidadChoiva != null) ? pred.getPchoiva().getManha()
                                        : null;
                                stmtProbabilidadChoiva.setInt(2, (pChoivaManha != null) ? pChoivaManha.intValue() : -1);

                                Integer pChoivaTarde = (probabilidadChoiva != null) ? pred.getPchoiva().getTarde()
                                        : null;
                                stmtProbabilidadChoiva.setInt(3, (pChoivaTarde != null) ? pChoivaTarde.intValue() : -1);

                                Integer pChoivaNoite = (probabilidadChoiva != null) ? pred.getPchoiva().getNoite()
                                        : null;
                                stmtProbabilidadChoiva.setInt(4, (pChoivaNoite != null) ? pChoivaNoite.intValue() : -1);

                                stmtProbabilidadChoiva.executeUpdate();

                                // Insertar datos en TemperaturasFranxa
                                stmtTemperaturasFranxa.setInt(1, idPrediccion);
                                TemperaturasFranxa tmaxFranxa = pred.getTmaxFranxa();
                                stmtTemperaturasFranxa.setInt(2, (tmaxFranxa != null) ? tmaxFranxa.getManha() : -1);
                                stmtTemperaturasFranxa.setInt(3, (tmaxFranxa != null) ? tmaxFranxa.getTarde() : -1);
                                stmtTemperaturasFranxa.setInt(4, (tmaxFranxa != null) ? tmaxFranxa.getNoite() : -1);

                                stmtTemperaturasFranxa.executeUpdate();

                                // Insertar datos en Vento
                                stmtVento.setInt(1, idPrediccion);
                                Vento vento = pred.getVento();
                                stmtVento.setInt(2, (vento != null) ? vento.getManha() : -1);
                                stmtVento.setInt(3, (vento != null) ? vento.getTarde() : -1);
                                stmtVento.setInt(4, (vento != null) ? vento.getNoite() : -1);
                                stmtVento.executeUpdate();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                );

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

    public static void MostrarDatosTablasPrediccion1(Connection conexion) {
        try {
            Statement statement = conexion.createStatement();

            // Seleccionar datos de prueba
            System.out.println("Datos de la tabla Concellos:");
            System.out.println("IdConcello\tNome");
            printResultSet(statement.executeQuery("SELECT * FROM Concellos"));

            System.out.println("Datos de la tabla Predicciones:");
            System.out.println("idPred\tidCon\tdataPred\tnAviso\ttMax\ttMin\tuvMax");
            printResultSet(statement.executeQuery("SELECT * FROM Predicciones"));

            // Seleccionar datos relacionados con la predicción
            System.out.println("Datos de la tabla Cielo:");
            System.out.println("idCeova\tidPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM Cielo WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla ProbabilidadChoiva:");
            System.out.println("IdPChoiva\tIdPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM ProbabilidadChoiva WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla TemperaturasFranxa:");
            System.out.println("IdTFranxa\tIdPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM TemperaturasFranxa WHERE idPrediccion = 1"));

            System.out.println("Datos de la tabla Vento:");
            System.out.println("IdVento\tIdPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM Vento WHERE idPrediccion = 1"));

        } catch (SQLException e) {
            System.out.println("Error al seleccionar datos de prueba.");
            e.printStackTrace();
        }
    }

    public static void MostrarDatosTablasPorIdPrediccion(Connection conexion, int idConcello) {
        try {
            System.out.println();
            System.out.println("------------\t\t\tPrediccion con id: " + idConcello + "\t\t\t -----------------------");
            Statement statement = conexion.createStatement();

            // Seleccionar datos de prueba
            System.out.println("Datos de la tabla Concellos:");
            System.out.println("IdConcello\tNome");
            printResultSet(statement.executeQuery("SELECT * FROM Concellos where idConcello = " + idConcello));

            System.out.println("Datos de la tabla Predicciones:");
            System.out.println("idPred\tidCon\tdataPred\tnAviso\ttMax\ttMin\tuvMax");
            printResultSet(statement.executeQuery("SELECT * FROM Predicciones where idConcello = " + idConcello));

            // Seleccionar datos relacionados con la predicción
            System.out.println("Datos de la tabla Cielo:");
            System.out.println("idCeova\tidPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM Cielo where idPrediccion = " + idConcello));

            System.out.println("Datos de la tabla ProbabilidadChoiva:");
            System.out.println("IdPChoiva\tIdPred\tManha\tTarde\tNoite");
            printResultSet(
                    statement.executeQuery("SELECT * FROM ProbabilidadChoiva where idPrediccion = " + idConcello));

            System.out.println("Datos de la tabla TemperaturasFranxa:");
            System.out.println("IdTFranxa\tIdPred\tManha\tTarde\tNoite");
            printResultSet(
                    statement.executeQuery("SELECT * FROM TemperaturasFranxa where idPrediccion = " + idConcello));

            System.out.println("Datos de la tabla Vento:");
            System.out.println("IdVento\tIdPred\tManha\tTarde\tNoite");
            printResultSet(statement.executeQuery("SELECT * FROM Vento where idPrediccion = " + idConcello));
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println();
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

}
