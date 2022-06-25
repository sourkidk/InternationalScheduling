package utilities;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

public class DateTimeHelper {

    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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
        return dateTime;
    }

    public static ZonedDateTime convertToUTC(LocalDate date, int hour, int min, ZoneId timeZone) {

        LocalDateTime localDateTime = LocalDateTime.parse(date + "T"+ formatTime(hour,min));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, timeZone).withZoneSameInstant(UTC);

        return zonedDateTime;

    }
    public static ZonedDateTime convertFromUTC(LocalDateTime datetime, ZoneId timeZone) {

        ZonedDateTime zonedDateTime = ZonedDateTime.of(datetime, UTC).withZoneSameInstant(timeZone);
        return zonedDateTime;

    }

    public static LocalDateTime convertFromUTCLocal(String timeString, DateTimeFormatter sqlFormatter, ZoneId currentZone) {

        LocalDateTime utcTime = LocalDateTime.parse(timeString, sqlFormatter);
        ZonedDateTime zdtTime = ZonedDateTime.of(utcTime, UTC).withZoneSameInstant(currentZone);
        LocalDateTime localTime = zdtTime.toLocalDateTime();
        return localTime;

    }


    public static LocalDate getStartofMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate monthStart = LocalDate.of(year,month,1);

        return monthStart;
    }

    public static LocalDate getEndOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate nextMonthStart = LocalDate.of(year,month + 1,1);
        int lastDay = nextMonthStart.getDayOfYear() - 1;
        LocalDate monthEnd = LocalDate.ofYearDay(year, lastDay);
        return monthEnd;
    }


}
