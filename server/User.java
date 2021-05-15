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
    public User(String username, String password, Organisation unit) {
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
     *
     * @param username to set
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Changes user's password to specified.
     * @param username
     */
    public void changeUsername(String username) {
        this.username = username;
    }

    /**
     * Get user's salted and hashed password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password to set
     */
    public void setPassword(String password) { this.password = password; }

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

    /**
     * Get organisation user is from
     * @return Unit name
     */
    public Organisation getUnit() {  return unit; }

    /**
     *
     * @param unit to set
     */
    public void setUnit(Organisation unit) { this.unit = unit; }

    /**
     * Changes user's organisation to specified.
     * @param unit
     */
    public void changeUnit(Organisation unit) {
        this.unit = unit;
    }



}
