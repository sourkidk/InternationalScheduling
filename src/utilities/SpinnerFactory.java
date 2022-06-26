package utilities;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class SpinnerFactory {

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
