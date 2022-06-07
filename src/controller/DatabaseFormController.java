package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import database.JDBC;
import database.NewQuery;
import model.Table;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class DatabaseFormController implements Initializable {

    @FXML private TableColumn<Table, String> contactEmailColumn;
    @FXML private TableColumn<Table, Integer> contactIdColumn;
    @FXML private TableColumn<Table, String> contactNameColumn;
    @FXML private TableView<Table> mainTableview;
    @FXML private Button dynamicAddButton;
    @FXML private Button dynamicDeleteButton;
    @FXML private Button dynamicModifyButton;
    @FXML private DatePicker mainDatePicker;
    @FXML private Label systemTimeZoneLabel;
    @FXML private RadioButton viewAllRadioButton;
    @FXML private RadioButton viewCustomersRadioButton;
    @FXML private RadioButton viewMonthRadioButton;
    @FXML private RadioButton viewWeekRadioButton;

    @FXML
    void onActionViewCustomers(ActionEvent event) {
        dynamicAddButton.setText("Add Customer");
        dynamicModifyButton.setText("Modify Customer");
        dynamicDeleteButton.setText("Delete Customer");

    }

    @FXML
    void onActionByWeekView(ActionEvent event) {
        dynamicAddButton.setText("Add Appointment");
        dynamicModifyButton.setText("Modify Appointment");
        dynamicDeleteButton.setText("Delete Appointment");

    }
    @FXML
    void onActionByMonthView(ActionEvent event) {
        dynamicAddButton.setText("Add Appointment");
        dynamicModifyButton.setText("Modify Appointment");
        dynamicDeleteButton.setText("Delete Appointment");
    }

    @FXML
    void onActionViewAll(ActionEvent event) {
        dynamicAddButton.setText("Add Appointment");
        dynamicModifyButton.setText("Modify Appointment");
        dynamicDeleteButton.setText("Delete Appointment");

    }

    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        switchToScene(event,"/view/ReportForm.fxml");
    }

    @FXML
    void onActionAddEntry(ActionEvent event) throws IOException {

        if ( viewCustomersRadioButton.isSelected() ) {
            switchToScene(event, "/view/AddCustomerForm.fxml");
        }
        else {
            switchToScene(event, "/view/AddAppointmentForm.fxml");
        }



    }

    @FXML
    void onActionModifyEntry(ActionEvent event) throws IOException {

        if ( viewCustomersRadioButton.isSelected() ) {
            switchToScene(event, "/view/ModifyCustomerForm.fxml");
        }
        else {
            switchToScene(event, "/view/ModifyAppointmentForm.fxml");
        }

    }

    @FXML
    void onActionDeleteEntry(ActionEvent event) {

    }

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        switchToScene(event,"/view/LoginForm.fxml");

    }





    ObservableList<Table> contactList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JDBC.makeConnection();

        try {
            ResultSet rs = NewQuery.getSelect();
            while (rs.next() ) {
                contactList.add(new Table(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email")));
                System.out.println("Adding contact to list " + rs.getString("Contact_Name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        JDBC.closeConnection();

        dynamicAddButton.setText("Add Appointment");
        dynamicModifyButton.setText("Modify Appointment");
        dynamicDeleteButton.setText("Delete Appointment");

        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        mainTableview.setItems(contactList);

    }
}