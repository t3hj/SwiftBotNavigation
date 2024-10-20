import swiftbot.*;
import java.awt.image.BufferedImage;

public class QRCodeProcessing {
    private static SwiftBotAPI swiftBot;

    // Constructor
    public QRCodeProcessing(SwiftBotAPI api) {
        try {
            swiftBot = api;
        } catch (RuntimeException e) {
            // Print error message if I2C is disabled
            System.out.println("\nI2C disabled!");
            System.out.println("Run the following command:");
            System.out.println("sudo raspi-config nonint do_i2c 0\n");
            // Exit program with error code 5
            System.exit(5);
//            handleInitialisationError();
        }
    }

//    // Method to handle initialisation error
//    private void handleInitialisationError() {
//        System.out.println("\nSwiftBotAPI initialisation failed!");
//        System.out.println("Please make sure the SwiftBot is properly connected and initialised.");
//        System.out.println("If the issue persists, check the SwiftBotAPI documentation or contact support.");
//        System.exit(1); // Exit the program with an error code
//    }

    // Method to capture a QR code image
    public BufferedImage captureQRImage() {
        return swiftBot.getQRImage();
    }

    // Method to decode a QR code image
    public String decodeQRImage(BufferedImage img) {
        try { 
            String decodedText = swiftBot.decodeQRImage(img);
            if (decodedText != null && !decodedText.isEmpty()) {
                System.out.println("Decoded message: " + decodedText); // Display decoded message to the user
                return decodedText; // Return the decoded text if it's not null or empty
            } else {
                System.out.println("Failed to decode QR code");
                return ""; // Return an empty string if decoding fails
            }
        } catch (RuntimeException e) {
            handleDecodingError(e);
            return ""; // Return an empty string if decoding fails
        }
    }

    // Method to handle decoding error
    private void handleDecodingError(RuntimeException e) {
        System.out.println("QR code decoding failed: " + e.getMessage());
        System.out.println("Make sure the provided image contains a valid QR code.");
    }
}
