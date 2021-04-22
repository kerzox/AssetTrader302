package server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Main {


    // main function.
    public static void main(String[] args) {

        User user = new User(UUID.randomUUID(),
                "test",
                "test",
                new Organisation("Accounting"),
                User.AccountType.ADMIN);

    }

    /*

       Core functionality.

     */


}
