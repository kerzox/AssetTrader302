package client;

import util.NetworkUtils;
import util.Request;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class ClientServer implements Runnable {

    private static final String HOSTNAME = "127.0.0.1";
    private static final int CURRENT_PORT = 10000;

    private static Socket SERVER = null;
    private static boolean shutdown = false;

    @Override
    public void run() {
        client();
    }

    public void client() {
        try (Socket server = new Socket(HOSTNAME, CURRENT_PORT)) {
            handleInteraction(server);

        } catch (UnknownHostException e) {

            System.out.println("Server not found: " + e.getMessage());

        } catch (IOException e) {

            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public void handleInteraction(Socket socket) {
        do {
            SERVER = socket;

            List<Object> list = NetworkUtils.read(SERVER);
            parseServer(list);
        } while(SERVER.isConnected());

        shutdown = true;

    }

    private void parseServer(List<Object> data) {
        if (SwingUtilities.isEventDispatchThread()) {
            Gui.readServer(data);
        } else {
            SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            Gui.readServer(data);
                        }
                    });
        }
    }

    public static Socket getServer() {
        return SERVER;
    }
}
