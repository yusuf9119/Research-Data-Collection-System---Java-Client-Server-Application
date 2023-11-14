
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        int port = 5235;
        List<ResearchData> dataStore = new ArrayList<>();

        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is listening on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New connection from " + clientSocket.getInetAddress());

                    // Handle client connection using a separate thread
                    ClientHandler handler = new ClientHandler(clientSocket, dataStore);
                    Thread thread = new Thread(handler);
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private List<ResearchData> dataStore;

        public ClientHandler(Socket socket, List<ResearchData> dataStore) {
            this.socket = socket;
            this.dataStore = dataStore;
        }

        @Override
        public void run() {
            try (ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream())) {
                while (true) {
                    // Read object sent from client
                    Object receivedObject = objectInput.readObject();

                    if (receivedObject instanceof ResearchData) {
                        ResearchData researchData = (ResearchData) receivedObject;

                        // Print received data with timestamp
                        System.out.println("Received data: User ID: " + researchData.getUserID()
                                + ", Postcode: " + researchData.getPostcode()
                                + ", CO2 Concentration: " + researchData.getCo2Concentration()
                                + ", Timestamp: " + researchData.getTimestamp());

                        // Add received data to dataStore
                        synchronized (dataStore) {
                            dataStore.add(researchData);
                        }
                    } else {
                        System.out.println("Invalid object received from client.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}