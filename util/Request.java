package util;

public class Request {
    public enum Header {
        ALTER,
        CREATE,
        DELETE
    }
    public enum Type {
        ACCOUNT,
        ASSET,
        ORGANISATION,
        LISTING
    }
}
