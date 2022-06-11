package utilities;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class DateTimeHelper {

    public static String concatDateTime(LocalDate date, int hour, int minute) {
        String time = String.valueOf(hour) + ":" + String.valueOf(minute) + ":00";
        String dateTime = String.valueOf(date) + " " + time;
        return dateTime;
    }


}
