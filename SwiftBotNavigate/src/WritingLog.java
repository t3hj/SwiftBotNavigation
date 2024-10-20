import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WritingLog {

    // Method to write the command log to a file
    public static void writeLogToFile(List<String> commandLog, String filePath) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            // Open a BufferedWriter to append to the specified file path

            // Iterate through the command log
            for (String command : commandLog) {
                // Format the current time-stamp
                String formattedTime = formatTime(LocalDateTime.now());

                // Write the formatted log entry to the file
                logWriter.write(formattedTime + " - " + command);
                // Write a newline character to separate log entries
                logWriter.newLine();
            }
        } catch (IOException e) {
            // Catch and handle any IOExceptions that might occur during the file writing process
            System.err.println("Error writing to log file: " + e.getMessage());
            // Print an error message to the standard error stream
        }
    }

    // Method to format the time-stamp
    private static String formatTime(LocalDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
}
