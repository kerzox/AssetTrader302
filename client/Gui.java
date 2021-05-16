package client;

import javax.swing.*;

public class Gui implements Runnable {

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();

            // create client server thread
            new Thread(new ClientServer()).start();
        }); // Execute Runnable on AWT thread


        // USER LOGIN: test, pwd
        // ADMIN LOGIN: root, secret
    }

    public static void buildUser() {
        UserFrame userFrame = new UserFrame();

    }

    public static void buildAdmin() {
        AdminFrame adminFrame = new AdminFrame();
    }

}
