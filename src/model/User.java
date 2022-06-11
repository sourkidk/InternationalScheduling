package model;

import java.time.LocalDateTime;

public class User {

    private int userID;
    private String userName;
    private String userPassword;

    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return userID + " " + userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
