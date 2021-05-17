package client;

import util.NetworkUtils;
import util.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static util.Request.Header.*;
import static util.Request.Type.*;

public class AdminFrame extends JFrame implements ActionListener {

    /**
     * DUMMY ARRAY OF OBJECTS
     */
    private String dummyUnits[] = {"unit1", "unit2", "unit3"};
    private String dummyUsers[] = {"user1", "user2", "user3"};

    private GridBagLayout gblPanelCont = new GridBagLayout();
    private GridBagLayout gblPanelMain = new GridBagLayout();
    private GridBagConstraints gbcPanelMain = new GridBagConstraints();
    private GridBagLayout gblPanelSide = new GridBagLayout();
    private GridBagConstraints gbcPanelSide = new GridBagConstraints();
    private GridBagLayout gblPanelGrid1 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid1 = new GridBagConstraints();
    private GridBagLayout gblPanelGrid2 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid2 = new GridBagConstraints();
    private GridBagLayout gblPanelGrid3 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid3 = new GridBagConstraints();
    private GridBagLayout gblPanelGrid4 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid4 = new GridBagConstraints();
    private GridBagLayout gblPanelGrid5 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid5 = new GridBagConstraints();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panelSide = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelGrid1 = new JPanel();
    private JPanel panelGrid2 = new JPanel();
    private JPanel panelGrid3 = new JPanel();
    private JPanel panelGrid4 = new JPanel();
    private JPanel panelGrid5 = new JPanel();

    private JLabel adminLabel = new JLabel("ADMIN SETTINGS");

    private JTextField newUsernameTxt = new JTextField();
    private JPasswordField newPasswordTxt = new JPasswordField();
    private JPasswordField editPasswordTxt = new JPasswordField();
    private JTextField addCreditsText = new JTextField();
    private JTextField newAssetText = new JTextField();
    private JTextField newOrganisationText = new JTextField();

    private JComboBox editUsernameCombo = new JComboBox(dummyUsers);
    private JComboBox newOrganisationCombo = new JComboBox(dummyUnits);
    private JComboBox editOrganisationCombo = new JComboBox(dummyUnits);
    private JComboBox creditsOrganisationCombo = new JComboBox(dummyUnits);

    private JButton newAccountBtn = new JButton("Add Account");
    private JButton editAccountBtn = new JButton("Edit Account");
    private JButton addCreditsBtn = new JButton("Add Credits");
    private JButton newAssetBtn = new JButton("Add Asset");
    private JButton newOrganisationBtn = new JButton("Add Organisation");

    private Color panelColor = new Color(240, 240, 240);
    private Color sideColor = new Color(205, 205, 205);



