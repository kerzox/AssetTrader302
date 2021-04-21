package server;

public enum OrganisationUnit {
    IT,
    ACCOUNTING;

    public String getName() {
        return toString().toLowerCase();
    }

}
