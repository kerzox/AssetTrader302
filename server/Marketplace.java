package server;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Marketplace {

    /**
     * Creates a listing on the marketplace
     * @param unit organisation
     * @param asset asset
     * @param amount amount
     * @param creditCost creditCost
     * @param selling is it a sell listing
     */

    public static void addListing(Organisation unit, Asset asset, int amount, int creditCost, boolean selling) {
        try {
            if (!selling) unit.createBuyOrder(asset, amount, creditCost);
            else unit.createSellOrder(asset, amount, creditCost);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public static void removeListing(Organisation unit, Asset asset, boolean selling) {
        if (!selling) unit.removeBuyOrder(asset);
        else unit.removeSellOrder(asset);
    }

}
