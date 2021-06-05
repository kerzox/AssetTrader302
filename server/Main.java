package server;

import util.NetworkUtils;
import util.Request;

import javax.sound.midi.SysexMessage;
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

    private static Socket CLIENT = null;

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
                CLIENT = client;
                List<Object> list = NetworkUtils.read(client);

                doRequests(client, list);

            } while (running.get());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void doRequests(Socket client, List<Object> request) {
        try {

            Request.Header command = Request.grabValidHeader(request.get(0).toString());
            if (command == null) throw new CommandException(request.get(0).toString() + " is invalid as a command.");

            // do non sql command handling
            if (!Request.Header.isSQLCommand(command)) {
                if (command == MESSAGE) {
                    if (request.get(1).toString().contains("quit")) running.set(false);
                }
            }

            Request.Type type = Request.grabValidType(request.get(1).toString());
            if (type == null) throw new CommandException(request.get(1).toString()
                    + " is an invalid type.");

            parseClientRequest(request);

        } catch (CommandException | IOException e) {
            NetworkUtils.write(client, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void parseClientRequest(List<Object> data) throws IOException {
        switch (Request.Type.valueOf(data.get(1).toString())) {
                case ACCOUNT:
                    if (Request.Header.valueOf(data.get(0).toString()) == ALTER) {
                        editAccountDB(data);
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
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        addListingDB(data);
                    }
                    break;
                case ORGANISATION:
                    if (Request.Header.valueOf(data.get(0).toString()) == ALTER) {
                        editOrganisationBudgetDB(data);
                    }
                    if (Request.Header.valueOf(data.get(0).toString()) == CREATE) {
                        addOrganisationDB(data);
                    }
                    break;
                case CLIENTREQUEST:
                    String arg = data.get(2).toString();
                    if (arg.equals("GET_USER_LOGIN")) {
                        String userName = data.get(3).toString();
                        String hashPwd = data.get(4).toString();
                        if (database.getAccount(userName) != null && database.getAccount(userName)[2].equals(hashPwd)) {
                            NetworkUtils.write(CLIENT, Request.Type.SERVERRESPONSE, "LOGIN", 1);
                        }
                        else {
                            NetworkUtils.write(CLIENT, Request.Type.SERVERRESPONSE, "LOGIN", 0);
                        }
                    }
                    if (arg.equals("GET_ADMIN_INFO")) {
                        String requestSource = data.get(3).toString();
                        String[] allOrganisations = database.getAllOrganisations();
                        System.out.println(allOrganisations);
                        String[][] allAccounts = database.getAllAccounts();
                        NetworkUtils.write(CLIENT, Request.Type.SERVERRESPONSE, "INFO_RESPONSE", requestSource, allOrganisations, allAccounts);
                    }
                    if (arg.equals("GET_ORG_INFO")) {
                        String requestSource = data.get(3).toString();
                        String userName = data.get(4).toString();
                        String organisation = database.getAccount(userName)[3];
                        String budget = database.getOrganisation(organisation)[2];
                        String[][] userListings = database.getUserListing(userName);
                        String[][] allListings = database.getAllListings();
                        String[] allOrgs = database.getAllOrganisations();
                        String[] assets = database.getAllAssets();
                        NetworkUtils.write(CLIENT, Request.Type.SERVERRESPONSE, "INFO_RESPONSE", requestSource,
                                userName, organisation, budget, userListings, allListings, allOrgs, assets);

                    }
                    break;

                default:
                    //data.forEach(System.out::println);
        }
    }

    /**
     * Adds organisation to database
     * @param data List of objects from client
     */
    private void addOrganisationDB(List<Object> data) {
        database.addOrganisation(new Organisation((String) data.get(2)));
    }

    /**
     * Edits budget of organisation in database
     * @param data List of objects from client
     */
    private void editOrganisationBudgetDB(List<Object> data) {
        String unitName = (String) data.get(2);
        if (database.getOrganisation(unitName) != null) {
            database.updateOrganisation(new Organisation(
                    (String) data.get(2)
            ), Integer.parseInt((String) data.get(3)));
        }
        else {
            System.out.println("Failed to edit budget");
        }
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

    /**
     * Edits account in database
     * @param data
     */
    private void editAccountDB(List<Object> data) {
        String userName = (String) data.get(2);
        String organisation = (String) data.get(4);
        if (database.getAccount(userName) != null && database.getOrganisation(organisation) != null) {
            database.updateAccount(new User(
                    (String) data.get(2), // Username
                    (String) data.get(3), // Password
                    new Organisation( // Organisation
                            database.getOrganisation((String) data.get(4))[1], // Unit Name
                            Integer.parseInt(database.getOrganisation((String) data.get(4))[2]) // Budget
                    )));
        }
        else {
            System.out.println("Failed to edit account");
        }
    }

    /**
     * Adds listing to database
     * @param data
     */
    private void addListingDB(List<Object> data) {
        String listingType = data.get(2).toString();
        String listingAsset = data.get(3).toString();
        String listingQuantity = data.get(4).toString();
        String listingPrice = data.get(5).toString();
        String listingUser = data.get(6).toString();

        if (database.getAccount(listingUser) != null && database.getAsset(listingAsset) != null) {
            String listingOrganisation = database.getAccount(listingUser)[3];
            String budget = database.getOrganisation(listingOrganisation)[2];
            UUID uuid = UUID.randomUUID();
            Listing.enumType listType = Listing.enumType.valueOf(listingType);
            Listing a = new Listing(
                    uuid,
                    listType,
                    Integer.parseInt(listingQuantity),
                    Integer.parseInt(listingPrice),
                    listingUser,
                    listingOrganisation,
                    listingAsset
            );
            if (listType == Listing.enumType.BUY) {
                int totalCost = Integer.parseInt(listingPrice) * Integer.parseInt(listingQuantity);
                if (totalCost <= Integer.parseInt(budget)) { // Less than budget
                    database.addListing(a);
                    database.updateOrganisation(new Organisation(
                            listingOrganisation
                    ), (0-totalCost));
                }
                else {

                    System.out.println("Not enough budget");
                    NetworkUtils.write(CLIENT, Request.Type.SERVERRESPONSE, "LISTING", "FAILED_ADD", "Not enough budget");
                }
            }
            else {
                database.addListing(a);
            }
            database.resolveListings(a);
        }
        else {
            System.out.println("Failed to add listing");
        }
    }

    public static int getPort(){
        return CURRENT_PORT;
    }
}
