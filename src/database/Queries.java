package database;

import database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Queries {

    public static int insertCustomer(String customerName, String customerAddress,
                                     String customerPostalCode, String customerPhoneNumber,
                                     String createdBy, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,customerName);
        ps.setString(2,customerAddress);
        ps.setString(3,customerPostalCode);
        ps.setString(4,customerPhoneNumber);
        ps.setString(5,createdBy);
        ps.setString(6,lastUpdatedBy);
        ps.setInt(7,divisionID);
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
    public static ResultSet getSelect() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
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


