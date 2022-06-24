package utilities;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SignOnLog {

    public static String logFileName = "/Users/Keith/Downloads/InternationalSchedulingKSF-master/src/resources/Scheduling_App_Log_File.txt";
    public static DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logSignOnAttempt(String input) throws FileNotFoundException, IOException {
        try {

            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFileName, true));
            logWriter.write(ZonedDateTime.now().format(sqlFormatter) + " " + ZoneId.systemDefault() + " " +
            input);
            logWriter.newLine();
            logWriter.flush();
            logWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
