package com.example.manejador;

import com.example.database.DatabaseConnection;
import com.example.tablas.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorEmployee {

    public List<Employee> leerEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM public.\"Employees\"";

        try (Connection conn = DatabaseConnection.abrirConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("role_code"),
                        rs.getInt("supervisor_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error al tratar de obtener a los empleados: " + e.getMessage());
        }
        return employees;
    }

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO public.\"Employees\" (role_code, supervisor_id, first_name, last_name, age) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employee.getRoleCode());
            pstmt.setInt(2, employee.getSupervisorId());
            pstmt.setString(3, employee.getFirstName());
            pstmt.setString(4, employee.getLastName());
            pstmt.setInt(5, employee.getAge());

            int columnasAfectadas = pstmt.executeUpdate();
            return columnasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al añadir empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE public.\"Employees\" SET role_code = ?, supervisor_id = ?, first_name = ?, last_name = ?, age = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employee.getRoleCode());
            pstmt.setInt(2, employee.getSupervisorId());
            pstmt.setString(3, employee.getFirstName());
            pstmt.setString(4, employee.getLastName());
            pstmt.setInt(5, employee.getAge());
            pstmt.setInt(6, employee.getEmployeeId());

            int columnasAfectadas = pstmt.executeUpdate();
            return columnasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM public.\"Employees\" WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            int columnasAfectadas = pstmt.executeUpdate();
            return columnasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar al empleado: " + e.getMessage());
            return false;
        }
    }
}
