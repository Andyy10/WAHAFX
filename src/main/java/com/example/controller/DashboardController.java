package com.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML private Button buttonEmployees;
    @FXML private Button buttonProjects;
    @FXML private Button buttonRoles;
    @FXML private Button buttonSkills;
    @FXML private Button buttonAddresses;

    @FXML
    private void initialize() {
        //no es necesario inicializarlo porque los botones ya están configurados en el fxml
    }

    @FXML
    private void handleEmployees() {
        loadView("/com/example/employees.fxml", "Gestión de Empleados");
    }

    @FXML
    private void handleProjects() {
        loadView("/com/example/projects.fxml", "Gestión de Proyectos");
    }

    @FXML
    private void handleRoles() {
        loadView("/com/example/roles.fxml", "Gestión de Roles");
    }

    @FXML
    private void handleSkills() {
        loadView("/com/example/skills.fxml", "Gestión de Habilidades");
    }

    @FXML
    private void handleAddresses() {
        loadView("/com/example/addresses.fxml", "Gestión de Direcciones");
    }

    private void loadView(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            //cerrar dashboard
            Stage currentStage = (Stage) buttonEmployees.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("aAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + e.getMessage());
        }
    }
}

