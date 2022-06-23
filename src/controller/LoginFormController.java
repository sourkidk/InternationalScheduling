package controller;

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

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String userNameEntry = usernameTextField.getText();
        if ( userNameEntry == null ) {
            Alerts.dialogBox("Invalid UserName", "Blank or Incorrect Username", "Please enter a valid username.");
        }
        else {
            try {
                ResultSet rs = Queries.getUserLoginSelect(userNameEntry);
                if (rs.next()) {
                    int userID = rs.getInt("User_ID");
                    String userName = rs.getString("User_Name");
                    String password = rs.getString("Password");
                    users.add(new User(userID,userName,password));

                }
            }
            catch (SQLException e) {
                Alerts.dialogBox("Invalid UserName", "Incorrect Username", "That username could not be found.  " +
                        "Please enter a valid username.");

            }

        }



        switchToScene(event, "/view/DatabaseForm.fxml");

    }

    @FXML
    void onActionResetFields(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZonedDateTime currentSystemTime = ZonedDateTime.now();
        timezoneLabel.setText(ZoneId.systemDefault().getId());

    }
}
