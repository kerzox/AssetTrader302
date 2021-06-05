package util;

import java.util.regex.Pattern;

public class RegexUtil {
    public static final Pattern ONLY_LETTERS_INSENSITIVE = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
    public static final Pattern LETTERS_AND_NUMBERS_INSENSITIVE = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
}
