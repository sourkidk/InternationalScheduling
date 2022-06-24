package controller;

import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import utilities.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class LoginFormController implements Initializable {

    @FXML
    private ComboBox<?> languageCombobox;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label timezoneLabel;

    @FXML
    private TextField usernameTextField;

    private ObservableList<User> users = FXCollections.observableArrayList();
    private static String appUsername = null;


    public static String getAppUsername() {
        return appUsername;
    }

    public static void setAppUsername(String appUsername) {
        LoginFormController.appUsername = appUsername;
    }


    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        boolean validLogin = true;
        String userNameEntry = usernameTextField.getText();
        String passwordEntry = passwordTextField.getText();


        if ( userNameEntry == null ) {
            validLogin = false;
            Alerts.dialogBox("No UserName", "Blank or Incorrect Username", "Please enter a valid username.");
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
                    users.add(new User(userID,userName,password));

                    if (passwordEntry.equals(password)) {
                        validLogin = true;
                    }
                    else {
                        validLogin = false;
                        Alerts.dialogBox("Incorrect Password", "Password not found", "Please enter the correct password.");

                    }



                }
                else {
                    validLogin = false;
                    Alerts.dialogBox("Incorrect UserName", "Username not found", "Please enter a valid username.");

                }
            }
            catch (SQLException e) {
                validLogin = false;
                Alerts.dialogBox("Invalid UserName", "Incorrect Username", "That username could not be found.  " +
                        "Please enter a valid username.");

            }

        }


        if (validLogin) {
            switchToScene(event, "/view/DatabaseForm.fxml");
        }
    }

    @FXML
    void onActionResetFields(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        try {
//            JDBC.makeConnection();
//            ResultSet rs = Queries.getUsersSelect();
//            if (rs.next()) {
//                int userID = rs.getInt("User_ID");
//                String userName = rs.getString("User_Name");
//                String password = rs.getString("Password");
//                users.add(new User(userID, userName, password));
//            }
//
//            JDBC.closeConnection();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alerts.dialogBox("Database Connection Error", "Database connection failed",
//                    "Please contact your administator for assistance.");
//        }

        ZonedDateTime currentSystemTime = ZonedDateTime.now();
        timezoneLabel.setText(ZoneId.systemDefault().getId());

    }
}
