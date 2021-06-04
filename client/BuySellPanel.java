package client;

import util.NetworkUtils;
import static util.Request.Header.*;
import static util.Request.Type.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class BuySellPanel extends JPanel implements ActionListener {

    private GridBagLayout gblMain = new GridBagLayout();
    private GridBagConstraints gbcMain = new GridBagConstraints();
    private GridBagLayout gblGrid1 = new GridBagLayout();
    private GridBagConstraints gbcGrid1 = new GridBagConstraints();

    private JPanel panelGrid1 = new JPanel();

    private JTextField quantityText = new JTextField();
    private JTextField priceText = new JTextField();

    private JComboBox typeCombo = new JComboBox(new String[] {"BUY", "SELL"});
    private JComboBox assetCombo;

    private JButton postListingBtn = new JButton("Post Listing");

    private Color panelColor = new Color(240, 240, 240);

    private Font boldFont = new Font("Dialog", Font.BOLD, 12);
    private Font plainFont = new Font("Dialog", Font.PLAIN, 12);

    private String[] assets;

    public BuySellPanel(String[] assets) {
        this.assets = assets;
        assetCombo = new JComboBox(assets);
        buildGrid1();
        addBtnAction();

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

        gbcGrid1.insets = new Insets(5, 150, 5, 5);
        gbcGrid1.fill = GridBagConstraints.BOTH;

        // HEADER
        gbcGrid1.gridwidth = 2;
        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 0;
        JLabel newListingLabel = buildLabel("Create Listing");
        newListingLabel.setFont(new Font("Courier", Font.BOLD, 15));
        panelGrid1.add(newListingLabel, gbcGrid1);

        // LABELS
        gbcGrid1.gridwidth = 1;
        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 1;
        JLabel typeLabel = buildLabel("Type");
        panelGrid1.add(typeLabel, gbcGrid1);

        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 2;
        JLabel assetLabel = buildLabel("Asset");
        panelGrid1.add(assetLabel, gbcGrid1);

        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 3;
        JLabel quantityLabel = buildLabel("Quantity");
        panelGrid1.add(quantityLabel, gbcGrid1);

        gbcGrid1.gridx = 0;
        gbcGrid1.gridy = 4;
        JLabel priceLabel = buildLabel("Price");
        panelGrid1.add(priceLabel, gbcGrid1);

        // COMBO FIELDS
        gbcGrid1.insets = new Insets(5, 5, 5, 150);
        gbcGrid1.weightx = 1;

        gbcGrid1.gridwidth = 1;
        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 1;
        panelGrid1.add(typeCombo, gbcGrid1);

        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 2;
        panelGrid1.add(assetCombo, gbcGrid1);

        // TEXT FIELDS
        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 3;
        panelGrid1.add(quantityText, gbcGrid1);

        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 4;
        panelGrid1.add(priceText, gbcGrid1);

        // BUTTONS
        gbcGrid1.weightx = 0;
        gbcGrid1.gridx = 1;
        gbcGrid1.gridy = 5;
        JPanel btnContainer = new JPanel(); // Need to hold btn in container panel
        btnContainer.setLayout(new BorderLayout()); // Set BorderLayout
        btnContainer.add(postListingBtn, BorderLayout.EAST); // Align Button right
        panelGrid1.add(btnContainer, gbcGrid1);

    }

    /**
     * Builds labels
     * @return label containing title input
     */
    private JLabel buildLabel(String title) {
        return new JLabel(title);
    }

    /**
     *
     */
    private void addBtnAction() {
        postListingBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == postListingBtn) {
                createListing();
            }
        } catch (TextInputException tiex) {
            JOptionPane.showMessageDialog(this, tiex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfex) {
            JOptionPane.showMessageDialog(this, "Input must be an integer.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createListing() {
        if (quantityText.getText().isEmpty() || priceText.getText().isEmpty()) {
            throw new TextInputException("Fields cannot be empty");
        }

        String listingType = (String) typeCombo.getSelectedItem();
        String listingAsset = (String) assetCombo.getSelectedItem();
        int listingQuantity = Integer.parseInt(quantityText.getText());
        int listingPrice = Integer.parseInt(priceText.getText());

        if (listingQuantity <= 0 || listingPrice <= 0) {
            throw new TextInputException("Fields cannot be less than 1");
        }

        System.out.println("ADDING --- LISTING " + listingType + " " +
                    listingAsset + " " + String.valueOf(listingQuantity) +
                    " " + String.valueOf(listingPrice));

        NetworkUtils.write(ClientServer.getServer(), CREATE, LISTING, listingType,
                listingAsset, listingQuantity, listingPrice, Gui.getSessionUser());
    }


}
