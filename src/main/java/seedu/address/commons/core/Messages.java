package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is out of range!";
    public static final String MESSAGE_INVALID_LOCATION_INDEX = "The location index provided is out of range!";
    public static final String MESSAGE_INVALID_VISIT_INDEX = "The visit index provided is out of range!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PERSON_ID = "The person ID provided cannot be found!";
    public static final String MESSAGE_INVALID_LOCATION_ID = "The location ID provided cannot be found!";
    public static final String MESSAGE_INVALID_FILE_PATH = "Invalid file path. Please enter the "
            + "absolute path of the file.";
    public static final String MESSAGE_MISSING_DATA_FORMAT = "Line %d of the CSV file does not "
            + "specify enough parameters "
            + "for the specified data type. Please check the CSV file again.";
    public static final String MESSAGE_PREAMBLE_SHOULD_BE_EMPTY = "The preamble should be empty. Please check if "
            + "you have added prefixes to all required fields.";
    public static final String MESSAGE_INVALID_DATA_TYPE = "That data type is not supported by this command."
            + "\n%1$s";
}
