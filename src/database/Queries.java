package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Month;
import utilities.DateTimeHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public abstract class Queries {

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();


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

    public static int insert(String contactName, String contactEmail) throws SQLException {
        String sql = "INSERT INTO CONTACTS (Contact_Name, Email) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,contactName);
        ps.setString(2,contactEmail);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(int contactID, String contactName) throws SQLException {
        String sql = "UPDATE CONTACTS SET Contact_Name = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,contactName);
        ps.setInt(2, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int contactID) throws SQLException{
        String sql = "DELETE FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            System.out.println(contactId + " | " + contactName + " | " + contactEmail);
        }
    }
    public static ResultSet getContactsSelect() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getCountriesSelect() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getAllAppointmentsSelect() throws SQLException {
        String sql = "SELECT * FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public static ResultSet getUniqueTypeSelect() throws SQLException {
        String sql = "SELECT DISTINCT Type FROM Appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getThisWeeksAppointmentsSelect(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM Appointments WHERE Start > ? AND Start < ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int dayValue = date.getDayOfWeek().getValue(); // ? 1
        int yearValue = date.getDayOfYear();            // 164
        int sunday = yearValue - (dayValue);        //163


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
     * */
    public static ResultSet getThisMonthsAppointmentsSelect(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM Appointments WHERE Start > ? AND Start < ?";

        LocalDate monthStart = DateTimeHelper.getStartofMonth(date);
        LocalDate monthEnd = DateTimeHelper.getEndOfMonth(date);

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, monthStart.toString());
        ps.setString(2, monthEnd.toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    }


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

    public static ResultSet getCustomersByTypeSelect(String type) throws SQLException {
        String sql = "Select Count(*) AS \"Total Customer Appointments by Type\"  from customers inner join appointments \n" +
                "on customers.Customer_ID = appointments.Customer_ID \n" +
                "WHERE appointments.Type = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public static ResultSet getThisContactsAppointmentsSelect(int contactID) throws SQLException {
        String sql = "SELECT * FROM Appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(contactID));
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getFirstLevelDivSelect() throws SQLException {
        String sql = "SELECT * FROM First_Level_Divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getFilteredFirstLevelDivSelect(int country) throws SQLException {
        String sql = "SELECT * FROM First_Level_Divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, country);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet getAllCustomersSelect() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public static ResultSet getUsersSelect() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public static ResultSet getContactNameSelect() throws SQLException {
        String sql = "SELECT DISTINCT Contact_Name FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static void select(int contactID) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            System.out.println(contactId + " | " + contactName + " | " + contactEmail);
        }
    }
}


