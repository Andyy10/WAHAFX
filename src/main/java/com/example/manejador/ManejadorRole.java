package com.example.manejador;

import com.example.database.DatabaseConnection;
import com.example.tablas.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorRole {
    
    public List<Role> leerRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM public.\"Ref_Roles\"";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Role role = new Role(
                    rs.getInt("role_code"),
                    rs.getString("role_name")
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar obtener roles: " + e.getMessage());
        }
        return roles;
    }
    
    public boolean addRole(Role role) {
        String sql = "INSERT INTO public.\"Ref_Roles\" (role_name) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, role.getRoleName());
            
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al añadir rol: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateRole(Role role) {
        String sql = "UPDATE public.\"Ref_Roles\" SET role_name = ? WHERE role_code = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, role.getRoleName());
            pstmt.setInt(2, role.getRoleCode());
            
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteRole(int roleCode) {
        String sql = "DELETE FROM public.\"Ref_Roles\" WHERE role_code = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, roleCode);
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
            return false;
        }
    }
}