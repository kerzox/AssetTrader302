package client;

import util.NetworkUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer implements Runnable {

    private static final String HOSTNAME = "127.0.0.1";
    private static final int CURRENT_PORT = 10000;

    private static Socket SERVER = null;
    private static boolean shutdown = false;

    @Override
    public void run() {
        client();
    }

    public static void client() {
        try (Socket server = new Socket(HOSTNAME, CURRENT_PORT)) {
            handleInteraction(server);

        } catch (UnknownHostException e) {

            System.out.println("Server not found: " + e.getMessage());

        } catch (IOException e) {

            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public static void handleInteraction(Socket socket) {
        do {
            SERVER = socket;
        } while(SERVER.isConnected());

        shutdown = true;

    }

    public static Socket getServer() {
        return SERVER;
    }
}
