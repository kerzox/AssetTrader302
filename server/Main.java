package server;

import javax.swing.*;

public class Main {

    // main function.
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> Gui.buildLogin()); // Execute Runnable on AWT thread

    }
}
