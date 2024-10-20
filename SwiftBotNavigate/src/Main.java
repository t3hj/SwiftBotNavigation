import swiftbot.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static SwiftBotAPI swiftBot = new SwiftBotAPI();
    static Movements movements = new Movements(swiftBot);
    static QRCodeProcessing qrCodeProcessing = new QRCodeProcessing(swiftBot);
    static CommandProcessing commandProcessing = new CommandProcessing();
    static List<String> commandLog = new ArrayList<>(); // Declare commandLog
    private static final int maxConsecutiveInvalidQR = 5; // Maximum allowed consecutive invalid QR codes

    public static void main(String[] args) throws InterruptedException {
        boolean validQRProcessed = false; // Track if a valid QR code has been processed
        int consecutiveInvalidQR = 0; // Count the number of consecutive invalid QR codes

        try {
            // Welcome message
            System.out.println("Welcome to the Swiftbot Navigation Program!");

            // Enable buttons
            enableButtons();

            // Main program loop
            while (true) {
                // Display menu
                displayMenu();

                // Prompt for user input
                System.out.print("\nScan a QR code with your command: ");

                // Capture and decode QR code
                BufferedImage qrCodeImage = qrCodeProcessing.captureQRImage();
                String decodedCommand = qrCodeProcessing.decodeQRImage(qrCodeImage);

                // Process the command
                if (decodedCommand != null && !decodedCommand.isEmpty()) {
                    // Reset the counter if a valid QR code is processed
                    validQRProcessed = true;
                    consecutiveInvalidQR = 0;
                    CommandProcessing.processCommand(decodedCommand, commandLog, swiftBot);
                } else {
                    // Increment the counter if an invalid QR code is processed
                    consecutiveInvalidQR++;
                    // Check if the maximum number of consecutive invalid QR codes is reached
                    if (consecutiveInvalidQR >= maxConsecutiveInvalidQR && !validQRProcessed) {
                        System.out.println("Maximum consecutive invalid QR codes reached. Stopping program.");
                        System.exit(0); // Terminate the program
                        break; // Exit the loop
                    }
                }

                // Add delay between iterations
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            System.err.println("Please check the following:");
            System.err.println("- Ensure that the QR code is visible and undamaged.");
            System.err.println("- Make sure the Swiftbot is connected and powered on.");
            System.err.println("- Verify that the command format is correct.");
            System.err.println("- Check if any external factors may be affecting QR code scanning.");
            e.printStackTrace(); 
        }

    }

    private static void enableButtons() {
        // Enable button X
        swiftBot.enableButton(Button.X, () -> {
            System.out.println("Button X Pressed. Terminating program.");
//            swiftBot.fillUnderlights(rgb);
            System.exit(0);
        });

        // Enable button A
        swiftBot.enableButton(Button.A, () -> {
            System.out.println("Button A Pressed. Stopping Movement.");
            swiftBot.stopMove();
            System.out.println("Emergency stop executed.");
        });
        
        // Enable button B
        swiftBot.enableButton(Button.B, () -> {
            System.out.println("Button B Pressed. Stopping Movement.");
            swiftBot.stopMove();
            System.out.println("Emergency stop executed.");
        });
    }

    private static void displayMenu() {
    	System.out.println("+---------------------------------------------------+");
    	System.out.println("|               Swiftbot Navigation Program         |");
    	System.out.println("+---------------------------------------------------+");
    	System.out.println("| Commands:                                         |");
    	System.out.println("| 1. F [duration] [speed] - Move forward            |");
    	System.out.println("| 2. B [duration] [speed] - Move backward           |");
    	System.out.println("| 3. R [duration] [speed] - Turn right              |");
    	System.out.println("| 4. L [duration] [speed] - Turn left               |");
    	System.out.println("| 5. T [steps]            - Retrace  movements      |");
    	System.out.println("| 6. W                    - Write log to a text file|");
    	System.out.println("| 7. X [Button X]         - Terminate program       |");
    	System.out.println("| 8. A [Button A]         - Emergency Stop          |");
    	System.out.println("+---------------------------------------------------+");

    }
}
