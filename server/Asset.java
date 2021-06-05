package server;

import java.util.regex.Pattern;

public class Asset {

    private final int MIN_LENGTH = 1;
    private final int MAX_LENGTH = 30;
    private final Pattern INVALID_CHRACTERS = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);

    private final String name;

    /**
     * Constructor of Asset object
     * @param name name of asset
     */
    public Asset(String name) throws TextInputException {
        if (INVALID_CHRACTERS.matcher(name).find()) {
            throw new TextInputException(String.format("%s contains invalid characters, cannot contain spaces, symbols or numbers", name));
        }
        if (!(name.length() >= MIN_LENGTH && name.length() <= MAX_LENGTH)) {
            throw new TextInputException(String.format("%s does not fit constants of size at %d, must be between %d and %d", name, name.length(), MIN_LENGTH, MAX_LENGTH));
        }
        this.name = name;
    }

    /**
     * retrieve name of asset
     * @return name of asset
     */
    public String getName() {
        return name;
    }
}
