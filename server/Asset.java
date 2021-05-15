package server;

public class Asset {

    private final String name;

    /**
     * Constructor
     * @param name sets name of Asset
     */
    public Asset(String name) {
        this.name = name;
        DatabaseHelper.addAsset(this);
    }

    /**
     * retrieve name of asset
     * @return name of asset
     */
    public String getName() {
        return name;
    }
}
