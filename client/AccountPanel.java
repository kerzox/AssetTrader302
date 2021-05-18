package client;

import util.NetworkUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static util.Request.Header.ALTER;
import static util.Request.Type.ACCOUNT;

public class AccountPanel extends JPanel implements ActionListener {

    private String currentOrganisation = "HARDCODED";
    private String currentCredits = "HARDCODED";

    private GridBagLayout gblMain = new GridBagLayout();
    private GridBagConstraints gbcMain = new GridBagConstraints();
    private GridBagLayout gblGrid1 = new GridBagLayout();
    private GridBagConstraints gbcGrid1 = new GridBagConstraints();
    private GridBagLayout gblGrid2 = new GridBagLayout();
    private GridBagConstraints gbcGrid2 = new GridBagConstraints();
    private GridBagLayout gblGrid3 = new GridBagLayout();
    private GridBagConstraints gbcGrid3 = new GridBagConstraints();
    private GridBagLayout gblScrollCont = new GridBagLayout();
    private GridBagConstraints gbcScrollCont = new GridBagConstraints();

    private JPanel panelGrid1 = new JPanel();
    private JPanel panelGrid2 = new JPanel();
    private JPanel panelGrid3 = new JPanel();
    private JPanel panelScrollCont = new JPanel();

    private JTextField editPasswordText = new JTextField();

    private JButton editPasswordButton = new JButton("Change");

    private Color panelColor = new Color(240, 240, 240);

    private Font boldFont = new Font("Dialog", Font.BOLD, 12);
    private Font plainFont = new Font("Dialog", Font.PLAIN, 12);

    /**
     * Constructor
     */
    public AccountPanel() {
        buildMainPanel();
        addBtnAction();
    }

    /**
     * Builds main panel with grid panels
     */
    private void buildMainPanel() {
        buildGrid1();
        buildGrid2();
        buildGrid3();

        setBackground(Color.BLACK);
        setLayout(gblMain);

        // Ensures components properly fill Main Panel
        gbcMain.insets = new Insets(-1, -1, -1, -1);

        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;

        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        add(panelGrid1, gbcMain);

        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        add(panelGrid2, gbcMain);

        gbcMain.gridwidth = 2;
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        add(panelGrid3, gbcMain);
    }

    /**
     * Builds grid 1 with components
     */
    private void buildGrid1() {
        panelGrid1.setBackground(panelColor);
        panelGrid1.setLayout(gblGrid1);

        gbcGrid1.insets = new Insets(5, 25, 5, 5); // Padding left +20
        gbcGrid1.fill = GridBagConstraints.BOTH;

        // LABELS
        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 0;
        JLabel usernameLabel = buildLabel("Username");
        panelGrid1.add(usernameLabel, gbcGrid1);

        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 1;
        JLabel passwordLabel = buildLabel("Password");
        panelGrid1.add(passwordLabel, gbcGrid1);

        gbcGrid1.insets = new Insets(5, 5, 5, 25); // Padding right +20
        gbcGrid1.gridwidth = 2;
        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 0;
        JLabel currentUserLabel = buildLabel(Gui.getSessionUser());
        currentUserLabel.setFont(plainFont);
        panelGrid1.add(currentUserLabel, gbcGrid1);

        // TEXT FIELDS
        gbcGrid1.insets = new Insets(5, 5, 5, 5);
        gbcGrid1.gridwidth = 1;
        gbcGrid1.weightx = 1;
        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 1;
        panelGrid1.add(editPasswordText, gbcGrid1);

        // BUTTONS
        gbcGrid1.insets = new Insets(5, 5, 5, 25);
        gbcGrid1.weightx = 0;
        gbcGrid1.gridx = 2;
        gbcGrid1.gridy = 1;
        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(editPasswordButton, BorderLayout.EAST); // Align Button right
        panelGrid1.add(btnContainer, gbcGrid1);
    }

