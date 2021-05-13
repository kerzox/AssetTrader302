package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame implements ActionListener {

    private CardLayout cl = new CardLayout();
    private GridBagLayout gbl = new GridBagLayout();

    private JFrame frame = new JFrame(); // Create holding frame

    private JPanel panelCont = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();

    private JButton button1 = new JButton("Switch to 2");
    private JButton button2 = new JButton("Switch to 1");

    public UserFrame() {
        buildUserFrame();
        addBtnActionUser();

        frame.add(panelCont);
        frame.pack(); // Leave size management to layout manager -- CardLayout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate program on closure
        frame.setLocationRelativeTo(null); // Centers GUI on open
        frame.setVisible(true);
    }

    public void buildUserFrame() {

        panel2.add(button1);
        panel3.add(button2);

        panel2.setBackground(Color.blue);
        panel3.setBackground(Color.green);

        panelCont.setLayout(cl);
        panelCont.add(panel2, "panel2");
        panelCont.add(panel3, "panel3");
        cl.show(panelCont, "panel2");

    }

    public void addBtnActionUser() {
        button1.addActionListener(this);
        button2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cl.show(panelCont, "panel3");
        }
        if (e.getSource() == button2) {
            cl.show(panelCont, "panel2");
        }
    }
}
