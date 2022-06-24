package utilities;

import java.io.*;

public class SignOnLog {

    public static String logFileName = "/Users/Keith/Downloads/InternationalSchedulingKSF-master/src/resources/Scheduling_App_Log_File.txt";

    public static void addSignOnAttempt(String input) throws FileNotFoundException, IOException {
        try {

            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFileName, true));
            logWriter.write(input);
            logWriter.newLine();
            logWriter.flush();
            logWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
