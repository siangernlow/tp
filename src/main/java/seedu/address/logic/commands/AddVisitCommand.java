package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Model;
import seedu.address.model.visit.Visit;


/**
 * Adds the new visit to the visit list by visit details (personId, locationId and date)
 */

public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addVisit";


    public static final String MESSAGE_SUCCESS = "New visit added: %1$s";
    public static final String MESSAGE_DUPLICATE_VISIT = "This visit already exists in the address book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new visit to the visits list "
            + "by the personId of visit, locationId of visit and date of visit "
            + "Existing visits list will be updated.\n"
            + "Parameters: PersonId (must be a positive integer) ,"
            + "LocationId (must be a positive integer),  "
            + "Date (must be in the format of yyyy-MM-dd "
            + "Example: " + COMMAND_WORD + " 1 " + " 2 " + "2020-09-09";

    private final Visit toAdd;

    public AddVisitCommand(Visit visit) {
        requireAllNonNull(visit);

        toAdd = visit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVisit(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }

        model.addVisit(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddVisitCommand // instanceof handles nulls
                && toAdd.equals(((AddVisitCommand) other).toAdd));
    }
}
