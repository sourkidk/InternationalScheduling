package controller;

import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.User;
import utilities.Alerts;
import utilities.SignOnLog;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

/**
 * The type Login form controller.
 */
public class LoginFormController implements Initializable {

    @FXML private Label currentTimeZoneLabel;
    @FXML private Label languageLabel;
    @FXML private Text passwordLabel;
    @FXML private TextField passwordTextField;
    @FXML private Text timeZoneLabel;
    @FXML private Text titleLabel;
    @FXML private Text usernameLabel;
    @FXML private TextField usernameTextField;
    @FXML private Button resetButton;
    @FXML private Button loginButton;
    /**
     * The Locale code.
     */
    public String localeCode;
    private static String appUsername = null;
    private ObservableList<User> users = FXCollections.observableArrayList();

    /**
     * Gets app user id.
     *
     * @return the app user id
     */
    public static int getAppUserID() {
        return appUserID;
    }

    private static int appUserID;


    /**
     * Gets app username.
     *
     * @return the app username
     */
    public static String getAppUsername() {

        return appUsername;
    }

    /**
     * Sets app username.
     *
     * @param appUsername the app username
     */
    public static void setAppUsername(String appUsername) {

        LoginFormController.appUsername = appUsername;
    }


    /**
     * On action login.  When pressed this method checks the entered values against the database of users and determines
     * if the credentials are sufficient to allow access to the program.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        boolean validLogin = true;
        String userNameEntry = usernameTextField.getText();
        String passwordEntry = passwordTextField.getText();


        if ( userNameEntry.equals("") ) {
            SignOnLog.logSignOnAttempt(" --- No Username Attempt.");
            validLogin = false;
            if( localeCode.equals("en_US")) {
                Alerts.invalidUsernameFrenchDialogBox();
            }
            else {
                Alerts.invalidUsernameEnglishDialogBox();
            }
        }
        else {
            try {
                JDBC.makeConnection();
                ResultSet rs = Queries.getUserLoginSelect(userNameEntry);
                if (rs.next()) {
                    setAppUsername(userNameEntry);
                    int userID = rs.getInt("User_ID");
                    String userName = rs.getString("User_Name");
                    String password = rs.getString("Password");
                    appUserID = userID;

                    users.add(new User(userID,userName,password));

                    if (passwordEntry.equals(password)) {
                        validLogin = true;
                    }
                    else {
                        SignOnLog.logSignOnAttempt(" --- Username: " + userNameEntry + ": Invalid Password ---  Login Failed.");

                        validLogin = false;
                        if( localeCode.equals("fr_CA")) {
                            Alerts.invalidPasswordFrenchDialogBox();
                        }
                        else {
                            Alerts.invalidPasswordEnglishDialogBox();
                        }
                    }



                }
                else {
                    SignOnLog.logSignOnAttempt(" --- Username: " + userNameEntry + ": Invalid Username ---  Login Failed.");
                    validLogin = false;
                    if( localeCode.equals("fr_CA")) {
                        Alerts.invalidUsernameFrenchDialogBox();
                    }
                    else {
                        Alerts.invalidUsernameEnglishDialogBox();
                    }
                }
            }
            catch (SQLException e) {
                validLogin = false;
                SignOnLog.logSignOnAttempt(" --- Username: " + userNameEntry + ": Invalid Username ---  Login Failed.");

                if( localeCode.equals("fr_CA")) {
                    Alerts.invalidUsernameFrenchDialogBox();
                }
                else {
                    Alerts.invalidUsernameEnglishDialogBox();
                }

            }

        }


        if (validLogin) {
            SignOnLog.logSignOnAttempt(" --- Username: " + userNameEntry + " --- Successful Login.");
            switchToScene(event, "/view/MainForm.fxml");
        }
    }

    /**
     * On action reset fields.
     *
     * @param event the event
     */
    @FXML
    void onActionResetFields(ActionEvent event) {

    }

    /**
     * When the Login form is loaded, the system Locale Code is checked and used to display the form in English or French..
     * @param url
     * @param resourceBundle
     * */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        localeCode = Locale.getDefault().toString();

        if( localeCode.equals("fr_CA")) {
            titleLabel.setText("Horaire de Rendez-vous");
            languageLabel.setText("Française");
            timeZoneLabel.setText("Fuseau Horaire Actuel");
            usernameLabel.setText("Nom D'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginButton.setText("Connexion");
            resetButton.setText("Réinitialiser");

        }

        currentTimeZoneLabel.setText(ZoneId.systemDefault().getId());

    }
}
