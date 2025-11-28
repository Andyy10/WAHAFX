package com.example.manejador;

import com.example.database.DatabaseConnection;
import com.example.tablas.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorSkill {
    
    public List<Skill> leerSkills() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM public.\"Ref_Skill\"";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Skill skill = new Skill(
                    rs.getInt("skill_code"),
                    rs.getString("skill_name")
                );
                skills.add(skill);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener skills: " + e.getMessage());
        }
        return skills;
    }
    
    public boolean addSkill(Skill skill) {
        String sql = "INSERT INTO public.\"Ref_Skill\" (skill_name) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skill.getSkillName());
            
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al añadir una skill: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateSkill(Skill skill) {
        String sql = "UPDATE public.\"Ref_Skill\" SET skill_name = ? WHERE skill_code = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skill.getSkillName());
            pstmt.setInt(2, skill.getSkillCode());
            
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar una skill: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteSkill(int skillCode) {
        String sql = "DELETE FROM public.\"Ref_Skill\" WHERE skill_code = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, skillCode);
            int registrosAfectados = pstmt.executeUpdate();
            return registrosAfectados > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar skill: " + e.getMessage());
            return false;
        }
    }
}