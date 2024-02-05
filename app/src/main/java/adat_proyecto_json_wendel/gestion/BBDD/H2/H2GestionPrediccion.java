package adat_proyecto_json_wendel.gestion.BBDD.H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import adat_proyecto_json_wendel.gestion.BBDD.MYSQL.GestionMYSQL;
import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.TemperaturasFranxa;
import adat_proyecto_json_wendel.model.Vento;

public class H2GestionPrediccion {

    public static void insertaListaPredicciones(Connection conn, List<PrediccionConcello> listaPredicciones) {
        try {
            for (PrediccionConcello prediccionConcello : listaPredicciones) {
                insertarDatosPrediccion(conn, prediccionConcello);
            }
        } catch (Exception e) {
            System.out.println("Error al insertar las predicciones.");
            e.printStackTrace();
        }

    }

    public static boolean insertarDatosPrediccion(Connection conexion, PrediccionConcello prediccion) {
        try {
            String insertConcello = "INSERT INTO Concellos (idConcello, nome) VALUES (?, ?)";
            String insertPrediccion = "INSERT INTO Predicciones (idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?, ?, ?, ?, ?, ?)";
            String insertCielo = "INSERT INTO Cielo (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertProbabilidadChoiva = "INSERT INTO ProbabilidadeChoiva (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertTemperaturasFranxa = "INSERT INTO TemperaturasFranxa (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertVento = "INSERT INTO Vento (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";

            // Instanciamos los objetos de las consultas
            try (PreparedStatement stmtConcello = conexion.prepareStatement(insertConcello,
                    Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtPrediccion = conexion.prepareStatement(insertPrediccion,
                            Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtCielo = conexion.prepareStatement(insertCielo);
                    PreparedStatement stmtProbabilidadChoiva = conexion.prepareStatement(insertProbabilidadChoiva);
                    PreparedStatement stmtTemperaturasFranxa = conexion.prepareStatement(insertTemperaturasFranxa);
                    PreparedStatement stmtVento = conexion.prepareStatement(insertVento)) {

                // Comprobar si ya existe el concello antes de insertarlo.
                try {
                    if (!GestionMYSQL.existeConcello(prediccion.getIdConcello(), conexion)) {
                        // Insertar datos del concello
                        stmtConcello.setInt(1, prediccion.getIdConcello());
                        stmtConcello.setString(2, prediccion.getNome());
                        stmtConcello.executeUpdate();
                    }
                } catch (Exception e) {
                    System.out.println("Error al insertar el concello.");
                    e.printStackTrace();
                }

                // Lista de Predicción por día, ya que podría haber hasta 3 predicciones para
                // cada concello en un mismo día.
                List<DiaPrediccion> listaPredDiaConcello;
                listaPredDiaConcello = prediccion.getListaPredDiaConcello();
                // Para cada predicción vamos agregar todos los valores correspondientes en los
                // PreparedStatement "Consultas".
                listaPredDiaConcello.forEach(pred -> {
                    try {
                        // Insertar datos de Predicciones, comprobamos si hay algún nulo y asignamos
                        // valores por defecto para evitar posibles errores futuros si hay algún null.
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

                        // Obtener la clave generada para Predicciones con el método getGeneratedKeys(),
                        // esta clave es el id en la tabla y la necesitamos porque está relacionado con
                        // el resto de tablas.
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
                                Integer pChoivaManha = (probabilidadChoiva != null) ? probabilidadChoiva.getManha()
                                        : null;
                                stmtProbabilidadChoiva.setInt(2, (pChoivaManha != null) ? pChoivaManha.intValue() : -1);
                                Integer pChoivaTarde = (probabilidadChoiva != null) ? probabilidadChoiva.getTarde()
                                        : null;
                                stmtProbabilidadChoiva.setInt(3, (pChoivaTarde != null) ? pChoivaTarde.intValue() : -1);
                                Integer pChoivaNoite = (probabilidadChoiva != null) ? probabilidadChoiva.getNoite()
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
                                Integer ventoManha = (vento != null) ? vento.getManha() : null;
                                stmtVento.setInt(2, (ventoManha != null) ? ventoManha.intValue() : -1);
                                Integer ventoTarde = (vento != null) ? vento.getTarde() : null;
                                stmtVento.setInt(3, (ventoTarde != null) ? ventoTarde.intValue() : -1);
                                Integer ventoNoite = (vento != null) ? vento.getNoite() : null;
                                stmtVento.setInt(4, (ventoNoite != null) ? ventoNoite.intValue() : -1);
                                stmtVento.executeUpdate();
                            }
                            // Control de Excepciones
                        } catch (SQLException e) {
                            System.out.println(
                                    "Error al insertar datos de: Cielo, ProbabilidadChoiva, TemperaturasFranxa, Vento.");
                            e.printStackTrace();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al Insertar datos de una o varias predicciones.");
                        e.printStackTrace();
                    }
                });
                System.out.println("Datos de prueba insertados correctamente.");
                return true;
            } catch (SQLException e) {
                System.out.println("Error al preparar las consultas.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error al preparar las sentencias.");
            e.printStackTrace();
        }
        return false;
    }

    // Método estático para insertar datos de prueba de varias predicciones
    // ficticias pasando como parámetro una conexión a una BBDD.
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

    // Método estático para mostrar datos de las predicciones de un concello,
    // pasando como parámetro el id de un concello y un objeto conexión a una BBDD.
    public static void MostrarDatosTablasPorIdConcello(Connection conexion, int idConcello) {
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
                    statement.executeQuery("SELECT * FROM ProbabilidadeChoiva where idPrediccion = " + idConcello));

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
            System.out.println("Error al obtener datos de prediccion con idConcello: " + idConcello);
            e.printStackTrace();
        }
    }

    // Método que muestra los datos de un objeto ResulSet (Resultado de realizar una
    // consulta).
    private static void printResultSet(ResultSet resultSet) throws SQLException {
        // Iteramos sobre las filas del ResulSet y por cada fila vamos mostrando los
        // valores de las celdas.
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            // Por cada fila iteramos sobre cada columna, mostramos el valor y agregamos una
            // tabulación.
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            // Salto de línea al terminar de iterar sobre las columnas de una fila.
            System.out.println();
        }
        resultSet.close();
    }

}
