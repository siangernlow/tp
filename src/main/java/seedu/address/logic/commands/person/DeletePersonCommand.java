package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ReadOnlyIndexIdPair;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeletePersonCommand extends Command {

    public static final String COMMAND_WORD = "deletePerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by Id or the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or "
            + PREFIX_PERSON_ID + "ID \n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_ID + "T123A ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final ReadOnlyIndexIdPair pair;

    /**
     * @param pair contains the index or Id of the person to be deleted.
     */
    public DeletePersonCommand(ReadOnlyIndexIdPair pair) {
        requireNonNull(pair);
        this.pair = pair;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = pair.getPersonFromPair(model);
        model.deletePerson(personToDelete);
        model.deleteVisitsWithPerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && pair.equals(((DeletePersonCommand) other).pair)); // state check
    }
}
