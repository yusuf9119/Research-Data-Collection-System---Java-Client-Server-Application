import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server's IP address or hostname
        int serverPort = 12345; // Server's port

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            // Create an ObjectOutputStream to send data to the server
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());

            int userID = 123;
            String postcode = "SW1A 1AA";
            double co2Concentration = 400.5;

            // Send data to the server
            objectOutput.writeInt(userID);
            objectOutput.writeObject(postcode);
            objectOutput.writeDouble(co2Concentration);
            objectOutput.flush();

            // Close the client socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}