    /**
     * Constructor of UserFrame
     */
    public AdminFrame() {
        buildFrame();
        addBtnAction();

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout managers
        frame.setTitle("AssetTrader302- ADMIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null); // Centers GUI on open
        frame.setVisible(true);
    }

    /**
     * Constructs positions and size of components in frame
     */
    private void buildFrame() {
        buildMainFrame();
        buildSideFrame();


        panelCont.setLayout(gblPanelCont);
        panelCont.add(panelSide);
        panelCont.add(panelMain);
    }

    /**
     * Builds side panel of frame
     */
    private void buildSideFrame() {
        panelSide.setBackground(sideColor);
        panelSide.setPreferredSize(new Dimension(150, 500));
        panelSide.setLayout(gblPanelSide);
        gbcPanelSide.gridwidth = GridBagConstraints.REMAINDER; // Sets component as last in row
        gbcPanelSide.fill = GridBagConstraints.HORIZONTAL; // Horizontal fill components
        gbcPanelSide.insets = new Insets(5, 0, 5, 0); // Vertical spacing 5 - 5

        adminLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelSide.add(adminLabel, gbcPanelSide);
    }

    /**
     * Builds main panel of frame
     */
    private void buildMainFrame() {
        buildGridFrames();

        panelMain.setBackground(Color.BLACK);
        panelMain.setPreferredSize(new Dimension(850, 500));
        panelMain.setLayout(gblPanelMain);

        // Ensures components properly fill Main Frame
        gbcPanelMain.insets = new Insets(-1, -1, -1, -1);

        gbcPanelMain.fill = GridBagConstraints.BOTH;
        gbcPanelMain.weightx = 1;
        gbcPanelMain.weighty = 1;

        gbcPanelMain.gridx = 0;
        gbcPanelMain.gridy = 0;
        panelMain.add(panelGrid1, gbcPanelMain);

        gbcPanelMain.gridx = 1;
        gbcPanelMain.gridy = 0;
        panelMain.add(panelGrid2, gbcPanelMain);

        gbcPanelMain.gridx = 0;
        gbcPanelMain.gridy = 1;
        panelMain.add(panelGrid3, gbcPanelMain);

        gbcPanelMain.gridx = 0;
        gbcPanelMain.gridy = 2;
        panelMain.add(panelGrid4, gbcPanelMain);

        gbcPanelMain.gridheight = 2;
        gbcPanelMain.gridx = 1;
        gbcPanelMain.gridy = 1;
        panelMain.add(panelGrid5, gbcPanelMain);

    }

    /**
     * Builds grid panels in main panel
     */
    private void buildGridFrames() {
        buildGrid1();
        buildGrid2();
        buildGrid3();
        buildGrid4();
        buildGrid5();
    }

    /**
     * Builds grid panel 1
     */
    private void buildGrid1() {
        panelGrid1.setBackground(panelColor);
        panelGrid1.setLayout(gblPanelGrid1);

        gbcPanelGrid1.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcPanelGrid1.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcPanelGrid1.gridwidth = 2;

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 0;
        JLabel newAccountLabel = buildLabel("New Account");
        newAccountLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid1.add(newAccountLabel, gbcPanelGrid1);

        // LABELS
        gbcPanelGrid1.gridwidth = 1;

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 1;
        JLabel usernameLabel = buildLabel("Username");
        panelGrid1.add(usernameLabel, gbcPanelGrid1);

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 2;
        JLabel passwordLabel = buildLabel("Password");
        panelGrid1.add(passwordLabel, gbcPanelGrid1);

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 3;
        JLabel organisationLabel = buildLabel("Organisation");
        panelGrid1.add(organisationLabel, gbcPanelGrid1);

        // TEXT FIELDS
        gbcPanelGrid1.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcPanelGrid1.weightx = 1;
        gbcPanelGrid1.gridwidth = 2;
        gbcPanelGrid1.gridx = 1;
        gbcPanelGrid1.gridy = 1;
        panelGrid1.add(newUsernameTxt, gbcPanelGrid1);

        gbcPanelGrid1.gridx = 1;
        gbcPanelGrid1.gridy = 2;
        panelGrid1.add(newPasswordTxt, gbcPanelGrid1);

        // COMBO FIELDS
        gbcPanelGrid1.gridx = 1;
        gbcPanelGrid1.gridy = 3;
        panelGrid1.add(newOrganisationCombo, gbcPanelGrid1);

        // BUTTONS
        gbcPanelGrid1.weightx = 0;
        gbcPanelGrid1.gridwidth = 1;
        gbcPanelGrid1.gridx = 2;
        gbcPanelGrid1.gridy = 4;

        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(newAccountBtn, BorderLayout.EAST); // Align Button right
        panelGrid1.add(btnContainer, gbcPanelGrid1);
    }

    /**
     * Builds grid panel 2
     */
    private void buildGrid2() {
        panelGrid2.setBackground(panelColor);
        panelGrid2.setLayout(gblPanelGrid2);

        gbcPanelGrid2.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcPanelGrid2.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcPanelGrid2.gridwidth = 2;

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 0;
        JLabel editAccountLabel = buildLabel("Edit Account");
        editAccountLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid2.add(editAccountLabel, gbcPanelGrid2);

        // LABELS
        gbcPanelGrid2.gridwidth = 1;

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 1;
        JLabel usernameLabel = buildLabel("Username");
        panelGrid2.add(usernameLabel, gbcPanelGrid2);

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 2;
        JLabel passwordLabel = buildLabel("Password");
        panelGrid2.add(passwordLabel, gbcPanelGrid2);

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 3;
        JLabel organisationLabel = buildLabel("Organisation");
        panelGrid2.add(organisationLabel, gbcPanelGrid2);

        // TEXT FIELDS
        gbcPanelGrid2.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcPanelGrid2.weightx = 1;
        gbcPanelGrid2.gridwidth = 2;

        gbcPanelGrid2.gridx = 1;
        gbcPanelGrid2.gridy = 2;
        panelGrid2.add(editPasswordTxt, gbcPanelGrid2);

        // COMBO FIELDS
        gbcPanelGrid2.gridx = 1;
        gbcPanelGrid2.gridy = 1;
        panelGrid2.add(editUsernameCombo, gbcPanelGrid2);

        gbcPanelGrid2.gridx = 1;
        gbcPanelGrid2.gridy = 3;
        panelGrid2.add(editOrganisationCombo, gbcPanelGrid2);

        // BUTTONS
        gbcPanelGrid2.weightx = 0;
        gbcPanelGrid2.gridwidth = 1;
        gbcPanelGrid2.gridx = 2;
        gbcPanelGrid2.gridy = 4;

        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(editAccountBtn, BorderLayout.EAST); // Align Button right
        panelGrid2.add(btnContainer, gbcPanelGrid2);
    }

    /**
     * Builds grid panel 3
     */
    private void buildGrid3() {
        panelGrid3.setBackground(panelColor);
        panelGrid3.setLayout(gblPanelGrid3);

        gbcPanelGrid3.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcPanelGrid3.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcPanelGrid3.gridwidth = 2;

        gbcPanelGrid3.gridx = 0;
        gbcPanelGrid3.gridy = 0;
        JLabel newAssetLabel = buildLabel("New Asset");
        newAssetLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid3.add(newAssetLabel, gbcPanelGrid3);

        // LABELS
        gbcPanelGrid3.gridwidth = 1;

        gbcPanelGrid3.gridx = 0;
        gbcPanelGrid3.gridy = 1;
        JLabel assetNameLabel = buildLabel("Asset Name");
        panelGrid3.add(assetNameLabel, gbcPanelGrid3);

        // TEXT FIELDS
        gbcPanelGrid3.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcPanelGrid3.weightx = 1;
        gbcPanelGrid3.gridwidth = 2;

        gbcPanelGrid3.gridx = 1;
        gbcPanelGrid3.gridy = 1;
        panelGrid3.add(newAssetText, gbcPanelGrid3);

        // BUTTONS
        gbcPanelGrid3.weightx = 0;
        gbcPanelGrid3.gridwidth = 1;
        gbcPanelGrid3.gridx = 2;
        gbcPanelGrid3.gridy = 2;

        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(newAssetBtn, BorderLayout.EAST); // Align Button right
        panelGrid3.add(btnContainer, gbcPanelGrid3);
    }

    /**
     * Builds grid panel 4
     */
    private void buildGrid4() {
        panelGrid4.setBackground(panelColor);
        panelGrid4.setLayout(gblPanelGrid4);

        gbcPanelGrid4.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcPanelGrid4.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcPanelGrid4.gridwidth = 2;

        gbcPanelGrid4.gridx = 0;
        gbcPanelGrid4.gridy = 0;
        JLabel newOrganisationHeader = buildLabel("New Organisation");
        newOrganisationHeader.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid4.add(newOrganisationHeader, gbcPanelGrid4);

        // LABELS
        gbcPanelGrid4.gridwidth = 1;

        gbcPanelGrid4.gridx = 0;
        gbcPanelGrid4.gridy = 1;
        JLabel newOrganisationLabel = buildLabel("Organisation Name");
        panelGrid4.add(newOrganisationLabel, gbcPanelGrid4);

        // TEXT FIELDS
        gbcPanelGrid4.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcPanelGrid4.weightx = 1;
        gbcPanelGrid4.gridwidth = 2;

        gbcPanelGrid4.gridx = 1;
        gbcPanelGrid4.gridy = 1;
        panelGrid4.add(newOrganisationText, gbcPanelGrid4);

        // BUTTONS
        gbcPanelGrid4.weightx = 0;
        gbcPanelGrid4.gridwidth = 1;
        gbcPanelGrid4.gridx = 2;
        gbcPanelGrid4.gridy = 2;

        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(newOrganisationBtn, BorderLayout.EAST); // Align Button right
        panelGrid4.add(btnContainer, gbcPanelGrid4);
    }

    /**
     * Builds grid panel 5
     */
    private void buildGrid5() {
        panelGrid5.setBackground(panelColor);
        panelGrid5.setLayout(gblPanelGrid5);

        gbcPanelGrid5.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcPanelGrid5.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcPanelGrid5.gridwidth = 2;

        gbcPanelGrid5.gridx = 0;
        gbcPanelGrid5.gridy = 0;
        JLabel addCreditsLabel = buildLabel("Add Credits");
        addCreditsLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid5.add(addCreditsLabel, gbcPanelGrid5);

        // LABELS
        gbcPanelGrid5.gridwidth = 1;

        gbcPanelGrid5.gridx = 0;
        gbcPanelGrid5.gridy = 1;
        JLabel organisationLabel = buildLabel("Organisation");
        panelGrid5.add(organisationLabel, gbcPanelGrid5);

        gbcPanelGrid5.gridx = 0;
        gbcPanelGrid5.gridy = 2;
        JLabel totalCreditsLabel = buildLabel("Total Credits");
        panelGrid5.add(totalCreditsLabel, gbcPanelGrid5);

        // TEXT FIELDS
        gbcPanelGrid5.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcPanelGrid5.weightx = 1;
        gbcPanelGrid5.gridwidth = 2;

        gbcPanelGrid5.gridx = 1;
        gbcPanelGrid5.gridy = 2;
        panelGrid5.add(addCreditsText, gbcPanelGrid5);

        // COMBO FIELDS
        gbcPanelGrid5.gridx = 1;
        gbcPanelGrid5.gridy = 1;
        panelGrid5.add(creditsOrganisationCombo, gbcPanelGrid5);


        // BUTTONS
        gbcPanelGrid5.weightx = 0;
        gbcPanelGrid5.gridwidth = 1;
        gbcPanelGrid5.gridx = 2;
        gbcPanelGrid5.gridy = 3;

        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(addCreditsBtn, BorderLayout.EAST); // Align Button right
        panelGrid5.add(btnContainer, gbcPanelGrid5);
    }


    /**
     * Builds labels
     * @param title
     * @return label containing title input
     */
    private JLabel buildLabel(String title) {
        JLabel label = new JLabel(title);
        return label;
    }

    /**
     * Add action listeners to buttons
     * Set self as source of listener
     */
    private void addBtnAction() {
        newAccountBtn.addActionListener(this);
        editAccountBtn.addActionListener(this);
        addCreditsBtn.addActionListener(this);
        newAssetBtn.addActionListener(this);
        newOrganisationBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == newAccountBtn) {
                createAccount();
            }
            if (e.getSource() == editAccountBtn) {
                editAccount();
            }
            if (e.getSource() == addCreditsBtn) {
                addCredits();
            }
            if (e.getSource() == newAssetBtn) {
                createAsset();
            }
            if (e.getSource() == newOrganisationBtn) {
                createOrganisation();
            }
        } catch (TextInputException tiex) {
            JOptionPane.showMessageDialog(this, tiex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfex) {
            JOptionPane.showMessageDialog(this, "Input must be an integer.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createOrganisation() {
        String organisationNameText = newOrganisationText.getText();

        if (organisationNameText.isEmpty()) {
            throw new TextInputException("Fields cannot be empty.");
        }
        else {
            System.out.println("ADDING ORGANISATION: " + organisationNameText);
            NetworkUtils.write(ClientServer.getServer(), CREATE, ORGANISATION, organisationNameText);
        }
    }

    private void createAsset() {
        String assetNameText = newAssetText.getText();

        if (assetNameText.isEmpty()) {
            throw new TextInputException("Fields cannot be empty.");
        }
        else {
            System.out.println("ADDING ASSET: " + assetNameText);
            NetworkUtils.write(ClientServer.getServer(), CREATE, ASSET, assetNameText);
        }
    }

    private void addCredits() {
        String organisationText = (String) editOrganisationCombo.getSelectedItem();
        int creditsInt = Integer.parseInt(addCreditsText.getText());

        if (creditsInt <= 0) {
            throw new TextInputException("Fields cannot be empty or cannot be less than 1.");
        }
        else {
            System.out.println("ADDING CREDITS " + creditsInt +
                    " to " + organisationText);
        }
    }

    private void editAccount() {
        String userNameText = (String) editUsernameCombo.getSelectedItem();
        char[] passwordChar = editPasswordTxt.getPassword();
        String pwdText = new String(passwordChar);
        String organisationText = (String) editOrganisationCombo.getSelectedItem();

        if (pwdText.isEmpty()) {
            throw new TextInputException("Fields cannot be empty.");
        }
        else {
            System.out.println("EDITING --- Username: " + userNameText + " Password: "
                    + pwdText + " Organisation: " + organisationText);
        }
    }

    private void createAccount() {
        String userNameText = newUsernameTxt.getText();
        char[] passwordChar = newPasswordTxt.getPassword();
        String pwdText = new String(passwordChar);
        String organisationText = (String) newOrganisationCombo.getSelectedItem();

        if (userNameText.isEmpty() || pwdText.isEmpty()) {
            throw new TextInputException("Fields cannot be empty.");
        }

        System.out.println("ADDING --- Username: " + userNameText + " Password: " + pwdText + " Organisation: " + organisationText);

        NetworkUtils.write(ClientServer.getServer(), CREATE, ACCOUNT, userNameText, pwdText, organisationText);

    }
}
