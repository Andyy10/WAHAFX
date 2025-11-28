package com.example.manejador;

import com.example.database.DatabaseConnection;
import com.example.tablas.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorAddress {
    
    public List<Address> leerAddresses() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM public.\"Addresess\"";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Address address = new Address(
                    rs.getInt("adress_id"),
                    rs.getString("line_1"),
                    rs.getString("line_2"),
                    rs.getString("line_3"),
                    rs.getString("town_city"),
                    rs.getString("state_province"),
                    rs.getInt("country_code")
                );
                addresses.add(address);
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar leer las direcciones: " + e.getMessage());
        }
        return addresses;
    }
    
    public boolean addAddress(Address address) {
        String sql = "INSERT INTO public.\"Addresess\" (line_1, line_2, line_3, town_city, state_province, country_code) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, address.getLine1());
            pstmt.setString(2, address.getLine2());
            pstmt.setString(3, address.getLine3());
            pstmt.setString(4, address.getTownCity());
            pstmt.setString(5, address.getStateProvince());
            pstmt.setInt(6, address.getCountryCode());
            
            int columnasAfectadas = pstmt.executeUpdate();
            return columnasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error a añadir dirección: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateAddress(Address address) {
        String sql = "UPDATE public.\"Addresess\" SET line_1 = ?, line_2 = ?, line_3 = ?, town_city = ?, state_province = ?, country_code = ? WHERE adress_id = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, address.getLine1());
            pstmt.setString(2, address.getLine2());
            pstmt.setString(3, address.getLine3());
            pstmt.setString(4, address.getTownCity());
            pstmt.setString(5, address.getStateProvince());
            pstmt.setInt(6, address.getCountryCode());
            pstmt.setInt(7, address.getAddressId());
            
            int columasAfectadas = pstmt.executeUpdate();
            return columasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar dirección: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteAddress(int addressId) {
        String sql = "DELETE FROM public.\"Addresess\" WHERE adress_id = ?";
        
        try (Connection conn = DatabaseConnection.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, addressId);
            int columnasAfectadas = pstmt.executeUpdate();
            return columnasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar dirección: " + e.getMessage());
            return false;
        }
    }
}