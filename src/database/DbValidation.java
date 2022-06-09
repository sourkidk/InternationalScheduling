package database;
import javafx.scene.control.Alert;
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
    }    private static boolean validateAddress(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Address";
            return false;
        }
        else{
            return true;
        }
    }    private static boolean validatePostalCode(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Postal Code";
            return false;
        }
        else{
            return true;
        }
    }    private static boolean validatePhoneNumber(String name) {
        if(name.isEmpty()) {
            error = "Please enter a Valid Phone Number";
            return false;
        }
        else{
            return true;
        }
    }
}
