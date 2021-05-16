package client;

import util.NetworkUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer implements Runnable {

    private static final String HOSTNAME = "127.0.0.1";
    private static final int CURRENT_PORT = 10000;

    private static Socket SERVER = null;


    @Override
    public void run() {
        while(true) {
            client();
        }
    }

    public static void client() {
        try (Socket server = new Socket(HOSTNAME, CURRENT_PORT)) {
            handleInteration(server);

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public static void handleInteration(Socket socket) {
        do {
            SERVER = socket;
        } while(SERVER.isConnected());

    }

    public static Socket getServer() {
        return SERVER;
    }
}
