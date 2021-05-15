package server;

import java.sql.*;

public class JDBCDatabaseSource {

    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS account ("
                    + "userID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "userName VARCHAR(30) NOT NULL UNIQUE,"
                    + "password CHAR(128) NOT NULL,"
                    + "unitName VARCHAR(60) NOT NULL,"
                    + "PRIMARY KEY (userName),"
                    + "FOREIGN KEY (unitName) REFERENCES organisation(unitName)"
                    + ");";
    private static final String INSERT_ACCOUNT = "INSERT INTO account (userName, password, unitName) VALUES (?, ?, ?);";
    private static final String GET_ACCOUNT = "SELECT * FROM account WHERE userName=?";

    private static final String CREATE_TABLE_ORGANISATION =
            "CREATE TABLE IF NOT EXISTS organisation ("
                    + "unitID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "unitName VARCHAR(60) NOT NULL UNIQUE,"
                    + "budget INTEGER NOT NULL,"
                    + "PRIMARY KEY (unitName)"
                    + ");";
    private static final String INSERT_UNIT = "INSERT INTO organisation (unitName, budget) VALUES (?, ?);";
    private static final String GET_UNIT = "SELECT * FROM organisation WHERE unitName=?;";

    private static final String CREATE_TABLE_ASSET =
            "CREATE TABLE IF NOT EXISTS asset ("
                + "assetID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                + "assetName VARCHAR(60) NOT NULL UNIQUE,"
                + "PRIMARY KEY (assetName)"
                + ");";
    private static final String INSERT_ASSET = "INSERT INTO asset (assetName) VALUES (?);";
    private static final String GET_ASSET = "SELECT * from asset WHERE assetName=?;";

    private static final String CREATE_TABLE_LISTING =
            "CREATE TABLE IF NOT EXISTS listing ("
                    + "listingID VARCHAR(36) NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "listingActive TINYINT(1) NOT NULL," // MariaDB uses TINYINT(1) instead of BOOL
                    + "listingType ENUM('BUY', 'SELL') NOT NULL,"
                    + "userName VARCHAR(30) NOT NULL,"
                    + "unitName VARCHAR(60) NOT NULL,"
                    + "assetName VARCHAR(60) NOT NULL,"
                    + "assetQuantity INTEGER NOT NULL,"
                    + "assetPrice INTEGER NOT NULL,"
                    + "dateTime DATETIME NOT NULL,"
                    + "PRIMARY KEY (listingID),"
                    + "FOREIGN KEY (userName) REFERENCES account(userName),"
                    + "FOREIGN KEY (unitName) REFERENCES account(unitName),"
                    + "FOREIGN KEY (assetName) REFERENCES asset(assetName)"
                    + ");";
    private static final String INSERT_LISTING = "INSERT INTO listing (listingID, listingActive, listingType,"
                                                + "userName, unitName, assetName, assetQuantity, assetPrice, dateTime)"
                                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private Connection connection;

    private PreparedStatement addAccount;
    private PreparedStatement getAccount;
    private PreparedStatement addOrganisation;
    private PreparedStatement getOrganisation;
    private PreparedStatement addAsset;
    private PreparedStatement getAsset;
    private PreparedStatement addListing;

    /**
     * Constructor, constructs Database instance
     */
    public JDBCDatabaseSource() {
        connection = JDBCConnection.getConnection();

        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_ORGANISATION);
            st.execute(CREATE_TABLE_ACCOUNT);
            st.execute(CREATE_TABLE_ASSET);
            st.execute(CREATE_TABLE_LISTING);
            addAccount = connection.prepareStatement(INSERT_ACCOUNT);
            getAccount = connection.prepareStatement(GET_ACCOUNT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addOrganisation = connection.prepareStatement(INSERT_UNIT);
            getOrganisation = connection.prepareStatement(GET_UNIT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addAsset = connection.prepareStatement(INSERT_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addListing = connection.prepareStatement(INSERT_LISTING);

        } catch (SQLException SQLex) {
            System.out.println(SQLex);
        }
    }

    /**
     * Adds account to database
     * @param a User object to be added
     */
    public void addAccount(User a) {
        try {
            addAccount.setString(1, a.getUsername());
            addAccount.setString(2, a.getPassword());
            addAccount.setString(3, getOrganisation(a.getUnitName())[1]);
            addAccount.execute();
        } catch (SQLException SQLex) {
            System.out.println(SQLex);
        } catch (NullPointerException NPex) {
            System.out.println(NPex);
        }
    }

    /**
     * Gets account from database
     * @param name Username of account
     * @return User account as array list, else null
     */
    public String[] getAccount(String name) {
        String[] account;
        String userID;
        String userName;
        String pwd;
        String unit;
        ResultSet rs;

        try {
            getAccount.setString(1, name);
            rs = getAccount.executeQuery();
            rs.first();
            userID = rs.getString("userID");
            userName = rs.getString("userName");
            pwd = rs.getString("password");
            unit = rs.getString("unitName");
            account = new String[] {userID, userName, pwd, unit};
            return account;
        } catch (SQLException SQLex) {
            System.out.println(SQLex);
        }
        return null;
    }

    /**
     * Adds organisation to database
     * @param a organisation object to be added
     */
    public void addOrganisation(Organisation a) {
        try {
            addOrganisation.setString(1, a.getName());
            addOrganisation.setString(2, String.valueOf(a.getBudget()));
            addOrganisation.execute();
        } catch (SQLException SQLex) {
            //SQLex.printStackTrace();
            System.out.println(SQLex);
        }
    }

    /**
     * Gets organisation from database
     * @param name Name of organisation
     * @return Organisation as an array list, else null
     */
    public String[] getOrganisation(String name) {
        String[] organisation;
        String unitID;
        String unitName;
        String budget;
        ResultSet rs;

        try {
            getOrganisation.setString(1, name);
            rs = getOrganisation.executeQuery();
            rs.first();
            unitID = rs.getString("unitID");
            unitName = rs.getString("unitName");
            budget = rs.getString("budget");
            organisation = new String[] {unitID, unitName, budget};
            return organisation;
        } catch (SQLException SQLex) {
            System.out.println(SQLex);
        }
        return null;
    }

    /**
     * Adds asset to database
     * @param a asset object to be added
     */
    public void addAsset(Asset a) {
        try {
            addAsset.setString(1, a.getName());
            addAsset.execute();
        } catch (SQLException SQLex) {
            //SQLex.printStackTrace();
            System.out.println(SQLex);
        }
    }

    /**
     * Gets organisation from database
     * @param name Name of asset
     * @return Asset as an array list, else null
     */
    public String[] getAsset(String name) {
        String[] asset;
        String assetID;
        String assetName;
        ResultSet rs;

        try {
            getAsset.setString(1, name);
            rs = getAsset.executeQuery();
            rs.first();
            assetID = rs.getString("assetID");
            assetName = rs.getString("assetName");
            asset = new String[] {assetID, assetName};
            return asset;
        } catch (SQLException SQLex) {
            System.out.println(SQLex);
        }
        return null;
    }

    /**
     * Adds listing to database
     * @param a listing object to be added
     */
    public void addListing(Listing a) {
        try {
            addListing.setString(1, a.getUUID().toString());
            addListing.setString(2, String.valueOf(a.getActive()));
            addListing.setString(3, a.getType());
            addListing.setString(4, a.getUsername());
            addListing.setString(5, a.getUnit());
            addListing.setString(6, a.getAsset());
            addListing.setString(7, String.valueOf(a.getAssetQuantity()));
            addListing.setString(8, String.valueOf(a.getAssetPrice()));
            addListing.setString(9, a.getDateTime());
            addListing.execute();
        } catch (SQLException SQLex) {
            //SQLex.printStackTrace();
            System.out.println(SQLex);
        }
    }

    /**
     * Closes database connection.
     */
    public void closeDatabaseSource() {
        JDBCConnection.closeConnection();
    }

}
