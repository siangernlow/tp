package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the VirusTracker.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addPerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the VirusTracker. "
            + "Person must have a unique identifier.\n"
            + "Parameters: "
            + PREFIX_PERSON_ID + "ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_QUARANTINE_STATUS + "QUARANTINE_STATUS "
            + PREFIX_INFECTION_STATUS + "INFECTION STATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_ID + "T123A "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_QUARANTINE_STATUS + "false "
            + PREFIX_INFECTION_STATUS + "2020-02-02 ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the VirusTracker.";
    public static final String MESSAGE_DUPLICATE_PERSON_ID =
            "A person with this Id already exists in the VirusTracker.";

    private final Person toAdd;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasPersonId(toAdd.getId())) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_ID);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPersonCommand // instanceof handles nulls
                && toAdd.equals(((AddPersonCommand) other).toAdd));
    }
}
