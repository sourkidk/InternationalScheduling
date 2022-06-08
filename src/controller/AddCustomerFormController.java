package controller;

import database.JDBC;
import database.Queries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class AddCustomerFormController implements Initializable {

    @FXML private TextField customerIdTextfield;
    @FXML private TextField customerNameTextfield;
    @FXML private TextField customerAddressTextfield;
    @FXML private TextField customerPostalTextfield;
    @FXML private TextField customerPhoneTextfield;
    @FXML private ComboBox<?> customerCountryCombo;
    @FXML private ComboBox<?> customerDivisionCombo;
    @FXML private Label customerIdErrorMessage;
    @FXML private Label customerNameErrorMessage;
    @FXML private Label customerAddressErrorMessage;
    @FXML private Label customerPhoneErrorMessage;
    @FXML private Label customerPostalErrorMessage;

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException, SQLException {
        String userName = JDBC.getUserName();
        String customerName = customerNameTextfield.getText();
        String customerAddress = customerAddressTextfield.getText();
        String customerPostalCode = customerPostalTextfield.getText();
        String customerPhoneNumber = customerPhoneTextfield.getText();
        int divisionID = 10; // To be filled by combobox when that functionality is added.




        int rowsAffected = Queries.insertCustomer(customerName,customerAddress,customerPostalCode,customerPhoneNumber,userName,userName,divisionID);

        if (rowsAffected > 0) {
            System.out.println("Update Successful! " + rowsAffected + " rows affected!");
        }
        else {
            System.out.println("Update Failed...");
        }

        JDBC.closeConnection();
        switchToScene(event, "/view/DatabaseForm.fxml");

    }

    @FXML void onActionCancel(ActionEvent event) throws IOException {
        JDBC.closeConnection();
        switchToScene(event, "/view/DatabaseForm.fxml");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

    }
}
