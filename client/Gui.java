package client;

import server.User;

import javax.swing.*;
import java.util.List;

public class Gui implements Runnable {

    private static String ClientUser;

    public static String getSessionUser() { return ClientUser;}

    public static void setSessionUser(String value) { ClientUser = value; }

    public static LoginFrame login;
    public static UserFrame user;
    public static AdminFrame admin;

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            login = new LoginFrame();

            // create client server thread
            new Thread(new ClientServer()).start();
        }); // Execute Runnable on AWT thread


        // USER LOGIN: needs to be created through database in Admin Panel
        // ADMIN LOGIN: root, secret
    }

    public static void buildUser() { user = new UserFrame(); }

    public static void buildAdmin() { admin = new AdminFrame(); }

    public static void readServer(List<Object> data) {
        System.out.println(SwingUtilities.isEventDispatchThread());
        String arg = data.get(1).toString();
        switch(arg) {
            case "LOGIN":
                int status = (int) data.get(2);
                if (status == 1) {
                    login.loginSuccessful();
                }
                else if (status == 0) {
                    login.loginFailed();
                }
                break;
            case "INFO_RESPONSE":
                if (data.get(2).toString().equals("UserFrame")) {
                    String userString = data.get(3).toString();
                    String organisationString = data.get(4).toString();
                    String budgetString = data.get(5).toString();
                    String[][] userListings = (String[][]) data.get(6);
                    String[][] allListings = (String[][]) data.get(7);
                    String[] assets = (String[]) data.get(9);
                    user.setServerResponse(userString, organisationString, budgetString, userListings, allListings, assets);
                }
                if (data.get(2).toString().equals("AdminFrame")) {
                    String[] allOrganisations = (String[]) data.get(3);
                    String[][] allAccounts = (String[][]) data.get(4);
                    admin.setServerResponse(allOrganisations, allAccounts);
                }
                break;
            case "LISTING":
                if (data.get(2).toString().equals("FAILED_ADD")) {
                    user.notification(data.get(3).toString());
                }
        }
    }

}
