package server;

import java.util.UUID;

public class DatabaseHelper {

    /**
     * Add user to database
     *
     * @param toAdd
     */

    public static void addUser(User toAdd) {

    }

    /**
     * Get user account by UUID
     *
     * @param uuid
     * @return user object by that id from database
     */

    public static User getAccount(UUID uuid) {
        return null;
    }

    /**
     * Deletes a user by user object
     *
     * @param toDelete
     */

    public static void deleteAccount(User toDelete) {

    }

    /**
     * Adds an organisation to the database
     *
     * @param toAdd
     */

    public static void addOrganisation(Organisation toAdd) {

    }

    /**
     * Get organisation by String
     *
     * @param name
     * @return organisation object by name from database
     */

    public static Organisation getOrganisation(String name) {
        return null;
    }

    /**
     * Adds a asset to the database
     *
     * @param asset
     */

    public static void addAsset(Asset asset) {

    }

    /**
     * Gets a asset from the database
     *
     * @param name
     * @return asset from name.
     */

    public static Asset getAsset(String name) {
        return null;
    }

    /**
     * Add a listing to the database
     *
     * @param unit    unit the listing belongs to
     * @param selling
     */

    public static void addListing(Organisation unit, Asset asset, boolean selling) {

    }

    /**
     * Remove a listing from the database
     *
     * @param unit    unit the listing belongs to
     * @param selling
     */

    public static void removeListing(Organisation unit, Asset asset, boolean selling) {

    }
}
