package utilities;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Sign on log.  Class for generating log file from login attempts.
 */
public class SignOnLog {

    /**
     * The constant logFileName.
     */
    public static String logFileName = "/login_activity.txt";
    /**
     * The constant sqlFormatter.
     */
    public static DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Log sign on attempt.  Uses BufferedWriter class to generate lines of text from the login parameters.
     *
     * @param input the input
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           the io exception
     */
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
