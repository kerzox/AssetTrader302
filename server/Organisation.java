package server;

import java.util.HashMap;

public class Organisation {

    private final String name;
    private int creditBudget;

    // not sure if this will stay a map could be added to the asset class

    private HashMap<Asset, Integer> totalAssets = new HashMap<>();

    public Organisation(String name) {
        this(name, 0);
    }

    /**
     * Creates a new organisation
     * @param name
     * @param budget
     */

    public Organisation(String name, int budget) {
        this.name = name;
        this.creditBudget = budget;
        DatabaseHelper.addOrganisation(this);
    }

    /**
     * Gets name of organisation
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Gets current budget of organisation
     * @return
     */

    public int getBudget() {
        return creditBudget;
    }

    /**
     * Adds credits to organisation's budget
     * @param amount
     */

    public void addCredits(int amount) {
        this.creditBudget = amount;
    }

    /**
     * Removes credits from the organisation's budget as long as its able to.
     * @param amount
     */

    /*TODO
        Maybe change this to a max function.
        So instead of rejecting a call because its unable to remove the whole amount,
        it instead removes what it can.
     */

    public void removeCredits(int amount) {
        if (creditBudget < amount) return;
        this.creditBudget -= amount;
    }

    /**
     * Gets total asset map
     * @return
     */

    public HashMap<Asset, Integer> getTotalAssets() {
        return totalAssets;
    }

    /**
     * Creates a buy order
     * @param assetToBuy
     * @param amount
     * @param creditCost
     */

    public void createBuyOrder(Asset assetToBuy, int amount, int creditCost) throws IllegalArgumentException {
        if (creditCost > creditBudget) throw new IllegalArgumentException("Not enough credit"); // throw exception
        // add buy order to gui and database
    }

    /**
     * Creates a sell order
     * @param assetToSell
     * @param amount
     * @param creditCost
     */

    public void createSellOrder(Asset assetToSell, int amount, int creditCost) {
        if (totalAssets.get(assetToSell) < amount) return; // throw exception
    }

    public void removeBuyOrder(Asset asset) {
        DatabaseHelper.removeListing(this, asset, false);
    }

    public void removeSellOrder(Asset asset) {
        DatabaseHelper.removeListing(this, asset, true);
    }

}
