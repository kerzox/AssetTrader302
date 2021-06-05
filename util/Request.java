package util;

public class Request {

    public enum Header {
        ALTER,
        CREATE,
        MESSAGE,
        DELETE;

        public static boolean isSQLCommand(Header command) {
            return command != MESSAGE;
        }
    }
    public enum Type {
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
