package server;

import java.util.UUID;

public class User {

    private String username;
    private String password;
    private Organisation unit;

    /**
     * User constructor
     * @param username username for account
     * @param password password for account
     * @param unit unit organisation
     */
    public User(String username, String password, Organisation unit) throws TextInputException{
        if (username.contains(" ")) {
            throw new TextInputException("Username cannot contain whitespace");
        }
        if (password.contains(" ")) {
            throw new TextInputException("Password cannot contain whitespace");
        }
        if (username.length() < 1 || username.length() > 30) {
            throw new TextInputException("Username must be between 1 and 30 characters");
        }
        if (password.length() < 1) {
            throw new TextInputException("Password cannot be empty!");
        }
        this.username = username;
        this.password = password;
        this.unit = unit;
    }

    /**
     * Get username
     * @return Username String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get user's salted and hashed password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes user's password to specified.
     * @param password
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * Get organisation name user is from
     * @return Unit name
     */
    public String getUnitName() {  return unit.getName(); }




}
