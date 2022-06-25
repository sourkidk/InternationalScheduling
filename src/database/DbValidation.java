package database;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import utilities.Alerts;

import java.time.*;

import static java.time.ZoneOffset.UTC;

public class DbValidation {

    private static String error;


    public static Boolean validateCustomer(String name, String address, String postalCode, String phoneNumber){
        error = "";
        if (validateName(name) == false || validateAddress(address) == false || validatePostalCode(postalCode) == false || validatePhoneNumber(phoneNumber) == false ){
            System.out.println(error);

            Alerts.dialogBox("Missing field","Additional Info Required", error );

            return false;
        }
        else {
            return true;
        }
    }

    private static boolean validateCombo(ComboBox combo) {
        if ( combo.getValue() == null ) {
            error = "Please enter a value for each dropdown menu.";
            return false;
        }
        else {
            return true;
        }
    }

    private static boolean validateName(String name) {
        if(name.isEmpty()) {
            error = "Please enter Customer Name";
            return false;
        }
        else{
            return true;
        }

    }

    private static boolean validateAddress(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Address";
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean validatePostalCode(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Postal Code";
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean validatePhoneNumber(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Phone Number";
            return false;
        }
        else{
            return true;
        }
    }

    public static Boolean validateAppointment(String title, String description, String location,
                                              String type, String userName) {
        error = "";
        if (validateTitle(title) == false || validateDescription(description) == false ||
                validateLocation(location) == false || validateType(type) == false
                || validateUserName(userName) == false ){
            System.out.println(error);

            Alerts.dialogBox("Missing field","Additional Info Required", error );

            return false;
        }
        else {
            return true;
        }
    }

    private static boolean validateTitle (String title) {
        if (title.isEmpty()) {
            error = "Please enter an Appointment Title";
            return false;
        } else {
            return true;
        }
    }

    private static boolean validateDescription (String description) {
        if (description.isEmpty()) {
            error = "Please enter an Appointment Description";
            return false;
        } else {
            return true;
        }
    }

    private static boolean validateLocation (String location) {
        if (location.isEmpty()) {
            error = "Please enter an Appointment Location";
            return false;
        } else {
            return true;
        }
    }
    private static boolean validateType (String type) {
        if (type.isEmpty()) {
            error = "Please enter an Appointment Type";
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateAppointmentCombos( ComboBox userCombo, ComboBox customerCombo, ComboBox contactCombo) {
        boolean validCombos = false;
        if ( userCombo.getValue() == null || customerCombo.getValue() == null || contactCombo.getValue() == null) {
            Alerts.dialogBox("Invalid Input","Input Fields Blank", "Please select an option for each dropdown field.");
        }
        else {
            validCombos = true;
        }
        return validCombos;
    }

    private static boolean validateUserID ( int userID) {
        if (userID == 0) {
            error = "Please select a User ID for the appointment.";
            return false;
        } else {
            return true;
        }
    }

    private static boolean validateContact (int contactID) {
        if (contactID == -1) {
            error = "Please select a Contact for the appointment.";
            return false;
        } else {
            return true;
        }
    }
    private static boolean validateCustomerID (int customerID) {
        if (customerID == -1) {
            error = "Please select a Customer ID for the appointment.";
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateAppointmentTime (LocalDate date, ZonedDateTime startTime, ZonedDateTime endTime) {
        boolean validDateTimes = false;

        DayOfWeek startDayOfWeek = startTime.withZoneSameInstant(ZoneId.of("America/New_York")).getDayOfWeek();

        ZonedDateTime estBusinessStart = ZonedDateTime.of(date, LocalTime.of(8,0), ZoneId.of("America/New_York") );
        ZonedDateTime estBusinessEnd = ZonedDateTime.of(date, LocalTime.of(22,0), ZoneId.of("America/New_York") );

        ZonedDateTime utcBusinessStart = estBusinessStart.withZoneSameInstant(UTC);
        ZonedDateTime utcBusinessEnd = estBusinessEnd.withZoneSameInstant(UTC);

        if (  startTime.isBefore(ZonedDateTime.now(UTC))) {
            Alerts.dialogBox("Invalid Date Input", "Improper Date Values", "Please enter valid values for start and end date.  " +
                    "Start date must be today or later.");
        }
        else if ( startTime.isBefore(utcBusinessStart) || startTime.isAfter(utcBusinessEnd) || endTime.isBefore(utcBusinessStart) || endTime.isAfter(utcBusinessEnd) ) {
            Alerts.dialogBox("Invalid Date Input", "Outside Business Hours", "Please select a time Monday through Friday between 8AM and 10PM Eastern Time");
        }
        else if (startDayOfWeek == DayOfWeek.SATURDAY || startDayOfWeek == DayOfWeek.SUNDAY ) {
            Alerts.dialogBox("Invalid Date Input", "Outside Business Hours", "Please select a time Monday through Friday between 8AM and 10PM Eastern Time");
        }

        else {
            validDateTimes = true;
        }
        return validDateTimes;
    }

    private static boolean validateUserName (String userName) {
        if (userName.isEmpty()) {
            error = "Username is not available";
            return false;
        } else {
            return true;
        }
    }



}
