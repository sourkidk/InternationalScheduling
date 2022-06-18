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
import model.Customer;
import model.User;
import utilities.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


import static controller.SceneController.switchToScene;
import static database.DbValidation.*;
import static database.Queries.*;
import static java.time.ZoneOffset.UTC;
import static utilities.DateTimeHelper.convertToUTC;
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
    private static ZoneId currentTimeZone;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        boolean validDateTimes = false;
        boolean validCombos = false;
        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();


        ZonedDateTime utcStartDate = convertToUTC(startDatePicker.getValue(),startHour,startMinute, currentTimeZone);
        ZonedDateTime utcEndDate = convertToUTC(endDatePicker.getValue(),endHour,endMinute, currentTimeZone);

        String formatStart = utcStartDate.format(sqlFormatter).toString();
        String formatEnd = utcEndDate.format(sqlFormatter).toString();


        ZonedDateTime estBusinessStart = ZonedDateTime.of(startDatePicker.getValue(), LocalTime.of(8,0), ZoneId.of("America/New_York") );
        ZonedDateTime estBusinessEnd = ZonedDateTime.of(startDatePicker.getValue(), LocalTime.of(22,0), ZoneId.of("America/New_York") );


        ZonedDateTime estStartDate = utcStartDate.minusHours(4);
        ZonedDateTime estEndDate = utcEndDate.minusHours(4);

        System.out.println("");
        System.out.println("");
        System.out.println("UTC Start    " + utcStartDate);
        System.out.println("UTC End    " + utcEndDate);
        System.out.println("EST Start    " + estStartDate);
        System.out.println("EST END   " + estEndDate);

        System.out.println("Business Start    " + estBusinessStart);
        System.out.println("Business End    " + estBusinessEnd);


        if (  utcStartDate.isAfter(utcEndDate) || utcStartDate.isEqual(utcEndDate)|| utcStartDate.isBefore(ZonedDateTime.now(UTC))) {
            Alerts.dialogBox("Invalid Date Input", "Improper Date Values", "Please enter valid values for start and end date.  " +
                    "Start date must be today or later.");
        }
        else if ( estStartDate.isBefore(estBusinessStart) || estStartDate.isAfter(estBusinessEnd) || estEndDate.isBefore(estBusinessStart) || estEndDate.isAfter(estBusinessEnd) ) {
            Alerts.dialogBox("Invalid Date Input", "Outside Business Hours", "Please select a time between 8AM and 10PM Eastern Standard Time");
        }
        else {
            validDateTimes = true;
        }

        if ( userIdCombo.getValue() == null || customerIdCombo.getValue() == null || contactIdCombo.getValue() == null) {
            Alerts.dialogBox("Invalid Input","Input Fields Blank", "Please select an option for each dropdown field.");
        }
        else {validCombos = true;}

        int userID = userIdCombo.getValue().getUserID();
        int customerID = customerIdCombo.getValue().getCustomerId();
        int contactID = contactIdCombo.getValue().getContactID();


        if (validateAppointment(apptTitle, apptDescription, apptLocation, apptType, userName) && validDateTimes && validCombos) {
            insertAppointment(apptTitle, apptDescription, apptLocation, apptType, userName, userName, customerID, userID, contactID, formatStart, formatEnd);
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

        currentTimeZone = ZoneId.systemDefault();


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
            ResultSet rs = Queries.getAllCustomersSelect();
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
                ;
            }
        }
        catch (SQLException e) {
        }
        userIdCombo.getItems().addAll(users);

//        LocalTime start = LocalTime.parse("12:00");
//        System.out.println(start);
    }
}
