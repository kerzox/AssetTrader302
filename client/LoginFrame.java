package client;

import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private GridBagLayout gbl = new GridBagLayout();

    JFrame frame = new JFrame("Login"); // Create holding frame
    JPanel container = new JPanel();

    JButton userBtn = new JButton("USER");
    JButton adminBtn = new JButton("ADMIN");

    JLabel userLabel = new JLabel("Username");
    JLabel pwdLabel = new JLabel("Password");

    JTextField userTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();

    /**
     * Constructor of LoginFrame
     */
    public LoginFrame() {

        setLayoutManager();
        setTextFields();
        buildLoginFrame();
        addBtnAction();

        frame.add(container);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setResizable(false); // Disable resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Add action listeners to buttons
     * Set self as source of listener
     */
    public void addBtnAction() {
        userBtn.addActionListener(this);
        adminBtn.addActionListener(this);
    }

    /**
     * Sets layout of container frame
     */
    public void setLayoutManager() {
        container.setLayout(gbl); // Set layout to GridBagLayout for container Panel
    }

    /**
     * Configures text field objects
     */
    public void setTextFields() {
        userTextField.setColumns(13);
        passwordTextField.setColumns(13);
    }

    /**
     * Constructs positions and size of components in frame
     */
    public void buildLoginFrame() {
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
        container.add(passwordTextField, gbc);

        gbc.gridwidth = 1; // Reset grid width

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
            if (userNameText.equalsIgnoreCase("test") && pwdText.equalsIgnoreCase("pwd")) {
                JOptionPane.showMessageDialog(this, "Login as User");
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed Login as User", "Incorrect Login", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Admin ActionEvent
        if (e.getSource() == adminBtn) {
            if (userNameText.equalsIgnoreCase("root") && pwdText.equalsIgnoreCase("secret")) {
                JOptionPane.showMessageDialog(this, "Login as Admin");
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed Login as Admin", "Incorrect Login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
