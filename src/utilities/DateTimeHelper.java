package utilities;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

/**
 * The type Date time helper.
 */
public class DateTimeHelper {

    /**
     * The Sql formatter.
     */
    DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Format time string.
     *
     * @param hour   the hour
     * @param minute the minute
     * @return the string
     */
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

    /**
     * To string date time string.
     *
     * @param date   the date
     * @param hour   the hour
     * @param minute the minute
     * @return the string
     */
    public static String toStringDateTime(String date, int hour, int minute) {
        String time = formatTime(hour, minute);
        String dateTime = String.valueOf(date) + " " + time;
        return dateTime;
    }

    /**
     * Convert to utc zoned date time.
     *
     * @param date     the date
     * @param hour     the hour
     * @param min      the min
     * @param timeZone the time zone
     * @return the zoned date time
     */
    public static ZonedDateTime convertToUTC(LocalDate date, int hour, int min, ZoneId timeZone) {

        LocalDateTime localDateTime = LocalDateTime.parse(date + "T"+ formatTime(hour,min));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, timeZone).withZoneSameInstant(UTC);

        return zonedDateTime;

    }

    /**
     * Convert from utc zoned date time.
     *
     * @param datetime the datetime
     * @param timeZone the time zone
     * @return the zoned date time
     */
    public static ZonedDateTime convertFromUTC(LocalDateTime datetime, ZoneId timeZone) {

        ZonedDateTime zonedDateTime = ZonedDateTime.of(datetime, UTC).withZoneSameInstant(timeZone);
        return zonedDateTime;

    }

    /**
     * Convert from utc local local date time.
     *
     * @param timeString   the time string
     * @param sqlFormatter the sql formatter
     * @param currentZone  the current zone
     * @return the local date time
     */
    public static LocalDateTime convertFromUTCLocal(String timeString, DateTimeFormatter sqlFormatter, ZoneId currentZone) {

        LocalDateTime utcTime = LocalDateTime.parse(timeString, sqlFormatter);
        ZonedDateTime zdtTime = ZonedDateTime.of(utcTime, UTC).withZoneSameInstant(currentZone);
        LocalDateTime localTime = zdtTime.toLocalDateTime();
        return localTime;

    }


    /**
     * Gets startof month.
     *
     * @param date the date
     * @return the startof month
     */
    public static LocalDate getStartofMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate monthStart = LocalDate.of(year,month,1);

        return monthStart;
    }

    /**
     * Gets end of month.
     *
     * @param date the date
     * @return the end of month
     */
    public static LocalDate getEndOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate nextMonthStart = LocalDate.of(year,month + 1,1);
        int lastDay = nextMonthStart.getDayOfYear() - 1;
        LocalDate monthEnd = LocalDate.ofYearDay(year, lastDay);
        return monthEnd;
    }


}
