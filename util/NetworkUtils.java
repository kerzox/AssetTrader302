package util;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkUtils {

//    /**
//     * Write to the specified socket
//     * @param socket
//     * @param msg
//     */
//
//    public static void writeString(Socket socket, String msg) {
//        try {
//            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
//            writer.println(msg);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    /**
//     * Read from the specified socket
//     * @param socket
//     * @return
//     */
//
//    public static String readString(Socket socket) {
//        try {
//             return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return "nothing to read.";
//    }


    /**
     * Write to the specified socket.
     * @param socket specified socket
     * @param data data in form of an object
     */

    public static void write(Socket socket, Object... data) {
        try {
            OutputStream output = socket.getOutputStream();
            output.flush();
            List<Object> temp = new ArrayList<>(Arrays.asList(data));
            new ObjectOutputStream(output).writeObject(temp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads from a specified socket.
     * @param socket specified socket
     * @return List of all objects sent
     */

    public static List<Object> read(Socket socket) {
        try {
            InputStream stream = socket.getInputStream();
            Object object = new ObjectInputStream(socket.getInputStream()).readObject();
            return (List<Object>) object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
