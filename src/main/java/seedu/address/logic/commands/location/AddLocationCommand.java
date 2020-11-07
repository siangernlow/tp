package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

/**
 * Adds a location to the virus tracker.
 */
public class AddLocationCommand extends Command {

    public static final String COMMAND_WORD = "addLocation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a location to the virus tracker. "
            + "Location must have a unique identifier.\n"
            + "Parameters: "
            + PREFIX_LOCATION_ID + "ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOCATION_ID + "L234F "
            + PREFIX_NAME + "School of Computing "
            + PREFIX_ADDRESS + "NUS School of Computing COM1 13 Computing Dr, 117417 ";

    public static final String MESSAGE_SUCCESS = "New location added: %1$s";
    public static final String MESSAGE_DUPLICATE_LOCATION = "This location already exists in the virus tracker.";
    public static final String MESSAGE_DUPLICATE_LOCATION_ID =
            "A location with this Id already exists in the VirusTracker.";

    private final Location toAdd;

    /**
     * Creates an AddLocationCommand to add the specified {@code Location}
     */
    public AddLocationCommand(Location location) {
        requireNonNull(location);
        toAdd = location;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLocation(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOCATION);
        }

        if (model.hasLocationId(toAdd.getId())) {
            throw new CommandException(MESSAGE_DUPLICATE_LOCATION_ID);
        }

        model.addLocation(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLocationCommand // instanceof handles nulls
                && toAdd.equals(((AddLocationCommand) other).toAdd));
    }
}
