package client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame login = new LoginFrame();
            }
        }); // Execute Runnable on AWT thread
    }
}
