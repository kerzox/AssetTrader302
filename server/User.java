package server;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;
    private String password;
    private OrganisationUnit unit;
    private final AccountType accountType;

    /**
     * User constructor
     * @param id unique id
     * @param username username for account
     * @param password password for account
     * @param unit unit organisation
     * @param type account type
     */
    public User(UUID id, String username, String password, OrganisationUnit unit, AccountType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.unit = unit;
        this.accountType = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public javax.crypto.SecretKey getPassword() throws java.security.spec.InvalidKeySpecException, java.security.NoSuchAlgorithmException {
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        java.security.spec.KeySpec spec = new javax.crypto.spec.PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        return javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OrganisationUnit getUnit() {
        return unit;
    }

    public void setUnit(OrganisationUnit unit) {
        this.unit = unit;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public UUID getId() {
        return id;
    }

    enum AccountType {
        ADMIN,
        USER;
    }

}
