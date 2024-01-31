package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.Vento;

public class GestionMYSQL {

    public static void insertarConcello(
            int idConcello,
            String nome,
            Connection conn) {

        String insertString = "INSERT INTO prediccionconcellos.concellos VALUES (?,?)";

        try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
            if (!existeConcello(idConcello, conn)) {
                conn.setAutoCommit(false);
                insertStatement.setInt(1, idConcello);
                insertStatement.setString(2, nome);

                insertStatement.executeUpdate();
                conn.commit();
            }
            System.out.println("Insertado Concello (" + idConcello + "," + nome + ")");

        } catch (SQLException e) {
            System.err.println("Error al insertar Concello:");
            e.printStackTrace();
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

    public static boolean existeConcello(int idConcello, Connection conn) {
        String query = "SELECT COUNT(*) FROM prediccionconcellos.concellos WHERE idConcello = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idConcello);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("El concello con id: " + idConcello + " no existe.");
        }
        return false;
    }

    public static boolean existePrediccion(int idPrediccion, Connection conn) {
        String query = "SELECT COUNT(*) FROM prediccionconcellos.predicciones WHERE idPrediccion = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idPrediccion);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("La prediccion con id: " + idPrediccion + " no existe.");
        }
        return false;
    }

    public static void InsertarListaPrediccionesEnBBDDMysql(List<PrediccionConcello> listaPredicciones,
            Connection conn) {
        try {
            for (PrediccionConcello prediccion : listaPredicciones) {
                int idConcello = prediccion.getIdConcello();
                String nomeConcello = prediccion.getNome();

                if (!existeConcello(idConcello, conn)) {
                    insertarConcello(idConcello, nomeConcello, conn);
                }

                List<DiaPrediccion> listaDiaPredicciones = prediccion.getListaPredDiaConcello();
                for (DiaPrediccion dia : listaDiaPredicciones) {
                    String fechaPrediccion = dia.getDataPredicion();
                    Integer nivelAviso = (dia.getNivelAviso() != null) ? dia.getNivelAviso() : -1;

                    // Temperatura máxima y mínima
                    Integer tempMax = (dia.gettMax() != null) ? dia.gettMax() : -1;
                    Integer tempMin = dia.gettMin();

                    // Índice ultravioleta máximo
                    Integer indiceUV = dia.getUvMax();

                    // idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax
                    insertarPrediccionConcello(idConcello, fechaPrediccion, nivelAviso, tempMax, tempMin, indiceUV,
                            conn);

                    // get idPrediccion que se acaba de insertar
                    Integer idPrediccion = getIdPrediccion(idConcello, fechaPrediccion, nivelAviso, tempMax, tempMin,
                            indiceUV, conn);

                    if (idPrediccion != -1) {

                        // Datos Cielo
                        Cielo cielo = dia.getCeo();
                        Integer ceoManha = cielo.getManha();
                        Integer ceoTarde = cielo.getTarde();
                        Integer ceoNoite = cielo.getNoite();

                        insertaCielo(idPrediccion, ceoManha, ceoTarde, ceoNoite, conn);

                        // Probabilidad de choiva
                        ProbabilidadChoiva pchoiva = dia.getPchoiva();
                        Integer pChoivaManha = pchoiva.getManha();
                        Integer pChoivaTarde = pchoiva.getTarde();
                        Integer pChoivaNoite = pchoiva.getNoite();

                        insertaProbabilidadChoiva(idPrediccion, pChoivaManha, pChoivaTarde, pChoivaNoite, conn);

                        // Información del viento
                        Vento vento = dia.getVento();
                        Integer ventoManha = vento.getManha();
                        Integer ventoTarde = vento.getTarde();
                        Integer ventoNoite = vento.getNoite();

                        insertaVento(idPrediccion, ventoManha, ventoTarde, ventoNoite, conn);

                        System.out.println("Prediccion insertada en la BBDD MYSQL correctamente.\n\tidPrediccion: "
                                + idPrediccion);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error al insertar las predicciones en la base de datos.");
            e.printStackTrace();
        }

    }

    public static void insertarPrediccionConcello(
            int idConcello,
            String dataPredicion,
            int nivelAviso,
            int tMax,
            int tMin,
            int uvMax,
            Connection conn) {
        if (existeConcello(idConcello, conn)) {
            String insertString = "INSERT INTO prediccionconcellos.predicciones (idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                conn.setAutoCommit(false);
                insertStatement.setInt(1, idConcello);
                insertStatement.setString(2, dataPredicion);
                insertStatement.setInt(3, nivelAviso);
                insertStatement.setInt(4, tMax);
                insertStatement.setInt(5, tMin);
                insertStatement.setInt(6, uvMax);
                insertStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error al insertar la prediccion:");
                e.printStackTrace();
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

    public static void insertaCielo(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        if (existePrediccion(idPrediccion, conn)) {
            String insertString = "INSERT INTO prediccionconcellos.Cielo (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                conn.setAutoCommit(false);

                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);
                insertStatement.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error al insertar datos de cielo con idPrediccion: " + idPrediccion);
                e.printStackTrace();
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } finally {
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

    public static void insertaProbabilidadChoiva(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        if (existePrediccion(idPrediccion, conn)) {
            String insertString = "INSERT INTO prediccionconcellos.probabilidadchoiva (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                conn.setAutoCommit(false);
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);
                insertStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error al insertar datos de probabilidade choiva con idPrediccion: " + idPrediccion);
                e.printStackTrace();
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

    public static void insertaTemperaturasFranxa(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        if (existePrediccion(idPrediccion, conn)) {
            String insertString = "INSERT INTO prediccionconcellos.temperaturasfranxa (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                conn.setAutoCommit(false);
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);
                insertStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error al insertar datos de temperaturas franxa con idPrediccion: " + idPrediccion);
                e.printStackTrace();
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

    public static void insertaVento(
            int idPrediccion,
            int manha,
            int tarde,
            int noite,
            Connection conn) {
        if (existePrediccion(idPrediccion, conn)) {
            String insertString = "INSERT INTO prediccionconcellos.vento (idPrediccion, manha, tarde, noite) VALUES (?,?,?,?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertString)) {
                conn.setAutoCommit(false);
                insertStatement.setInt(1, idPrediccion);
                insertStatement.setInt(2, manha);
                insertStatement.setInt(3, tarde);
                insertStatement.setInt(4, noite);
                insertStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                System.err.println("Error al insertar datos de vento con idPrediccion: " + idPrediccion);
                e.printStackTrace();
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

    public static int getIdPrediccion(
            int idConcello,
            String dataPredicion,
            int nivelAviso,
            int tMax,
            int tMin,
            int uvMax,
            Connection conn) throws SQLException {
        String query = "select p.idPrediccion from prediccionconcellos.predicciones p where p.idConcello = ? and p.dataPredicion = ? and p.nivelAviso = ? and p.tMax = ? and p.tMin = ? and p.uvMax = ?;";
        int idPrediccion = -1;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idConcello);
            stmt.setString(2, dataPredicion);
            stmt.setInt(3, nivelAviso);
            stmt.setInt(4, tMax);
            stmt.setInt(5, tMin);
            stmt.setInt(6, uvMax);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idPrediccion = rs.getInt("idPrediccion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPrediccion;
    }

    /*
     * public static void insertarDepartamento(
     * Departamento dept) throws SQLException {
     * 
     * String insertString =
     * "INSERT INTO ejerciciosboletin.departamentos VALUES (?,?,?)";
     * Connection conn = establecerConexion();
     * 
     * try (PreparedStatement insertStatement = conn.prepareStatement(insertString))
     * {
     * if (!existeDepartamento(dept.getDeptNo())) {
     * conn.setAutoCommit(false);
     * 
     * insertStatement.setInt(1, dept.getDeptNo());
     * insertStatement.setString(2, dept.getdNombre());
     * insertStatement.setString(3, dept.getLoc());
     * 
     * insertStatement.executeUpdate();
     * conn.commit();
     * }
     * System.out.println("Insertado departamento (" + dept.getDeptNo() + "," +
     * dept.getdNombre() + ","
     * + dept.getLoc() + ")");
     * 
     * } catch (SQLException e) {
     * System.err.println("Error al insertar departamento:");
     * e.printStackTrace();
     * if (conn != null) {
     * try {
     * System.err.print("Transaction is being rolled back");
     * conn.rollback();
     * } catch (SQLException ex) {
     * ex.printStackTrace();
     * }
     * }
     * }
     * }
     */

    /*
     * public static void updateDepartamento(
     * Departamento dept) throws SQLException {
     * 
     * String updateString =
     * "UPDATE ejerciciosboletin.departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?;"
     * ;
     * Connection conn = establecerConexion();
     * 
     * try (PreparedStatement updateStatement = conn.prepareStatement(updateString))
     * {
     * if (existeDepartamento(dept.getDeptNo())) {
     * Departamento deptSinModificar = getDepartamento(dept.getDeptNo());
     * 
     * conn.setAutoCommit(false);
     * if (dept.getdNombre() != null) {
     * updateStatement.setString(1, dept.getdNombre());
     * } else {
     * updateStatement.setString(1, deptSinModificar.getdNombre());
     * }
     * if (dept.getLoc() != null) {
     * updateStatement.setString(2, dept.getLoc());
     * } else {
     * updateStatement.setString(2, deptSinModificar.getLoc());
     * }
     * updateStatement.setInt(3, dept.getDeptNo());
     * 
     * updateStatement.executeUpdate();
     * conn.commit();
     * System.out.println("Actualizado departamento con datos (" + dept.getDeptNo()
     * + "," + dept.getdNombre()
     * + "," + dept.getLoc() + ")");
     * } else {
     * System.out.
     * println("No existe un departamento con ese id de departamento en la base de datos se procedera a insertar un departamento nuevo."
     * );
     * insertarDepartamento(dept);
     * }
     * 
     * } catch (SQLException e) {
     * System.err.println("Error al insertar departamento:");
     * e.printStackTrace();
     * if (conn != null) {
     * try {
     * System.err.print("Transaction is being rolled back");
     * conn.rollback();
     * } catch (SQLException ex) {
     * ex.printStackTrace();
     * }
     * }
     * }
     * }
     * 
     * public static boolean deleteDepartamento(
     * int numeroDepartamento) throws SQLException {
     * 
     * String insertString =
     * "DELETE FROM ejerciciosboletin.departamentos WHERE dept_no = ?;";
     * Connection conn = establecerConexion();
     * 
     * try (PreparedStatement insertStatement = conn.prepareStatement(insertString))
     * {
     * if (existeDepartamento(numeroDepartamento)) {
     * conn.setAutoCommit(false);
     * 
     * insertStatement.setInt(1, numeroDepartamento);
     * 
     * insertStatement.executeUpdate();
     * conn.commit();
     * System.out.println(
     * "Departamento eliminado correctamente con numero de departamento: " +
     * numeroDepartamento);
     * return true;
     * }
     * 
     * } catch (SQLException e) {
     * System.err.println("Error al insertar departamento:");
     * e.printStackTrace();
     * if (conn != null) {
     * try {
     * System.err.print("Transaction is being rolled back");
     * conn.rollback();
     * } catch (SQLException ex) {
     * ex.printStackTrace();
     * }
     * }
     * }
     * return false;
     * 
     * }
     * 
     * public static int deleteDepartamento2(
     * int numeroDepartamento) throws SQLException {
     * 
     * String deleteString =
     * "DELETE FROM ejerciciosboletin.departamentos WHERE dept_no = ?;";
     * Connection conn = establecerConexion();
     * 
     * int filasAfectadas = 0;
     * 
     * try (PreparedStatement deleteStatement = conn.prepareStatement(deleteString))
     * {
     * if (existeDepartamento(numeroDepartamento)) {
     * conn.setAutoCommit(false);
     * 
     * deleteStatement.setInt(1, numeroDepartamento);
     * 
     * filasAfectadas = deleteStatement.executeUpdate();
     * conn.commit();
     * }
     * System.out
     * .println("Departamento eliminado correctamente con numero de departamento: "
     * + numeroDepartamento);
     * 
     * } catch (SQLException e) {
     * System.err.println("Error al insertar departamento:");
     * e.printStackTrace();
     * if (conn != null) {
     * try {
     * System.err.print("Transaction is being rolled back");
     * conn.rollback();
     * } catch (SQLException ex) {
     * ex.printStackTrace();
     * }
     * }
     * }
     * return filasAfectadas;
     * 
     * }
     * 
     * public static List<Departamento> getDepartamentos() throws SQLException {
     * List<Departamento> lista = new ArrayList();
     * String query = "select * from ejerciciosboletin.departamentos";
     * Connection conn = establecerConexion();
     * 
     * try (Statement stmt = conn.createStatement()) {
     * ResultSet rs = stmt.executeQuery(query);
     * while (rs.next()) {
     * String nombre = rs.getString("dnombre");
     * String localidad = rs.getString("loc");
     * int numero = rs.getInt("dept_no");
     * lista.add(new Departamento(numero, nombre, localidad));
     * }
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * return lista;
     * }
     * 
     * public static Departamento getDepartamento(int deptNumero) throws
     * SQLException {
     * String query =
     * "select * from ejerciciosboletin.departamentos WHERE dept_no = ?";
     * Connection conn = establecerConexion();
     * Departamento dept = null;
     * try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
     * preparedStatement.setInt(1, deptNumero);
     * 
     * try (ResultSet rs = preparedStatement.executeQuery()) {
     * if (rs.next()) {
     * String nombre = rs.getString("dnombre");
     * String localidad = rs.getString("loc");
     * int numero = rs.getInt("dept_no");
     * dept = new Departamento(numero, nombre, localidad);
     * }
     * }
     * 
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * return dept;
     * }
     * 
     * 
     * 
     */

}
