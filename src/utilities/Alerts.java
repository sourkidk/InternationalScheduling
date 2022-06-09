package utilities;

import javafx.scene.control.Alert;

public class Alerts {

    public static void dialogBox(String alertTitle, String alertReason, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertReason);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }


}
