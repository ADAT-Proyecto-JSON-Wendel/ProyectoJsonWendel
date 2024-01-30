package adat_proyecto_json_wendel.gestion.BBDD.MYSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionMYSQL {

    public static void insertarConcello(
            int idConcello,
            String nome,
            Connection conn) throws SQLException {

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

    public static boolean existeConcello(int idConcello, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM prediccionconcellos.concellos WHERE idConcello = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idConcello);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
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
