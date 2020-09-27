package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddLocationCommand;
import seedu.address.model.location.Location;

/**
 * A utility class for Location.
 */
public class LocationUtil {
    /**
     * Returns an add location command string for adding the {@code location}.
     */
    public static String getAddLocationCommand(Location location) {
        return AddLocationCommand.COMMAND_WORD + " " + getLocationDetails(location);
    }

    /**
     * Returns the part of command string for the given {@code location}'s details.
     */
    public static String getLocationDetails(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + location.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + location.getAddress().value + " ");
        return sb.toString();
    }
}
