package server;

public interface IDatabaseAccount {


    /**
     * Add user to database
     * @param userToAdd
     */

    void addUser(User userToAdd);


    /**
     * Get user account by username
     * @param username
     * @return user object by that name from database
     */

    User getAccount(String username);


}
