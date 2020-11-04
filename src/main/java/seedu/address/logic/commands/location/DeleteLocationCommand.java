package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ReadOnlyIndexIdPair;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

/**
 * Deletes a location identified using it's displayed index from the location book.
 */
public class DeleteLocationCommand extends Command {
    public static final String COMMAND_WORD = "deleteLocation";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the location identified by Id or the index number used in the displayed location list.\n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_LOCATION_ID + "ID \n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOCATION_ID + "L123A ";

    public static final String MESSAGE_DELETE_LOCATION_SUCCESS = "Deleted Location: %1$s";

    private final ReadOnlyIndexIdPair pair;

    /**
     * @param pair contains the index or Id of the location to be deleted.
     */
    public DeleteLocationCommand(ReadOnlyIndexIdPair pair) {
        requireNonNull(pair);
        this.pair = pair;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Location locationToDelete = pair.getLocationFromPair(model);
        model.deleteLocation(locationToDelete);
        model.deleteVisitsWithLocation(locationToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLocationCommand // instanceof handles nulls
                && pair.equals(((DeleteLocationCommand) other).pair)); // state check
    }
}
