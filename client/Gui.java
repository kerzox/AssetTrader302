package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {

    private static CardLayout cl = new CardLayout();
    private static GridBagLayout gbl = new GridBagLayout();

    public static void buildLogin() {

        JFrame frame = new JFrame("Login"); // Create holding frame
        JPanel container = new JPanel();
        container.setLayout(gbl); // Set layout to GridBagLayout for container Panel

        JButton userBtn = new JButton("USER");
        JButton adminBtn = new JButton("ADMIN");

        userBtn.addActionListener(e -> {
            frame.dispose(); // Close Login Frame
            buildUser(); // Build User Frame
        });

        JLabel userLabel = new JLabel("Username");
        JLabel pwdLabel = new JLabel("Password");

        JTextField userTextField = new JTextField();
        userTextField.setColumns(13);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(13);

        GridBagConstraints gbc = new GridBagConstraints();
        // gbc.fill = GridBagConstraints.HORIZONTAL; // All fields same horizontal length
        gbc.insets = new Insets(5, 5, 5, 5); // Between Spacing

        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(userLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(pwdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        container.add(userTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        container.add(passwordField, gbc);

        gbc.gridwidth = 1; // Reset grid width

        gbc.gridx = 1;
        gbc.gridy = 2;
        container.add(adminBtn, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        container.add(userBtn, gbc);

        frame.add(container);

        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setResizable(false); // Disable resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setVisible(true);

    }

    public static void buildUser() {
        JFrame frame = new JFrame(); // Create holding frame

        JPanel panelCont = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton button1 = new JButton("Switch to 2");
        JButton button2 = new JButton("Switch to 1");

        panel2.add(button1);
        panel3.add(button2);

        panel2.setBackground(Color.blue);
        panel3.setBackground(Color.green);

        panelCont.setLayout(cl);
        panelCont.add(panel2, "panel2");
        panelCont.add(panel3, "panel3");
        cl.show(panelCont, "panel2");

        button1.addActionListener(e -> cl.show(panelCont, "panel3"));
        button2.addActionListener(e -> cl.show(panelCont, "panel2"));

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setVisible(true);


    }

}
