package controller;

import database.DbValidation;
import database.JDBC;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDivision;
import utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SceneController.switchToScene;

public class ModifyCustomerFormController implements Initializable {

    @FXML private TextField customerIdTextfield;
    @FXML private TextField customerAddressTextfield;
    @FXML private ComboBox<Country> customerCountryCombo;
    @FXML private ComboBox<FirstLevelDivision> customerDivisionCombo;
    @FXML private TextField customerNameTextfield;
    @FXML private TextField customerPhoneTextfield;
    @FXML private TextField customerPostalTextfield;
    public static int transferredCustomerID;

    private ObservableList<Country> countries = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> filterFirstLevelDivisions = FXCollections.observableArrayList();

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        if (Alerts.confirmCancelBox()) {
            switchToScene(event, "/view/DatabaseForm.fxml");
        }
    }

    public void sendCustomers(int selectedCustomerID) {

        transferredCustomerID = selectedCustomerID;
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {
        int customerID = Integer.parseInt(customerIdTextfield.getText());
        String userName = LoginFormController.getAppUsername();
        String customerName = customerNameTextfield.getText();
        String customerAddress = customerAddressTextfield.getText();
        String customerPostalCode = customerPostalTextfield.getText();
        String customerPhoneNumber = customerPhoneTextfield.getText();
        int divisionID = 0; // To be filled by combobox when that functionality is added.


        if (customerCountryCombo.getValue() == null || customerDivisionCombo.getValue() == null) {
            Alerts.dialogBox("Invalid Input", "Input Fields Blank", "Please select a valid Country and State/Territory.");
        }
        else {
            divisionID = customerDivisionCombo.getValue().getDivisionID();
        }

        try {
            if (Alerts.confirmUpdateBox()) {
                DbValidation.validateCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber);
                Queries.updateCustomer(customerID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, userName, divisionID);
                switchToScene(event, "/view/DatabaseForm.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerFieldsForEdit(int customerIDToGrab) {
        try {
            ResultSet rs = Queries.getCustomerToModifySelect(customerIDToGrab);
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                for (FirstLevelDivision div : firstLevelDivisions) {
                    if (div.getDivisionID() == divisionID) {
                        for (Country country : countries) {
                            if (country.getCountryID() == div.getCountryID()) {
                                customerCountryCombo.setValue(country);
                                customerDivisionCombo.setValue(div);
                            }
                        }
                    }


                    customerIdTextfield.setText(String.valueOf(customerID));
                    customerNameTextfield.setText(customerName);
                    customerAddressTextfield.setText(customerAddress);
                    customerPostalTextfield.setText(customerPostal);
                    customerPhoneTextfield.setText(customerPhone);


                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

        System.out.println("Init: transfer customer id: " + transferredCustomerID);



        customerCountryCombo.getItems().addAll(countries);
        try {
            ResultSet rs = Queries.getCountriesSelect();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                countries.add(new Country(countryID, countryName));
            }
        } catch (SQLException e) {
        }
        customerCountryCombo.getItems().addAll(countries);

        try {
            ResultSet rs = Queries.getFirstLevelDivSelect();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions.add(new FirstLevelDivision(divisionID, divisionName, countryID));
            }
        } catch (SQLException e) {
        }
        customerDivisionCombo.getItems().addAll(firstLevelDivisions);

        customerCountryCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                customerDivisionCombo.getItems().clear();
                customerDivisionCombo.setDisable(true);

            } else {
                customerDivisionCombo.setDisable(false);
                customerDivisionCombo.getItems().clear();
                filterFirstLevelDivisions.clear();
                try {
                    ResultSet rs = Queries.getFilteredFirstLevelDivSelect(customerCountryCombo.getValue().getCountryID());
                    while (rs.next()) {
                        int divisionID = rs.getInt("Division_ID");
                        String divisionName = rs.getString("Division");
                        int countryID = rs.getInt("Country_ID");
                        filterFirstLevelDivisions.add(new FirstLevelDivision(divisionID, divisionName, countryID));
                    }
                } catch (SQLException e) {
                }
                customerDivisionCombo.getItems().addAll(filterFirstLevelDivisions);

            }

        });





    }
}
