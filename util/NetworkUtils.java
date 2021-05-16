package util;

import java.io.*;
import java.net.Socket;

public class NetworkUtils {

    /**
     * Write to the specified socket
     * @param socket
     * @param msg
     */

    public static void writeString(Socket socket, String msg) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read from the specified socket
     * @param socket
     * @return
     */

    public static String readString(Socket socket) {
        try {
             return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "nothing to read.";
    }

}
