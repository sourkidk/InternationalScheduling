package database;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import utilities.Alerts;

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
                || validateUserName(userName) ){
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

    private static boolean validateUserName (String userName) {
        if (userName.isEmpty()) {
            error = "Username is not available";
            return false;
        } else {
            return true;
        }
    }



}
