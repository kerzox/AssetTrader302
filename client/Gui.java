package client;

import javax.swing.*;
import java.util.List;

public class Gui implements Runnable {

    private static String ClientUser;

    public static String getSessionUser() { return ClientUser;}

    public static void setSessionUser(String value) { ClientUser = value; }

    public static LoginFrame login;

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            login = new LoginFrame();

            // create client server thread
            new Thread(new ClientServer()).start();
        }); // Execute Runnable on AWT thread


        // USER LOGIN: test, pwd
        // ADMIN LOGIN: root, secret
    }

    public static void buildUser() { UserFrame userFrame = new UserFrame(); }

    public static void buildAdmin() {
        AdminFrame adminFrame = new AdminFrame();
    }

    public static void readServer(List<Object> data) {
        if (data.get(1).toString().equals("LOGIN")) {
            int status = (int) data.get(2);
            if (status == 1) {
                login.loginSuccessful();
            }
            else if (status == 0) {
                login.loginFailed();
            }
        }
    }

}
