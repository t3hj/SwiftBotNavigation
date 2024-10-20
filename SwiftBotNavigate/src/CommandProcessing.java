import java.util.List;
import swiftbot.*;

public class CommandProcessing {

	// Static variables to store speed and duration for command processing
	public static int speed = 0;
	public static int duration = 0;
	public static char commandType = ' ';
	// Method to process a decoded command
	public static void processCommand(String command, List<String> commandLog, SwiftBotAPI api) {

		// Validate the command format
		if (!isValidCommand(command)) {
			System.out.println("Invalid command format: " + command);
			return;
		}

		// Validate the movement command
		if (!isValidMovementCommand(command)) {
			System.out.println("Invalid movement command: " + command);
			return;
		}

		// Add the command to the command log
		commandLog.add(command);

		// Extract the command type
		commandType = command.charAt(0);

		// Process the command based on its type
		switch (commandType) {
		case 'F':
			// Move forward with the specified duration and speed
			System.out.println("Moving forward with duration " + duration + " and speed " + speed);
			Movements.moveForward(duration, speed);
			System.out.println("Forward movement completed.");
			break;
		case 'B':
			// Move backward with the specified duration and speed
			System.out.println("Moving backward with duration " + duration + " and speed " + speed);
			Movements.moveBackward(duration, speed);
			System.out.println("Backward movement completed.");
			break;
		case 'R':
			// Turn right with the specified duration and speed
			System.out.println("Turning right with duration " + duration + " and speed " + speed);
			Movements.turnRight(duration, speed);
			System.out.println("Right turn completed.");
			break;
		case 'L':
			// Turn left with the specified duration and speed
			System.out.println("Turning left with duration " + duration + " and speed " + speed);
			Movements.turnLeft(duration, speed);
			System.out.println("Left turn completed.");
			break;
		case 'T':
			// Extract the number of steps from the command
			int steps = extractSteps(command, commandLog);
			// Retrace previous movements with the specified number of steps
			System.out.println("Retracing previous " + steps + " movements.");
			RetraceMovements.retraceMovements(commandLog, steps, api );
			System.out.println("Retracing completed.");
			break;
		case 'W':
			// Write the command log to a file
			System.out.println("Writing command log to file...");
			WritingLog.writeLogToFile(commandLog, "commandLogFile.txt");
			System.out.println("Command log written to file.");
			break;
		case 'X':
			// Terminate the program
			System.out.println("Terminating program.");
			System.exit(0);
			break;
		case 'A':
			// Perform an emergency stop
			System.out.println("Activating emergency stop.");
			Movements.emergencyStop();
			System.out.println("Emergency stop completed.");
			break;
		default:
			// Handle invalid command types (should not reach here with the validation checks above)
			System.out.println("Invalid command type: " + commandType);
			break;
		}
	}

	public static boolean isMovementCommand(char commandType) {
		// Valid movement commands: F, B, R, L
		return commandType == 'F' || commandType == 'B' || commandType == 'R' || commandType == 'L';
	}

	// Method to extract the number of steps from the command
	public static int extractSteps(String command, List<String> commandLog) {
	    String[] parts = command.split(" ");
	    int steps = 0;

	    if (parts.length >= 2 && parts[0].equalsIgnoreCase("T")) {
	        try {
	            steps = Integer.parseInt(parts[1]);
	            
	            // Validate steps using isValidSteps method
	            if (!isValidSteps(steps, commandLog)) {
	                throw new IllegalArgumentException("Invalid number of steps.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Failed to translate string to number: " + e);
	        }
	    }
	    return steps; // Return 0 if no steps are found or if steps is invalid
	}

	// Method to extract the duration from the command string
	public static int extractDuration(String command) {
		// Split the command string by space and get the second element (index 1)
		String[] parts = command.split(" ");
		return Integer.parseInt(parts[1]);
	}

	// Method to extract the speed from the command string
	public static int extractSpeed(String command) {
		// Split the command string by space and get the third element (index 2)
		String[] parts = command.split(" ");
		return Integer.parseInt(parts[2]);
	}

	// Method to validate a command
	public static boolean isValidCommand(String command) {
		// Regular expression pattern for valid movement commands: [F|B|R|L] followed by two numbers
		// The pattern is case-insensitive (?i)
		// Valid movement commands are: F, B, R, L [fbrl]
		// Each movement command is followed by a space '\\s', then one or more digits representing duration '\\d+', and another space '\\s', then one or more digits representing speed '\\d+'
		String movementPattern = "(?i)[fbrl]\\s\\d+\\s\\d+";

		// Valid non-movement commands: W, X, A
		// These commands do not require any characters after the command
		String nonMovementPattern = "(?i)[wxa]\\s*";

		//    	// Valid retrace command: T followed by one or more digits
		//        String retracePattern = "(?i)t\\s\\d+";

		// Check if the command is a retracing command
		if (command.split(" ")[0].equalsIgnoreCase("T") && command.split(" ").length == 2) {
			System.out.println("IS A RETRACE COMMAND");
			return true;
		}

		// Check if the command matches either the movement pattern or the non-movement pattern
		return command.matches(movementPattern) || command.matches(nonMovementPattern) ;
	}

	// Additional methods for validation
	// Method to validate speed and duration for movement commands
	private static boolean isValidMovementCommand(String command) {
		speed = 0;
		duration = 0;

		// Extract command and its arguments from the command
		String[] parts = command.split(" ");

		// Ensure that there are at least 2 parts (command and its argument)
		if (parts.length >= 1) {
			Character tempCommand = parts[0].charAt(0);
			
			if (tempCommand.equals('W') || tempCommand.equals('X') || tempCommand.equals('A')) {
				
				return true;
				
			}

			// Check if the command is not "T" (retrace command)
			if (!tempCommand.equals('T')) {
				// For movement commands, validate duration and speed
				if (parts.length >= 3) {
					int tempDuration = Integer.parseInt(parts[1]); // Duration or number of steps is the second part
					int tempSpeed = Integer.parseInt(parts[2]); // Speed is the third part

					// Checking if duration/steps and speed are within valid ranges
					if (isValidDuration(tempDuration) && isValidSpeed(tempSpeed)) {
						duration = tempDuration;
						speed = tempSpeed;
						return true; // Return true if both duration/steps and speed are valid
					}
				}
			} else {
				// For the "T" command, ensure that the argument is a valid number of steps
				if (parts.length == 2) {
					int numSteps = Integer.parseInt(parts[1]); // Number of steps is the second part
					return numSteps >= 1; // Return true if the number of steps is valid (greater than or equal to 1)
				}
			}
		}
		return false; // If the command format is incorrect or if duration/steps and speed are invalid
	}


	// Method to validate a duration
	private static boolean isValidDuration(int duration) {
		return duration >= 1 && duration <= 6;
	}

	// Method to validate a speed
	private static boolean isValidSpeed(int speed) {
		return speed >= -100 && speed <= 100;
	}
	
	public static boolean isValidSteps(int steps, List<String> commandLog) {
	    // Ensure steps is a positive integer and does not exceed the total number of movements executed so far
	    return steps > 0 && steps <= commandLog.size();
	}
}
