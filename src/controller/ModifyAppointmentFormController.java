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
import utilities.SpinnerFactory;

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

/**
 * The type Modify appointment form controller.
 */
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
    /**
     * The constant transferredAppointmentID.
     */
    public static int transferredAppointmentID;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    /**
     * The Sql formatter.
     */
    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * On action cancel.  This button will cancel any changes made to the Appointment after being prompted for confirmation.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        if (Alerts.confirmCancelBox()) {
            switchToScene(event, "/view/MainForm.fxml");
        }
    }

    /**
     * Send customers.  This method transfers the appointment ID from the main form to the modify form.
     *
     * @param selectedAppointmentID the selected appointment id
     */
    public void sendAppointment(int selectedAppointmentID) {

        transferredAppointmentID = selectedAppointmentID;
    }

    /**
     * On action save appointment.  This method collects input from all the fields on the form and validates them against a number of
     * parameters.  DateTime time zone adjustment occurs here.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
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
            switchToScene(event, "/view/MainForm.fxml");
        }
    }

    /**
     * Sets appointment fields for edit.  This method populates all the form fields with the information from the currently selected item.
     *
     * @param appointmentIDToGrab the appointment id to grab
     */
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

                SpinnerFactory.setSpinners(startHourSpinner,startHour,startMinuteSpinner,startMinute,endHourSpinner,endHour,endMinuteSpinner,  endMinute);


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

    /**
     * When the modify form is loaded, the date pickers and comboboxes are initialized.
     * @param url
     * @param resourceBundle
     * */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

        SpinnerFactory.setSpinners(startHourSpinner,12,startMinuteSpinner,0,endHourSpinner,12,endMinuteSpinner,  0);

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
