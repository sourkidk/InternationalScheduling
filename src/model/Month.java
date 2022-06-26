package model;

/**
 * The type Month.
 */
public class Month {

    private int monthID;
    private String monthName;

    /**
     * Instantiates a new Month.
     *
     * @param monthID   the month id
     * @param monthName the month name
     */
    public Month(int monthID, String monthName) {
        this.monthID = monthID;
        this.monthName = monthName;
    }

    @Override
    public String toString() {
        return monthID + " " + monthName;
    }

    /**
     * Gets month id.
     *
     * @return the month id
     */
    public int getMonthID() {
        return monthID;
    }

    /**
     * Sets month id.
     *
     * @param monthID the month id
     */
    public void setMonthID(int monthID) {
        this.monthID = monthID;
    }

    /**
     * Gets month name.
     *
     * @return the month name
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * Sets month name.
     *
     * @param monthName the month name
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
