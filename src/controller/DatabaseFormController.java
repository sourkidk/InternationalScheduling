package controller;

import database.DynamicTableview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import database.JDBC;
import database.Queries;
import model.Table;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.Set;

import static controller.SceneController.switchToScene;

public class DatabaseFormController implements Initializable {

//    @FXML private TableColumn<Table, String> contactEmailColumn;
//    @FXML private TableColumn<Table, Integer> contactIdColumn;
//    @FXML private TableColumn<Table, String> contactNameColumn;
    @FXML private TableView mainTableview;
    @FXML private Button dynamicAddButton;
    @FXML private Button dynamicDeleteButton;
    @FXML private Button dynamicModifyButton;
    @FXML private DatePicker mainDatePicker;
    @FXML private Label systemTimeZoneLabel;
    @FXML private RadioButton viewAllRadioButton;
    @FXML private RadioButton viewCustomersRadioButton;
    @FXML private RadioButton viewMonthRadioButton;
    @FXML private RadioButton viewWeekRadioButton;

    private ObservableList<ObservableList> data;

    public void setRadioButtonsLabel(String viewType) {
        dynamicAddButton.setText("Add " + viewType);
        dynamicModifyButton.setText("Modify " + viewType);
        dynamicDeleteButton.setText("Delete " + viewType);
    }

    @FXML
    void onActionViewCustomers(ActionEvent event) throws SQLException {
        setRadioButtonsLabel("Customer");


        try {
            ResultSet rs = Queries.getAllCustomersSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }



    }

    @FXML
    void onActionByWeekView(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getThisWeeksAppointmentsSelect(mainDatePicker.getValue());
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }
    @FXML
    void onActionByMonthView(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getThisMonthsAppointmentsSelect(mainDatePicker.getValue());
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }

    @FXML
    void onActionViewAll(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getAllAppointmentsSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }


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

        mainDatePicker.setValue(LocalDate.now());

//        systemTimeZoneLabel.setText(LocalDateTime.now().toString());
        systemTimeZoneLabel.setText(ZoneId.systemDefault().getId());

        Set<String> zones = ZoneId.getAvailableZoneIds();
        System.out.println(zones.size());

//        for ( String zone : zones  ) {
//            System.out.println(zone);
//        }





        data = FXCollections.observableArrayList();
        try{
            JDBC.makeConnection();
            ResultSet rs = Queries.getAllAppointmentsSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

            mainDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {

                    mainDatePicker.setDisable(false);
                    if( viewMonthRadioButton.isSelected() ) {

                        try {
                            ResultSet rs2 = Queries.getThisMonthsAppointmentsSelect(newVal);
                            DynamicTableview.populateTableView(mainTableview, rs2, data);
                        } catch (SQLException e) {
                        }
                    } else if ( viewWeekRadioButton.isSelected() ) {

                        try {
                            ResultSet rs3 = Queries.getThisWeeksAppointmentsSelect(newVal);
                            DynamicTableview.populateTableView(mainTableview, rs3, data);
                        } catch (SQLException e) {
                        }
                    } else {
                        return;
                    }

            });

//        JDBC.closeConnection();
            //TODO Maybe delete this closeConnection

            setRadioButtonsLabel("Appointment");




        } catch (SQLException e) {
            }
        }
    }