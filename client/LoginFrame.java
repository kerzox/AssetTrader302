package client;

import util.NetworkUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements ActionListener {

    private GridBagLayout gbl = new GridBagLayout();

    private JFrame frame = new JFrame("Login"); // Create holding frame
    private JPanel container = new JPanel();

    private JButton userBtn = new JButton("USER");
    private JButton adminBtn = new JButton("ADMIN");

    private JLabel userLabel = new JLabel("Username");
    private JLabel pwdLabel = new JLabel("Password");

    private JTextField userTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField();

    /**
     * Constructor of LoginFrame
     */
    public LoginFrame() {

        setLayoutManager();
        //setTextFieldsLogin();
        buildFrame();
        addBtnAction();

        frame.add(container);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setResizable(false); // Disable resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null); // Centers GUI on open
        frame.setVisible(true);
    }

    /**
     * Add action listeners to buttons
     * Set self as source of listener
     */
    private void addBtnAction() {
        userBtn.addActionListener(this);
        adminBtn.addActionListener(this);
    }

    /**
     * Sets layout of container frame
     */
    private void setLayoutManager() {
        container.setLayout(gbl); // Set layout to GridBagLayout for container Panel
    }

    /**
     * Configures text field objects
     */
    private void setTextFieldsLogin() {
        userTextField.setColumns(13);
        passwordTextField.setColumns(13);
    }

    /**
     * Constructs positions and size of components in frame
     */
    private void buildFrame() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // All fields same horizontal length
        gbc.insets = new Insets(5, 5, 5, 5); // Between Spacing

        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(userLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(pwdLabel, gbc);

        gbc.gridwidth = 2;
        gbc.weightx = 1;

        gbc.gridx = 1;
        gbc.gridy = 0;
        container.add(userTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        container.add(passwordTextField, gbc);

        gbc.gridwidth = 1; // Reset grid width
        gbc.weightx = 0;

        gbc.gridx = 1;
        gbc.gridy = 2;
        container.add(adminBtn, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        container.add(userBtn, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String userNameText = userTextField.getText();
        char[] passwordChar = passwordTextField.getPassword();
        String pwdText = new String(passwordChar);

        // User ActionEvent
        if (e.getSource() == userBtn) {
            if (userNameText.equals("test") && pwdText.equals("pwd")) {
                JOptionPane.showMessageDialog(this, "Login as User");
                frame.dispose(); // Close Login Frame
                Gui.buildUser();

            }
            else {
                JOptionPane.showMessageDialog(this, "Failed Login as User", "Incorrect Login", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Admin ActionEvent
        if (e.getSource() == adminBtn) {
            if (userNameText.equals("root") && pwdText.equals("secret")) {
                JOptionPane.showMessageDialog(this, "Login as Admin");
                frame.dispose(); // Close Login Frame
                Gui.buildAdmin();
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed Login as Admin", "Incorrect Login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
