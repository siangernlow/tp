package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.LocationBook;
import seedu.address.model.Model;
import seedu.address.model.PersonBook;
import seedu.address.model.VisitBook;

/**
 * Clears the VirusTracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "VirusTracker has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new PersonBook());
        model.setLocationBook(new LocationBook());
        model.setVisitBook(new VisitBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
