package server;

import util.NetworkUtils;
import util.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static util.Request.Header.*;

public class Main implements Runnable  {

    private boolean initialized = false;
    private static final int CURRENT_PORT = 10000;
    private static final int SOCKET_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);

    private static User test;
    private static User test2;

    private static Organisation unitTest;
    private static Organisation unitTest1; // Not in Database

    private static Asset assetTest;
    private static Asset assetTest2;

    private static Listing listingTest;
    private static Listing listingTest2; // Not in Database

    private static JDBCDatabaseSource database;


    @Override
    public void run() {

        // Initialise Process: you need to drop the database and recreate it or
        // you will have some unexpected errors during testing
        if (!initialized) {
            database = new JDBCDatabaseSource();
        }

        while(true) {
            runServer();
        }

    }

    private void initalize() {

    }

    public void runServer() {
        try (ServerSocket server = new ServerSocket(CURRENT_PORT)) {

            System.out.println("Server is running");

            while(true) {
                Socket client = server.accept();
                System.out.println("Client connected");
                handleConnection(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket client) {
        try {

            String msg = "";

            do {

                List<Object> list = NetworkUtils.read(client);
                parseClientRequest(list);

            } while (msg == null || !msg.equals("quit"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void parseClientRequest(List<Object> data) {
        if (Arrays.stream(Request.Type.values()).anyMatch(p -> p.equals(data.get(1)))) {
            switch (Request.Type.valueOf(data.get(1).toString())) {
                case ACCOUNT:
                    if (Request.Header.valueOf(data.get(0).toString()) == ALTER) {
                    }
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        addAccountDB(data);
                    }
                    break;
                case ASSET:
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        addAssetDB(data);
                    }
                    break;
                case LISTING:
                    if (Request.Header.valueOf(data.get(0).toString()) == ALTER) {

                    }
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        if (Listing.enumType.valueOf(data.get(3).toString()) == Listing.enumType.BUY) {
                            database.addListing(new Listing(UUID.randomUUID(), Listing.enumType.BUY,
                                    (int) data.get(4), (int) data.get(5), (User) data.get(6), (Asset) data.get(7)));
                        } else {
                            database.addListing(new Listing(UUID.randomUUID(), Listing.enumType.SELL,
                                    (int) data.get(4), (int) data.get(5), (User) data.get(6), (Asset) data.get(7)));
                        }
                    }
                    break;
                case ORGANISATION:
                    if (Request.Header.valueOf(data.get(0).toString()) == ALTER) {
                    }
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        addOrganisationDB(data);
                    }
                    break;
                default:
                    //data.forEach(System.out::println);
            }
        }
        //data.forEach(System.out::println);
    }

    /**
     * Adds organisation to database
     * @param data List of objects from client
     */
    private void addOrganisationDB(List<Object> data) {
        database.addOrganisation(new Organisation((String) data.get(2)));
    }

    /**
     * Adds asset to database
     * @param data List of objects from client
     */
    private void addAssetDB(List<Object> data) {
        database.addAsset(new Asset((String) data.get(2)));
    }

    /**
     * Adds account to database
     * @param data List of objects from client
     */
    private void addAccountDB(List<Object> data) {
        String unitName = (String) data.get(4);
        if (database.getOrganisation(unitName) != null) {
            database.addAccount(
                    new User(
                            (String) data.get(2), // Username
                            (String) data.get(3), // Password
                            new Organisation( // Organisation
                                    database.getOrganisation((String) data.get(4))[1], // Unit Name
                                    Integer.parseInt(database.getOrganisation((String) data.get(4))[2]) // Budget
                            )));
        }
        else {
            System.out.println("Failed to add account");
        }
    }

    public static int getPort(){
        return CURRENT_PORT;
    }

    /**
     * Testing Function for database purposes
     */
    private static void addData() {
        unitTest = new Organisation("unit1", 10000);
        database.addOrganisation(unitTest);

        unitTest1 = new Organisation("unit2", 9999);

        // This will work because unitTest organisation is in the database!!
        test = new User("adaeo", "secret", unitTest);
        database.addAccount(test);

        // This will not work because unitTest1 organisation is not in the database!!
        test2 = new User("kerzox", "secret", unitTest1);
        database.addAccount(test2);

        assetTest = new Asset("CHAIRS");
        database.addAsset(assetTest);

        assetTest2 = new Asset("TABLES");
        database.addAsset(assetTest2);

        try {
            UUID uuid = UUID.randomUUID(); // Create random UUID
            listingTest = new Listing(uuid, Listing.enumType.BUY, 1000, 6, test, assetTest);
            database.addListing(listingTest);

            // This will not work because the organisation will run out of budget!!
            UUID uuid2 = UUID.randomUUID(); // Create random UUID
            listingTest2 = new Listing(uuid2, Listing.enumType.BUY, 1000, 5, test, assetTest);
            database.addListing(listingTest2);

        } catch (BudgetException err) { // Catch if budget is exceeded.
            System.out.println(err);
        }
    }

    private static void getData() {
        String[] account = database.getAccount("adaeo");

        String userID = account[0];
        String thisUser = account[1];
        String thisPwd = account[2];
        String thisUnit = account[3];

        System.out.println(userID + " " + thisUser + " " + thisPwd + " " + thisUnit);

        String[] unit = database.getOrganisation("unit1");

        String unitID = unit[0];
        String unitName = unit[1];
        String unitBudget = unit[2];

        System.out.println(unitID + " " + unitName + " " + unitBudget);

        String[] asset = database.getAsset("CHAIRS");

        String assetID = asset[0];
        String assetName = asset[1];

        System.out.println(assetID + " " + assetName);

        String[] asset2 = database.getAsset("TABLES");

        String assetID2 = asset2[0];
        String assetName2 = asset2[1];

        System.out.println(assetID2 + " " + assetName2);

        String[] listing = database.getListing(listingTest.getUUID().toString());
        String listingString = "";
        for (int i = 0; i < listing.length; i++) {
            listingString = listingString + listing[i] + " ";
        }
        System.out.println(listingString);
    }
}
