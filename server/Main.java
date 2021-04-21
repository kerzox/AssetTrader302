package server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Main {


    // main function.
    public static void main(String[] args) {

        User user = new User(UUID.randomUUID(), "test", "test", OrganisationUnit.IT, User.AccountType.ADMIN);

        try {
            System.out.println(user.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    /*

       Core functionality.

     */


}
