package server;

public class Marketplace {

    /**
     * Creates a listing on the marketplace
     * @param unit
     * @param asset
     * @param amount
     * @param creditCost
     * @param selling
     */

    public static void addListing(Organisation unit, Asset asset, int amount, int creditCost, boolean selling) {
        if (!selling) unit.createBuyOrder(asset, amount, creditCost);
        else unit.createSellOrder(asset, amount, creditCost);
    }

}
