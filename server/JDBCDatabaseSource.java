package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String UPDATE_ACCOUNT = "UPDATE account SET password=?, unitName=? WHERE userName=?";
    private static final String GET_ACCOUNT = "SELECT * FROM account WHERE userName=?";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM account";

    private static final String CREATE_TABLE_ORGANISATION =
            "CREATE TABLE IF NOT EXISTS organisation ("
                    + "unitID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "unitName VARCHAR(60) NOT NULL UNIQUE,"
                    + "budget INTEGER NOT NULL,"
                    + "PRIMARY KEY (unitName)"
                    + ");";
    private static final String INSERT_UNIT = "INSERT INTO organisation (unitName, budget) VALUES (?, ?);";
    private static final String UPDATE_UNIT = "UPDATE organisation SET budget=? WHERE unitName=?";
    private static final String GET_UNIT = "SELECT * FROM organisation WHERE unitName=?;";
    private static final String GET_UNIT_ALL = "SELECT * from organisation;";

    private static final String CREATE_TABLE_ASSET =
            "CREATE TABLE IF NOT EXISTS asset ("
                + "assetID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                + "assetName VARCHAR(60) NOT NULL UNIQUE,"
                + "PRIMARY KEY (assetName)"
                + ");";
    private static final String INSERT_ASSET = "INSERT INTO asset (assetName) VALUES (?);";
    private static final String GET_ASSET = "SELECT * from asset WHERE assetName=?;";
    private static final String GET_ASSET_ALL = "SELECT * from asset;";

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
    private static final String GET_LISTING = "SELECT * from listing WHERE listingID=?;";
    private static final String GET_LISTING_BUY = "SELECT * from listing WHERE listingActive=1 AND listingType!=? AND " +
                                                "unitName!=? AND assetName=? AND assetPrice<=?;";
    private static final String GET_LISTING_SELL = "SELECT * from listing WHERE listingActive=1 AND listingType!=? AND " +
                                                "unitName!=? AND assetName=? AND assetPrice>=?;";
    private static final String GET_LISTING_USER = "SELECT * from listing WHERE userName=?;";
    private static final String GET_LISTING_ALL = "SELECT * from listing;";
    private static final String UPDATE_LISTING = "UPDATE listing SET assetQuantity=? WHERE listingID=?;";
    private static final String CLOSE_LISTING = "UPDATE listing SET listingActive=0 WHERE listingID=?;";

    private Connection connection;

    private PreparedStatement addAccount;
    private PreparedStatement updateAccount;
    private PreparedStatement getAccount;
    private PreparedStatement getAllAccounts;
    private PreparedStatement addOrganisation;
    private PreparedStatement updateOrganisation;
    private PreparedStatement getOrganisation;
    private PreparedStatement getOrganisationAll;
    private PreparedStatement addAsset;
    private PreparedStatement getAsset;
    private PreparedStatement getAssetAll;
    private PreparedStatement addListing;
    private PreparedStatement getListing;
    private PreparedStatement getListingBuy;
    private PreparedStatement getListingSell;
    private PreparedStatement getListingAll;
    private PreparedStatement updateListing;
    private PreparedStatement closeListing;
    private PreparedStatement getUserListing;

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
            updateAccount = connection.prepareStatement(UPDATE_ACCOUNT);
            getAccount = connection.prepareStatement(GET_ACCOUNT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getAllAccounts = connection.prepareStatement(GET_ALL_ACCOUNTS, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addOrganisation = connection.prepareStatement(INSERT_UNIT);
            updateOrganisation = connection.prepareStatement(UPDATE_UNIT);
            getOrganisation = connection.prepareStatement(GET_UNIT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getOrganisationAll = connection.prepareStatement(GET_UNIT_ALL, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addAsset = connection.prepareStatement(INSERT_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getAssetAll = connection.prepareStatement(GET_ASSET_ALL, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addListing = connection.prepareStatement(INSERT_LISTING);
            getListing = connection.prepareStatement(GET_LISTING, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getListingBuy = connection.prepareStatement(GET_LISTING_BUY, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getListingSell = connection.prepareStatement(GET_LISTING_SELL, ResultSet.TYPE_SCROLL_INSENSITIVE);
            updateListing = connection.prepareStatement(UPDATE_LISTING);
            closeListing = connection.prepareStatement(CLOSE_LISTING);
            getUserListing = connection.prepareStatement(GET_LISTING_USER, ResultSet.TYPE_SCROLL_INSENSITIVE);
            getListingAll = connection.prepareStatement(GET_LISTING_ALL, ResultSet.TYPE_SCROLL_INSENSITIVE);

        } catch (SQLException SQLex) {
            System.err.println(SQLex);
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
        } catch (SQLIntegrityConstraintViolationException SQLICVex) {
            // Duplicate Entry
            System.err.println(SQLICVex);
            System.out.println("Duplicate account " + a.getUsername());
            System.out.println("Failed to add account");
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
            System.out.println("Failed to add account");
        }
    }

    /**
     * Updates existing account in database
     * @param a User object to be updated
     */
    public void updateAccount(User a) {
        try {
            updateAccount.setString(1, a.getPassword());
            updateAccount.setString(2, getOrganisation(a.getUnitName())[1]);
            updateAccount.setString(3, a.getUsername());
            updateAccount.execute();
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
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
            System.err.println(SQLex);
            System.out.println("No account " + name);
        }
        return null;
    }

    public String[][] getAllAccounts() {
        List<String[]> list = new ArrayList<>();
        String userID;
        String userName;
        String unit;
        String pwd;
        ResultSet rs;

        try {
            rs = getAllAccounts.executeQuery();
            while(rs.next()) {
                userID = rs.getString("userID");
                userName = rs.getString("userName");
                pwd = rs.getString("password");
                unit = rs.getString("unitName");
                list.add(new String[] {userID, userName, pwd, unit});
            }
            String[][] ret = new String[list.size()][list.size()];
            return list.toArray(ret);

        } catch(SQLException SQLex) {
            System.err.println(SQLex);
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
        } catch (SQLIntegrityConstraintViolationException SQLICVex) {
            // Duplicate Entry
            System.err.println(SQLICVex);
            System.out.println("Duplicate organisation " + a.getName());
            System.out.println("Failed to add organisation");
        } catch (SQLException SQLex) {
            // Other Exceptions SQL
            System.err.println(SQLex);
            System.out.println("Failed to add organisation");
        }
    }

    /**
     * Updates organisation in database
     * @param a
     * @param credits
     */
    public void updateOrganisation(Organisation a, int credits) {
        try {
            int currentBudget = Integer.parseInt(getOrganisation(a.getName())[2]);
            int newBudget = currentBudget + credits;
            updateOrganisation.setString(1, String.valueOf(newBudget));
            updateOrganisation.setString(2, a.getName());
            updateOrganisation.execute();
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
        }
    }


    public String[] getAllOrganisations() {
        List<String> list = new ArrayList<>();
        String[] organisations;
        String orgName;
        ResultSet rs;

        try {
            rs = getOrganisationAll.executeQuery();

            while( rs.next() ) {
                orgName = rs.getString(2);
                list.add(orgName);
            }

            organisations = new String[list.size()];
            list.toArray(organisations);
            return organisations;

        } catch(SQLException SQLex) {
            System.err.println(SQLex);
        }

        return null;
    }

    /**
     * Adds credits to budget of organisation
     * @param unit
     * @param credits
     */
    public void awardCredits(String unit, int credits) {
        try {
            String[] org = getOrganisation(unit);
            int currentBudget = Integer.valueOf(org[2]);
            int newBudget = currentBudget + credits;
            updateOrganisation.setString(1, String.valueOf(newBudget));
            updateOrganisation.setString(2, unit);
            updateOrganisation.execute();
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
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
            System.err.println(SQLex);
            System.out.println("No organisation " + name);
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
        } catch (SQLIntegrityConstraintViolationException SQLICVex) {
            // Duplicate Entry
            System.err.println(SQLICVex);
            System.out.println("Duplicate asset " + a.getName());
            System.out.println("Failed to add asset");
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
            System.out.println("Failed to add asset");
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
            System.err.println(SQLex);
        }
        return null;
    }

    /**
     * Gets all assets from the database
     * @return string array of all assets else null
     */
    public String[] getAllAssets() {
        List<String> list = new ArrayList<>();
        String[] assets;
        String assetName;
        ResultSet rs;

        try {
            rs = getAssetAll.executeQuery();

            while( rs.next() ) {
                assetName = rs.getString(2);
                list.add(assetName);
            }

            assets = new String[list.size()];
            list.toArray(assets);
            return assets;

        } catch(SQLException SQLex) {
            System.err.println(SQLex);
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
            System.err.println(SQLex);
            System.out.println("Failed to add listing");
        }
    }

    /**
     * Updates listing in database
     * @param uuid
     * @param quantity
     */
    public void updateListing(String uuid, int quantity) {
        try {
            updateListing.setString(1, String.valueOf(quantity));
            updateListing.setString(2, uuid);
            updateListing.executeQuery();
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
        }

    }

    /**
     * Closes listing in database
     * @param uuid
     */
    public void closeListing(String uuid) {
        try {
            closeListing.setString(1, uuid);
            closeListing.executeQuery();
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
        }

    }

    /**
     * Gets all listings belonging to a user
     * @param username
     * @return 2d string array of userListings else null
     */
    public String[][] getUserListing(String username) {
        ResultSet rs;
        String[][] listings;
        int nCol;
        List<String[]> listArr = new ArrayList<>();
        try {
            getUserListing.setString(1, username);
            rs = getUserListing.executeQuery();

            nCol = rs.getMetaData().getColumnCount(); // Adapted from https://stackoverflow.com/questions/24547406/resultset-into-2d-array

            while( rs.next() ) {
                String[] row = new String[nCol];
                for (int i = 1; i <= nCol; i++) {
                    String str = rs.getString(i);
                    row[i-1] = str;
                }
                listArr.add( row );
            }

            listings = new String[listArr.size()][];
            for (int j = 0; j < listArr.size(); j++) {
                String[] row = listArr.get(j);
                listings[j] = row;
            }

            return listings;

        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all listings from database
     * @return 2d string array of all listings else null
     */
    public String[][] getAllListings() {
        ResultSet rs;
        String[][] listings;
        int nCol;
        List<String[]> listArr = new ArrayList<>();
        try {
            rs = getListingAll.executeQuery();

            nCol = rs.getMetaData().getColumnCount(); // Adapted from https://stackoverflow.com/questions/24547406/resultset-into-2d-array

            while( rs.next() ) {
                String[] row = new String[nCol];
                for (int i = 1; i <= nCol; i++) {
                    String str = rs.getString(i);
                    row[i-1] = str;
                }
                listArr.add( row );
            }

            listings = new String[listArr.size()][];
            for (int j = 0; j < listArr.size(); j++) {
                String[] row = listArr.get(j);
                listings[j] = row;
            }

            return listings;

        } catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }
        return null;
    }

    /**
     * Resolve listings in database
     * @param a Listing object
     */
    public void resolveListings(Listing a) {
        String thisUUID = a.getUUID().toString();
        String[] thisListing = getListing(thisUUID);
        String thisType = thisListing[2];
        String thisUnit = thisListing[4];
        String thisAsset = thisListing[5];
        int thisQuantity = Integer.valueOf(thisListing[6]);
        int thisPrice = Integer.valueOf(thisListing[7]);

        ResultSet rs;

        String resolveUUID;
        int resolveQuantity;
        int resolvePrice;
        String resolveUnit;

        int leftOverAsset;
        int fullCredit;
        int sellCredit;
        int leftOverCredit;

        try {
            if (thisType.equals("BUY")) {
                getListingBuy.setString(1, thisType);
                getListingBuy.setString(2, thisUnit);
                getListingBuy.setString(3, thisAsset);
                getListingBuy.setString(4, String.valueOf(thisPrice));
                rs = getListingBuy.executeQuery();
                rs.last();
                resolveUUID = rs.getString("listingID");
                resolveQuantity = Integer.valueOf(rs.getString("assetQuantity"));
                resolvePrice = Integer.valueOf(rs.getString("assetPrice"));
                resolveUnit = rs.getString("unitName");

                if (resolveQuantity < thisQuantity) {
                    leftOverAsset = thisQuantity - resolveQuantity;
                    fullCredit = resolveQuantity * thisPrice;
                    sellCredit = resolveQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    closeListing(resolveUUID);
                    updateListing(thisUUID, leftOverAsset);
                    awardCredits(resolveUnit, sellCredit);
                    awardCredits(thisUnit, leftOverCredit); // Give back leftover
                }
                else if (resolveQuantity == thisQuantity) {
                    fullCredit = resolveQuantity * thisPrice;
                    sellCredit = resolveQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    closeListing(resolveUUID);
                    closeListing(thisUUID);
                    awardCredits(resolveUnit, sellCredit);
                    awardCredits(thisUnit, leftOverCredit); // Give back leftover
                }
                else {
                    leftOverAsset = resolveQuantity - thisQuantity;
                    fullCredit = thisQuantity * thisPrice;
                    sellCredit = thisQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    updateListing(resolveUUID, leftOverAsset);
                    closeListing(thisUUID);
                    awardCredits(resolveUnit, sellCredit);
                    awardCredits(thisUnit, leftOverCredit); // Give back leftover
                }

            }
            else if (thisType.equals("SELL")) {
                getListingBuy.setString(1, thisType);
                getListingBuy.setString(2, thisUnit);
                getListingBuy.setString(3, thisAsset);
                getListingBuy.setString(4, String.valueOf(thisPrice));
                rs = getListingBuy.executeQuery();
                rs.last();
                resolveUUID = rs.getString("listingID");
                resolveQuantity = Integer.valueOf(rs.getString("assetQuantity"));
                resolvePrice = Integer.valueOf(rs.getString("assetPrice"));
                resolveUnit = rs.getString("unitName");

                if (thisQuantity < resolveQuantity) {
                    leftOverAsset = resolveQuantity - thisQuantity;
                    sellCredit = thisQuantity * thisPrice;
                    fullCredit = thisQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    closeListing(thisUUID);
                    updateListing(resolveUUID, leftOverAsset);
                    awardCredits(resolveUnit, leftOverCredit);
                    awardCredits(thisUnit, sellCredit);
                }
                else if (resolveQuantity == thisQuantity) {
                    sellCredit = thisQuantity * thisPrice;
                    fullCredit = thisQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    closeListing(resolveUUID);
                    closeListing(thisUUID);
                    awardCredits(thisUnit, sellCredit);
                    awardCredits(resolveUnit, leftOverCredit); // Give back leftover
                }
                else {
                    leftOverAsset = thisQuantity - resolveQuantity;
                    sellCredit = resolveQuantity * thisPrice;
                    fullCredit = resolveQuantity * resolvePrice;
                    leftOverCredit = fullCredit - sellCredit;
                    updateListing(thisUUID, leftOverAsset);
                    closeListing(resolveUUID);
                    awardCredits(thisUnit, sellCredit);
                    awardCredits(resolveUnit, leftOverCredit); // Give back leftover
                }
            }


        } catch (SQLException SQLex) {
            System.err.println(SQLex);
            System.out.println("Nothing to Resolve");
        }
    }

    /**
     * Retrieve listing based on uuid
     * @param uuid
     * @return string array of listing
     */
    public String[] getListing(String uuid) {
        String[] listing;
        String listingID;
        String listingType;
        String listingActive;
        String userName;
        String unitName;
        String assetName;
        String assetQuantity;
        String assetPrice;
        String dateTime;
        ResultSet rs;

        try {
            getListing.setString(1, uuid);
            rs = getListing.executeQuery();
            rs.first();
            listingID = rs.getString("listingID");
            listingActive = rs.getString("listingActive");
            listingType = rs.getString("listingType");
            userName = rs.getString("userName");
            unitName = rs.getString("unitName");
            assetName = rs.getString("assetName");
            assetQuantity = rs.getString("assetQuantity");
            assetPrice = rs.getString("assetPrice");
            dateTime = rs.getString("dateTime");
            listing = new String[] {listingID, listingActive, listingType, userName, unitName, assetName,
                                    assetQuantity, assetPrice, dateTime};
            return listing;
        } catch (SQLException SQLex) {
            System.err.println(SQLex);
        }
        return null;
    }

    /**
     * Closes database connection.
     */
    public void closeDatabaseSource() {
        JDBCConnection.closeConnection();
    }

}
