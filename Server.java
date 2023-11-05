import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        List<ResearchData> dataStore = new ArrayList<>();

        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is listening on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New connection from " + clientSocket.getInetAddress());

                    // Handle client connection using a separate thread
                    ClientHandler handler = new ClientHandler(clientSocket, dataStore);
                    handler.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private List<ResearchData> dataStore;

        public ClientHandler(Socket socket, List<ResearchData> dataStore) {
            this.socket = socket;
            this.dataStore = dataStore;
        }

        @Override
        public void run() {
            try (ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream())) {
                int userID = objectInput.readInt();
                String postcode = (String) objectInput.readObject();
                double co2Concentration = objectInput.readDouble();

                ResearchData researchData = new ResearchData(userID, postcode, co2Concentration);
                dataStore.add(researchData);

                System.out.println("Received data: User ID: " + userID + ", Postcode: " + postcode + ", CO2 Concentration: " + co2Concentration);

                // Close the client socket
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
