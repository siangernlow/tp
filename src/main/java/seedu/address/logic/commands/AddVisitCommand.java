package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Model;

import java.time.LocalDate;

/**
 * Adds the new visit to the visit list by visit details (personId, locationId and date)
 */

public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addVisit";

    public static final String MESSAGE_ARGUMENTS = "personId: %1$d, locationId: %2$d, date: %3$s";

    private final Index personId;
    private final Index locationId;
    private final LocalDate dateOfVisit;

    public AddVisitCommand(Index personId, Index locationId, String date) {
        requireAllNonNull(personId, locationId, date);

        this.personId = personId;
        this.locationId = locationId;
        this.dateOfVisit = date;
    }


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new visit to the visits list "
            + "by the personId of visit, locationId of visit and date of visit "
            + "Existing visits list will be updated.\n"
            + "Parameters: PersonId (must be a positive integer) ,"
            + "LocationId (must be a positive integer),  "
            + "Date (must be in the format of yyyy-MM-dd "
            + "Example: " + COMMAND_WORD + " 1 " + " 2 " + "2020-09-09";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddVisitCommand)) {
            return false;
        }

        // state check
        AddVisitCommand e = (AddVisitCommand) other;
        return personId.equals(e.personId)&&
                locationId.equals(e.locationId)
                && dateOfVisit.equals(e.dateOfVisit);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, personId.getOneBased()
                ,personId.getOneBased(), dateOfVisit));
    }
}
