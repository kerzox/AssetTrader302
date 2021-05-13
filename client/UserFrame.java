package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame implements ActionListener {

    private CardLayout cl = new CardLayout();
    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panelSide = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelCard1 = new JPanel();
    private JPanel panelCard2 = new JPanel();
    private JPanel panelCard3 = new JPanel();

    private JButton button1 = new JButton("Switch to 1");
    private JButton button2 = new JButton("Switch to 2");
    private JButton button3 = new JButton("Switch to 3");

    private JLabel panelLabel1 = new JLabel("PanelCard1");
    private JLabel panelLabel2 = new JLabel("PanelCard2");
    private JLabel panelLabel3 = new JLabel("PanelCard3");

    public UserFrame() {
        buildUserFrame();
        addBtnActionUser();

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setTitle("AssetTrader302");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null); // Centers GUI on open
        frame.setVisible(true);
    }

    public void buildUserFrame() {

        panelCard1.setBackground(Color.green);
        panelCard1.add(panelLabel1);
        panelCard2.setBackground(Color.yellow);
        panelCard2.add(panelLabel2);
        panelCard3.setBackground(Color.blue);
        panelCard3.add(panelLabel3);

        panelSide.setBackground(Color.red);
        panelSide.setPreferredSize(new Dimension(150, 500));
        panelSide.add(button1);
        panelSide.add(button2);
        panelSide.add(button3);


        panelMain.setBackground(Color.BLACK);
        panelMain.setPreferredSize(new Dimension(500, 500));
        panelMain.setLayout(cl);
        panelMain.add(panelCard1, "panel1");
        panelMain.add(panelCard2, "panel2");
        panelMain.add(panelCard3, "panel3");
        cl.show(panelMain, "panel1");

        panelCont.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCont.add(panelSide);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCont.add(panelMain);


    }

    public void addBtnActionUser() {
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cl.show(panelMain, "panel1");
        }
        if (e.getSource() == button2) {
            cl.show(panelMain, "panel2");
        }
        if (e.getSource() == button3) {
            cl.show(panelMain, "panel3");
        }
    }
}
