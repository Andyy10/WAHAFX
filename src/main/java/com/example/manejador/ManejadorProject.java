package com.example.manejador;

import com.example.database.DatabaseConnection;
import com.example.tablas.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorProject {

    public List<Project> leerEmployees() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM public.\"Projects\"";

        try (Connection conn = DatabaseConnection.abrirConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("project_id"),
                        rs.getInt("client_id"),
                        rs.getString("project_name"),
                        rs.getDate("project_start_date").toLocalDate(),
                        rs.getDate("project_end_date").toLocalDate(),
                        rs.getString("project_description"),
                        rs.getInt("budget")
                );
                if (rs.wasNull()) {
                    project.setBudget(null);
                }
                projects.add(project);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los proyectos: " + e.getMessage());
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
        return projects;
    }

    public boolean addProject(Project project) {
        String sql = "INSERT INTO public.\"Projects\" (client_id, project_name, project_start_date, project_end_date, project_description, budget) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, project.getClientId());
            pstmt.setString(2, project.getProjectName());
            pstmt.setDate(3, Date.valueOf(project.getProjectStartDate()));
            pstmt.setDate(4, Date.valueOf(project.getProjectEndDate()));
            pstmt.setString(5, project.getProjectDescription());
            if (project.getBudget() != null) {
                pstmt.setInt(6, project.getBudget());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al añadir proyecto: " + e.getMessage());
            return false;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean updateProject(Project project) {
        String sql = "UPDATE public.\"Projects\" SET client_id = ?, project_name = ?, project_start_date = ?, project_end_date = ?, project_description = ?, budget = ? WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, project.getClientId());
            pstmt.setString(2, project.getProjectName());
            pstmt.setDate(3, Date.valueOf(project.getProjectStartDate()));
            pstmt.setDate(4, Date.valueOf(project.getProjectEndDate()));
            pstmt.setString(5, project.getProjectDescription());
            if (project.getBudget() != null) {
                pstmt.setInt(6, project.getBudget());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }
            pstmt.setInt(7, project.getProjectId());

            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar proyecto: " + e.getMessage());
            return false;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM public.\"Projects\" WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, projectId);
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar poyecto: " + e.getMessage());
            return false;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }
}
