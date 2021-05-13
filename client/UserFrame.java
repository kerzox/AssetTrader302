package client;

import javax.swing.*;
import java.awt.*;

public class UserFrame {

    private static CardLayout cl = new CardLayout();
    private static GridBagLayout gbl = new GridBagLayout();
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
