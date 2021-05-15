package server;

import client.Gui;

import javax.swing.*;

public class Main {

    private static User test;
    private static User test2;

    private static Organisation unitTest;
    private static Organisation unitTest1; // Not in Database

    private static Asset assetTest;
    private static Asset assetTest2;

    private static JDBCDatabaseSource database;

    // main function.
    public static void main(String[] args) {

        database = new JDBCDatabaseSource();

        TemporaryTesting();

        database.closeDatabaseSource();
    }

    /**
     * Testing Function for database purposes
     */
    private static void TemporaryTesting() {
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
    }
}
