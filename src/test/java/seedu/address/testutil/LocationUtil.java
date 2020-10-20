package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.location.AddLocationCommand;
import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
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
        sb.append(PREFIX_LOCATION_ID + location.getId().value + " ");
        sb.append(PREFIX_NAME + location.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + location.getAddress().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditLocationDescriptor}'s details.
     */
    public static String getEditLocationDescriptorDetails(EditLocationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }
}
