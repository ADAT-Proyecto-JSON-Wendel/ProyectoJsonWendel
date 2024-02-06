package adat_proyecto_json_wendel.gestion.bbdd.gestionPrediccionesBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.TemperaturasFranxa;
import adat_proyecto_json_wendel.model.Vento;

// Clase para gestionar las predicciones en una base de datos
public class GestionPredicciones {

    private static final String SELECT_CIELO_BY_PREDICCION = "SELECT * FROM cielo WHERE idPrediccion = ?";
    private static final String SELECT_PROBABILIDAD_CHOIVA_BY_PREDICCION = "SELECT * FROM probabilidadeChoiva WHERE idPrediccion = ?";
    private static final String SELECT_TEMPERATURAS_FRANXA_BY_PREDICCION = "SELECT * FROM temperaturasFranxa WHERE idPrediccion = ?";
    private static final String SELECT_VENTO_BY_PREDICCION = "SELECT * FROM vento WHERE idPrediccion = ?";

    /**
     * Guarda los datos de las predicciones en la base de datos.
     *
     * @param predicciones La lista de predicciones a guardar.
     * @param conn         La conexión a la base de datos.
     */
    public static void GuardarDatosPrediccionesEnBaseDeDatos(List<PrediccionConcello> predicciones, Connection conn) {
        try {
            // Determinamos el tipo de conexión (H2 o MySQL) basándose en el objeto
            // Connection
            String tipoConexion = conn.getMetaData().getURL().contains("mysql") ? "MySQL" : "H2";

            for (PrediccionConcello pr : predicciones) {
                if (pr != null) {
                    // Se obtiene el ID del concello
                    int idConcello = -1;
                    try {
                        idConcello = pr.getIdConcello();
                    } catch (Exception e) {
                    }

                    // Inserta los datos de la predicción en la base de datos
                    if (GestionPredicciones.insertarDatosPrediccion(conn, pr)) {
                        System.out.println(
                                "Guardada Prediccion con idConcello: " + idConcello + " en BBDD " + tipoConexion + ".");
                    } else {
                        System.out.println("Error al guardar Prediccion con idConcello: " + idConcello + " en BBDD "
                                + tipoConexion + ".");
                    }
                }
                try {
                    // Espera de 50 milisegundos entre cada predicción
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Error al guardar las predicciones.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar las predicciones.");
        }
    }

    /**
     * Muestra las predicciones almacenadas en la base de datos para una lista dada
     * de predicciones de concellos.
     * 
     * @param predicciones la lista de predicciones de concellos.
     * @param conn         la conexión a la base de datos.
     */
    public static void MostrarPrediccionesBBDDLista(List<PrediccionConcello> predicciones, Connection conn) {
        // Verificar si la conexión no es nula
        if (conn == null) {
            System.out.println("Error. La conexión no puede ser null para mostrar la lista de predicciones.");
        }
        // Verificar si la lista de predicciones no es nula
        if (predicciones != null) {
            try {
                // Iterar sobre cada predicción en la lista
                for (PrediccionConcello pr : predicciones) {
                    // Verificar si la predicción no es nula
                    if (pr != null) {
                        // Por cada predicción, mostrar los datos de las tablas relacionadas al concello
                        // llamando al método MostrarDatosTablasPorIdConcello pasando la conexión y el
                        // id del concello.
                        MostrarDatosTablasPorIdConcello(conn, pr.getIdConcello());
                    }
                    try {
                        // Realizar una espera en el hilo para no ejecutar las sentencias tan rápido.
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Error al pausar el hilo.");
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error en la lectura de las predicciones.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error. La lista de predicciones no puede ser null.");
        }
    }

    /**
     * Selecciona la base de datos especificada en la conexión proporcionada.
     * 
     * @param conn   la conexión a la base de datos.
     * @param dbName el nombre de la base de datos a seleccionar.
     * @return true si la operación de selección fue exitosa, false de lo contrario.
     */
    public static boolean seleccionarBaseDeDatos(Connection conn, String dbName) {
        // Verificar si la conexión no es nula
        if (conn != null) {
            try (Statement statement = conn.createStatement()) {
                // Ejecutar la instrucción SQL para cambiar a la base de datos especificada
                statement.executeUpdate("USE " + dbName);
                System.out.println("Base de datos seleccionada: " + dbName);
                return true; // Retorna true indicando que la operación fue exitosa
            } catch (SQLException e) {
                // Capturar y manejar cualquier excepción SQL que pueda ocurrir
                System.out.println("Error al seleccionar la base de datos: " + dbName);
                e.printStackTrace();
                return false; // Retorna false indicando que la operación no fue exitosa
            }
        } else {
            // Si la conexión es nula, imprimir un mensaje de error
            System.out.println("Error. La conexión no puede ser null.");
        }
        return false; // Retorna false si la conexión es nula
    }

    /**
     * Inserta un nuevo concello en la base de datos.
     *
     * @param idConcello el ID del concello a insertar.
     * @param nome       el nombre del concello a insertar.
     * @param conn       la conexión a la base de datos.
     */
    public static void insertarConcello(
            int idConcello,
            String nome,
            Connection conn) {

        // Verificar si la conexión no es nula
        if (conn != null) {

            // Definir la consulta SQL para insertar un nuevo concello en la base de datos
            String insertString = "INSERT INTO prediccionconcellos.concellos VALUES (?,?)";

            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Verificar si el concello no existe ya en la base de datos
                if (!existeConcello(idConcello, conn)) {
                    // Deshabilitar el modo de autocommit para iniciar una transacción
                    conn.setAutoCommit(false);

                    // Establecer los parámetros de la consulta preparada
                    insertStatement.setInt(1, idConcello);
                    insertStatement.setString(2, nome);

                    // Ejecutar la inserción
                    insertStatement.executeUpdate();

                    // Confirmar la transacción
                    conn.commit();
                }
                // Imprimir un mensaje indicando que el concello ha sido insertado correctamente
                System.out.println("Insertado Concello (" + idConcello + "," + nome + ")");

            } catch (SQLException e) {
                // Manejar cualquier excepción SQL
                System.err.println("Error al insertar Concello:");
                e.printStackTrace();
                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            // Imprimir un mensaje de error si la conexión es nula
            System.out.println("Error. La conexión no puede ser null.");
        }
    }

    /**
     * Comprueba si un concello con el ID especificado ya existe en la base de
     * datos.
     *
     * @param idConcello el ID del concello a verificar.
     * @param conn       la conexión a la base de datos.
     * @return true si el concello existe, false si no.
     */
    public static boolean existeConcello(int idConcello, Connection conn) {
        // Consulta SQL para contar el número de concellos con el ID especificado
        String query = "SELECT COUNT(*) FROM prediccionconcellos.concellos WHERE idConcello = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Establecer el parámetro del ID del concello en la consulta preparada
            preparedStatement.setInt(1, idConcello);
            // Ejecutar la consulta y obtener el resultado
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Si hay algún resultado asignamos el valor de la primera columna a la variable
                // count
                if (rs.next()) {
                    // Obtener el recuento de concellos encontrados
                    int count = rs.getInt(1);
                    // Si el recuento es mayor que 0, significa que el concello existe
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL e imprimir un mensaje de error
            System.out.println("El concello con id: " + idConcello + " no existe.");
        }
        // Si hubo un error o no se encontró ningún concello, devolver false
        return false;
    }

    /**
     * Comprueba si una predicción con el ID especificado ya existe en la base de
     * datos.
     *
     * @param idPrediccion el ID de la predicción a verificar.
     * @param conn         la conexión a la base de datos.
     * @return true si la predicción existe, false si no.
     */
    public static boolean existePrediccion(int idPrediccion, Connection conn) {
        // Consulta SQL para contar el número de predicciones con el ID especificado
        String query = "SELECT COUNT(*) FROM prediccionconcellos.predicciones WHERE idPrediccion = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Establecer el parámetro del ID de la predicción en la consulta preparada
            preparedStatement.setInt(1, idPrediccion);
            // Ejecutar la consulta y obtener el resultado
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Si hay algún resultado
                if (rs.next()) {
                    // Obtener el recuento de predicciones encontradas
                    int count = rs.getInt(1);
                    // Si el recuento es mayor que 0, significa que la predicción existe
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL e imprimir un mensaje de error
            System.out.println("La predicción con id: " + idPrediccion + " no existe.");
        }
        // Si hubo un error o no se encontró ninguna predicción, devolver false
        return false;
    }

    /**
     * Inserta una lista de predicciones en la base de datos MySQL.
     *
     * @param listaPredicciones la lista de predicciones a insertar.
     * @param conn              la conexión a la base de datos.
     */
    public static void InsertarListaPrediccionesEnBBDDMysql(List<PrediccionConcello> listaPredicciones,
            Connection conn) {
        try {
            // Iterar sobre cada predicción en la lista
            for (PrediccionConcello prediccion : listaPredicciones) {
                // Obtener el ID y el nombre del concello de la predicción
                int idConcello = prediccion.getIdConcello();
                String nomeConcello = prediccion.getNome();

                // Si el concello no existe en la base de datos, insertarlo primero
                if (!existeConcello(idConcello, conn)) {
                    insertarConcello(idConcello, nomeConcello, conn);
                }

                // Obtener la lista de predicciones diarias para el concello actual
                List<DiaPrediccion> listaDiaPredicciones = prediccion.getListaPredDiaConcello();
                for (DiaPrediccion dia : listaDiaPredicciones) {
                    // Obtener los datos de la predicción diaria
                    String fechaPrediccion = dia.getDataPredicion();
                    Integer nivelAviso = (dia.getNivelAviso() != null) ? dia.getNivelAviso() : -1;
                    Integer tempMax = (dia.gettMax() != null) ? dia.gettMax() : -1;
                    Integer tempMin = (dia.gettMin() != null) ? dia.gettMin() : -1;
                    Integer indiceUV = (dia.getUvMax() != null) ? dia.getUvMax() : -1;

                    // Insertar la predicción diaria del concello en la base de datos
                    insertarPrediccionConcello(idConcello, fechaPrediccion, nivelAviso, tempMax, tempMin, indiceUV,
                            conn);

                    // Obtener el ID de la predicción recién insertada
                    Integer idPrediccion = getIdPrediccion(idConcello, fechaPrediccion, nivelAviso, tempMax, tempMin,
                            indiceUV, conn);

                    // Si se pudo obtener el ID de la predicción
                    if (idPrediccion != -1) {
                        // Insertar información del cielo
                        Cielo cielo = dia.getCeo();
                        Integer ceoManha = (cielo.getManha() != null) ? cielo.getManha() : -1;
                        Integer ceoTarde = (cielo.getTarde() != null) ? cielo.getTarde() : -1;
                        Integer ceoNoite = (cielo.getNoite() != null) ? cielo.getNoite() : -1;
                        insertaCielo(idPrediccion, ceoManha, ceoTarde, ceoNoite, conn);

                        // Insertar probabilidad de lluvia
                        ProbabilidadChoiva pchoiva = dia.getPchoiva();
                        Integer pChoivaManha = (pchoiva.getManha() != null) ? pchoiva.getManha() : -1;
                        Integer pChoivaTarde = (pchoiva.getTarde() != null) ? pchoiva.getTarde() : -1;
                        Integer pChoivaNoite = (pchoiva.getNoite() != null) ? pchoiva.getNoite() : -1;
                        insertaProbabilidadChoiva(idPrediccion, pChoivaManha, pChoivaTarde, pChoivaNoite, conn);

                        // Insertar información del viento
                        Vento vento = dia.getVento();
                        Integer ventoManha = (vento.getManha() != null) ? vento.getManha() : -1;
                        Integer ventoTarde = (vento.getTarde() != null) ? vento.getTarde() : -1;
                        Integer ventoNoite = (vento.getNoite() != null) ? vento.getNoite() : -1;
                        insertaVento(idPrediccion, ventoManha, ventoTarde, ventoNoite, conn);

                        // Imprimir un mensaje de confirmación
                        System.out.println(
                                "Predicción insertada en la base de datos MySQL correctamente.\n\tidPrediccion: "
                                        + idPrediccion);
                    }
                }
            }
        } catch (Exception e) {
            // Manejar cualquier excepción e imprimir un mensaje de error
            System.out.println("Error al insertar las predicciones en la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Inserta una nueva predicción para un concello en la base de datos.
     *
     * @param idConcello    el ID del concello al que pertenece la predicción.
     * @param dataPredicion la fecha de la predicción.
     * @param nivelAviso    el nivel de aviso para la predicción.
     * @param tMax          la temperatura máxima pronosticada.
     * @param tMin          la temperatura mínima pronosticada.
     * @param uvMax         el índice UV máximo pronosticado.
     * @param conn          la conexión a la base de datos.
     */
    public static void insertarPrediccionConcello(
            int idConcello,
            String dataPredicion,
            int nivelAviso,
            int tMax,
            int tMin,
            int uvMax,
            Connection conn) {
        // Verificar si el concello existe en la base de datos
        if (existeConcello(idConcello, conn)) {
            // Definir la consulta SQL para insertar la predicción
            String insertString = "INSERT INTO prediccionconcellos.predicciones (idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Deshabilitar el modo de autocommit para iniciar una transacción
                conn.setAutoCommit(false);
                // Establecer los parámetros de la consulta preparada
                insertStatement.setInt(1, idConcello);
                insertStatement.setString(2, dataPredicion);
                insertStatement.setInt(3, nivelAviso);
                insertStatement.setInt(4, tMax);
                insertStatement.setInt(5, tMin);
                insertStatement.setInt(6, uvMax);
                // Ejecutar la inserción
                insertStatement.executeUpdate();
                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL e imprimir un mensaje de error
                System.err.println("Error al insertar la predicción:");
                e.printStackTrace();
                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Inserta información sobre el estado del cielo para una predicción específica
     * en la base de datos.
     *
     * @param idPrediccion el ID de la predicción asociada.
     * @param manha        el estado del cielo durante la mañana.
     * @param tarde        el estado del cielo durante la tarde.
     * @param noite        el estado del cielo durante la noche.
     * @param conn         la conexión a la base de datos.
     */
    public static void insertaCielo(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        // Verificar si la predicción asociada existe en la base de datos
        if (existePrediccion(idPrediccion, conn)) {
            // Definir la consulta SQL para insertar información sobre el estado del cielo
            String insertString = "INSERT INTO prediccionconcellos.Cielo (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Deshabilitar el modo de autocommit para iniciar una transacción
                conn.setAutoCommit(false);

                // Establecer los parámetros de la consulta preparada
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);

                // Ejecutar la inserción
                insertStatement.executeUpdate();

                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL e imprimir un mensaje de error
                System.err.println("Error al insertar datos de cielo con idPrediccion: " + idPrediccion);
                e.printStackTrace();

                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } finally {
                // Establecer el modo de autocommit nuevamente en true
                try {
                    if (conn != null) {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Inserta información sobre la probabilidad de lluvia para una predicción
     * específica en la base de datos.
     *
     * @param idPrediccion el ID de la predicción asociada.
     * @param manha        la probabilidad de lluvia durante la mañana.
     * @param tarde        la probabilidad de lluvia durante la tarde.
     * @param noite        la probabilidad de lluvia durante la noche.
     * @param conn         la conexión a la base de datos.
     */
    public static void insertaProbabilidadChoiva(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        // Verificar si la predicción asociada existe en la base de datos
        if (existePrediccion(idPrediccion, conn)) {
            // Definir la consulta SQL para insertar información sobre la probabilidad de
            // lluvia
            String insertString = "INSERT INTO prediccionconcellos.probabilidadchoiva (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Deshabilitar el modo de autocommit para iniciar una transacción
                conn.setAutoCommit(false);

                // Establecer los parámetros de la consulta preparada
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);

                // Ejecutar la inserción
                insertStatement.executeUpdate();

                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL e imprimir un mensaje de error
                System.err.println("Error al insertar datos de probabilidade choiva con idPrediccion: " + idPrediccion);
                e.printStackTrace();

                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Inserta información sobre las temperaturas por franja horaria para una
     * predicción específica en la base de datos.
     *
     * @param idPrediccion el ID de la predicción asociada.
     * @param manha        la temperatura durante la mañana.
     * @param tarde        la temperatura durante la tarde.
     * @param noite        la temperatura durante la noche.
     * @param conn         la conexión a la base de datos.
     */
    public static void insertaTemperaturasFranxa(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        // Verificar si la predicción asociada existe en la base de datos
        if (existePrediccion(idPrediccion, conn)) {
            // Definir la consulta SQL para insertar información sobre las temperaturas por
            // franja horaria
            String insertString = "INSERT INTO prediccionconcellos.temperaturasfranxa (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Deshabilitar el modo de autocommit para iniciar una transacción
                conn.setAutoCommit(false);

                // Establecer los parámetros de la consulta preparada
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);

                // Ejecutar la inserción
                insertStatement.executeUpdate();

                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL e imprimir un mensaje de error
                System.err.println("Error al insertar datos de temperaturas franxa con idPrediccion: " + idPrediccion);
                e.printStackTrace();

                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Inserta información sobre el viento para una predicción específica en la base
     * de datos.
     *
     * @param idPrediccion el ID de la predicción asociada.
     * @param manha        la velocidad del viento durante la mañana.
     * @param tarde        la velocidad del viento durante la tarde.
     * @param noite        la velocidad del viento durante la noche.
     * @param conn         la conexión a la base de datos.
     */
    public static void insertaVento(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        // Verificar si la predicción asociada existe en la base de datos
        if (existePrediccion(idPrediccion, conn)) {
            // Definir la consulta SQL para insertar información sobre el viento
            String insertString = "INSERT INTO prediccionconcellos.vento (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                // Deshabilitar el modo de autocommit para iniciar una transacción
                conn.setAutoCommit(false);

                // Establecer los parámetros de la consulta preparada
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);

                // Ejecutar la inserción
                insertStatement.executeUpdate();

                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL e imprimir un mensaje de error
                System.err.println("Error al insertar datos de vento con idPrediccion: " + idPrediccion);
                e.printStackTrace();

                // Realizar un rollback en caso de error para deshacer la transacción
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Obtiene el ID de una predicción según los parámetros especificados.
     *
     * @param idConcello    el ID del concello asociado a la predicción.
     * @param dataPredicion la fecha de la predicción.
     * @param nivelAviso    el nivel de aviso de la predicción.
     * @param tMax          la temperatura máxima de la predicción.
     * @param tMin          la temperatura mínima de la predicción.
     * @param uvMax         el índice UV máximo de la predicción.
     * @param conn          la conexión a la base de datos.
     * @return el ID de la predicción si se encuentra, -1 en caso contrario.
     * @throws SQLException si ocurre un error de SQL durante la ejecución de la
     *                      consulta.
     */
    public static int getIdPrediccion(
            int idConcello,
            String dataPredicion,
            int nivelAviso,
            int tMax,
            int tMin,
            int uvMax,
            Connection conn) throws SQLException {
        // Consulta SQL para obtener el ID de la predicción
        String query = "SELECT p.idPrediccion FROM prediccionconcellos.predicciones p WHERE p.idConcello = ? AND p.dataPredicion = ? AND p.nivelAviso = ? AND p.tMax = ? AND p.tMin = ? AND p.uvMax = ?";
        int idPrediccion = -1;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Establecer los parámetros de la consulta preparada
            stmt.setInt(1, idConcello);
            stmt.setString(2, dataPredicion);
            stmt.setInt(3, nivelAviso);
            stmt.setInt(4, tMax);
            stmt.setInt(5, tMin);
            stmt.setInt(6, uvMax);

            // Ejecutar la consulta y obtener el resultado
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idPrediccion = rs.getInt("idPrediccion");
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL e imprimir un mensaje de error
            e.printStackTrace();
        }
        return idPrediccion;
    }

    /**
     * Inserta una lista de predicciones en la base de datos.
     *
     * @param conn              la conexión a la base de datos.
     * @param listaPredicciones la lista de predicciones a insertar.
     */
    public static void insertaListaPredicciones(Connection conn, List<PrediccionConcello> listaPredicciones) {
        try {
            // Iterar sobre cada predicción en la lista y insertarla en la base de datos
            for (PrediccionConcello prediccionConcello : listaPredicciones) {
                insertarDatosPrediccion(conn, prediccionConcello);
            }
        } catch (Exception e) {
            // Manejar cualquier excepción e imprimir un mensaje de error
            System.out.println("Error al insertar las predicciones.");
            e.printStackTrace();
        }
    }

    /**
     * Inserta los datos de una predicción en la base de datos.
     *
     * @param conexion   la conexión a la base de datos.
     * @param prediccion la predicción a insertar.
     * @return true si la inserción fue exitosa, false de lo contrario.
     */
    public static boolean insertarDatosPrediccion(Connection conexion, PrediccionConcello prediccion) {
        try {
            // Consultas SQL para insertar datos en las tablas correspondientes
            String insertConcello = "INSERT INTO Concellos (idConcello, nome) VALUES (?, ?)";
            String insertPrediccion = "INSERT INTO Predicciones (idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?, ?, ?, ?, ?, ?)";
            String insertCielo = "INSERT INTO Cielo (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertProbabilidadChoiva = "INSERT INTO ProbabilidadeChoiva (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertTemperaturasFranxa = "INSERT INTO TemperaturasFranxa (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";
            String insertVento = "INSERT INTO Vento (idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?)";

            // Utilizar try-with-resources para manejar las consultas preparadas
            try (PreparedStatement stmtConcello = conexion.prepareStatement(insertConcello,
                    Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtPrediccion = conexion.prepareStatement(insertPrediccion,
                            Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtCielo = conexion.prepareStatement(insertCielo);
                    PreparedStatement stmtProbabilidadChoiva = conexion.prepareStatement(insertProbabilidadChoiva);
                    PreparedStatement stmtTemperaturasFranxa = conexion.prepareStatement(insertTemperaturasFranxa);
                    PreparedStatement stmtVento = conexion.prepareStatement(insertVento)) {

                // Insertar datos del concello si no existe previamente
                if (!existeConcello(prediccion.getIdConcello(), conexion)) {
                    stmtConcello.setInt(1, prediccion.getIdConcello());
                    stmtConcello.setString(2, prediccion.getNome());
                    stmtConcello.executeUpdate();
                }

                // Iterar sobre cada día de la predicción y insertar los datos correspondientes
                for (DiaPrediccion diaPrediccion : prediccion.getListaPredDiaConcello()) {
                    stmtPrediccion.setInt(1, prediccion.getIdConcello());
                    String dataPrediccion = diaPrediccion.getDataPredicion();
                    stmtPrediccion.setString(2, (dataPrediccion != null) ? dataPrediccion : "0000-00-00");
                    Integer nivelAviso = diaPrediccion.getNivelAviso();
                    stmtPrediccion.setInt(3, (nivelAviso != null) ? nivelAviso : -1);
                    Integer tMax = diaPrediccion.gettMax();
                    stmtPrediccion.setInt(4, (tMax != null) ? tMax : -1);
                    Integer tMin = diaPrediccion.gettMin();
                    stmtPrediccion.setInt(5, (tMin != null) ? tMin : -1);
                    Integer uvMax = diaPrediccion.getUvMax();
                    stmtPrediccion.setInt(6, (uvMax != null) ? uvMax : -1);
                    stmtPrediccion.executeUpdate();

                    // Obtener el ID generado para la predicción
                    try (ResultSet generatedKeys = stmtPrediccion.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idPrediccion = generatedKeys.getInt(1);

                            // Insertar datos en la tabla Cielo
                            stmtCielo.setInt(1, idPrediccion);
                            Cielo ceo = diaPrediccion.getCeo();
                            Integer manha = (ceo != null) ? ceo.getManha() : null;
                            stmtCielo.setInt(2, (manha != null) ? manha : -1);
                            Integer tarde = (ceo != null) ? ceo.getTarde() : null;
                            stmtCielo.setInt(3, (tarde != null) ? tarde : -1);
                            Integer noite = (ceo != null) ? ceo.getNoite() : null;
                            stmtCielo.setInt(4, (noite != null) ? noite : -1);
                            stmtCielo.executeUpdate();

                            // Insertar datos en la tabla ProbabilidadeChoiva
                            stmtProbabilidadChoiva.setInt(1, idPrediccion);
                            ProbabilidadChoiva probabilidadeChoiva = diaPrediccion.getPchoiva();
                            Integer manhaProbabilidadeChoiva = (probabilidadeChoiva != null)
                                    ? probabilidadeChoiva.getManha()
                                    : null;
                            stmtProbabilidadChoiva.setInt(2,
                                    (manhaProbabilidadeChoiva != null) ? manhaProbabilidadeChoiva : -1);
                            Integer tardeProbabilidadeChoiva = (probabilidadeChoiva != null)
                                    ? probabilidadeChoiva.getTarde()
                                    : null;
                            stmtProbabilidadChoiva.setInt(3,
                                    (tardeProbabilidadeChoiva != null) ? tardeProbabilidadeChoiva : -1);
                            Integer noiteProbabilidadeChoiva = (probabilidadeChoiva != null)
                                    ? probabilidadeChoiva.getNoite()
                                    : null;
                            stmtProbabilidadChoiva.setInt(4,
                                    (noiteProbabilidadeChoiva != null) ? noiteProbabilidadeChoiva : -1);
                            stmtProbabilidadChoiva.executeUpdate();

                            // Insertar datos en la tabla TemperaturasFranxa
                            stmtTemperaturasFranxa.setInt(1, idPrediccion);
                            TemperaturasFranxa temperaturasFranxa = diaPrediccion.getTmaxFranxa();
                            Integer manhaTemperaturasFranxa = (temperaturasFranxa != null)
                                    ? temperaturasFranxa.getManha()
                                    : null;
                            stmtTemperaturasFranxa.setInt(2,
                                    (manhaTemperaturasFranxa != null) ? manhaTemperaturasFranxa : -1);
                            Integer tardeTemperaturasFranxa = (temperaturasFranxa != null)
                                    ? temperaturasFranxa.getTarde()
                                    : null;
                            stmtTemperaturasFranxa.setInt(3,
                                    (tardeTemperaturasFranxa != null) ? tardeTemperaturasFranxa : -1);
                            Integer noiteTemperaturasFranxa = (temperaturasFranxa != null)
                                    ? temperaturasFranxa.getNoite()
                                    : null;
                            stmtTemperaturasFranxa.setInt(4,
                                    (noiteTemperaturasFranxa != null) ? noiteTemperaturasFranxa : -1);
                            stmtTemperaturasFranxa.executeUpdate();

                            // Insertar datos en la tabla Vento
                            stmtVento.setInt(1, idPrediccion);
                            Vento vento = diaPrediccion.getVento();
                            Integer manhaVento = (vento != null) ? vento.getManha() : null;
                            stmtVento.setInt(2, (manhaVento != null) ? manhaVento : -1);
                            Integer tardeVento = (vento != null) ? vento.getTarde() : null;
                            stmtVento.setInt(3, (tardeVento != null) ? tardeVento : -1);
                            Integer noiteVento = (vento != null) ? vento.getNoite() : null;
                            stmtVento.setInt(4, (noiteVento != null) ? noiteVento : -1);
                            stmtVento.executeUpdate();
                        }
                    } catch (SQLException e) {
                        // Manejar cualquier excepción relacionada con la obtención de las claves
                        // generadas
                        System.out.println("Error al obtener las claves generadas.");
                        e.printStackTrace();
                    }
                }
                return true;
            } catch (SQLException e) {
                // Manejar cualquier excepción relacionada con la preparación de las consultas
                System.out.println("Error al preparar las consultas.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Manejar cualquier excepción general
            System.out.println("Error al preparar las sentencias.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserta datos de prueba en la base de datos.
     *
     * @param conexion la conexión a la base de datos.
     */
    public static void insertarDatosPrueba(Connection conexion) {
        try {
            // Consultas SQL para insertar datos de prueba en las tablas correspondientes
            String insertConcello = "INSERT INTO Concellos (idConcello, nome) VALUES (?, ?)";
            String insertPrediccion = "INSERT INTO Predicciones (idPrediccion, idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String insertCielo = "INSERT INTO Cielo (idCielo, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
            String insertProbabilidadChoiva = "INSERT INTO ProbabilidadeChoiva (idProbabilidadeChoiva, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
            String insertTemperaturasFranxa = "INSERT INTO TemperaturasFranxa (idTemperaturasFranxa, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";
            String insertVento = "INSERT INTO Vento (idVento, idPrediccion, manha, tarde, noite) VALUES (?, ?, ?, ?, ?)";

            // Utilizar try-with-resources para manejar las consultas preparadas
            try (PreparedStatement stmtConcello = conexion.prepareStatement(insertConcello);
                    PreparedStatement stmtPrediccion = conexion.prepareStatement(insertPrediccion);
                    PreparedStatement stmtCielo = conexion.prepareStatement(insertCielo);
                    PreparedStatement stmtProbabilidadChoiva = conexion.prepareStatement(insertProbabilidadChoiva);
                    PreparedStatement stmtTemperaturasFranxa = conexion.prepareStatement(insertTemperaturasFranxa);
                    PreparedStatement stmtVento = conexion.prepareStatement(insertVento)) {

                // Insertar datos de prueba en las tablas correspondientes
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
                // Manejar cualquier excepción relacionada con la ejecución de las consultas
                System.out.println("Error al insertar datos de prueba.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Manejar cualquier excepción general
            System.out.println("Error al preparar las sentencias.");
            e.printStackTrace();
        }
    }

    public static void MostrarDatosTablasPorIdConcello(Connection conexion, int idConcello) {
        try {
            // Imprimir título
            System.out.println();
            System.out.println("------------\t\t\tPredicción con id: " + idConcello + "\t\t\t -----------------------");
    
            // Obtener la lista de predicciones para el concello dado
            List<DiaPrediccion> predicciones = obtenerPrediccionesPorConcello(conexion, idConcello);
    
            // Iterar sobre cada predicción y mostrar los resultados
            for (DiaPrediccion prediccion : predicciones) {
                // Mostrar datos de la predicción actual
                System.out.println("Datos de la predicción:");
                System.out.println("Fecha: " + prediccion.getDataPredicion());
                System.out.println("Nivel de aviso: " + prediccion.getNivelAviso());
                System.out.println("Temperatura máxima: " + prediccion.gettMax());
                System.out.println("Temperatura mínima: " + prediccion.gettMin());
                System.out.println("Índice ultravioleta máximo: " + prediccion.getUvMax());
    
                // Mostrar datos de la tabla Cielo
                System.out.println("Datos de la tabla Cielo:");
                Cielo cielo = prediccion.getCeo();
                System.out.println("Mañana\tTarde\tNoche");
                System.out.println(cielo.getManha() + "\t" + cielo.getTarde() + "\t" + cielo.getNoite());
    
                // Mostrar datos de la tabla ProbabilidadChoiva
                System.out.println("Datos de la tabla ProbabilidadChoiva:");
                ProbabilidadChoiva probabilidadChoiva = prediccion.getPchoiva();
                System.out.println("Mañana\tTarde\tNoche");
                System.out.println(probabilidadChoiva.getManha() + "\t" + probabilidadChoiva.getTarde() + "\t" + probabilidadChoiva.getNoite());
    
                // Mostrar datos de la tabla TemperaturasFranxa
                System.out.println("Datos de la tabla TemperaturasFranxa:");
                TemperaturasFranxa tmaxFranxa = prediccion.getTmaxFranxa();
                TemperaturasFranxa tminFranxa = prediccion.getTminFranxa();
                System.out.println("Temperatura máxima - Mañana\tTarde\tNoche");
                System.out.println(tmaxFranxa.getManha() + "\t" + tmaxFranxa.getTarde() + "\t" + tmaxFranxa.getNoite());
                System.out.println("Temperatura mínima - Mañana\tTarde\tNoche");
                System.out.println(tminFranxa.getManha() + "\t" + tminFranxa.getTarde() + "\t" + tminFranxa.getNoite());
    
                // Mostrar datos de la tabla Vento
                System.out.println("Datos de la tabla Vento:");
                Vento vento = prediccion.getVento();
                System.out.println("Mañana\tTarde\tNoche");
                System.out.println(vento.getManha() + "\t" + vento.getTarde() + "\t" + vento.getNoite());
    
                // Imprimir separador y salto de línea
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println();
            }
    
        } catch (Exception e) {
            // Manejar cualquier excepción relacionada con la ejecución de las consultas
            System.out.println("Error al obtener datos de predicción para el concello con ID: " + idConcello);
            e.printStackTrace();
        }
    }
    
    

    public static List<DiaPrediccion> obtenerPrediccionesPorConcello(Connection conexion, int idConcello) {
        List<DiaPrediccion> predicciones = new ArrayList<>();

        try {
            String query = "SELECT * FROM Predicciones WHERE idConcello = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, idConcello);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idPrediccion = resultSet.getInt("idPrediccion");
                String dataPredicion = resultSet.getString("dataPredicion");
                int nivelAviso = resultSet.getInt("nivelAviso");
                int tMax = resultSet.getInt("tMax");
                int tMin = resultSet.getInt("tMin");
                int uvMax = resultSet.getInt("uvMax");

                // Aquí haces consultas adicionales para obtener los datos de las tablas
                // relacionadas
                Cielo ceo = obtenerCieloPorPrediccion(conexion, idPrediccion);
                ProbabilidadChoiva pchoiva = obtenerProbabilidadChoivaPorPrediccion(conexion, idPrediccion);
                TemperaturasFranxa tmaxFranxa = obtenerTemperaturasFranxaPorPrediccion(conexion, idPrediccion);
                TemperaturasFranxa tminFranxa = obtenerTemperaturasFranxaPorPrediccion(conexion, idPrediccion);
                Vento vento = obtenerVentoPorPrediccion(conexion, idPrediccion);

                DiaPrediccion prediccion = new DiaPrediccion();
                prediccion.setCeo(ceo);
                prediccion.setDataPredicion(dataPredicion);
                prediccion.setNivelAviso(nivelAviso);
                prediccion.setPchoiva(pchoiva);
                prediccion.settMax(tMax);
                prediccion.settMin(tMin);
                prediccion.setTmaxFranxa(tmaxFranxa);
                prediccion.setTminFranxa(tminFranxa);
                prediccion.setUvMax(uvMax);
                prediccion.setVento(vento);

                predicciones.add(prediccion);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener las predicciones para el concello con ID: " + idConcello);
            e.printStackTrace();
        }

        return predicciones;
    }

    public static Cielo obtenerCieloPorPrediccion(Connection conexion, int idPrediccion) throws SQLException {
        PreparedStatement statement = conexion.prepareStatement(SELECT_CIELO_BY_PREDICCION);
        statement.setInt(1, idPrediccion);
        ResultSet resultSet = statement.executeQuery();
        Cielo cielo = null;
        if (resultSet.next()) {
            cielo = new Cielo();
            cielo.setManha(resultSet.getInt("manha"));
            cielo.setTarde(resultSet.getInt("tarde"));
            cielo.setNoite(resultSet.getInt("noite"));
        }
        resultSet.close();
        statement.close();
        return cielo;
    }

    public static ProbabilidadChoiva obtenerProbabilidadChoivaPorPrediccion(Connection conexion, int idPrediccion)
            throws SQLException {
        PreparedStatement statement = conexion.prepareStatement(SELECT_PROBABILIDAD_CHOIVA_BY_PREDICCION);
        statement.setInt(1, idPrediccion);
        ResultSet resultSet = statement.executeQuery();
        ProbabilidadChoiva probabilidadeChoiva = null;
        if (resultSet.next()) {
            probabilidadeChoiva = new ProbabilidadChoiva();
            probabilidadeChoiva.setManha(resultSet.getInt("manha"));
            probabilidadeChoiva.setTarde(resultSet.getInt("tarde"));
            probabilidadeChoiva.setNoite(resultSet.getInt("noite"));
        }
        resultSet.close();
        statement.close();
        return probabilidadeChoiva;
    }

    public static TemperaturasFranxa obtenerTemperaturasFranxaPorPrediccion(Connection conexion, int idPrediccion)
            throws SQLException {
        PreparedStatement statement = conexion.prepareStatement(SELECT_TEMPERATURAS_FRANXA_BY_PREDICCION);
        statement.setInt(1, idPrediccion);
        ResultSet resultSet = statement.executeQuery();
        TemperaturasFranxa temperaturasFranxa = null;
        if (resultSet.next()) {
            temperaturasFranxa = new TemperaturasFranxa();
            temperaturasFranxa.setManha(resultSet.getInt("manha"));
            temperaturasFranxa.setTarde(resultSet.getInt("tarde"));
            temperaturasFranxa.setNoite(resultSet.getInt("noite"));
        }
        resultSet.close();
        statement.close();
        return temperaturasFranxa;
    }

    public static Vento obtenerVentoPorPrediccion(Connection conexion, int idPrediccion) throws SQLException {
        PreparedStatement statement = conexion.prepareStatement(SELECT_VENTO_BY_PREDICCION);
        statement.setInt(1, idPrediccion);
        ResultSet resultSet = statement.executeQuery();
        Vento vento = null;
        if (resultSet.next()) {
            vento = new Vento();
            vento.setManha(resultSet.getInt("manha"));
            vento.setTarde(resultSet.getInt("tarde"));
            vento.setNoite(resultSet.getInt("noite"));
        }
        resultSet.close();
        statement.close();
        return vento;
    }

    /**
     * Imprime los datos de un ResultSet en la consola.
     *
     * @param resultSet el ResultSet que contiene los datos a imprimir.
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet.
     */
    private static void printResultSet(ResultSet resultSet) throws SQLException {
        // Iteramos sobre las filas del ResultSet y por cada fila vamos mostrando los
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
        // Cerramos el ResultSet después de usarlo para liberar recursos.
        resultSet.close();
    }

}
