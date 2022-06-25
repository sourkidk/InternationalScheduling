package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static controller.SceneController.switchToScene;

public class Alerts {

    public static void dialogBox(String alertTitle, String alertReason, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertReason);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
    public static void deletedApptMessage(int apptID, String apptType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Cancelled");
        alert.setHeaderText("Appointment " + apptID ) ;
        alert.setContentText(apptType + " Session has been cancelled " );
        alert.showAndWait();
    }
    public static void invalidUsernameFrenchDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText("Nom d'utilisateur invalide");
        alert.setContentText("Merci d'entrer un nom d'utilisateur valide.");
        alert.showAndWait();
    }
    public static void invalidUsernameEnglishDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Username");
        alert.setContentText("Please enter a valid Username.");
        alert.showAndWait();
    }
    public static void invalidPasswordFrenchDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText("Mot de passe incorrecte");
        alert.setContentText("Veuillez entrer le mot de passe correct.");
        alert.showAndWait();
    }
    public static void invalidPasswordEnglishDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Password");
        alert.setContentText("Please enter the correct password.");
        alert.showAndWait();
    }

    public static boolean confirmCancelBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any changes you've made. " +
                "Do you want to continue? ");
        Optional<ButtonType> result = alert.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean confirmDeleteBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item? ");
        Optional<ButtonType> result = alert.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean confirmUpdateBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this item? ");
        Optional<ButtonType> result = alert.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }


}
