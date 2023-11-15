import java.io.*;
import java.net.Socket;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server's IP address or hostname
        int serverPort = 5235; // Server's port

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    int userID;
                    String postcode;
                    double co2Concentration;

                    try {
                        System.out.print("Enter User ID: ");
                        userID = scanner.nextInt();

                        System.out.print("Enter Postcode: ");
                        postcode = scanner.next();

                        System.out.print("Enter CO2 Concentration (ppm): ");
                        co2Concentration = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input format. Please enter valid data.");
                        scanner.next(); // Clear the invalid input from the scanner
                        continue; // Restart the loop for valid input
                    }

                    // Create ResearchData object
                    ResearchData researchData = new ResearchData(userID, postcode, co2Concentration);

                    // Send ResearchData object to the server
                    objectOutput.writeObject(researchData);
                    objectOutput.flush();

                    // Example of reading response from the server if needed
                    // Object response = objectInput.readObject();
                    // Handle the response accordingly

                    System.out.print("Do you want to enter more data? (yes/no): ");
                    String moreData = scanner.next();

                    if (!moreData.equalsIgnoreCase("yes")) {
                        break; // Exit the loop if the user doesn't want to enter more data
                    }
                }
            }

            // Close the client socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}