package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_QUARANTINE_STATUS = new Prefix("q/");
    public static final Prefix PREFIX_INFECTION_STATUS = new Prefix("i/");
    public static final Prefix PREFIX_LIST = new Prefix("l/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_PERSON_ID = new Prefix("idp/");
    public static final Prefix PREFIX_LOCATION_ID = new Prefix("idl/");
}
