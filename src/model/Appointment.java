package model;

import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public class Appointment {

    private int apptID;
    private String apptName;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime apptStart;
    private LocalDateTime apptEnd;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Instantiates a new Appointment.
     *
     * @param apptID          the appt id
     * @param apptName        the appt name
     * @param apptDescription the appt description
     * @param apptLocation    the appt location
     * @param apptType        the appt type
     * @param apptStart       the appt start
     * @param apptEnd         the appt end
     * @param customerID      the customer id
     * @param userID          the user id
     * @param contactID       the contact id
     */
    public Appointment(int apptID, String apptName, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, int userID, int contactID) {
        this.apptID = apptID;
        this.apptName = apptName;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Gets appt id.
     *
     * @return the appt id
     */
    public int getApptID() {
        return apptID;
    }

    /**
     * Sets appt id.
     *
     * @param apptID the appt id
     */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /**
     * Gets appt name.
     *
     * @return the appt name
     */
    public String getApptName() {
        return apptName;
    }

    /**
     * Sets appt name.
     *
     * @param apptName the appt name
     */
    public void setApptName(String apptName) {
        this.apptName = apptName;
    }

    /**
     * Gets appt description.
     *
     * @return the appt description
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * Sets appt description.
     *
     * @param apptDescription the appt description
     */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * Gets appt location.
     *
     * @return the appt location
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * Sets appt location.
     *
     * @param apptLocation the appt location
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * Gets appt type.
     *
     * @return the appt type
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * Sets appt type.
     *
     * @param apptType the appt type
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * Gets appt start.
     *
     * @return the appt start
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    /**
     * Sets appt start.
     *
     * @param apptStart the appt start
     */
    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    /**
     * Gets appt end.
     *
     * @return the appt end
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    /**
     * Sets appt end.
     *
     * @param apptEnd the appt end
     */
    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets contact id.
     *
     * @param contactID the contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
