package server;

import client.Gui;

import javax.swing.*;

public class Main {

    private static User test;

    private static Organisation unitTest;

    private static JDBCDatabaseSource database;

    // main function.
    public static void main(String[] args) {

        unitTest = new Organisation("unit1", 10000);
        test = new User("adaeo", "secret", unitTest);

        database = new JDBCDatabaseSource();

        database.addAccount(test);

        String[] account = database.getAccount("adaeo");

        String thisUser = account[0];
        String thisPwd = account[1];
        String thisUnit = account[2];

        System.out.println(thisUser + " " + thisPwd + " " + thisUnit);

        database.closeDatabaseSource();
    }
}
