import java.util.ArrayList;
import java.util.List;
import swiftbot.*;

public class RetraceMovements {

	// Method to retrace previous movements
	public static void retraceMovements(List<String> commandLog, int steps, SwiftBotAPI api) {
		// Create a new list to store only movement commands
		List<String> movementCommands = new ArrayList<>();
		System.out.println(commandLog);

		// Populate the movementCommands list by filtering out 'T' and 'W' commands
		for (String command : commandLog) {
		    char commandType = command.charAt(0);
		    if (CommandProcessing.isMovementCommand(commandType)) {
		        movementCommands.add(command);
		    }
		}

		// Determine the starting index for retracing movements
		int startIndex = Math.max(0, movementCommands.size() - steps);

		// Track the number of movements retraced
		int retracedMovements = 0;

		// Iterate over the movementCommands list in reverse order
		for (int i = movementCommands.size() - 1; i >= startIndex && retracedMovements < steps; i--) {
		    String command = movementCommands.get(i);

		    // Print the command being executed
		    System.out.println("Executing command: " + command);

		    // Process the movement command using CommandProcessing class
		    try {
		        CommandProcessing.processCommand(command, commandLog, api);
		        // Print a message after the command is executed
		        System.out.println("Command executed successfully.");
		    } catch (Exception e) {
		        // If there's an error during retracing, provide a clear error message
		        System.out.println("Error retracing movement: " + e.getMessage());
		        e.printStackTrace();
		    }

		    // Increment retracedMovements
		    retracedMovements++;
		}

	}    
}   