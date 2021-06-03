package client;

import javax.swing.*;
import java.awt.*;

public class ListingsPanel extends JPanel {

    private String[][] listings;

    private GridBagLayout gblMain = new GridBagLayout();
    private GridBagConstraints gbcMain = new GridBagConstraints();
    private GridBagLayout gblGrid1 = new GridBagLayout();
    private GridBagConstraints gbcGrid1 = new GridBagConstraints();
    private GridBagLayout gblScrollCont = new GridBagLayout();
    private GridBagConstraints gbcScrollCont = new GridBagConstraints();

    private JPanel panelGrid1 = new JPanel();
    private JPanel panelScrollCont = new JPanel();

    private Color panelColor = new Color(240, 240, 240);

    private Font boldFont = new Font("Dialog", Font.BOLD, 12);
    private Font plainFont = new Font("Dialog", Font.PLAIN, 12);

    public ListingsPanel(String[][] listings) {
        this.listings = listings;

        buildGrid1();

        setLayout(gblMain);

        gbcMain.insets = new Insets(-1, -1 ,-1 ,-1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1;
        gbcMain.weightx = 1;
        add(panelGrid1, gbcMain);

        setBackground(Color.BLACK);
    }

    /**
     * Builds Grid1
     */
    private void buildGrid1() {
        panelGrid1.setBackground(panelColor);
        panelGrid1.setLayout(gblGrid1);

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

        panelGrid1.setBackground(panelColor);
        panelGrid1.setLayout(gblGrid1);

        gbcGrid1.insets = new Insets(50, 50, 5, 50);
        gbcGrid1.fill = GridBagConstraints.BOTH;

        // HEADER

        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 0;
        JLabel allListingsLabel = buildLabel("All Listings");
        allListingsLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid1.add(allListingsLabel, gbcGrid1);

        // SCROLL PANE
        gbcGrid1.insets = new Insets(5, 50, 50, 50);

        gbcGrid1.weightx = 1;
        gbcGrid1.weighty = 1;
        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 1;
        panelGrid1.add(panelScrollCont, gbcGrid1);

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
}
