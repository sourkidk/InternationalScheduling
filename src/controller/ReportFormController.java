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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

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

    void addMonths() {
        String[] months = {"January", "February", "March", "April","May","June", "July","August","September","October", "November", "December"};
        for (int i = 0 ; i < months.length; i++ ) {
            int monthID = i + 1;
            String monthName = months[i];
            Month month = new Month(monthID, monthName);
            monthsList.add(month);
            System.out.println(month);
        }

        System.out.println(monthsList);
    }

    void addYears() {
        for (int i = 0; i < 31 ; i++ ) {
            int startYear = 2020;
            yearList.add(startYear + i);

        }
    }


    @FXML
    void onActionByMonthReport(ActionEvent event) {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();

        contactComboBox.setDisable(true);
        typeComboBox.setDisable(true);
        monthComboBox.setDisable(false);
        yearComboBox.setDisable(false);

        Month selectedMonth = monthComboBox.getValue();
        int year = yearComboBox.getValue();

        try {
            ResultSet rs = Queries.getMonthsCustomersSelect(year, selectedMonth);
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }
    }

    @FXML
    void onActionByTypeReport(ActionEvent event) {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();

        contactComboBox.setDisable(true);
        typeComboBox.setDisable(false);
        monthComboBox.setDisable(true);
        yearComboBox.setDisable(true);

    }

    @FXML
    void onActionContactScheduleReport(ActionEvent event) {
        typeComboBox.setDisable(true);
        monthComboBox.setDisable(true);
        yearComboBox.setDisable(true);
        contactComboBox.setDisable(false);

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
    void onActionSpecialReport(ActionEvent event) throws SQLException {
        mainTableview.getColumns().clear();
        mainTableview.getItems().clear();
        typeComboBox.setDisable(true);
        monthComboBox.setDisable(true);
        yearComboBox.setDisable(true);
        contactComboBox.setDisable(true);
        ResultSet rs = Queries.getTotalAppointmentsByContact();
        DynamicTableview.populateTableView(mainTableview, rs, data);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeComboBox.setDisable(true);
        monthComboBox.setDisable(true);
        yearComboBox.setDisable(true);
        contactComboBox.setDisable(false);

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





        contactComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
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

        typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal == null) {
                        typeComboBox.getItems().clear();
                        typeComboBox.setDisable(true);

                    } else {
                        typeComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getCustomersByTypeSelect(newVal);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });



        monthComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    int year = yearComboBox.getValue();
                    if (newVal == null) {
                        monthComboBox.getItems().clear();
                        monthComboBox.setDisable(true);

                    } else {
                        monthComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getMonthsCustomersSelect(year, newVal);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });

        yearComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    Month month = monthComboBox.getValue();
                    if (newVal == null) {
                        yearComboBox.getItems().clear();
                        yearComboBox.setDisable(true);

                    } else {
                        yearComboBox.setDisable(false);
                        try {
                            ResultSet rs = Queries.getMonthsCustomersSelect(newVal, month);
                            DynamicTableview.populateTableView(mainTableview,rs,data);
                            } catch (SQLException e) {
                        }
                    }
                });
    }

}
