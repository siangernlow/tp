package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ReadOnlyIndexIdPair;
import seedu.address.model.Model;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.Name;
import seedu.address.model.location.Location;

/**
 * Edits the details of an existing location in the location book.
 */
public class EditLocationCommand extends Command {

    public static final String COMMAND_WORD = "editLocation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the location identified "
            + "by Id or the index number used in the displayed location list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_LOCATION_ID + "ID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOCATION_ID + "L123A "
            + PREFIX_NAME + "National University of Singapore "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Rd, Singapore 119077\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "National University of Singapore "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Rd, Singapore 119077";

    public static final String MESSAGE_EDIT_LOCATION_SUCCESS = "Edited Location: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LOCATION = "This location already exists in the location book.";

    private final ReadOnlyIndexIdPair pair;
    private final EditLocationDescriptor editLocationDescriptor;

    /**
     * @param pair contains the index or Id of the location in the VirusTracker to edit
     * @param editLocationDescriptor details to edit the location with
     */
    public EditLocationCommand(ReadOnlyIndexIdPair pair, EditLocationDescriptor editLocationDescriptor) {
        requireNonNull(pair);
        requireNonNull(editLocationDescriptor);

        this.pair = pair;
        this.editLocationDescriptor = new EditLocationDescriptor(editLocationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Location locationToEdit = pair.getLocationFromPair(model);
        Location editedLocation = createEditedLocation(locationToEdit, editLocationDescriptor);

        if (!locationToEdit.isSameLocation(editedLocation) && model.hasLocation(editedLocation)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOCATION);
        }

        model.setLocation(locationToEdit, editedLocation);
        model.updateVisitBookWithEditedLocation(editedLocation);

        return new CommandResult(String.format(MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation));
    }

    /**
     * Creates and returns a {@code Location} with the details of {@code locationToEdit}
     * edited with {@code editLocationDescriptor}.
     */
    private static Location createEditedLocation(Location locationToEdit,
                                                 EditLocationDescriptor editLocationDescriptor) {
        assert locationToEdit != null;

        Name updatedName = editLocationDescriptor.getName().orElse(locationToEdit.getName());
        Address updatedAddress = editLocationDescriptor.getAddress().orElse(locationToEdit.getAddress());
        Id updatedId = locationToEdit.getId();

        return new Location(updatedId, updatedName, updatedAddress);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLocationCommand)) {
            return false;
        }

        // state check
        EditLocationCommand e = (EditLocationCommand) other;
        return pair.equals(e.pair)
                && editLocationDescriptor.equals(e.editLocationDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditLocationDescriptor {
        private Name name;
        private Address address;

        public EditLocationDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditLocationDescriptor(EditLocationDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLocationDescriptor)) {
                return false;
            }

            // state check
            EditLocationDescriptor e = (EditLocationDescriptor) other;
            return getName().equals(e.getName())
                    && getAddress().equals(e.getAddress());
        }
    }
}
