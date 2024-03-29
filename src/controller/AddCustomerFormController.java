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

/**
 * The Add customer form controller.
 */
public class AddCustomerFormController implements Initializable {

    @FXML private TextField customerIdTextfield;
    @FXML private TextField customerNameTextfield;
    @FXML private TextField customerAddressTextfield;
    @FXML private TextField customerPostalTextfield;
    @FXML private TextField customerPhoneTextfield;
    @FXML private ComboBox<Country> customerCountryCombo;
    @FXML private ComboBox<FirstLevelDivision> customerDivisionCombo;


    private ObservableList<Country> countries = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> filterFirstLevelDivisions = FXCollections.observableArrayList();

    /**
     * On action filter divisions.  This method refers to the Country comboBox and filters the selection of divisions based
     * on that value.
     *
     * @param event the event
     */
    @FXML
    void onActionFilterDivisions(ActionEvent event) {

        try {
            ResultSet rs = Queries.getFilteredFirstLevelDivSelect(1);
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                firstLevelDivisions.add(new FirstLevelDivision(divisionID, divisionName, countryID));
            }
        }
        catch (SQLException e) {
        }
        customerDivisionCombo.getItems().addAll(filterFirstLevelDivisions);

    }


    /**
     * On action save customer.  This method collects input from all the fields on the form and validates they are not blank.  Once
     * verified the customer is written to the database.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException, SQLException {

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
            DbValidation.validateCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber);
            Queries.insertCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber, userName, userName, divisionID);
            switchToScene(event, "/view/MainForm.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action cancel.  This method confirms that the user will lose any input and then returns to the main form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        if (Alerts.confirmCancelBox()) {
            switchToScene(event, "/view/MainForm.fxml");
        }
    }

    /**
     * When the modify form is loaded, comboboxes are initialized with the Countries and First-level Divisions.
     * Lambda function is used here to filter the first-level divisions bases on the country selected.
     * @param url
     * @param resourceBundle
     * */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBC.makeConnection();

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
