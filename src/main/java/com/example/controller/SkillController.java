package com.example.controller;

import com.example.manejador.ManejadorSkill;
import com.example.tablas.Skill;
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

public class SkillController {
    
    @FXML private TableView<Skill> skillTable;
    @FXML private TableColumn<Skill, Integer> colSkillCode;
    @FXML private TableColumn<Skill, String> colSkillName;
    
    @FXML private TextField tfSkillCode;
    @FXML private TextField tfSkillName;
    
    @FXML private Button buttonAñadir;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonLimpiar;
    @FXML private Button buttonVolver;
    
    private ManejadorSkill manejadorSkill;
    private ObservableList<Skill> skillObservableList;
    
    @FXML
    private void initialize() {
        manejadorSkill = new ManejadorSkill();
        skillObservableList = FXCollections.observableArrayList();
        
        // Configure table columns
        colSkillCode.setCellValueFactory(new PropertyValueFactory<>("skillCode"));
        colSkillName.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        
        loadSkills();
        
        // Add selection listener
        skillTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showSkillDetails(newValue));
    }
    
    private void loadSkills() {
        skillObservableList.clear();
        skillObservableList.addAll(manejadorSkill.leerSkills());
        skillTable.setItems(skillObservableList);
    }
    
    private void showSkillDetails(Skill skill) {
        if (skill != null) {
            tfSkillCode.setText(String.valueOf(skill.getSkillCode()));
            tfSkillName.setText(skill.getSkillName());
        }
    }
    
    @FXML
    private void handleAddSkill() {
        if (validateInput()) {
            Skill skill = new Skill();
            skill.setSkillName(tfSkillName.getText());
            
            if (manejadorSkill.addSkill(skill)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habilidad agregada correctamente");
                loadSkills();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar la habilidad");
            }
        }
    }
    
    @FXML
    private void handleUpdateSkill() {
        Skill selectedSkill = skillTable.getSelectionModel().getSelectedItem();
        if (selectedSkill != null && validateInput()) {
            selectedSkill.setSkillName(tfSkillName.getText());
            
            if (manejadorSkill.updateSkill(selectedSkill)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habilidad actualizada correctamente");
                loadSkills();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la habilidad");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione una habilidad para actualizar");
        }
    }
    
    @FXML
    private void handleDeleteSkill() {
        Skill selectedSkill = skillTable.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar habilidad");
            alert.setContentText("¿Está seguro de que desea eliminar la habilidad: " + selectedSkill.getSkillName() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (manejadorSkill.deleteSkill(selectedSkill.getSkillCode())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Habilidad eliminada correctamente");
                    loadSkills();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la habilidad");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione una habilidad para eliminar");
        }
    }
    
    @FXML
    private void handleClear() {
        clearFields();
        skillTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleBack() {
        loadDashboard();
    }
    
    private boolean validateInput() {
        String errorMessage = "";
        
        if (tfSkillName.getText() == null || tfSkillName.getText().trim().isEmpty()) {
            errorMessage += "Nombre de la habilidad es requerido!\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", errorMessage);
            return false;
        }
    }
    
    private void clearFields() {
        tfSkillCode.clear();
        tfSkillName.clear();
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
            System.out.println("aAAAAAAAAAAAAAAAAAAA: " + e.getMessage());
        }
    }
}