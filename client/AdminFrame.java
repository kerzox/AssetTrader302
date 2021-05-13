package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

    private CardLayout clPanelMain = new CardLayout();
    private GridBagLayout gblPanelCont = new GridBagLayout();
    private GridBagLayout gblPanelSide = new GridBagLayout();
    private GridBagConstraints gbcPanelSide = new GridBagConstraints();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panelSide = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelCard1 = new JPanel();

    private JButton button1 = new JButton("Switch to 1");


    private JLabel panelLabel1 = new JLabel("PanelCard1");

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

        panelCard1.setBackground(Color.green);
        panelCard1.add(panelLabel1);

        panelSide.setBackground(Color.red);
        panelSide.setPreferredSize(new Dimension(150, 500));
        panelSide.setLayout(gblPanelSide);
        gbcPanelSide.gridwidth = GridBagConstraints.REMAINDER; // Sets component as last in row
        gbcPanelSide.fill = GridBagConstraints.HORIZONTAL; // Horizontal fill components
        gbcPanelSide.insets = new Insets(5, 0, 5, 0); // Vertical spacing 5 - 5
        panelSide.add(button1, gbcPanelSide);

        panelMain.setBackground(Color.BLACK);
        panelMain.setPreferredSize(new Dimension(850, 500));
        panelMain.setLayout(clPanelMain);
        panelMain.add(panelCard1, "panel1");
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            clPanelMain.show(panelMain, "panel1");
        }
    }
}
