package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Listing {

    private byte active; // 1 or 0; true or false;
    private UUID uuid;
    private String type;
    private int assetQuantity;
    private int assetPrice;

    private String asset;
    private String user;
    private String organisation;

    private String dateTime;

    public enum enumType {
        BUY("buy"),
        SELL("sell");
        enumType(String s) {
            type = s;
        }
        private String type;
        public String getType() {
            return type;
        }
    }

    /**
     * Constructor of listing object
     * @param uuid
     * @param type
     * @param assetQuantity
     * @param assetPrice
     * @param user
     * @param organisation
     * @param asset
     */
    public Listing(UUID uuid, enumType type, int assetQuantity, int assetPrice, String user, String organisation, String asset) throws ListingException {
        this.uuid = uuid;
        active = 1; // 1 is True, 0 is False
        this.type = type.getType();
        if (assetQuantity <= 0) throw new ListingException(String.format("Asset quantity cannot be negative, %d", assetQuantity));
        this.assetQuantity = assetQuantity;
        if (assetPrice <= 0) throw new ListingException(String.format("Asset price cannot be negative, %d", assetPrice));
        this.assetPrice = assetPrice;
        this.user = user;
        this.asset = asset;
        this.organisation = organisation;
        LocalDateTime preDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = preDateTime.format(dateTimeFormat);

    }

    /**
     * retrieve UUID of listing
     * @return UUID of listing
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * retrieve account username
     * @return account username
     */
    public String getUsername() {
        return user;
    }

    /**
     * retrieve account organisation
     * @return account organisation
     */
    public String getUnit() {
        return organisation;
    }

    /**
     * retrieve type of listing
     * @return type of listing
     */
    public String getType() {
        return type;
    }

    /**
     * retrieve type of asset
     * @return type of asset
     */
    public String getAsset() {
        return asset;
    }

    /**
     * retrieve quantity of asset
     * @return assetQuantity
     */
    public int getAssetQuantity() {
        return assetQuantity;
    }

    /**
     * retrieve price of asset
     * @return assetPrice
     */
    public int getAssetPrice() {
        return assetPrice;
    }

    /**
     * retrieve active status of listing
     * @return  byte active status of listing
     */
    public byte getActive() {
        return active;
    }

    /**
     * retrieve dateTime of listing
     * @return  String dateTime of listing
     */
    public String getDateTime() {
        return dateTime;
    }

}
