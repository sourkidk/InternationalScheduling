package controller;

import database.DynamicTableview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import database.JDBC;
import database.Queries;
import javafx.stage.Stage;
import model.Table;
import utilities.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
import java.util.Set;

import static controller.SceneController.switchToScene;
import static java.time.ZoneOffset.*;

public class DatabaseFormController implements Initializable {

    private static Stage stage;
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
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }
    @FXML
    void onActionByMonthView(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getThisMonthsAppointmentsSelect(mainDatePicker.getValue());
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }

    @FXML
    void onActionViewAll(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getAllAppointmentsSelect();
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

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
            try {

                String selectedIndex = mainTableview.getSelectionModel().getSelectedItems().get(0).toString();


                String newString = selectedIndex.substring(1, selectedIndex.indexOf(","));
                int selectedCustomer = Integer.parseInt(newString);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModifyCustomerForm.fxml"));
                loader.load();

                ModifyCustomerFormController ModCusController = loader.getController();
                ModCusController.sendCustomers(selectedCustomer);
                ModCusController.setCustomerFieldsForEdit(selectedCustomer);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Product Selected.");
                alert.show();
            }
        }
        else {
            try {

                String selectedIndex = mainTableview.getSelectionModel().getSelectedItems().get(0).toString();


                String newString = selectedIndex.substring(1, selectedIndex.indexOf(","));
                int selectedAppointment = Integer.parseInt(newString);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModifyAppointmentForm.fxml"));
                loader.load();

                ModifyAppointmentFormController ModApptController = loader.getController();
                ModApptController.sendCustomers(selectedAppointment);
                ModApptController.setAppointmentFieldsForEdit(selectedAppointment);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Product Selected.");
                alert.show();
            }
        }

    }

    @FXML
    void onActionDeleteEntry(ActionEvent event) throws IOException {

        if ( viewCustomersRadioButton.isSelected() ) {
            try {

                String selectedIndex = mainTableview.getSelectionModel().getSelectedItems().get(0).toString();


                String newString = selectedIndex.substring(1, selectedIndex.indexOf(","));
                int selectedCustomer = Integer.parseInt(newString);

                if( Alerts.confirmDeleteBox() ) {

                    Queries.deleteSelectedCustomer(selectedCustomer);

                    ResultSet rs = Queries.getAllCustomersSelect();
                    DynamicTableview.populateTableView(mainTableview, rs, data);
                }



            }
            catch (NullPointerException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Customer Selected.");
                alert.show();
            }
        }

    }

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        switchToScene(event,"/view/LoginForm.fxml");

    }





    ObservableList<Table> contactList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainDatePicker.setValue(LocalDate.now());

        systemTimeZoneLabel.setText(ZoneId.systemDefault().getId());
        ZonedDateTime currentSystemTime = ZonedDateTime.now();
        ZonedDateTime utcTime = currentSystemTime.withZoneSameInstant(UTC);


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