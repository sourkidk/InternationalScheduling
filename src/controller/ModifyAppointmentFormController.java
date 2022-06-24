package controller;

import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import utilities.Alerts;
import utilities.DateTimeHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;
import static database.DbValidation.*;
import static database.Queries.insertAppointment;
import static utilities.DateTimeHelper.convertToUTC;

public class ModifyAppointmentFormController implements Initializable {

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
    public static int transferredAppointmentID;

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

    public void sendCustomers(int selectedAppointmentID) {

        transferredAppointmentID = selectedAppointmentID;
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException, SQLException {
        String userName = LoginFormController.getAppUsername();
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


        ZonedDateTime utcStartTime = convertToUTC(startDatePicker.getValue(),startHour,startMinute, currentTimeZone);
        ZonedDateTime utcEndTime = convertToUTC(startDatePicker.getValue(),endHour,endMinute, currentTimeZone);

        String formattedStartTime = utcStartTime.format(sqlFormatter).toString();
        String formattedEndTime = utcEndTime.format(sqlFormatter).toString();

        if (validateAppointmentTime(startDatePicker.getValue(), utcStartTime, utcEndTime)) {
            validDateTimes = true;
        }

        if (validateAppointmentCombos(userIdCombo, customerIdCombo, contactIdCombo)) {
            validCombos = true;
        }

        int userID = userIdCombo.getValue().getUserID();
        int customerID = customerIdCombo.getValue().getCustomerId();
        int contactID = contactIdCombo.getValue().getContactID();


        if (validateAppointment(apptTitle, apptDescription, apptLocation, apptType, userName) && validDateTimes && validCombos) {
            insertAppointment(apptTitle, apptDescription, apptLocation, apptType, userName, userName, customerID, userID, contactID, formattedStartTime, formattedEndTime);
            switchToScene(event, "/view/DatabaseForm.fxml");
        }
    }

    public void setAppointmentFieldsForEdit(int appointmentIDToGrab) {
        try {
            ResultSet rs = Queries.getAppointmentToModifySelect(appointmentIDToGrab);
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                int customerID = rs.getInt("Customer_ID");
                String startDatetimeString = rs.getString("Start");
                String endDatetimeString = rs.getString("End");



                LocalDateTime start = LocalDateTime.parse(startDatetimeString,sqlFormatter);
                LocalDateTime end = LocalDateTime.parse(endDatetimeString,sqlFormatter);
                ZonedDateTime zonedStart = DateTimeHelper.convertFromUTC(start, ZoneId.systemDefault());
                ZonedDateTime zonedEnd = DateTimeHelper.convertFromUTC(end, ZoneId.systemDefault());


                int startHour = zonedStart.getHour();
                int startMinute = zonedStart.getMinute();
                int endHour = zonedEnd.getHour();
                int endMinute = zonedEnd.getMinute();


                SpinnerValueFactory<Integer> startHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, startHour);
                startHourValueFactory.setWrapAround(true);
                startHourSpinner.setValueFactory(startHourValueFactory);

                SpinnerValueFactory<Integer> startMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, startMinute, 5);
                startMinuteValueFactory.setWrapAround(true);
                startMinuteSpinner.setValueFactory(startMinuteValueFactory);

                SpinnerValueFactory<Integer> endHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, endHour);
                endHourValueFactory.setWrapAround(true);
                endHourSpinner.setValueFactory((endHourValueFactory));

                SpinnerValueFactory<Integer> endMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, endMinute, 5);
                endMinuteValueFactory.setWrapAround(true);
                endMinuteSpinner.setValueFactory(endMinuteValueFactory);




                appointmentIdTextfield.setText(String.valueOf(appointmentID));
                appointmentTitleTextfield.setText(appointmentTitle);
                appointmentDescriptionTextfield.setText(appointmentDescription);
                appointmentLocationTextfield.setText(appointmentLocation);
                appointmentTypeTextfield.setText(appointmentType);
                for (User user: users) {
                    if ( user.getUserID() == userID) {
                        userIdCombo.setValue(user);
                    }
                }
                for (Contact contact: contacts) {
                    if ( contact.getContactID() == contactID) {
                        contactIdCombo.setValue(contact);
                    }
                }
                for (Customer customer:customers) {
                    if ( customer.getCustomerId() == customerID) {
                        customerIdCombo.setValue(customer);
                    }
                }



            }
        } catch (SQLException e) {
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

    }
}
