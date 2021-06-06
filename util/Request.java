package util;

public class Request {

    public enum Type {
        ALTER,
        CREATE,
        MESSAGE;

        public static boolean isSQLCommand(Type command) {
            return command != MESSAGE;
        }
    }
    public enum Header {
        ACCOUNT,
        ASSET,
        ORGANISATION,
        LISTING,
        CLIENTREQUEST,
        SERVERRESPONSE
    }


    public static Type grabValidType(String toString) {
        for (Type value : Type.values()) {
            if (value.toString().equals(toString)) {
                return value;
            }
        }
        return null;
    }

    public static Header grabValidHeader(String toString) {
        for (Header value : Header.values()) {
            if (value.toString().equals(toString)) {
                return value;
            }
        }
        return null;
    }


}
