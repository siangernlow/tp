package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import java.time.LocalDate;
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
import seedu.address.model.location.exceptions.LocationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.visit.Visit;

/**
 * Adds the new visit to the visit list by visit details (personId, locationId and date)
 */

public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addVisit";

    public static final String MESSAGE_SUCCESS = "New visit added: %1$s";
    public static final String MESSAGE_DUPLICATE_VISIT = "This visit already exists in the address book";
    public static final String MESSAGE_NO_WARNING = MESSAGE_SUCCESS;
    public static final String MESSAGE_INFECTED_MADE_VISIT = MESSAGE_SUCCESS + "\n"
            + "The following person is infected and "
            + "may have violated the Stay-Home Notice.";
    public static final String MESSAGE_QUARANTINED_MADE_VISIT = MESSAGE_SUCCESS + "\n"
            + "The following person is in quarantine and "
            + "may have violated the Stay-Home Notice.";
    public static final String MESSAGE_INFECTED_AND_QUARANTINED_MADE_VISIT = MESSAGE_SUCCESS + "\n"
            + "The following person is infected and "
            + "is in quarantine. The Stay-Home Notice may have been violated.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new visit to the visits list "
            + "using a person's index, location's index and date of visit.\n"
            + "Indexes are based on the most recently viewed persons and locations list.\n"
            + "Alternatively, a person's ID and a location's ID can be used. "
            + "However, a combination of ID and index is not allowed.\n"
            + "Updates the existing visits list.\n"
            + "Parameters: PERSON_INDEX LOCATION_INDEX d/DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + " 2 " + PREFIX_DATE + " 2020-05-31 \n"
            + "Parameters: "
            + PREFIX_PERSON_ID + "PERSON_INDEX "
            + PREFIX_LOCATION_ID + "LOCATION_INDEX d/DATE\n"
            + "Example: " + COMMAND_WORD + " idp/S11 " + " idl/L222 " + PREFIX_DATE + " 2020-05-31 ";

    private final Optional<Index> personIndex;
    private final Optional<Index> locationIndex;
    private final Optional<Id> personId;
    private final Optional<Id> locationId;
    private final LocalDate date;

    /**
     * Creates an AddVisitCommand to add the specified {@code Visit}
     */
    public AddVisitCommand(Index personIndex, Index locationIndex, LocalDate date) {
        requireAllNonNull(personIndex, locationIndex, date);
        this.personIndex = Optional.of(personIndex);
        this.locationIndex = Optional.of(locationIndex);
        personId = Optional.empty();
        locationId = Optional.empty();
        this.date = date;
    }

    /**
     * Creates an AddVisitCommand to add the specified {@code Visit}
     */
    public AddVisitCommand(Id personId, Id locationId, LocalDate date) {
        requireAllNonNull(personId, locationId, date);
        this.personId = Optional.of(personId);
        this.locationId = Optional.of(locationId);
        personIndex = Optional.empty();
        locationIndex = Optional.empty();
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Visit visit = null;
        if (personId.isPresent() && locationId.isPresent()) {
            visit = getVisitToAdd(personId.get(), locationId.get(), model);
        } else if (personIndex.isPresent() && locationIndex.isPresent()) {
            visit = getVisitToAdd(personIndex.get(), locationIndex.get(), model);
        }
        requireNonNull(visit);

        model.addVisit(visit);
        String successMessage = getIllegalVisitWarning(visit);
        return new CommandResult(String.format(successMessage, visit), false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);
    }

    private Visit getVisitToAdd(Id personId, Id locationId, Model model) throws CommandException {
        Person person;
        Location location;
        try {
            person = model.getPersonFromId(personId);
            location = model.getLocationFromId(locationId);
        } catch (PersonNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_ID);
        } catch (LocationNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_ID);
        }

        Visit visit = new Visit(person, location, date);

        assert visit.getPerson() != null;
        assert visit.getLocation() != null;
        assert visit.getDate() != null;

        if (model.hasVisit(visit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }
        return visit;
    }

    private Visit getVisitToAdd(Index personIndex, Index locationIndex, Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getSortedPersonList();
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        List<Location> lastShownLocationList = model.getSortedLocationList();
        if (locationIndex.getZeroBased() >= lastShownLocationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_DISPLAYED_INDEX);
        }
        Person person = model.getPersonFromIndex(personIndex);
        Location location = model.getLocationFromIndex(locationIndex);
        Visit visit = new Visit(person, location, date);
        if (model.hasVisit(visit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }
        return visit;
    }

    /**
     * Checks if the person made an illegal visit and returns the appropriate warning.
     * An illegal visit occurs when a {@code Person} visits an {@code Address} that is
     * not his own, and he is either infected, in quarantine or both.
     *
     * @param visit The visit to check
     * @return A warning string based on whether the visit is illegal or not.
     */
    private static String getIllegalVisitWarning(Visit visit) {
        Person person = visit.getPerson();
        Location location = visit.getLocation();
        boolean isPersonInfected = person.getInfectionStatus().getStatusAsBoolean();
        boolean isPersonQuarantined = person.getQuarantineStatus().getStatusAsBoolean();

        if (person.getAddress().equals(location.getAddress())) { // Person stayed home
            return MESSAGE_NO_WARNING;
        }

        if (isPersonInfected && isPersonQuarantined) { // Person is infected and in quarantine
            return MESSAGE_INFECTED_AND_QUARANTINED_MADE_VISIT;
        } else if (isPersonInfected) { // Person is infected only
            return MESSAGE_INFECTED_MADE_VISIT;
        } else if (isPersonQuarantined) { // Person is in quarantine only
            return MESSAGE_QUARANTINED_MADE_VISIT;
        } else { // Person is not infected or in quarantine
            return MESSAGE_NO_WARNING;
        }
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
                && personId.equals(e.personId)
                && locationId.equals(e.locationId)
                && date.equals(e.date);
    }
}
