package controller;

import database.DbValidation;
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
import main.Main;
import model.Appointment;
import model.Table;
import utilities.Alerts;
import utilities.DateTimeHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

import static controller.SceneController.switchToScene;
import static java.time.ZoneOffset.*;

public class MainFormController implements Initializable {

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
    private ObservableList<Appointment> currentUserAppointments = FXCollections.observableArrayList();
    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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
                int associatedAppointments = DbValidation.countCustomerAppointments(selectedCustomer);

                if (associatedAppointments != 0) {
                    Alerts.dialogBox("Problem","Associated Appointments",
                            "You cannot delete a customer that has appointments associated with it.  " +
                                    "Please delete all customer appointments before deleting a customer.");
                }

                else if( Alerts.confirmDeleteBox() ) {

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
        } else {
            try {

                String selectedIndex = mainTableview.getSelectionModel().getSelectedItems().get(0).toString();

                String selectedApptType = null;
                String newString = selectedIndex.substring(1, selectedIndex.indexOf(","));
                int selectedAppointment = Integer.parseInt(newString);

                if( Alerts.confirmDeleteBox() ) {

                    Queries.deleteSelectedAppointment(selectedAppointment);

                    ResultSet rs = Queries.getAllAppointmentsSelect();
                    DynamicTableview.populateTableView(mainTableview, rs, data);
                    ResultSet rsType = Queries.getSelectedAppointmentType(selectedAppointment);
                    if (rsType.next()) {
                        selectedApptType = rsType.getString("Type");
                    }
                    Alerts.deletedApptMessage(selectedAppointment, selectedApptType );
                }
            }
            catch (NullPointerException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No Appointment Selected.");
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
        Main.visitCount++;

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
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
            });


            setRadioButtonsLabel("Appointment");

        } catch (SQLException e) {
            e.printStackTrace();
            }

        int currentUserID = LoginFormController.getAppUserID();
        try {
            ResultSet rs5 = Queries.getTotalAppointmentsByUser(currentUserID);
            while (rs5.next()) {
                int ApptID = rs5.getInt("Appointment_ID");
                String apptTitle = rs5.getString("Title");
                String apptDesc = rs5.getString("Description");
                String apptLocation =rs5.getString("Location");
                String apptType = rs5.getString("Type");
//                LocalDateTime apptStart = LocalDateTime.parse(rs5.getString("Start"), sqlFormatter);
//                LocalDateTime apptEnd = LocalDateTime.parse(rs5.getString("End"),sqlFormatter);
                LocalDateTime apptStart = DateTimeHelper.convertFromUTCLocal(rs5.getString("Start"), sqlFormatter, ZoneId.systemDefault());
                LocalDateTime apptEnd = DateTimeHelper.convertFromUTCLocal(rs5.getString("End"), sqlFormatter, ZoneId.systemDefault());

                int custID = rs5.getInt("Customer_ID");
                int userId = rs5.getInt("User_ID");
                int contactId = rs5.getInt("Contact_ID");
                System.out.println(apptStart);
                System.out.println(apptEnd);
                currentUserAppointments.add(new Appointment(ApptID,apptTitle,apptDesc,apptLocation,
                        apptType,apptStart,apptEnd,custID,userId,contactId));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println(currentTime);

        boolean upcomingAppt = false;

        for (Appointment appt : currentUserAppointments) {
            System.out.println(appt.getApptStart());
            if ( appt.getApptStart().minusMinutes(15).isBefore(currentTime) && appt.getApptStart().isAfter(currentTime) ) {
                upcomingAppt = true;
                Alerts.dialogBox("Upcoming Appointment", "Appointment " + appt.getApptID() + ": " + appt.getApptName() + " Starting Soon", "Your " +
                        appt.getApptType() + " session is starting at " + appt.getApptStart().format(sqlFormatter));
            }
        }
        if (upcomingAppt == false && Main.visitCount < 2) {
            Alerts.dialogBox("No Upcoming Appointments", "", "You have no appointments in the next 15 minutes.");
        }

    }
    }