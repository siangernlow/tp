package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

/**
 * Deletes a location identified using it's displayed index from the location book.
 */
public class DeleteLocationCommand extends Command {
    public static final String COMMAND_WORD = "deleteLocation";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the location identified by the index number used in the displayed location list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LOCATION_SUCCESS = "Deleted Location: %1$s";

    private final Index targetIndex;

    public DeleteLocationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Location> lastShownList = model.getFilteredLocationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_DISPLAYED_INDEX);
        }

        Location locationToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteLocation(locationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete),
                false, false, CommandResult.SWITCH_TO_VIEW_LOCATIONS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLocationCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteLocationCommand) other).targetIndex)); // state check
    }
}
