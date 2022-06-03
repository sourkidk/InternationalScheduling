package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static controller.SceneController.switchToScene;

public class AddCustomerFormController {

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


    @FXML void onActionCancel(ActionEvent event) throws IOException {
        switchToScene(event, "/view/DatabaseForm.fxml");

    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {

        switchToScene(event, "/view/DatabaseForm.fxml");

    }

}
