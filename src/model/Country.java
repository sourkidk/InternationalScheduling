package model;

/**
 * The type Country.
 */
public class Country {

    private int countryID;
    private String countryName;

    /**
     * Instantiates a new Country.
     *
     * @param countryID   the country id
     * @param countryName the country name
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return countryID + " " + countryName;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets country id.
     *
     * @param countryID the country id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
