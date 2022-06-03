package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;

import java.io.IOException;

import static controller.SceneController.switchToScene;

public class ReportFormController {

    @FXML private RadioButton contactScheduleRadioButton;

    @FXML private RadioButton customerByMonthRadioButton;

    @FXML private RadioButton customerByTypeRadioButton;

    @FXML private TableView<?> mainTableview;

    @FXML private RadioButton specialReportRadioButton;

    @FXML
    void onActionByMonthReport(ActionEvent event) {

    }

    @FXML
    void onActionByTypeReport(ActionEvent event) {

    }

    @FXML
    void onActionContactScheduleReport(ActionEvent event) {

    }

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        switchToScene(event,"/view/LoginForm.fxml");

    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        switchToScene(event,"/view/DatabaseForm.fxml");


    }

    @FXML
    void onActionSpecialReport(ActionEvent event) {

    }

}