    /**
     * Builds grid 2 with components
     */
    private void buildGrid2() {
        panelGrid2.setBackground(panelColor);
        panelGrid2.setLayout(gblGrid2);

        gbcGrid2.insets = new Insets(5,25,5,5);
        gbcGrid2.fill = GridBagConstraints.BOTH;

        // LABELS
        gbcGrid2.gridx = 0;
        gbcGrid2.gridy = 0;
        JLabel organisationLabel = buildLabel("Organisation");
        panelGrid2.add(organisationLabel, gbcGrid2);

        gbcGrid2.gridx = 1;
        gbcGrid2.gridy = 0;
        JLabel currentUnitLabel = buildLabel(currentOrganisation);
        currentUnitLabel.setFont(plainFont);
        panelGrid2.add(currentUnitLabel, gbcGrid2);

        gbcGrid2.gridx = 0;
        gbcGrid2.gridy = 1;
        JLabel creditsLabel = buildLabel("Credits");
        panelGrid2.add(creditsLabel, gbcGrid2);

        gbcGrid2.gridx = 1;
        gbcGrid2.gridy = 1;
        JLabel currentCreditsLabel = buildLabel(currentCredits);
        currentCreditsLabel.setFont(plainFont);
        panelGrid2.add(currentCreditsLabel, gbcGrid2);
    }

    /**
     * Builds grid 3 with components
     */
    private void buildGrid3() {
        JTable table = buildTable(buildListings());

        JScrollPane scrollPane = new JScrollPane(table);

        panelScrollCont.setBackground(Color.BLACK);
        panelScrollCont.setLayout(gblScrollCont);

        gbcScrollCont.insets = new Insets(1, 1, 1, 1);
        gbcScrollCont.fill = GridBagConstraints.BOTH;

        gbcScrollCont.weightx = 1;
        gbcScrollCont.weighty = 1;
        gbcScrollCont.gridx = 0;
        gbcScrollCont.gridy = 1;
        panelScrollCont.add(scrollPane, gbcScrollCont);

        panelGrid3.setBackground(panelColor);
        panelGrid3.setLayout(gblGrid3);

        gbcGrid3.insets = new Insets(5, 25, 5, 5);
        gbcGrid3.fill = GridBagConstraints.BOTH;

        // HEADER

        gbcGrid3.gridx = 0;
        gbcGrid3.gridy = 0;
        JLabel yourListingsLabel = buildLabel("Your Listings");
        yourListingsLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid3.add(yourListingsLabel, gbcGrid3);

        gbcGrid3.insets = new Insets(15, 50, 15, 50);

        gbcGrid3.weightx = 1;
        gbcGrid3.weighty = 1;
        gbcGrid3.gridx = 0;
        gbcGrid3.gridy = 1;
        panelGrid3.add(panelScrollCont, gbcGrid3);
    }

    /**
     * Builds labels
     * @return label containing title input
     */
    private JLabel buildLabel(String title) {
        return new JLabel(title);
    }

    /**
     * Gets listings
     * @return listings
     */
    private String[][] buildListings() {
        String listings[][] = {
                {"1", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"2", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"3", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"4", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"5", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"6", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"7", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"8", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"9", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"10", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"11", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"12", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"13", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"14", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"15", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"16", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
                {"17", "OPEN", "BUY", "username", "organisation", "asset", "12", "10", "DATE"},
        };

        return listings;
    }

    /**
     * Builds Table of Listings
     * @return table
     */
    private JTable buildTable(String[][] data) {
        String[] columns = {"ID", "TYPE", "STATUS", "USER", "UNIT",
                            "ASSET", "QUANTITY", "PRICE", "DATE"};
        JTable table = new JTable(data, columns);
        return table;
    }

    /**
     * Add action listeners to buttons
     * Set self as source of listener
     */
    private void addBtnAction() {
        editPasswordButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == editPasswordButton) {
                editPassword();
            }
        } catch (TextInputException tiex) {
            JOptionPane.showMessageDialog(this, tiex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPassword() {
        String userNameText = Gui.getSessionUser();
        String pwdText = editPasswordText.getText();
        String hashedPwdTxt = PasswordHandling.HashString(pwdText);
        String organisationText = currentOrganisation;

        if (pwdText.isEmpty()) {
            throw new TextInputException("Fields cannot be empty.");
        }

        System.out.println("EDITING --- Username: " + userNameText + " Password: "
                + hashedPwdTxt + " Organisation: " + organisationText);
        NetworkUtils.write(ClientServer.getServer(), ALTER, ACCOUNT, userNameText,
                hashedPwdTxt, organisationText);
    }
}
