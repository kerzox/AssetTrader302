package server;

public class Asset {

    private final String name;

    /**
     * Constructor of Asset object
     * @param name name of asset
     */
    public Asset(String name) {
        this.name = name;
    }

    /**
     * retrieve name of asset
     * @return name of asset
     */
    public String getName() {
        return name;
    }
}
