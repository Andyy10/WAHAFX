package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = "jdbc:postgresql://localhost:5433/working_at_home_db";
    private static String username = "developer";
    private static String password = "developer";

    public DatabaseConnection (String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static Connection conexion;

    public static Connection abrirConexion(){
        try {
            if (conexion == null || conexion.isClosed()) {
                try {
                    conexion = DriverManager.getConnection(url,username,password);
                } catch (SQLException e) {
                    System.out.println("Error al conectarse a la base de datos. JODETE. " + e.getMessage());
                    throw e;
                }
            }
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cerrarConexion() {
        //significa que hay una conexion
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión. JODETE: " + e.getMessage());
        }
    }
}
