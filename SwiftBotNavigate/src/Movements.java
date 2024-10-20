import swiftbot.*;

public class Movements {
    static SwiftBotAPI swiftBot; // Static reference to SwiftBotAPI

    // Constructor to initialise the SwiftBotAPI
    public Movements(SwiftBotAPI api) {

            swiftBot = api;

    }

    // Method to move the robot forward
    public static void moveForward(int duration, int speed) {
        try {
            // Move the robot forward with the specified speed and duration
            swiftBot.move(speed, speed, duration * 1000);
        } catch (RuntimeException e) {
            // Print error message if there's an exception while moving forward
            handleMovementError("forward", e);
        }
    }

    // Method to move the robot backward
    public static void moveBackward(int duration, int speed) {
        try {
            // Move the robot backward with the specified speed and duration
            swiftBot.move(-speed, -speed, duration * 1000);
        } catch (RuntimeException e) {
            // Print error message if there's an exception while moving backward
            handleMovementError("backward", e);
        }
    }

    // Method to turn the robot left
    public static void turnLeft(int duration, int speed) {
        try {
            // Turn the robot left by moving one wheel backward and the other forward for a short duration
            swiftBot.move(-50, 50, 500);
            // Then, move both wheels forward to maintain the direction for the specified duration
            swiftBot.move(speed, speed, duration * 1000);
        } catch (RuntimeException e) {
            // Print error message if there's an exception while turning left
            handleMovementError("left", e);
        }
    }

    // Method to turn the robot right
    public static void turnRight(int duration, int speed) {
        try {
            // Turn the robot right by moving one wheel forward and the other backward for a short duration
            swiftBot.move(50, -50, 500);
            // Then, move both wheels forward to maintain the direction for the specified duration
            swiftBot.move(speed, speed, duration * 1000);
        } catch (RuntimeException e) {
            // Print error message if there's an exception while turning right
            handleMovementError("right", e);
        }
    }

    // Method to perform an emergency stop
    public static void emergencyStop() {
        try {
            // Stop the robot's movement
            swiftBot.stopMove();
            // Prompt the user to use Button A to carry out the Emergency Stop
            System.out.println("Please use Button A / B to carry out the Emergency Stop "
                    + "as the SwiftBot must be in motion for this function to work.");
        } catch (RuntimeException e) {
        	// Print error message if there's an exception during emergency stop
            handleMovementError("emergency stop", e);
        }
    }
    
 // Method to handle movement errors
    private static void handleMovementError(String movement, RuntimeException e) {
        System.err.println("Error: Unable to perform " + movement + " movement.");
        System.err.println("Please check the command and try again.");
        e.printStackTrace();
    }
}
