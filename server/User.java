package server;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;
    private String password;
    private Organisation unit;
    private final AccountType accountType;

    /**
     * User constructor
     * @param id unique id
     * @param username username for account
     * @param password password for account
     * @param unit unit organisation
     * @param type account type
     */
    public User(UUID id, String username, String password, Organisation unit, AccountType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.unit = unit;
        this.accountType = type;
    }

    /**
     * Get username
     * @return Username String
     */

    public String getUsername() {
        return username;
    }

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
     * Changes user's password to specified.
     * @param password
     */

    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * Get organisation user is from
     * @return Unit
     */

    public Organisation getUnit() {
        return unit;
    }

    /**
     * Changes user's organisation to specified.
     * @param unit
     */

    public void changeUnit(Organisation unit) {
        this.unit = unit;
    }

    /**
     * Gets user's account type
     * @return type.
     */

    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Gets users unique id
     * @return UUID
     */

    public UUID getId() {
        return id;
    }

    /**
     * Account type enum specify whether they are admin or a user.
     */

    enum AccountType {
        ADMIN,
        USER;
    }

}
