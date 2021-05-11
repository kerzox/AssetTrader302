package server;

import java.sql.*;

public class JDBCDatabaseSource {

    public static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS account ("
                    + "userID INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "userName VARCHAR(30) NOT NULL UNIQUE,"
                    + "password CHAR(128) NOT NULL,"
                    + "unit CHAR(50) NOT NULL"
                    + ");";

    private static final String INSERT_USER = "INSERT INTO account (userName, password, unit) VALUES (?, ?, ?);";
    private static final String GET_ACCOUNT = "SELECT * FROM account WHERE userName=?";


    private Connection connection;

    private PreparedStatement addAccount;
    private PreparedStatement getAccount;

    public JDBCDatabaseSource() {
        connection = JDBCConnection.getConnection();

        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_ACCOUNT);
            addAccount = connection.prepareStatement(INSERT_USER);
            getAccount = connection.prepareStatement(GET_ACCOUNT, ResultSet.TYPE_SCROLL_INSENSITIVE);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void addAccount(User a) {
        try {
            /* BEGIN MISSING CODE */
            addAccount.setString(1, a.getUsername());
            addAccount.setString(2, a.getPassword());
            addAccount.setString(3, a.getUnit());
            addAccount.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            System.out.println("Account with username " + a.getUsername()
            + " already exists.");
        }
    }

    public String[] getAccount(String name) {
        String[] account;
        String user;
        String pwd;
        String unit;
        ResultSet rs;

        try {
            getAccount.setString(1, name);
            rs = getAccount.executeQuery();
            rs.first();
            user = rs.getString("userName");
            pwd = rs.getString("password");
            unit = rs.getString("unit");
            account = new String[] {user, pwd, unit};
            return account;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void closeDatabaseSource() {
        JDBCConnection.closeConnection();
    }

}
