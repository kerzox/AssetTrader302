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
        return Type.valueOf(toString);
    }

    public static Header grabValidHeader(String toString) {
        return Header.valueOf(toString);
    }


}
