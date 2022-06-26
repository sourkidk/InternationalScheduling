package utilities;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * The type Spinner factory.
 */
public class SpinnerFactory {

    /**
     * Sets spinners.
     *
     * @param startHourSpinner   the start hour spinner
     * @param setStartHour       the set start hour
     * @param startMinuteSpinner the start minute spinner
     * @param setStartMinute     the set start minute
     * @param endHourSpinner     the end hour spinner
     * @param setEndHour         the set end hour
     * @param endMinuteSpinner   the end minute spinner
     * @param setEndMinute       the set end minute
     */
    public static void setSpinners(Spinner startHourSpinner, int setStartHour, Spinner startMinuteSpinner, int setStartMinute, Spinner endHourSpinner,int setEndHour, Spinner endMinuteSpinner, int setEndMinute) {

        SpinnerValueFactory<Integer> startHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, setStartHour);
        startHourValueFactory.setWrapAround(true);
        startHourSpinner.setValueFactory(startHourValueFactory);

        SpinnerValueFactory<Integer> startMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, setStartMinute, 5);
        startMinuteValueFactory.setWrapAround(true);
        startMinuteSpinner.setValueFactory(startMinuteValueFactory);


        SpinnerValueFactory<Integer> endHourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23, setEndHour);
        endHourValueFactory.setWrapAround(true);
        endHourSpinner.setValueFactory((endHourValueFactory));

        SpinnerValueFactory<Integer> endMinuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, setEndMinute, 5);
        endMinuteValueFactory.setWrapAround(true);
        endMinuteSpinner.setValueFactory(endMinuteValueFactory);
    }
}
