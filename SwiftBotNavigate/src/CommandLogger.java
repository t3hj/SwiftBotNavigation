import java.util.ArrayList;
import java.util.List;

public class CommandLogger {
	private List<String> executedCommands; // List to store executed commands

	public CommandLogger() {
		executedCommands = new ArrayList<>(); // Initialise the list
	}

	// Method to log a command
	public void logCommand(String command) {
		executedCommands.add(command); // Add the command to the list
	}

	// Method to check if a command is logged
	// If fromQRCode is true, allows re-logging the command from a QR code
	// If fromQRCode is false, prevents re-logging a retraced movement command
	public boolean isLoggedCommand(String command, boolean fromQRCode) {
		if (fromQRCode) {
			// Check if the command is present in the executed commands list
			return executedCommands.contains(command);
		} else {
			// Check if the command is present in the executed commands list and was not retraced
			return executedCommands.contains(command) && isRetracedMovement(command);
		}
	}

	// Method to check if a command is a retraced movement command
	public boolean isRetracedMovement(String command) {
		// Check if the command is a movement command that is being retraced
		return command.startsWith("T");
	}
}
