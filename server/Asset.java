package server;

public class Asset {

    private final String name;

    public Asset(String name) {
        this.name = name;
        DatabaseHelper.addAsset(this);
    }

    public String getName() {
        return name;
    }
}
