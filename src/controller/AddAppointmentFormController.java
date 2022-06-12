package controller;

import database.DbValidation;
import database.JDBC;
import database.Queries;
import database.DbValidation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Contact;
import model.Customer;
import model.User;
import utilities.Alerts;
import utilities.DateTimeHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


import static controller.SceneController.switchToScene;
import static database.DbValidation.*;
import static database.Queries.*;
import static utilities.DateTimeHelper.concatDateTime;
import static utilities.DateTimeHelper.formatTime;

public class AddAppointmentFormController implements Initializable {

    @FXML private TextField appointmentDescriptionTextfield;
    @FXML private TextField appointmentIdTextfield;
    @FXML private TextField appointmentLocationTextfield;
    @FXML private TextField appointmentTitleTextfield;
    @FXML private TextField appointmentTypeTextfield;
    @FXML private ComboBox<Contact> contactIdCombo;
    @FXML private ComboBox<Customer> customerIdCombo;
    @FXML private DatePicker endDatePicker;
    @FXML private Spinner<Integer> endHourSpinner;
    @FXML private Spinner<Integer> endMinuteSpinner;
    @FXML private DatePicker startDatePicker;
    @FXML private Spinner<Integer> startHourSpinner;
    @FXML private Spinner<Integer> startMinuteSpinner;
    @FXML private ComboBox<User> userIdCombo;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();

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
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        boolean validDates = false;
        boolean validTimes = false;
        boolean validCombos = false;

        if (  startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
            Alerts.dialogBox("Invalid Date Input", "Improper Date Values", "Please enter valid values for start and end date.  " +
                    "Start date must be today or later.");
        }
        else {validDates = true;}



        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();

//        System.out.println(DateTimeHelper.formatTime(startHour,startMinute));

        LocalTime startTime = LocalTime.parse(formatTime(startHour,startMinute));
        LocalTime endTime = LocalTime.parse(formatTime(endHour,endMinute));

        if ( startHour == endHour && startMinute >= endMinute || startHour > endHour ) {
            Alerts.dialogBox("Invalid Time Input", "Blank Time Values", "Please enter valid values for start and end time.");
        }
        else { validTimes = true;}


        String startDateTime = concatDateTime(startDate, startHour, startMinute);
        String endDateTime = concatDateTime(endDate, endHour, endMinute);



        if ( userIdCombo.getValue() == null || customerIdCombo.getValue() == null || contactIdCombo.getValue() == null) {
            Alerts.dialogBox("Invalid Input","Input Fields Blank", "Please select an option for each field.");
        }
        else {validCombos = true;}

        int userID = userIdCombo.getValue().getUserID();
        int customerID = customerIdCombo.getValue().getCustomerId();
        int contactID = contactIdCombo.getValue().getContactID();


        if ((validateAppointment(apptTitle, apptDescription, apptLocation, apptType, userName)) && validDates && validTimes && validCombos) {
            insertAppointment(apptTitle, apptDescription, apptLocation, apptType, userName, userName, customerID, userID, contactID, startDateTime, endDateTime);
            switchToScene(event, "/view/DatabaseForm.fxml");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

        SpinnerValueFactory<Integer> startHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, 12);
        startHourValueFactory.setWrapAround(true);
        startHourSpinner.setValueFactory(startHourValueFactory);

        SpinnerValueFactory<Integer> startMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, 0, 5);
        startMinuteValueFactory.setWrapAround(true);
        startMinuteSpinner.setValueFactory(startMinuteValueFactory);


        SpinnerValueFactory<Integer> endHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, 12);
        endHourValueFactory.setWrapAround(true);
        endHourSpinner.setValueFactory((endHourValueFactory));

        SpinnerValueFactory<Integer> endMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, 0, 5);
        endMinuteValueFactory.setWrapAround(true);
        endMinuteSpinner.setValueFactory(endMinuteValueFactory);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());


        try {
            ResultSet rs = Queries.getContactsSelect();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                contacts.add(new Contact(contactID,contactName,contactEmail));
            }
        }
        catch (SQLException e) {
        }
        contactIdCombo.getItems().addAll(contacts);

        try {
            ResultSet rs = Queries.getCustomersSelect();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                customers.add(new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, divisionID));
            }
        }
        catch (SQLException e) {
        }
        customerIdCombo.getItems().addAll(customers);

        try {
            ResultSet rs = Queries.getUsersSelect();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                users.add(new User(userID,userName, password));
            }
        }
        catch (SQLException e) {
        }
        userIdCombo.getItems().addAll(users);

//        LocalTime start = LocalTime.parse("12:00");
//        System.out.println(start);
    }
}
