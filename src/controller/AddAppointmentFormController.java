package controller;

import database.JDBC;
import database.Queries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import utilities.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class AddAppointmentFormController implements Initializable {

    @FXML private Label appointmentDescriptionErrorMessage;
    @FXML private TextField appointmentDescriptionTextfield;
    @FXML private Label appointmentIdErrorMessage;
    @FXML private TextField appointmentIdTextfield;
    @FXML private Label appointmentLocationErrorMessage;
    @FXML private TextField appointmentLocationTextfield;
    @FXML private Label appointmentTitleErrorMessage;
    @FXML private TextField appointmentTitleTextfield;
    @FXML private Label appointmentTypeErrorMessage;
    @FXML private TextField appointmentTypeTextfield;
    @FXML private ComboBox<?> contactIdCombo;
    @FXML private Label contactIdErrorMessage;
    @FXML private Label customerAddressErrorMessage;
    @FXML private ComboBox<?> customerIdCombo;
    @FXML private Label customerIdErrorMessage;
    @FXML private Label customerNameErrorMessage;
    @FXML private Label customerPhoneErrorMessage;
    @FXML private Label customerPostalErrorMessage;
    @FXML private DatePicker endDatePicker;
    @FXML private Spinner<?> endHourSpinner;
    @FXML private Spinner<?> endMinuteSpinner;
    @FXML private Label endTimeErrorMessage;
    @FXML private DatePicker startDatePicker;
    @FXML private Spinner<?> startHourSpinner;
    @FXML private Spinner<?> startMinuteSpinner;
    @FXML private Label startTimeErrorMessage;
    @FXML private ComboBox<?> userIdCombo;
    @FXML private Label userIdErrorMessage;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        if (Alerts.confirmCancelBox()) {
            switchToScene(event, "/view/DatabaseForm.fxml");
        }
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException, SQLException {
        String userName = JDBC.getUserName();
        String apptTitle = appointmentTitleTextfield.getText();
        String apptDescription = appointmentDescriptionTextfield.getText();
        String apptLocation = appointmentLocationTextfield.getText();
        String apptType = appointmentTypeTextfield.getText();
        int customerID = 1;
        int userID = 1;
        int contactID = 1;

        Queries.insertAppointment(apptTitle,apptDescription,apptLocation,apptType,userName,userName,customerID,userID,contactID);
        switchToScene(event, "/view/DatabaseForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();
    }
}
