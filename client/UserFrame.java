package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame implements ActionListener {

    private CardLayout clPanelMain = new CardLayout();
    private GridBagLayout gblPanelCont = new GridBagLayout();
    private GridBagLayout gblPanelSide = new GridBagLayout();
    private GridBagConstraints gbcPanelSide = new GridBagConstraints();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panelSide = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelCard1 = new AccountPanel();
    private JPanel panelCard2 = new BuySellPanel();
    private JPanel panelCard3 = new ListingsPanel();

    private JButton button1 = new JButton("Account");
    private JButton button2 = new JButton("Buy / Sell");
    private JButton button3 = new JButton("Listings");

    private Color panelColor = new Color(240, 240, 240);
    private Color sideColor = new Color(205, 205, 205);

    /**
     * Constructor of UserFrame
     */
    public UserFrame() {
        buildFrame();
        addBtnAction();

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setTitle("AssetTrader302");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null); // Centers GUI on open
        frame.setVisible(true);
    }

    /**
     * Constructs positions and size of components in frame
     */
    private void buildFrame() {
        panelSide.setBackground(sideColor);
        panelSide.setPreferredSize(new Dimension(150, 500));
        panelSide.setLayout(gblPanelSide);
        gbcPanelSide.gridwidth = GridBagConstraints.REMAINDER; // Sets component as last in row
        gbcPanelSide.fill = GridBagConstraints.HORIZONTAL; // Horizontal fill components
        gbcPanelSide.insets = new Insets(5, 0, 5, 0); // Vertical spacing 5 - 5
        panelSide.add(button1, gbcPanelSide);
        panelSide.add(button2, gbcPanelSide);
        panelSide.add(button3, gbcPanelSide);

        panelMain.setBackground(panelColor);
        panelMain.setPreferredSize(new Dimension(850, 500));
        panelMain.setLayout(clPanelMain);
        panelMain.add(panelCard1, "panel1");
        panelMain.add(panelCard2, "panel2");
        panelMain.add(panelCard3, "panel3");
        clPanelMain.show(panelMain, "panel1");

        panelCont.setLayout(gblPanelCont);
        panelCont.add(panelSide);
        panelCont.add(panelMain);


    }

    /**
     * Add action listeners to buttons
     * Set self as source of listener
     */
    private void addBtnAction() {
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            clPanelMain.show(panelMain, "panel1");
        }
        if (e.getSource() == button2) {
            clPanelMain.show(panelMain, "panel2");
        }
        if (e.getSource() == button3) {
            clPanelMain.show(panelMain, "panel3");
        }
    }
}
