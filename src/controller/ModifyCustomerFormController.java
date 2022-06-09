package controller;

import database.DbValidation;
import database.JDBC;
import database.Queries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilities.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class ModifyCustomerFormController implements Initializable {
    @FXML private Label customerAddressErrorMessage;
    @FXML private TextField customerAddressTextfield;
    @FXML private ComboBox<?> customerCountryCombo;
    @FXML private ComboBox<?> customerDivisionCombo;
    @FXML private Label customerIdErrorMessage;
    @FXML private TextField customerIdTextfield;
    @FXML private Label customerNameErrorMessage;
    @FXML private TextField customerNameTextfield;
    @FXML private Label customerPhoneErrorMessage;
    @FXML private TextField customerPhoneTextfield;
    @FXML private Label customerPostalErrorMessage;
    @FXML private TextField customerPostalTextfield;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        if (Alerts.confirmCancelBox()) {
            switchToScene(event, "/view/DatabaseForm.fxml");
        }
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {

        String userName = JDBC.getUserName();
        String customerName = customerNameTextfield.getText();
        String customerAddress = customerAddressTextfield.getText();
        String customerPostalCode = customerPostalTextfield.getText();
        String customerPhoneNumber = customerPhoneTextfield.getText();
        int divisionID = 10; // To be filled by combobox when that functionality is added.

        try {
            DbValidation.validateCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber);
            Queries.updateCustomer(9, customerName, customerAddress, customerPostalCode, customerPhoneNumber, userName, divisionID);
            switchToScene(event, "/view/DatabaseForm.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

    }
}
