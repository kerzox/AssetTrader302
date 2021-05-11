package server;

import client.Gui;

import javax.swing.*;

public class Main {

    private static User test;
    private static User test2;

    private static Organisation unitTest;
    private static Organisation unitTest1; // Not in Database

    private static JDBCDatabaseSource database;

    // main function.
    public static void main(String[] args) {

        database = new JDBCDatabaseSource();

        unitTest = new Organisation("unit1", 10000);
        database.addOrganisation(unitTest);

        unitTest1 = new Organisation("unit2", 9999);

        // This will work because unitTest organisation is in the database!!
        test = new User("adaeo", "secret", unitTest);
        database.addAccount(test);

        // This will not work because unitTest1 organisation is not in the database!!
        test2 = new User("william", "secret", unitTest1);
        database.addAccount(test2);

        String[] account = database.getAccount("adaeo");

        String thisUser = account[0];
        String thisPwd = account[1];
        String thisUnit = account[2];

        System.out.println(thisUser + " " + thisPwd + " " + thisUnit);

        String[] unit = database.getOrganisation("unit1");

        String unitID = unit[0];
        String unitName = unit[1];
        String unitBudget = unit[2];

        System.out.println(unitID + " " + unitName + " " + unitBudget);

        database.closeDatabaseSource();
    }
}
