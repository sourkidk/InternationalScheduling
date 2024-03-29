package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Jdbc.
 */
public class JDBC {
 private static final String protocol = "jdbc";
     private static final String vendor = ":mysql:";
         private static final String location = "//localhost/";
             private static final String databaseName = "client_schedule";
                 private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference

    /**
     * Sets db user name.
     *
     * @param dbUserName the db user name
     */
    public static void setDbUserName(String dbUserName) {
        JDBC.dbUserName = dbUserName;
    }

    private static String dbUserName = "sqlUser"; // Username
        private static String dbPassword = "Passw0rd!"; // Password
    /**
     * The constant connection.
     */
    public static Connection connection = null;  // Connection Interface
        private static PreparedStatement preparedStatement;

    /**
     * Make connection.
     */
    public static void makeConnection() {

          try {
              Class.forName(driver); // Locate Driver
              //password = Details.getPassword(); // Assign password
              connection = DriverManager.getConnection(jdbcUrl, dbUserName, dbPassword); // reference Connection object
              System.out.println("Connection successful!");
          }
                  catch(ClassNotFoundException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
                  catch(SQLException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
          }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Connection getConnection() {
                return connection;
            }

    /**
     * Close connection.
     */
    public static void closeConnection() {
                 try {
                     connection.close();
                     System.out.println("Connection closed!");
                 } catch (SQLException e) {
                     System.out.println(e.getMessage());
                 }
             }

    /**
     * Make prepared statement.
     *
     * @param sqlStatement the sql statement
     * @param conn         the conn
     * @throws SQLException the sql exception
     */
    public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
           if (conn != null)
               preparedStatement = conn.prepareStatement(sqlStatement);
           else
               System.out.println("Prepared Statement Creation Failed!");
       }

    /**
     * Gets prepared statement.
     *
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    public static PreparedStatement getPreparedStatement() throws SQLException {
           if (preparedStatement != null) {
               System.out.println(preparedStatement);
//               String rs =
               return preparedStatement;
           }
           else System.out.println("Null reference to Prepared Statement");
           return null;
       }

    /**
     * Gets db user name.
     *
     * @return the db user name
     */
    public static String getDbUserName() {
        return dbUserName;
    }

    /**
     * Gets db password.
     *
     * @return the db password
     */
    public static String getDbPassword() {
        return dbPassword;
    }

    /**
     * Sets db password.
     *
     * @param dbPassword the db password
     */
    public static void setDbPassword(String dbPassword) {
        JDBC.dbPassword = dbPassword;
    }
}