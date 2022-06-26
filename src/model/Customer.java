package model;

/**
 * The type Customer.
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionID;

    /**
     * Instantiates a new Customer.
     *
     * @param customerId          the customer id
     * @param customerName        the customer name
     * @param customerAddress     the customer address
     * @param customerPostalCode  the customer postal code
     * @param customerPhoneNumber the customer phone number
     * @param divisionID          the division id
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int divisionID) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
    }

    @Override
    public String toString() {
        return customerId + " " + customerName;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets customer address.
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets customer address.
     *
     * @param customerAddress the customer address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Gets customer postal code.
     *
     * @return the customer postal code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Sets customer postal code.
     *
     * @param customerPostalCode the customer postal code
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Gets customer phone number.
     *
     * @return the customer phone number
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Sets customer phone number.
     *
     * @param customerPhoneNumber the customer phone number
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets division id.
     *
     * @param divisionID the division id
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
