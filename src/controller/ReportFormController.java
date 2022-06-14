package controller;

import database.DynamicTableview;
import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import model.Contact;
import model.FirstLevelDivision;
import model.Month;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
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
    @FXML private ComboBox<?> extraComboBox;
    @FXML private ComboBox<Month> monthComboBox;

    private ObservableList<ObservableList> data;
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Month> monthsList = FXCollections.observableArrayList();
    private ObservableList<String> types = FXCollections.observableArrayList();

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



    @FXML
    void onActionByMonthReport(ActionEvent event) {

        try {
            ResultSet rs = Queries.getAllCustomersSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {

        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addMonths();

        data = FXCollections.observableArrayList();
        try{
            JDBC.makeConnection();
            ResultSet rs = Queries.getAllAppointmentsSelect();
            DynamicTableview.populateTableView(mainTableview, rs, data);

        } catch (SQLException e) {
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
        contactComboBox.getItems().addAll(contacts);



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

        monthComboBox.getItems().addAll(monthsList);




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
    }

}
