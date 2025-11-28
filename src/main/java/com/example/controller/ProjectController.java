package com.example.controller;

import com.example.manejador.ManejadorProject;
import com.example.tablas.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ProjectController {
    
    @FXML private TableView<Project> projectTable;
    @FXML private TableColumn<Project, Integer> colProjectId;
    @FXML private TableColumn<Project, Integer> colClientId;
    @FXML private TableColumn<Project, String> colProjectName;
    @FXML private TableColumn<Project, LocalDate> colStartDate;
    @FXML private TableColumn<Project, LocalDate> colEndDate;
    @FXML private TableColumn<Project, Integer> colBudget;
    
    @FXML private TextField tfProjectId;
    @FXML private TextField tfClientId;
    @FXML private TextField tfProjectName;
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private TextField tfBudget;
    @FXML private TextArea taDescription;
    
    @FXML private Button buttonAñadir;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonLimpiar;
    @FXML private Button buttonVolver;
    
    private ManejadorProject manejadorProject;
    private ObservableList<Project> projectObservableList;
    
    @FXML
    private void initialize() {
        manejadorProject = new ManejadorProject();
        projectObservableList = FXCollections.observableArrayList();
        
        // Configure table columns
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("projectStartDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("projectEndDate"));
        colBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        
        loadProjects();
        
        // Add selection listener
        projectTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showProjectDetails(newValue));
    }
    
    private void loadProjects() {
        projectObservableList.clear();
        projectObservableList.addAll(manejadorProject.leerEmployees());
        projectTable.setItems(projectObservableList);
    }
    
    private void showProjectDetails(Project project) {
        if (project != null) {
            tfProjectId.setText(String.valueOf(project.getProjectId()));
            tfClientId.setText(String.valueOf(project.getClientId()));
            tfProjectName.setText(project.getProjectName());
            dpStartDate.setValue(project.getProjectStartDate());
            dpEndDate.setValue(project.getProjectEndDate());
            taDescription.setText(project.getProjectDescription());
            if (project.getBudget() != null) {
                tfBudget.setText(String.valueOf(project.getBudget()));
            } else {
                tfBudget.clear();
            }
        }
    }
    
    @FXML
    private void handleAddProject() {
        if (validateInput()) {
            Project project = new Project();
            project.setClientId(Integer.parseInt(tfClientId.getText()));
            project.setProjectName(tfProjectName.getText());
            project.setProjectStartDate(dpStartDate.getValue());
            project.setProjectEndDate(dpEndDate.getValue());
            project.setProjectDescription(taDescription.getText());
            
            if (!tfBudget.getText().isEmpty()) {
                project.setBudget(Integer.parseInt(tfBudget.getText()));
            }
            
            if (manejadorProject.addProject(project)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proyecto agregado correctamente");
                loadProjects();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar el proyecto");
            }
        }
    }
    
    @FXML
    private void handleUpdateProject() {
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null && validateInput()) {
            selectedProject.setClientId(Integer.parseInt(tfClientId.getText()));
            selectedProject.setProjectName(tfProjectName.getText());
            selectedProject.setProjectStartDate(dpStartDate.getValue());
            selectedProject.setProjectEndDate(dpEndDate.getValue());
            selectedProject.setProjectDescription(taDescription.getText());
            
            if (!tfBudget.getText().isEmpty()) {
                selectedProject.setBudget(Integer.parseInt(tfBudget.getText()));
            } else {
                selectedProject.setBudget(null);
            }
            
            if (manejadorProject.updateProject(selectedProject)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proyecto actualizado correctamente");
                loadProjects();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el proyecto");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un proyecto para actualizar");
        }
    }
    
    @FXML
    private void handleDeleteProject() {
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar proyecto");
            alert.setContentText("¿Está seguro de que desea eliminar el proyecto: " + selectedProject.getProjectName() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (manejadorProject.deleteProject(selectedProject.getProjectId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proyecto eliminado correctamente");
                    loadProjects();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el proyecto");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un proyecto para eliminar");
        }
    }
    
    @FXML
    private void handleClear() {
        clearFields();
        projectTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleBack() {
        loadDashboard();
    }
    
    private boolean validateInput() {
        String errorMessage = "";
        
        if (tfClientId.getText() == null || tfClientId.getText().trim().isEmpty()) {
            errorMessage += "ID del cliente es requerido!\n";
        } else {
            try {
                Integer.parseInt(tfClientId.getText());
            } catch (NumberFormatException e) {
                errorMessage += "ID del cliente debe ser un número válido!\n";
            }
        }
        
        if (tfProjectName.getText() == null || tfProjectName.getText().trim().isEmpty()) {
            errorMessage += "Nombre del proyecto es requerido!\n";
        }
        
        if (dpStartDate.getValue() == null) {
            errorMessage += "Fecha de inicio es requerida!\n";
        }
        
        if (dpEndDate.getValue() == null) {
            errorMessage += "Fecha de fin es requerida!\n";
        }
        
        if (dpStartDate.getValue() != null && dpEndDate.getValue() != null && 
            dpStartDate.getValue().isAfter(dpEndDate.getValue())) {
            errorMessage += "La fecha de inicio no puede ser después de la fecha de fin!\n";
        }
        
        if (!tfBudget.getText().isEmpty()) {
            try {
                Integer.parseInt(tfBudget.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Presupuesto debe ser un número válido!\n";
            }
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", errorMessage);
            return false;
        }
    }
    
    private void clearFields() {
        tfProjectId.clear();
        tfClientId.clear();
        tfProjectName.clear();
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
        tfBudget.clear();
        taDescription.clear();
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fxml/dashboard.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Dashboard - Working at Home");
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage currentStage = (Stage) buttonVolver.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("aAAAAAAAAAAAAAAAAAAAA: " + e.getMessage());
        }
    }
}