import client.Gui;
import server.Main;

public class Assettrader {
    public static void main(String[] args) {
        new Thread(new Main()).start();
        new Thread(new Gui()).start();
    }
}
