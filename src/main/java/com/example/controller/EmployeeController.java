package com.example.controller;

import com.example.manejador.ManejadorEmployee;
import com.example.tablas.Employee;
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

public class EmployeeController {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> colId;
    @FXML private TableColumn<Employee, Integer> colRoleCode;
    @FXML private TableColumn<Employee, Integer> colSupervisorId;
    @FXML private TableColumn<Employee, String> colFirstName;
    @FXML private TableColumn<Employee, String> colLastName;
    @FXML private TableColumn<Employee, Integer> colAge;

    @FXML private TextField tfEmployeeId;
    @FXML private TextField tfRoleCode;
    @FXML private TextField tfSupervisorId;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfAge;

    @FXML private Button buttonAñadir;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonLimpiar;
    @FXML private Button buttonVolver;

    private ManejadorEmployee manejadorEmployee;
    private ObservableList<Employee> employeeObservableList;

    @FXML
    private void initialize() {
        manejadorEmployee = new ManejadorEmployee();
        employeeObservableList = FXCollections.observableArrayList();

        // Configure table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colRoleCode.setCellValueFactory(new PropertyValueFactory<>("roleCode"));
        colSupervisorId.setCellValueFactory(new PropertyValueFactory<>("supervisorId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        loadEmployees();

        // Add selection listener
        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEmployeeDetails(newValue));
    }

    private void loadEmployees() {
        employeeObservableList.clear();
        employeeObservableList.addAll(manejadorEmployee.leerEmployees());
        employeeTable.setItems(employeeObservableList);
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            tfEmployeeId.setText(String.valueOf(employee.getEmployeeId()));
            tfRoleCode.setText(String.valueOf(employee.getRoleCode()));
            tfSupervisorId.setText(String.valueOf(employee.getSupervisorId()));
            tfFirstName.setText(employee.getFirstName());
            tfLastName.setText(employee.getLastName());
            tfAge.setText(String.valueOf(employee.getAge()));
        }
    }

    @FXML
    private void handleAddEmployee() {
        if (validateInput()) {
            Employee employee = new Employee();
            employee.setRoleCode(Integer.parseInt(tfRoleCode.getText()));
            employee.setSupervisorId(Integer.parseInt(tfSupervisorId.getText()));
            employee.setFirstName(tfFirstName.getText());
            employee.setLastName(tfLastName.getText());
            employee.setAge(Integer.parseInt(tfAge.getText()));

            if (manejadorEmployee.addEmployee(employee)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Empleado agregado correctamente");
                loadEmployees();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar el empleado");
            }
        }
    }

    @FXML
    private void handleUpdateEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null && validateInput()) {
            selectedEmployee.setRoleCode(Integer.parseInt(tfRoleCode.getText()));
            selectedEmployee.setSupervisorId(Integer.parseInt(tfSupervisorId.getText()));
            selectedEmployee.setFirstName(tfFirstName.getText());
            selectedEmployee.setLastName(tfLastName.getText());
            selectedEmployee.setAge(Integer.parseInt(tfAge.getText()));

            if (manejadorEmployee.updateEmployee(selectedEmployee)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Empleado actualizado correctamente");
                loadEmployees();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el empleado");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un empleado para actualizar");
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar empleado");
            alert.setContentText("¿Está seguro de que desea eliminar al empleado: " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + "?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                if (manejadorEmployee.deleteEmployee(selectedEmployee.getEmployeeId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Empleado eliminado correctamente");
                    loadEmployees();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el empleado");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un empleado para eliminar");
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
        employeeTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
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
            System.out.println("Error al cargar el dashboard: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (tfFirstName.getText() == null || tfFirstName.getText().trim().isEmpty()) {
            errorMessage += "Nombre es requerido!\n";
        }
        if (tfLastName.getText() == null || tfLastName.getText().trim().isEmpty()) {
            errorMessage += "Apellido es requerido!\n";
        }
        if (tfRoleCode.getText() == null || tfRoleCode.getText().trim().isEmpty()) {
            errorMessage += "Código de rol es requerido!\n";
        }
        if (tfSupervisorId.getText() == null || tfSupervisorId.getText().trim().isEmpty()) {
            errorMessage += "ID de supervisor es requerido!\n";
        }
        if (tfAge.getText() == null || tfAge.getText().trim().isEmpty()) {
            errorMessage += "Edad es requerida!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", errorMessage);
            return false;
        }
    }

    private void clearFields() {
        tfEmployeeId.clear();
        tfRoleCode.clear();
        tfSupervisorId.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfAge.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}