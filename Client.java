import java.io.*;
import java.net.Socket;
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
                    System.out.print("Enter User ID: ");
                    int userID = scanner.nextInt();

                    System.out.print("Enter Postcode: ");
                    String postcode = scanner.next();

                    System.out.print("Enter CO2 Concentration (ppm): ");
                    double co2Concentration = scanner.nextDouble();

                    // Send user input data to the server
                    objectOutput.writeInt(userID);
                    objectOutput.writeObject(postcode);
                    objectOutput.writeDouble(co2Concentration);
                    objectOutput.flush();

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