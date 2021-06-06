package server;

import client.ClientServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.mock.MockServer;
import util.NetworkUtils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;

public class TestClasses {
    Organisation o1, o2;
    User u1;
    Asset a1;
    Listing l1;

    @BeforeEach
    public void initClasses() {
        o1 = new Organisation("unit1");
        u1 = new User("User1", "pwd", o1);
    }

    @Test
    public void testNewBudget() throws BudgetException {
        // Equivalence Classes
        assertThrows(BudgetException.class, () -> {
           o2 = new Organisation("unit3", -10);
        });
        o2 = new Organisation("unit3", 10);
        assertEquals(10, o2.getBudget());

        // Boundary Classes
        assertThrows(BudgetException.class, () -> {
            o2 = new Organisation("unit3", -1);
        });
        o2 = new Organisation("unit3", 0);
        assertEquals(0, o2.getBudget());
    }

    @Test
    public void testAddBudget() throws BudgetException {
        // Equivalence Classes
        assertThrows(BudgetException.class, () -> {
            o1.addBudget(-10);
        });
        o1.addBudget(10);
        assertEquals(10, o1.getBudget());

        // Boundary Classes
        assertThrows(BudgetException.class, () -> {
            o1.addBudget(-1);
        });
        o1.addBudget(0);
        assertEquals(10, o1.getBudget());
    }

