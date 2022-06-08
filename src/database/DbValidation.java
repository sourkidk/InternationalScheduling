package database;
import javafx.scene.control.Alert;

public class DbValidation {

    private static String error;


    public static Boolean validateCustomer(String name){
        error = "";
        if (!validName(name) == true ){
            System.out.println(error);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test");
            alert.setHeaderText("Test");
            alert.setContentText(error);
            alert.showAndWait();

//            Alerts.infoDialog("Error", "Please fix the following error:", error);
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }

    private static boolean validName(String name) {
        if(name.isEmpty()) {
            error = "Please enter Customer Name";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
}
