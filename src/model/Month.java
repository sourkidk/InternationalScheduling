package model;

public class Month {

    private int monthID;
    private String monthName;

    public Month(int monthID, String monthName) {
        this.monthID = monthID;
        this.monthName = monthName;
    }

    @Override
    public String toString() {
        return monthID + " " + monthName;
    }

    public int getMonthID() {
        return monthID;
    }

    public void setMonthID(int monthID) {
        this.monthID = monthID;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
