package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

    private GridBagLayout gblPanelCont = new GridBagLayout();
    private GridBagLayout gblPanelMain = new GridBagLayout();
    private GridBagConstraints gbcPanelMain = new GridBagConstraints();
    private GridBagLayout gblPanelSide = new GridBagLayout();
    private GridBagConstraints gbcPanelSide = new GridBagConstraints();
    private GridBagLayout gblPanelGrid1 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid1 = new GridBagConstraints();
    private GridBagLayout gblPanelGrid2 = new GridBagLayout();
    private GridBagConstraints gbcPanelGrid2 = new GridBagConstraints();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panelSide = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelGrid1 = new JPanel();
    private JPanel panelGrid2 = new JPanel();
    private JPanel panelGrid3 = new JPanel();
    private JPanel panelGrid4 = new JPanel();

    private JButton button1 = new JButton("ADMIN SETTINGS");

    private JTextField newUsername  = new JTextField();
    private JTextField editUsername  = new JTextField();

    private Color panelColor = new Color(240, 240, 240);
    private Color sideColor = new Color(205, 205, 205);



    /**
     * Constructor of UserFrame
     */
    public AdminFrame() {
        buildFrame();
        addBtnAction();

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout manager -- CardLayout
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

        panelSide.add(button1, gbcPanelSide);
    }

    /**
     * Builds main panel of frame
     */
    private void buildMainFrame() {
        buildGridFrames();

        panelMain.setBackground(Color.BLACK);
        panelMain.setPreferredSize(new Dimension(850, 500));
        panelMain.setLayout(gblPanelMain);

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

        gbcPanelMain.gridx = 1;
        gbcPanelMain.gridy = 1;
        panelMain.add(panelGrid4, gbcPanelMain);
    }

    /**
     * Builds grid panels in main panel
     */
    private void buildGridFrames() {
        buildGrid1();
        buildGrid2();
        panelGrid3.setBackground(Color.gray);
        panelGrid4.setBackground(Color.yellow);
    }

    /**
     * Builds grid panel 1
     */
    private void buildGrid1() {
        panelGrid1.setBackground(panelColor);
        panelGrid1.setLayout(gblPanelGrid1);

        gbcPanelGrid1.insets = new Insets(5, 5, 5, 5); // Between Spacing
        gbcPanelGrid1.fill = GridBagConstraints.BOTH;
        gbcPanelGrid1.gridwidth = 2;

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 0;
        JLabel newAccountLabel = buildLabel("New Account");
        newAccountLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid1.add(newAccountLabel, gbcPanelGrid1);

        gbcPanelGrid1.gridwidth = 1;

        gbcPanelGrid1.gridx = 0;
        gbcPanelGrid1.gridy = 1;
        JLabel usernameLabel = buildLabel("Username");
        panelGrid1.add(usernameLabel, gbcPanelGrid1);

        gbcPanelGrid1.gridx = 1;
        gbcPanelGrid1.gridy = 1;
        newUsername.setColumns(13);
        panelGrid1.add(newUsername, gbcPanelGrid1);


    }

    /**
     * Builds grid panel 2
     */
    private void buildGrid2() {
        panelGrid2.setBackground(panelColor);
        panelGrid2.setLayout(gblPanelGrid2);

        gbcPanelGrid2.insets = new Insets(5, 5, 5, 5); // Between Spacing
        gbcPanelGrid2.fill = GridBagConstraints.BOTH;

        gbcPanelGrid2.gridwidth = 2;

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 0;
        JLabel editAccountLabel = buildLabel("Edit Account");
        editAccountLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid2.add(editAccountLabel, gbcPanelGrid2);

        gbcPanelGrid2.gridwidth = 1;

        gbcPanelGrid2.gridx = 0;
        gbcPanelGrid2.gridy = 1;
        JLabel usernameLabel = buildLabel("Username");
        panelGrid2.add(usernameLabel, gbcPanelGrid2);

        gbcPanelGrid2.gridx = 1;
        gbcPanelGrid2.gridy = 1;
        editUsername.setColumns(13);
        panelGrid2.add(editUsername, gbcPanelGrid2);
    }

    /**
     * Builds JLabels
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
