package server;

import java.util.UUID;

public interface IDatabaseAccount {


    /**
     * Add user to database
     * @param userToAdd
     */

    void addUser(User userToAdd);


    /**
     * Get user account by UUID
     * @param uuid
     * @return user object by that id from database
     */

    User getAccount(UUID uuid);


}
