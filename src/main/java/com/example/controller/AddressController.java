package com.example.controller;

import com.example.manejador.ManejadorAddress;
import com.example.tablas.Address;
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

public class AddressController {
    
    @FXML private TableView<Address> addressTable;
    @FXML private TableColumn<Address, Integer> colAddressId;
    @FXML private TableColumn<Address, String> colLine1;
    @FXML private TableColumn<Address, String> colTownCity;
    @FXML private TableColumn<Address, String> colStateProvince;
    
    @FXML private TextField tfAddressId;
    @FXML private TextField tfLine1;
    @FXML private TextField tfLine2;
    @FXML private TextField tfLine3;
    @FXML private TextField tfTownCity;
    @FXML private TextField tfStateProvince;
    @FXML private TextField tfCountryCode;
    
    @FXML private Button buttonAñadir;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonLimpiar;
    @FXML private Button buttonVolver;
    
    private ManejadorAddress manejadorAddress;
    private ObservableList<Address> addressList;
    
    @FXML
    private void initialize() {
        manejadorAddress = new ManejadorAddress();
        addressList = FXCollections.observableArrayList();
        
        // Configure table columns
        colAddressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        colLine1.setCellValueFactory(new PropertyValueFactory<>("line1"));
        colTownCity.setCellValueFactory(new PropertyValueFactory<>("townCity"));
        colStateProvince.setCellValueFactory(new PropertyValueFactory<>("stateProvince"));
        
        loadAddresses();
        
        // Add selection listener
        addressTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showAddressDetails(newValue));
    }
    
    private void loadAddresses() {
        addressList.clear();
        addressList.addAll(manejadorAddress.leerAddresses());
        addressTable.setItems(addressList);
    }
    
    private void showAddressDetails(Address address) {
        if (address != null) {
            tfAddressId.setText(String.valueOf(address.getAddressId()));
            tfLine1.setText(address.getLine1());
            tfLine2.setText(address.getLine2());
            tfLine3.setText(address.getLine3());
            tfTownCity.setText(address.getTownCity());
            tfStateProvince.setText(address.getStateProvince());
            tfCountryCode.setText(String.valueOf(address.getCountryCode()));
        }
    }
    
    @FXML
    private void handleAddAddress() {
        if (validateInput()) {
            Address address = new Address();
            address.setLine1(tfLine1.getText());
            address.setLine2(tfLine2.getText());
            address.setLine3(tfLine3.getText());
            address.setTownCity(tfTownCity.getText());
            address.setStateProvince(tfStateProvince.getText());
            address.setCountryCode(Integer.parseInt(tfCountryCode.getText()));
            
            if (manejadorAddress.addAddress(address)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Dirección agregada correctamente");
                loadAddresses();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar la dirección");
            }
        }
    }
    
    @FXML
    private void handleUpdateAddress() {
        Address selectedAddress = addressTable.getSelectionModel().getSelectedItem();
        if (selectedAddress != null && validateInput()) {
            selectedAddress.setLine1(tfLine1.getText());
            selectedAddress.setLine2(tfLine2.getText());
            selectedAddress.setLine3(tfLine3.getText());
            selectedAddress.setTownCity(tfTownCity.getText());
            selectedAddress.setStateProvince(tfStateProvince.getText());
            selectedAddress.setCountryCode(Integer.parseInt(tfCountryCode.getText()));
            
            if (manejadorAddress.updateAddress(selectedAddress)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Dirección actualizada correctamente");
                loadAddresses();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la dirección");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione una dirección para actualizar");
        }
    }
    
    @FXML
    private void handleDeleteAddress() {
        Address direccionSeleccionada = addressTable.getSelectionModel().getSelectedItem();
        if (direccionSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("Eliminar dirección");
            alert.setContentText("¿Está seguro de que desea eliminar la dirección: " + direccionSeleccionada.getLine1() + "?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (manejadorAddress.deleteAddress(direccionSeleccionada.getAddressId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Dirección eliminada correctamente");
                    loadAddresses();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la dirección");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione una dirección para eliminar");
        }
    }
    
    @FXML
    private void handleClear() {
        clearFields();
        addressTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleBack() {
        loadDashboard();
    }
    
    private boolean validateInput() {
        String mensajeError = "";
        
        if (tfLine1.getText() == null || tfLine1.getText().trim().isEmpty()) {
            mensajeError += "Línea 1 es requerida!\n";
        }
        if (tfTownCity.getText() == null || tfTownCity.getText().trim().isEmpty()) {
            mensajeError += "Ciudad es requerida!\n";
        }
        if (tfStateProvince.getText() == null || tfStateProvince.getText().trim().isEmpty()) {
            mensajeError += "Estado/Provincia es requerido!\n";
        }
        if (tfCountryCode.getText() == null || tfCountryCode.getText().trim().isEmpty()) {
            mensajeError += "Código de país es requerido!\n";
        }
        
        if (mensajeError.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", mensajeError);
            return false;
        }
    }
    
    private void clearFields() {
        tfAddressId.clear();
        tfLine1.clear();
        tfLine2.clear();
        tfLine3.clear();
        tfTownCity.clear();
        tfStateProvince.clear();
        tfCountryCode.clear();
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
            System.out.println("Error al cargar el dashboard: " + e.getMessage());
        }
    }
}