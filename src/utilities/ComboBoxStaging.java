package utilities;

import javafx.scene.control.ComboBox;

/**
 * The type Combo box staging.
 */
public class ComboBoxStaging {

    /**
     * Sets combo box status.  Reduced code reuse by a factor of 5.
     *
     * @param contactComboBox the contact combo box
     * @param disableContact  the disable contact
     * @param typeComboBox    the type combo box
     * @param disableType     the disable type
     * @param monthComboBox   the month combo box
     * @param disableMonth    the disable month
     * @param yearComboBox    the year combo box
     * @param disableYear     the disable year
     */
    public static void setComboBoxStatus(ComboBox contactComboBox, boolean disableContact, ComboBox typeComboBox, boolean disableType,
                                         ComboBox monthComboBox, boolean disableMonth, ComboBox yearComboBox, boolean disableYear ) {
        contactComboBox.setDisable(true);
        typeComboBox.setDisable(true);
        monthComboBox.setDisable(false);
        yearComboBox.setDisable(false);
    }
}
