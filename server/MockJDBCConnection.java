package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MockJDBCConnection {
    /**
     * Instance of database connection.
     */
    private static Connection connection;

    /**
     * Constructor to initialise the connection to the database using properties input. (db.props)
     */
    public MockJDBCConnection() {
        Properties props = new Properties();
        FileInputStream propRead;
        try {
            // read properties file
            propRead = new FileInputStream("./db.props");
            props.load(propRead);
            propRead.close();

            // specifies properties
            String url = props.getProperty("jdbc.url");
            String schema = "mock";
            String user = props.getProperty("jdbc.username");
            String pwd = props.getProperty("jdbc.password");

            // get connection
            connection = DriverManager.getConnection(url +"/" + schema, user, pwd);
        } catch (SQLException | FileNotFoundException err) {
            System.err.println(err);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    /**
     * Closes database connection.
     */
    public static void closeConnection() {
        try {
            // close connection
            connection.close();

        }
        catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Provides global access to connection.
     *
     * @return database connection instance
     */
    public static Connection getConnection() {
        if (connection == null) {
            new MockJDBCConnection();
        }
        return connection;
    }
}
