package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Month;
import utilities.DateTimeHelper;
import utilities.WeekInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * The type Queries.
 */
public abstract class Queries implements WeekInterface {

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();


    /**
     * Insert customer int.
     *
     * @param customerName        the customer name
     * @param customerAddress     the customer address
     * @param customerPostalCode  the customer postal code
     * @param customerPhoneNumber the customer phone number
     * @param createdBy           the created by
     * @param lastUpdatedBy       the last updated by
     * @param divisionID          the division id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int insertCustomer(String customerName, String customerAddress,
                                     String customerPostalCode, String customerPhoneNumber,
                                     String createdBy, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPostalCode);
        ps.setString(4, customerPhoneNumber);
        ps.setString(5, createdBy);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Update customer int.
     *
     * @param customerID          the customer id
     * @param customerName        the customer name
     * @param customerAddress     the customer address
     * @param customerPostalCode  the customer postal code
     * @param customerPhoneNumber the customer phone number
     * @param lastUpdatedBy       the last updated by
     * @param divisionID          the division id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int updateCustomer(int customerID, String customerName, String customerAddress,
                                     String customerPostalCode, String customerPhoneNumber,
                                     String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By =?, Division_ID = ?"
                + " WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,customerAddress);
        ps.setString(3,customerPostalCode);
        ps.setString(4,customerPhoneNumber);
        ps.setString(5,lastUpdatedBy);
        ps.setInt(6,divisionID);
        ps.setInt(7, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Update appointment int.
     *
     * @param appointmentID   the appointment id
     * @param apptTitle       the appt title
     * @param apptDescription the appt description
     * @param apptLocation    the appt location
     * @param apptType        the appt type
     * @param lastUpdatedBy   the last updated by
     * @param customerID      the customer id
     * @param userID          the user id
     * @param contactID       the contact id
     * @param startDateTime   the start date time
     * @param endDateTime     the end date time
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int updateAppointment(int appointmentID, String apptTitle, String apptDescription,
                                             String apptLocation, String apptType,
                                             String lastUpdatedBy, int customerID,
                                             int userID, int contactID, String startDateTime, String endDateTime) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, "
                + "Last_Updated_By = ? , Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,apptTitle);
        ps.setString(2,apptDescription);
        ps.setString(3,apptLocation);
        ps.setString(4,apptType);
        ps.setString(5,startDateTime);
        ps.setString(6,endDateTime);
        ps.setString(7,lastUpdatedBy);
        ps.setInt(8,customerID);
        ps.setInt(9,userID);
        ps.setInt(10,contactID);
        ps.setInt(11, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Insert appointment int.
     *
     * @param apptTitle       the appt title
     * @param apptDescription the appt description
     * @param apptLocation    the appt location
     * @param apptType        the appt type
     * @param createdBy       the created by
     * @param lastUpdatedBy   the last updated by
     * @param customerID      the customer id
     * @param userID          the user id
     * @param contactID       the contact id
     * @param startDateTime   the start date time
     * @param endDateTime     the end date time
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int insertAppointment(String apptTitle, String apptDescription,
                                             String apptLocation, String apptType, String createdBy,
                                             String lastUpdatedBy, int customerID,
                                             int userID, int contactID, String startDateTime, String endDateTime) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS ( Title, Description, Location, Type, Start, End, Created_by, "
                + "Last_Updated_By, Customer_ID, User_ID, Contact_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,apptTitle);
        ps.setString(2,apptDescription);
        ps.setString(3,apptLocation);
        ps.setString(4,apptType);
        ps.setString(5,startDateTime);
        ps.setString(6,endDateTime);
        ps.setString(7,createdBy);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9,customerID);
        ps.setInt(10,userID);
        ps.setInt(11,contactID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Insert int.
     *
     * @param contactName  the contact name
     * @param contactEmail the contact email
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int insert(String contactName, String contactEmail) throws SQLException {
        String sql = "INSERT INTO CONTACTS (Contact_Name, Email) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,contactName);
        ps.setString(2,contactEmail);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Update int.
     *
     * @param contactID   the contact id
     * @param contactName the contact name
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int update(int contactID, String contactName) throws SQLException {
        String sql = "UPDATE CONTACTS SET Contact_Name = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,contactName);
        ps.setInt(2, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Delete int.
     *
     * @param contactID the contact id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int delete(int contactID) throws SQLException{
        String sql = "DELETE FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Delete selected customer int.
     *
     * @param customerID the customer id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteSelectedCustomer(int customerID) throws SQLException{
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Delete selected appointment int.
     *
     * @param appointmentID the appointment id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteSelectedAppointment(int appointmentID) throws SQLException{
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Gets selected appointment type.
     *
     * @param appointmentID the appointment id
     * @return the selected appointment type
     * @throws SQLException the sql exception
     */
    public static ResultSet getSelectedAppointmentType(int appointmentID) throws SQLException{
        String sql = "Select Type FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Select.
     *
     * @throws SQLException the sql exception
     */
    public static void select() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
        }
    }

    /**
     * Gets customer to modify select.
     *
     * @param selectedCustomerID the selected customer id
     * @return the customer to modify select
     * @throws SQLException the sql exception
     */
    public static ResultSet getCustomerToModifySelect(int selectedCustomerID) throws SQLException {
        String sql = "SELECT * FROM Customers Where Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedCustomerID);

        ResultSet rs = ps.executeQuery();
        return rs;

    }

    /**
     * Gets appointments by customer.
     *
     * @param customerID the customer id
     * @return the appointments by customer
     * @throws SQLException the sql exception
     */
    public static ResultSet getAppointmentsByCustomer (int customerID) throws SQLException {
        String sql = "SELECT * FROM Appointments Where Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        return rs;

    }

    /**
     * Gets appointment to modify select.
     *
     * @param selectedAppointmentID the selected appointment id
     * @return the appointment to modify select
     * @throws SQLException the sql exception
     */
    public static ResultSet getAppointmentToModifySelect(int selectedAppointmentID) throws SQLException {
        String sql = "SELECT * FROM Appointments Where Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedAppointmentID);
        ResultSet rs = ps.executeQuery();
        return rs;

    }

    /**
     * Gets contacts select.
     *
     * @return the contacts select
     * @throws SQLException the sql exception
     */
    public static ResultSet getContactsSelect() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets countries select.
     *
     * @return the countries select
     * @throws SQLException the sql exception
     */
    public static ResultSet getCountriesSelect() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets all appointments select.
     *
     * @return the all appointments select
     * @throws SQLException the sql exception
     */
    public static ResultSet getAllAppointmentsSelect() throws SQLException {
        String sql = "SELECT Appointment_ID,Title,Description,Location,Start,End, Customer_ID, User_ID, Contact_ID" +
                " FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets unique type select.
     *
     * @return the unique type select
     * @throws SQLException the sql exception
     */
    public static ResultSet getUniqueTypeSelect() throws SQLException {
        String sql = "SELECT DISTINCT Type FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }



    /**
     * Gets this weeks appointments select. A lamdba expression was implemented here to calculate
     * the day of the year for the sunday starting the week of the date selected.  The WeekInterface
     * was implemented which contains the abstract method sundayMath.
     *
     * @param date the date
     * @return the this weeks appointments select
     * @throws SQLException the sql exception
     */
    public static ResultSet getThisWeeksAppointmentsSelect(LocalDate date) throws SQLException {
        String sql = "SELECT Appointment_ID,Title,Description,Location,Start,End, Customer_ID, User_ID, Contact_ID FROM Appointments WHERE Start > ? AND Start < ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        WeekInterface weekInterface = (inputDate) -> {
            int sunday;
            int dayValue = inputDate.getDayOfWeek().getValue();
            int yearValue = inputDate.getDayOfYear();
            if (dayValue == 7) {sunday = yearValue;}
            else { sunday = yearValue - (dayValue);}
            return sunday;
        };


        int sunday = weekInterface.sundayMath(date);


        int saturday = sunday + 6;

        LocalDate mondayWeek = LocalDate.ofYearDay(2022, sunday);
        LocalDate saturdayWeek = LocalDate.ofYearDay(2022, saturday);

        ps.setString(1, mondayWeek.toString());
        ps.setString(2, saturdayWeek.toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Working on completing query for appointments by month view on database form.
     *
     * @param date the date
     * @return the this months appointments select
     * @throws SQLException the sql exception
     */
    public static ResultSet getThisMonthsAppointmentsSelect(LocalDate date) throws SQLException {
        String sql = "SELECT Appointment_ID,Title,Description,Location,Start,End, Customer_ID, User_ID, Contact_ID " +
                " FROM Appointments WHERE Start > ? AND Start < ?";

        LocalDate monthStart = DateTimeHelper.getStartofMonth(date);
        LocalDate monthEnd = DateTimeHelper.getEndOfMonth(date);

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, monthStart.toString());
        ps.setString(2, monthEnd.toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    }


    /**
     * Gets months customers select.
     *
     * @param year  the year
     * @param month the month
     * @return the months customers select
     * @throws SQLException the sql exception
     */
    public static ResultSet getMonthsCustomersSelect(int year, Month month) throws SQLException {
        String sql = "Select Count(*) AS \"Total Customer Appointments by Month\"  from customers inner join appointments \n" +
                "on customers.Customer_ID = appointments.Customer_ID \n" +
                "Where Start >= ? AND Start <= ?";
        LocalDate monthStart = LocalDate.of(year,month.getMonthID(),1);
        LocalDate monthEnd = LocalDate.of(year, month.getMonthID(), 30);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, monthStart.toString());
        ps.setString(2, monthEnd.toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets customers by type select.
     *
     * @param type the type
     * @return the customers by type select
     * @throws SQLException the sql exception
     */
    public static ResultSet getCustomersByTypeSelect(String type) throws SQLException {
        String sql = "Select Count(*) AS \"Total Customer Appointments by Type\"  from customers inner join appointments \n" +
                "on customers.Customer_ID = appointments.Customer_ID \n" +
                "WHERE appointments.Type = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets this contacts appointments select.
     *
     * @param contactID the contact id
     * @return the this contacts appointments select
     * @throws SQLException the sql exception
     */
    public static ResultSet getThisContactsAppointmentsSelect(int contactID) throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM Appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(contactID));
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets first level div select.
     *
     * @return the first level div select
     * @throws SQLException the sql exception
     */
    public static ResultSet getFirstLevelDivSelect() throws SQLException {
        String sql = "SELECT * FROM First_Level_Divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets total appointments by contact.
     *
     * @return the total appointments by contact
     * @throws SQLException the sql exception
     */
    public static ResultSet getTotalAppointmentsByContact() throws SQLException {
        String sql = "Select Contact_Name, Count(*) AS \"Total Appointments by Contact\" from appointments " +
                "Inner Join Contacts On appointments.Contact_ID =\n" +
                "contacts.Contact_ID\n" + "group by Contact_Name Order by Count(*) Desc;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets total appointments by user.
     *
     * @param userID the user id
     * @return the total appointments by user
     * @throws SQLException the sql exception
     */
    public static ResultSet getTotalAppointmentsByUser(int userID) throws SQLException {
        String sql = "Select * from Appointments Where User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,userID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets filtered first level div select.
     *
     * @param country the country
     * @return the filtered first level div select
     * @throws SQLException the sql exception
     */
    public static ResultSet getFilteredFirstLevelDivSelect(int country) throws SQLException {
        String sql = "SELECT * FROM First_Level_Divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, country);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets all customers select.
     *
     * @return the all customers select
     * @throws SQLException the sql exception
     */
    public static ResultSet getAllCustomersSelect() throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets users select.
     *
     * @return the users select
     * @throws SQLException the sql exception
     */
    public static ResultSet getUsersSelect() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets user login select.
     *
     * @param userName the user name
     * @return the user login select
     * @throws SQLException the sql exception
     */
    public static ResultSet getUserLoginSelect(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Gets contact name select.
     *
     * @return the contact name select
     * @throws SQLException the sql exception
     */
    public static ResultSet getContactNameSelect() throws SQLException {
        String sql = "SELECT DISTINCT Contact_Name FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Select.
     *
     * @param contactID the contact id
     * @throws SQLException the sql exception
     */
    public static void select(int contactID) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
        }
    }
}


