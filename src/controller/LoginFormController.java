package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
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
