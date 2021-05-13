package client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
        }); // Execute Runnable on AWT thread

        // USER LOGIN: test, pwd
        // ADMIN LOGIN: root, secret
    }
}
