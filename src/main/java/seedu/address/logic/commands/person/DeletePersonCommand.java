package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeletePersonCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Alternatively, user may identify the person by ID.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: "
            + PREFIX_PERSON_ID + "ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_ID + "T123A ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Optional<Index> targetIndex;
    private final Optional<Id> targetId;

    /**
     * @param targetIndex index of the person to be deleted
     */
    public DeletePersonCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = Optional.of(targetIndex);
        this.targetId = Optional.empty();
    }

    /**
     * @param targetId id of the person to be deleted
     */
    public DeletePersonCommand(Id targetId) {
        requireNonNull(targetId);
        this.targetIndex = Optional.empty();
        this.targetId = Optional.of(targetId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetId.isPresent()) {
            return deletePerson(targetId.get(), model);
        } else if (targetIndex.isPresent()) {
            return deletePerson(targetIndex.get(), model);
        } else {
            assert false : "Delete command should have either non empty id or index.";
            return null;
        }
    }

    private CommandResult deletePerson(Id id, Model model) throws CommandException {
        List<Person> lastShownList = model.getUnfilteredPersonList();
        Optional<Person> personToDelete = Optional.empty();
        for (Person p : lastShownList) {
            if (p.getId().equals(id)) {
                personToDelete = Optional.of(p);
            }
        }
        if (personToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_ID);
        }
        model.deletePerson(personToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.get()), false, false,
                CommandResult.SWITCH_TO_VIEW_PEOPLE);
    }

    private CommandResult deletePerson(Index index, Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(index.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete), false, false,
                CommandResult.SWITCH_TO_VIEW_PEOPLE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePersonCommand) other).targetIndex)); // state check
    }
}
