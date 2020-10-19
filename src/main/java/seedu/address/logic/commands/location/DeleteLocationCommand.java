package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;

/**
 * Deletes a location identified using it's displayed index from the location book.
 */
public class DeleteLocationCommand extends Command {
    public static final String COMMAND_WORD = "deleteLocation";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the location identified by the index number used in the displayed location list.\n"
            + "Alternatively, user may identify the location by ID.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: "
            + PREFIX_LOCATION_ID + "ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOCATION_ID + "L123A ";

    public static final String MESSAGE_DELETE_LOCATION_SUCCESS = "Deleted Location: %1$s";

    private final Optional<Index> targetIndex;
    private final Optional<Id> targetId;

    /**
     * @param targetIndex index of the location to be deleted
     */
    public DeleteLocationCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = Optional.of(targetIndex);
        this.targetId = Optional.empty();
    }

    /**
     * @param targetId id of the location to be deleted
     */
    public DeleteLocationCommand(Id targetId) {
        requireNonNull(targetId);
        this.targetIndex = Optional.empty();
        this.targetId = Optional.of(targetId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetId.isPresent()) {
            return deleteLocation(targetId.get(), model);
        } else if (targetIndex.isPresent()) {
            return deleteLocation(targetIndex.get(), model);
        } else {
            assert false : "Delete location command should have either non empty id or index.";
            return null;
        }
    }

    private CommandResult deleteLocation(Id id, Model model) throws CommandException {
        List<Location> lastShownList = model.getUnfilteredLocationList();
        Optional<Location> locationToDelete = Optional.empty();
        for (Location p : lastShownList) {
            if (p.getId().equals(id)) {
                locationToDelete = Optional.of(p);
            }
        }
        if (locationToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_ID);
        }
        model.deleteLocation(locationToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete.get()), false, false,
                CommandResult.SWITCH_TO_VIEW_PEOPLE);
    }

    private CommandResult deleteLocation(Index index, Model model) throws CommandException {
        List<Location> lastShownList = model.getFilteredLocationList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_DISPLAYED_INDEX);
        }

        Location locationToDelete = lastShownList.get(index.getZeroBased());
        model.deleteLocation(locationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete), false, false,
                CommandResult.SWITCH_TO_VIEW_PEOPLE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLocationCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteLocationCommand) other).targetIndex)); // state check
    }
}
