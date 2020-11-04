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
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Adds the new visit to the visit list by visit details (personId, locationId and date)
 */

public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addVisit";

    public static final String MESSAGE_SUCCESS = "New visit added: %1$s";
    public static final String MESSAGE_DUPLICATE_VISIT = "This visit already exists in the VirusTracker.";
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
    public static final String MESSAGE_FUTURE_VISIT = "This visit has a date from the future."
            + "Please give a date that is today or before today.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new visit to the visits list "
            + "using a person, location and date of visit. Dates must not be from the future.\n"
            + "You may identify a person and location by index or Id. Mixing of the index and Id is not allowed.\n"
            + "Indexes are based on the most recently viewed persons and locations list.\n"
            + "Parameters: (PERSON_INDEX LOCATION_INDEX) or ("
            + PREFIX_PERSON_ID + "PERSON_ID "
            + PREFIX_LOCATION_ID + "LOCATION_ID) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + " 2 " + PREFIX_DATE + "2020-05-31 \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON_ID + "S123A "
            + PREFIX_LOCATION_ID + "L123A "
            + PREFIX_DATE + "2020-05-31 ";

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

        if (date.isAfter(LocalDate.now())) {
            throw new CommandException(MESSAGE_FUTURE_VISIT);
        }

        Visit visit = null;
        if (personId.isPresent() && locationId.isPresent()) {
            visit = getVisitToAdd(personId.get(), locationId.get(), model);
        } else if (personIndex.isPresent() && locationIndex.isPresent()) {
            visit = getVisitToAdd(personIndex.get(), locationIndex.get(), model);
        }

        if (model.hasVisit(visit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }
        requireNonNull(visit);

        model.addVisit(visit);
        String successMessage = getIllegalVisitWarning(visit);
        return new CommandResult(String.format(successMessage, visit));
    }

    private Visit getVisitToAdd(Id personId, Id locationId, Model model) throws CommandException {
        Person person;
        Location location;

        if (model.hasPersonId(personId)) {
            person = model.getPersonById(personId);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_ID);
        }

        if (model.hasLocationId(locationId)) {
            location = model.getLocationById(locationId);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_ID);
        }

        return new Visit(person, location, date);
    }

    private Visit getVisitToAdd(Index personIndex, Index locationIndex, Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getSortedPersonList();
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDEX);
        }
        List<Location> lastShownLocationList = model.getSortedLocationList();
        if (locationIndex.getZeroBased() >= lastShownLocationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_INDEX);
        }
        Person person = model.getPersonFromIndex(personIndex);
        Location location = model.getLocationFromIndex(locationIndex);
        return new Visit(person, location, date);
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
        boolean isPersonInfected = person.isInfected();
        boolean isPersonQuarantined = person.isQuarantined();

        if (person.getAddress().equals(location.getAddress())) { // Person stayed home
            return MESSAGE_NO_WARNING;
        }

        LocalDate stayHomeDate;
        LocalDate visitDate = visit.getDate();

        if (isPersonInfected && isPersonQuarantined) { // Person is infected and in quarantine
            LocalDate infectionDate = person.getInfectionStatus().getInfectionDate().get();
            LocalDate quarantineDate = person.getQuarantineStatus().getQuarantineDate().get();
            stayHomeDate = infectionDate.isBefore(quarantineDate) ? infectionDate : quarantineDate;

            if (stayHomeDate.isBefore(visitDate)) {
                return MESSAGE_INFECTED_AND_QUARANTINED_MADE_VISIT;
            }
        } else if (isPersonInfected) { // Person is infected only
            stayHomeDate = person.getInfectionStatus().getInfectionDate().get();

            if (stayHomeDate.isBefore(visitDate)) {
                return MESSAGE_INFECTED_MADE_VISIT;
            }
        } else if (isPersonQuarantined) { // Person is in quarantine only
            stayHomeDate = person.getQuarantineStatus().getQuarantineDate().get();

            if (stayHomeDate.isBefore(visitDate)) {
                return MESSAGE_QUARANTINED_MADE_VISIT;
            }
        }

        // Person is not infected or in quarantine
        return MESSAGE_NO_WARNING;
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
