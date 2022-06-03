package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static controller.SceneController.switchToScene;

public class LoginFormController {

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

}
