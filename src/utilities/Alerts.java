package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static controller.SceneController.switchToScene;

/**
 * The type Alerts.  Custom alert templates and specific alerts.
 */
public class Alerts {

    /**
     * Dialog box.
     *
     * @param alertTitle   the alert title
     * @param alertReason  the alert reason
     * @param alertMessage the alert message
     */
    public static void dialogBox(String alertTitle, String alertReason, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertReason);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    /**
     * Deleted appt message.
     *
     * @param apptID   the appt id
     * @param apptType the appt type
     */
    public static void deletedApptMessage(int apptID, String apptType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Cancelled");
        alert.setHeaderText("Appointment " + apptID ) ;
        alert.setContentText(apptType + " Session has been cancelled " );
        alert.showAndWait();
    }

    /**
     * Invalid username french dialog box.
     */
    public static void invalidUsernameFrenchDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText("Nom d'utilisateur invalide");
        alert.setContentText("Merci d'entrer un nom d'utilisateur valide.");
        alert.showAndWait();
    }

    /**
     * Invalid username english dialog box.
     */
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

    /**
     * Invalid password english dialog box.
     */
    public static void invalidPasswordEnglishDialogBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect Password");
        alert.setContentText("Please enter the correct password.");
        alert.showAndWait();
    }

    /**
     * Confirm cancel box boolean.
     *
     * @return the boolean
     */
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

    /**
     * Confirm delete box boolean.
     *
     * @return the boolean
     */
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

    public static boolean confirmApptDeleteBox(int apptID, String apptType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete " + apptType+" Appointment with ID: " +
                apptID + "? ");
        Optional<ButtonType> result = alert.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Confirm update box boolean.
     *
     * @return the boolean
     */
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
