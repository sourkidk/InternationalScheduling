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

import static controller.SceneController.switchToScene;
import static java.time.ZoneOffset.*;

/**
 * The type Main form controller.
 */
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
    /**
     * The Sql formatter.
     */
    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Sets radio buttons label.
     *
     * @param viewType the view type
     */
    public void setRadioButtonsLabel(String viewType) {
        dynamicAddButton.setText("Add " + viewType);
        dynamicModifyButton.setText("Modify " + viewType);
        dynamicDeleteButton.setText("Delete " + viewType);
    }

    /**
     * On action view customers.  This fills the tableview with the full list of customers.
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws SQLException {
        setRadioButtonsLabel("Customer");


        try {
            ResultSet rs = Queries.getAllCustomersSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }



    }

    /**
     * On action by week view.  This fills the tableview with a set of appointments filtered by the week of the main
     * date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionByWeekView(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getThisWeeksAppointmentsSelect(mainDatePicker.getValue());
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }

    /**
     * On action by month view.  This fills the tableview with a set of appointments filtered by the month of the main
     * date picker.
     *
     * @param event the event
     */
    @FXML
    void onActionByMonthView(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getThisMonthsAppointmentsSelect(mainDatePicker.getValue());
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }

    }

    /**
     * On action view all.  This fills the tableview with all appointments.
     *
     * @param event the event
     */
    @FXML
    void onActionViewAll(ActionEvent event) {
        setRadioButtonsLabel("Appointment");

        try {
            ResultSet rs = Queries.getAllAppointmentsSelect();
            DynamicTableview.populateApptTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }


    }

    /**
     * On action view reports. This button takes the user to the report form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        switchToScene(event,"/view/ReportForm.fxml");
    }

    /**
     * On action add entry.  Depending on the radio button selection, this button takes the user to the AddAppointmentForm
     * or the AddCustomerForm
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddEntry(ActionEvent event) throws IOException {

        if ( viewCustomersRadioButton.isSelected() ) {
            switchToScene(event, "/view/AddCustomerForm.fxml");
        }
        else {
            switchToScene(event, "/view/AddAppointmentForm.fxml");
        }



    }

    /**
     * On action modify entry. Depending on the radio button selection, this button takes the user
     * to the ModifyAppointmentForm or the ModifyCustomerForm.  Because of the use of the generic tableview,
     * the method used to populate the modify forms is a combination of getters and string slicing.
     *
     * @param event the event
     * @throws IOException the io exception
     */
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

    /**
     * On action delete entry.
     *
     * @param event the event
     * @throws IOException the io exception
     */
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

    /**
     * On action logout.  Takes the user back to the Login Form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        switchToScene(event,"/view/LoginForm.fxml");

    }


    /**
     * The Contact list.
     */
    ObservableList<Table> contactList = FXCollections.observableArrayList();


    /**
     * When the Main form is loaded, the tableview is queried and diplayed.  The visit counter is incremented to prevent
     * excessive messages.  The lambda expression is used here to add a Listener to the Picker so that any changes in that
     * field, reload the tableview.
     * @param url
     * @param resourceBundle
     * */


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

            mainDatePicker.valueProperty().addListener((obs, previousValue, newValue) -> {

                    mainDatePicker.setDisable(false);
                    if( viewMonthRadioButton.isSelected() ) {

                        try {
                            ResultSet rs2 = Queries.getThisMonthsAppointmentsSelect(newValue);
                            DynamicTableview.populateTableView(mainTableview, rs2, data);
                        } catch (SQLException e) {
                        }
                    } else if ( viewWeekRadioButton.isSelected() ) {

                        try {
                            ResultSet rs3 = Queries.getThisWeeksAppointmentsSelect(newValue);
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
                LocalDateTime apptStart = DateTimeHelper.convertFromUTCLocal(rs5.getString("Start"), sqlFormatter, ZoneId.systemDefault());
                LocalDateTime apptEnd = DateTimeHelper.convertFromUTCLocal(rs5.getString("End"), sqlFormatter, ZoneId.systemDefault());

                int custID = rs5.getInt("Customer_ID");
                int userId = rs5.getInt("User_ID");
                int contactId = rs5.getInt("Contact_ID");
                currentUserAppointments.add(new Appointment(ApptID,apptTitle,apptDesc,apptLocation,
                        apptType,apptStart,apptEnd,custID,userId,contactId));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDateTime currentTime = LocalDateTime.now();

        boolean upcomingAppt = false;

        for (Appointment appt : currentUserAppointments) {
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