package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import database.JDBC;
import database.Queries;
import javafx.util.Callback;
import model.Table;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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




        data = FXCollections.observableArrayList();
        try{
            JDBC.makeConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER

            //ResultSet
            ResultSet rs = Queries.getAppointmentsSelect();

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0 ; i < rs.getMetaData().getColumnCount() ;  i++){
                //We are using non property style for making dynamic table
                int j = i;
                TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i+1));
                column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                mainTableview.getColumns().addAll(column);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            mainTableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }


        JDBC.closeConnection();

        dynamicAddButton.setText("Add Customer");
        dynamicModifyButton.setText("Modify Customer");
        dynamicDeleteButton.setText("Delete Customer");

//        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        contactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    }
}