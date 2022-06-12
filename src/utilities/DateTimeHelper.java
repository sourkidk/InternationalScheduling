package utilities;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class DateTimeHelper {

    public static String formatTime(int hour, int minute) {
        String tempHour = "";
        String tempMin = "";
        if ( hour < 10) { tempHour = "0" + String.valueOf(hour);}
        else tempHour = String.valueOf(hour);

        if ( minute == 0) { tempMin = "00";}
        else if ( minute < 10 ) { tempMin = "0" + String.valueOf(minute);}
        else tempMin = String.valueOf(minute);

        String time = tempHour + ":" + tempMin + ":00";
        return time;
     }

    public static String toStringDateTime(String date, int hour, int minute) {
        String time = formatTime(hour, minute);
        String dateTime = String.valueOf(date) + " " + time;
//        System.out.println(time);
        return dateTime;
    }


}
