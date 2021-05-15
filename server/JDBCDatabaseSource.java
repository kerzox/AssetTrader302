package server;

import java.sql.*;

public class JDBCDatabaseSource {

    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS account ("
                    + "userID INTEGER /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "userName VARCHAR(30) NOT NULL UNIQUE,"
                    + "password CHAR(128) NOT NULL,"
                    + "unitName VARCHAR(60) NOT NULL,"
                    + "PRIMARY KEY (userID),"
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


    private Connection connection;

    private PreparedStatement addAccount;
    private PreparedStatement getAccount;
    private PreparedStatement addOrganisation;
    private PreparedStatement getOrganisation;
    private PreparedStatement addAsset;
    private PreparedStatement getAsset;

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
            addAccount = connection.prepareStatement(INSERT_ACCOUNT);
            getAccount = connection.prepareStatement(GET_ACCOUNT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addOrganisation = connection.prepareStatement(INSERT_UNIT);
            getOrganisation = connection.prepareStatement(GET_UNIT, ResultSet.TYPE_SCROLL_INSENSITIVE);
            addAsset = connection.prepareStatement(INSERT_ASSET);
            getAsset = connection.prepareStatement(GET_ASSET, ResultSet.TYPE_SCROLL_INSENSITIVE);

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
            /* BEGIN MISSING CODE */
            addAccount.setString(1, a.getUsername());
            addAccount.setString(2, a.getPassword());
            addAccount.setString(3, getOrganisation(a.getUnit())[1]);
            addAccount.execute();
            /* END MISSING CODE */
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
            /* BEGIN MISSING CODE */
            addOrganisation.setString(1, a.getName());
            addOrganisation.setString(2, String.valueOf(a.getBudget()));
            addOrganisation.execute();
            /* END MISSING CODE */
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
            /* BEGIN MISSING CODE */
            addAsset.setString(1, a.getName());
            addAsset.execute();
            /* END MISSING CODE */
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
     * Closes database connection.
     */
    public void closeDatabaseSource() {
        JDBCConnection.closeConnection();
    }

}