    @Test
    public void testSubtractBudget() throws BudgetException {
        // Equivalence Classes
        o1.addBudget(100);
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(-10);
        });
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(110);
        });
        o1.subtractBudget(10);
        assertEquals(90, o1.getBudget());

        // Boundary Classes
        o1.addBudget(10);
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(-1);
        });
        assertThrows(BudgetException.class, () -> {
            o1.subtractBudget(101);
        });
        o1.subtractBudget(1);
        assertEquals(99, o1.getBudget());
        o1.subtractBudget(98);
        assertEquals(1, o1.getBudget());
    }

    @Test
    public void testNewUser() throws TextInputException {
        // Equivalence Classes
        assertThrows(TextInputException.class, () -> {
            u1 = new User("12345678901234567890123456789012345", "pwd", o1);
        });
        u1 = new User("user1", "pwd", o1);
        assertEquals("user1", u1.getUsername());
        assertEquals("pwd", u1.getPassword());

        // Boundary Classes
        assertThrows(TextInputException.class, () -> {
            u1 = new User("1234567890123456789012345678901", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("uname", "", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("u name", "pwd", o1);
        });
        assertThrows(TextInputException.class, () -> {
            u1 = new User("uname", "p wd", o1);
        });
        u1 = new User("1", "1", o1);
        assertEquals("1", u1.getUsername());
        assertEquals("1", u1.getPassword());
        u1 = new User("123456789012345678901234567890",
                "123456789012345678901234567890", o1);
        assertEquals("123456789012345678901234567890", u1.getUsername());
        assertEquals("123456789012345678901234567890", u1.getPassword());

    }

    @Test
    public void testAddListing() {
        // equivalence

        // quantity -5
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, -5, 10, "user", "organisation", "asset");
        });

        // quantity 5
        l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 5, 10, "user", "organisation", "asset");
        assertEquals(5, l1.getAssetQuantity());

        // quantity -5
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, -5, "user", "organisation", "asset");
        });

        // quantity 5
        l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, 5, "user", "organisation", "asset");
        assertEquals(5, l1.getAssetPrice());

        // boundary

        // negative boundary quantity
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, -10, 10, "user", "organisation", "asset");
        });

        // 0 quantity

        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 0, 10, "user", "organisation", "asset");
        });

        // negative boundary price
        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, -10, "user", "organisation", "asset");
        });

        // 0 price

        assertThrows(ListingException.class, () -> {
            l1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, 0, "user", "organisation", "asset");
        });

    }

    @Test
    public void testNewAsset() {
        // equivalence

        // size of 25
        a1 = new Asset("alkenylidenecyclopropanes");
        assertEquals(25, a1.getName().length());
        assertEquals("alkenylidenecyclopropanes", a1.getName());

        // size of 35
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("hippopotomonstrosesquipedaliophobic");
        });

        // boundary

        // length of 0
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("");
        });

        // length of 31
        assertThrows(TextInputException.class, () -> {
            a1 = new Asset("quinquagintaquadringentillionth");
        });

        //Whitespaces are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test ");
        });

        //Symbols are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test!");
        });

        //Numbers are invalid
        assertThrows(TextInputException.class, ()-> {
            a1 = new Asset("test1");
        });
    }

    // Glass Box
    MockJDBCDatabaseSource database = new MockJDBCDatabaseSource();
    Organisation unit1 = new Organisation("unit1");
    Organisation unit2 = new Organisation("unit2");
    User user1 = new User("user1", "pwd", unit1);
    User user2 = new User("user2", "pwd", unit2);
    Asset asset1 = new Asset("assetone");
    Asset asset2 = new Asset("assettwo");
    Listing listing1 = new Listing(UUID.randomUUID(), Listing.enumType.BUY, 10, 10,
            "user1", "unit1", "assetone");
    Listing listing2 = new Listing(UUID.randomUUID(), Listing.enumType.SELL, 10, 10,
            "user2", "unit2", "assetone");

    // GLASS BOX TESTING
    @BeforeEach
    public void initDatabase() throws SQLException {
        database.dropAllTables();
        database = new MockJDBCDatabaseSource();
    }

    @Test
    public void testAddOrganisation() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);
        // Add duplicate entry
        assertThrows(SQLException.class, ()-> {
            database.addOrganisation(unit1);
        });
    }

    @Test
    public void testUpdateBudget() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);

        assertThrows(SQLException.class, ()-> {
            database.awardCredits("unit0", 100);
        });
        database.awardCredits("unit1", 100);
        assertEquals(100, Integer.valueOf(database.getOrganisation("unit1")[2]));
        database.awardCredits("unit2", 50);
        assertEquals(50, Integer.valueOf(database.getOrganisation("unit2")[2]));

        database.updateOrganisation(unit1, -10);
        assertEquals(90, Integer.valueOf(database.getOrganisation("unit1")[2]));
        database.updateOrganisation(unit2, -20);
        assertEquals(30, Integer.valueOf(database.getOrganisation("unit2")[2]));
    }

    @Test
    public void testGetAllOrganisations() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);

        String[] listOrgs = {"unit1", "unit2"};

        assertTrue(Arrays.equals(listOrgs, database.getAllOrganisations()));
    }

    @Test
    public void testAddAccount() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);

        database.addAccount(user1);
        assertEquals("user1", database.getAccount("user1")[1]);
        assertEquals("pwd", database.getAccount("user1")[2]);
        assertEquals("unit1", database.getAccount("user1")[3]);
        // Add duplicate
        assertThrows(SQLException.class, ()-> {
            database.addAccount(user1);
        });

        database.addAccount(user2);
        assertEquals("user2", database.getAccount("user2")[1]);
        assertEquals("pwd", database.getAccount("user2")[2]);
        assertEquals("unit2", database.getAccount("user2")[3]);
        Organisation unit3 = new Organisation("unit3");
        assertThrows(SQLException.class, ()-> {
            database.addAccount(new User("user3", "pwd", unit3));
        });
    }

    @Test
    public void testUpdateAccount() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);
        database.addAccount(user1);
        assertEquals("user1", database.getAccount("user1")[1]);
        assertEquals("pwd", database.getAccount("user1")[2]);
        assertEquals("unit1", database.getAccount("user1")[3]);
        database.updateAccount(new User("user1", "newpwd", unit2));
        assertEquals("user1", database.getAccount("user1")[1]);
        assertEquals("newpwd", database.getAccount("user1")[2]);
        assertEquals("unit2", database.getAccount("user1")[3]);

        assertThrows(SQLException.class, ()-> {
            database.updateAccount(new User("user2", "pwd", unit1));
        });
    }

    @Test
    public void testGetAllAccounts() throws SQLException {
        database.addOrganisation(unit1);
        database.addOrganisation(unit2);
        database.addAccount(user1);
        database.addAccount(user2);

        String[][] listAcc = new String[][] {{"1", "user1", "pwd", "unit1"},
                            {"2", "user2", "pwd", "unit2"}};
        assertEquals(listAcc.length, database.getAllAccounts().length);
        assertTrue(Arrays.equals(listAcc[0], database.getAllAccounts()[0]));
        assertTrue(Arrays.equals(listAcc[1], database.getAllAccounts()[1]));
    }

    @Test
    public void testAddAsset() throws SQLException {
        database.addAsset(asset1);
        assertEquals("assetone", database.getAsset("assetone")[1]);
        // Add duplicate
        assertThrows(SQLException.class, ()-> {
            database.addAsset(asset1);
        });
        assertThrows(TextInputException.class, ()-> {
            Asset numberAsset = new Asset("123");
        });
    }

    @Test
    public void testGetAllAssets() throws SQLException {
        database.addAsset(asset1);
        database.addAsset(asset2);
        String[] listAss = new String[] {"assetone", "assettwo"};

        assertTrue(Arrays.equals(listAss, database.getAllAssets()));
    }

    @Test
    public void testAddListings() throws SQLException {
        database.addOrganisation(unit1);
        database.addAccount(user1);
        database.addAsset(asset1);
        database.addListing(listing1);
        assertEquals(user1.getUsername(), database.getListing(listing1.getUUID().toString())[3]);

        assertThrows(SQLException.class, ()-> {
           database.addListing(listing2);
        });

        assertThrows(SQLException.class, ()-> {
            database.getListing(listing2.getUUID().toString());
        });
    }

    @Test
    public void testUpdateListing() throws SQLException {
        database.addOrganisation(unit1);
        database.addAccount(user1);
        database.addAsset(asset1);
        database.addListing(listing1);
        assertEquals(10, Integer.valueOf(database.getListing(listing1.getUUID().toString())[6]));
        database.updateListing(listing1.getUUID().toString(), 5);
        assertEquals(5, Integer.valueOf(database.getListing(listing1.getUUID().toString())[6]));
    }

    @Test
    public void testCloseListing() throws SQLException {
        database.addOrganisation(unit1);
        database.addAccount(user1);
        database.addAsset(asset1);
        database.addListing(listing1);
        assertEquals(1, Integer.valueOf(database.getListing(listing1.getUUID().toString())[1]));
        database.closeListing(listing1.getUUID().toString());
        assertEquals(0, Integer.valueOf(database.getListing(listing1.getUUID().toString())[1]));
    }

    @Test
    public void testCloseDatabase() throws NullPointerException {
        assertThrows(NullPointerException.class, ()-> {
            database.closeDatabaseSource();
        });
    }

    MockServer mock = new MockServer();

    // mock network utils
    public void mockWrite(Object... data) {
        List<Object> temp = new ArrayList<>(Arrays.asList(data));
        mock.doRequests(temp);
    }

    @Test
    public void clientSendsIncorrectCommand() {
        assertThrows(CommandException.class, ()-> {
            mockWrite("REQUEST1", "CREATE", "add to database");
        });
    }

    @Test
    public void clientSendsIncorrectHeader() {
        assertThrows(CommandException.class, ()-> {
            mockWrite("REQUEST", "ADD", "add to database");
        });
    }

    @Test
    public void clientSendsQuitServer() {
        mockWrite("MESSAGE", "quit");
        assertEquals(true, mock.isShutdown());
    }
}
