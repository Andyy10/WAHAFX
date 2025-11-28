package com.example.controller;

import com.example.manejador.ManejadorRole;
import com.example.tablas.Role;
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

public class RoleController {
    
    @FXML private TableView<Role> roleTable;
    @FXML private TableColumn<Role, Integer> colRoleCode;
    @FXML private TableColumn<Role, String> colRoleName;
    
    @FXML private TextField tfRoleCode;
    @FXML private TextField tfRoleName;
    
    @FXML private Button buttonAñadir;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonLimpiar;
    @FXML private Button buttonVolver;
    
    private ManejadorRole roleService;
    private ObservableList<Role> roleList;
    
    @FXML
    private void initialize() {
        roleService = new ManejadorRole();
        roleList = FXCollections.observableArrayList();
        
        // Configure table columns
        colRoleCode.setCellValueFactory(new PropertyValueFactory<>("roleCode"));
        colRoleName.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        
        loadRoles();
        
        // Add selection listener
        roleTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showRoleDetails(newValue));
    }
    
    private void loadRoles() {
        roleList.clear();
        roleList.addAll(roleService.leerRoles());
        roleTable.setItems(roleList);
    }
    
    private void showRoleDetails(Role role) {
        if (role != null) {
            tfRoleCode.setText(String.valueOf(role.getRoleCode()));
            tfRoleName.setText(role.getRoleName());
        }
    }
    
    @FXML
    private void handleAddRole() {
        if (validateInput()) {
            Role role = new Role();
            role.setRoleName(tfRoleName.getText());
            
            if (roleService.addRole(role)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Rol agregado correctamente");
                loadRoles();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar el rol");
            }
        }
    }
    
    @FXML
    private void handleUpdateRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if (selectedRole != null && validateInput()) {
            selectedRole.setRoleName(tfRoleName.getText());
            
            if (roleService.updateRole(selectedRole)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Rol actualizado correctamente");
                loadRoles();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el rol");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un rol para actualizar");
        }
    }
    
    @FXML
    private void handleDeleteRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if (selectedRole != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar rol");
            alert.setContentText("¿Está seguro de que desea eliminar el rol: " + selectedRole.getRoleName() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (roleService.deleteRole(selectedRole.getRoleCode())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Rol eliminado correctamente");
                    loadRoles();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el rol");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un rol para eliminar");
        }
    }
    
    @FXML
    private void handleClear() {
        clearFields();
        roleTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleBack() {
        loadDashboard();
    }
    
    private boolean validateInput() {
        String errorMessage = "";
        
        if (tfRoleName.getText() == null || tfRoleName.getText().trim().isEmpty()) {
            errorMessage += "Nombre del rol es requerido!\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", errorMessage);
            return false;
        }
    }
    
    private void clearFields() {
        tfRoleCode.clear();
        tfRoleName.clear();
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
            System.out.println("aAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + e.getMessage());
        }
    }
}