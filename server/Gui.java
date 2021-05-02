package server;

import javax.swing.*;

public class Gui {

    public static void loginGUI() {
        JFrame frame = new JFrame("Login Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button = new JButton("Login");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

}
