package controller;

import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;
import utilities.Alerts;
import utilities.DateTimeHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;
import static database.Queries.getSelect;
import static database.Queries.insertAppointment;
import static utilities.DateTimeHelper.concatDateTime;

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
    @FXML private ComboBox<Contact> contactIdCombo;
    @FXML private Label contactIdErrorMessage;
    @FXML private Label customerAddressErrorMessage;
    @FXML private ComboBox<?> customerIdCombo;
    @FXML private Label customerIdErrorMessage;
    @FXML private Label customerNameErrorMessage;
    @FXML private Label customerPhoneErrorMessage;
    @FXML private Label customerPostalErrorMessage;
    @FXML private DatePicker endDatePicker;
    @FXML private Spinner<Integer> endHourSpinner;
    @FXML private Spinner<Integer> endMinuteSpinner;
    @FXML private Label endTimeErrorMessage;
    @FXML private DatePicker startDatePicker;
    @FXML private Spinner<Integer> startHourSpinner;
    @FXML private Spinner<Integer> startMinuteSpinner;
    @FXML private Label startTimeErrorMessage;
    @FXML private ComboBox<?> userIdCombo;
    @FXML private Label userIdErrorMessage;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

//    public int startHour;
//    public int startMinute;
//    public int endHour;
//    public int endMinute;


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
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();

        String startDateTime = concatDateTime(startDate,startHour,startMinute);
        String endDateTime = concatDateTime(endDate,endHour,endMinute);

        insertAppointment(apptTitle,apptDescription,apptLocation,apptType,userName,userName,customerID,userID,contactID, startDateTime, endDateTime);

        switchToScene(event, "/view/DatabaseForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

        SpinnerValueFactory<Integer> startHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,23, 12);
        startHourValueFactory.setWrapAround(true);
        startHourSpinner.setValueFactory(startHourValueFactory);

        SpinnerValueFactory<Integer> startMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,59, 0, 5);
        startMinuteValueFactory.setWrapAround(true);
        startMinuteSpinner.setValueFactory(startMinuteValueFactory);


        SpinnerValueFactory<Integer> endHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,23, 12);
        endHourValueFactory.setWrapAround(true);
        endHourSpinner.setValueFactory((endHourValueFactory));

        SpinnerValueFactory<Integer> endMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,59, 0, 5);
        endMinuteValueFactory.setWrapAround(true);
        endMinuteSpinner.setValueFactory(endMinuteValueFactory);

        try {
            ResultSet rs = getSelect();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contact contact = new Contact(contactId,contactName,contactEmail);
                contacts.add(contact);
            }
        }
        catch (SQLException e) {

        }

        contactIdCombo.getItems().addAll(contacts);
        contactIdCombo.



    }
}
