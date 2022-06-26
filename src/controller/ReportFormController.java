package controller;

import database.DynamicTableview;
import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import model.Contact;
import model.Month;
import utilities.ComboBoxStaging;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

/**
 * The type Report form controller.
 */
public class ReportFormController implements Initializable {

    @FXML private RadioButton contactScheduleRadioButton;
    @FXML private RadioButton customerByMonthRadioButton;
    @FXML private RadioButton customerByTypeRadioButton;
    @FXML private TableView mainTableview;
    @FXML private RadioButton specialReportRadioButton;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<Contact> contactComboBox;
    @FXML private ComboBox<Integer> yearComboBox;
    @FXML private ComboBox<Month> monthComboBox;

    private ObservableList<ObservableList> data;
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Month> monthsList = FXCollections.observableArrayList();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<Integer> yearList = FXCollections.observableArrayList();

    /**
     * Add months.
     */
    void addMonths() {
        String[] months = {"January", "February", "March", "April","May","June", "July","August","September","October", "November", "December"};
        for (int i = 0 ; i < months.length; i++ ) {
            int monthID = i + 1;
            String monthName = months[i];
            Month month = new Month(monthID, monthName);
            monthsList.add(month);
        }
    }

    /**
     * Add years.
     */
    void addYears() {
        for (int i = 0; i < 31 ; i++ ) {
            int startYear = 2020;
            yearList.add(startYear + i);

        }
    }


    /**
     * On action by month report.
     *
     * @param event the event
     */
    @FXML
    void onActionByMonthReport(ActionEvent event) {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();

        ComboBoxStaging.setComboBoxStatus(contactComboBox, true, typeComboBox, true, monthComboBox,false, yearComboBox, false);

        Month selectedMonth = monthComboBox.getValue();
        int year = yearComboBox.getValue();

        try {
            ResultSet rs = Queries.getMonthsCustomersSelect(year, selectedMonth);
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }
    }

    /**
     * On action by type report.
     *
     * @param event the event
     */
    @FXML
    void onActionByTypeReport(ActionEvent event) {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();

        ComboBoxStaging.setComboBoxStatus(contactComboBox, true, typeComboBox, false, monthComboBox,true, yearComboBox, true);


    }

    /**
     * On action contact schedule report.
     *
     * @param event the event
     */
    @FXML
    void onActionContactScheduleReport(ActionEvent event) {
        ComboBoxStaging.setComboBoxStatus(contactComboBox, false, typeComboBox, true, monthComboBox,true, yearComboBox, true);


    }

    /**
     * On action logout.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        switchToScene(event,"/view/LoginForm.fxml");

    }

    /**
     * On action main menu.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        switchToScene(event, "/view/MainForm.fxml");


    }

    /**
     * On action special report.
     *
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionSpecialReport(ActionEvent event) throws SQLException {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();
        ComboBoxStaging.setComboBoxStatus(contactComboBox, true, typeComboBox, true, monthComboBox,true, yearComboBox, true);

        ResultSet rs = Queries.getTotalAppointmentsByContact();
        DynamicTableview.populateTableView(mainTableview, rs, data);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ComboBoxStaging.setComboBoxStatus(contactComboBox, false, typeComboBox, true, monthComboBox,true, yearComboBox, true);


        addMonths();
        monthComboBox.getItems().addAll(monthsList);
        int currentMonth = LocalDate.now().getMonthValue();
        for (Month monthObj: monthsList) {
            if (monthObj.getMonthID() == currentMonth) {
                monthComboBox.setValue(monthObj);
            }
        }
        try {
            ResultSet rs = Queries.getContactsSelect();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                contacts.add(new Contact(contactID,contactName,contactEmail));
            }
        }
        catch (SQLException e) {
        }

        contactComboBox.setValue(contacts.get(0));

        addYears();
        contactComboBox.getItems().addAll(contacts);
        yearComboBox.getItems().addAll(yearList);
        yearComboBox.setValue(yearList.get(2));

        data = FXCollections.observableArrayList();
        try{
            JDBC.makeConnection();
            ResultSet rs = Queries.getThisContactsAppointmentsSelect(contactComboBox.getValue().getContactID());
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {
        }




        try {
            ResultSet rs = Queries.getUniqueTypeSelect();
            while (rs.next()) {
                String type = rs.getString("Type");
                types.add(type);
            }
        }
        catch (SQLException e) {
        }
        typeComboBox.getItems().addAll(types);





        contactComboBox.valueProperty().addListener((obs, previousValue, newValue) -> {
            if (newValue == null) {
                contactComboBox.getItems().clear();
                contactComboBox.setDisable(true);

            } else {
                contactComboBox.setDisable(false);
                try {
                    ResultSet rs = Queries.getThisContactsAppointmentsSelect(contactComboBox.getValue().getContactID());
                    DynamicTableview.populateTableView(mainTableview,rs,data);
                    } catch (SQLException e) {
                }
            }
        });

        typeComboBox.valueProperty().addListener((obs, previousValue, newValue) -> {
                    if (newValue == null) {
                        typeComboBox.getItems().clear();
                        typeComboBox.setDisable(true);

                    } else {
                        typeComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getCustomersByTypeSelect(newValue);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });



        monthComboBox.valueProperty().addListener((obs, previousValue, newValue) -> {
                    int year = yearComboBox.getValue();
                    if (newValue == null) {
                        monthComboBox.getItems().clear();
                        monthComboBox.setDisable(true);

                    } else {
                        monthComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getMonthsCustomersSelect(year, newValue);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });

        yearComboBox.valueProperty().addListener((obs, previousValue, newValue) -> {
                    Month month = monthComboBox.getValue();
                    if (newValue == null) {
                        yearComboBox.getItems().clear();
                        yearComboBox.setDisable(true);

                    } else {
                        yearComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getMonthsCustomersSelect(newValue, month);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });
    }

}
