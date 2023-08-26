import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static Map<String, PrintWriter> clientesList = new HashMap<>();
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null; // Declarar aquí para poder cerrarlo en el finally
        try {
            serverSocket = new ServerSocket(12345); // Port
            ExecutorService pool = Executors.newFixedThreadPool(10);
    
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private String nombreUsuario;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                try (Scanner in = new Scanner(socket.getInputStream())) {
                    out = new PrintWriter(socket.getOutputStream(), true);

                    nombreUsuario = in.nextLine();
                    clientesList.put(nombreUsuario, out);

                    broadcast(nombreUsuario + " se ha unido al chat grupal :)");

                    while (true) {
                        String message = in.nextLine();
                        broadcast(nombreUsuario + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (nombreUsuario != null) {
                    clientesList.remove(nombreUsuario);
                    broadcast(nombreUsuario + " abandonó el chat grupal :(");
                }
            }
        }

        private void broadcast(String message) {
            for (PrintWriter client : clientesList.values()) {
                client.println(message);
            }
        }
    }
}
