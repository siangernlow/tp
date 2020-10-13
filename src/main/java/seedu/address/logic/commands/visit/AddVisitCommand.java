package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Adds the new visit to the visit list by visit details (personId, locationId and date)
 */

public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addVisit";


    public static final String MESSAGE_SUCCESS = "New visit added: %1$s";
    public static final String MESSAGE_DUPLICATE_VISIT = "This visit already exists in the visit book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new visit to the visit book "
            + "by the personId of visit, locationId of visit and date of visit "
            + "Existing visits list will be updated.\n"
            + "Parameters: PersonId (must be a positive integer within the range of person book) ,"
            + "LocationId (must be a positive integer within the range of location book),  "
            + "Date (must be in the format of yyyy-MM-dd "
            + "Example: " + COMMAND_WORD + " 1 " + " 2 " + PREFIX_DATE + " 2020-09-09 ";

    private final Index personIndex;
    private final Index locationIndex;
    private final LocalDate date;

    /**
     * Creates an AddVisitCommand to add the specified {@code Visit}
     */
    public AddVisitCommand(Index personIndex, Index locationIndex, LocalDate date) {
        requireAllNonNull(personIndex, locationIndex, date);
        this.personIndex = personIndex;
        this.locationIndex = locationIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.getPersonFromIndex(personIndex);
        Location location = model.getLocationFromIndex(locationIndex);
        Visit visit = new Visit(person, location, date);
        if (model.hasVisit(visit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }

        model.addVisit(visit);
        return new CommandResult(String.format(MESSAGE_SUCCESS, visit), false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);
    }

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
        return personIndex.equals(e.personIndex)
                && locationIndex.equals(e.locationIndex)
                && date.equals(e.date);
    }
}
