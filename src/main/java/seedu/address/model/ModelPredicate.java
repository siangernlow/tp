package seedu.address.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Contains predicates used by the model to filter the relevant lists.
 */
public class ModelPredicate {

    public static final String INVALID_HIGH_RISK_LOCATIONS_NUMBER = "Number for high risk locations should not be "
            + "larger than the number of infected locations. There are only %d infected locations";

    /** {@code Predicate} that always evaluate to true */
    // Code duplications in the three lines below; future refactoring should take note of this.
    public static final Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    public static final Predicate<Location> PREDICATE_SHOW_ALL_LOCATIONS = unused -> true;
    public static final Predicate<Visit> PREDICATE_SHOW_ALL_VISITS = unused -> true;

    /** {@code Predicate} to handle person list */
    public static final Predicate<Person> PREDICATE_SHOW_ALL_INFECTED = Person::isInfected;
    public static final Predicate<Person> PREDICATE_SHOW_ALL_QUARANTINED = Person::isQuarantined;

    /** {@code Predicate} for filtering out the infected visits from all visits */
    public static Predicate<Visit> getPredicateForInfectedVisits(HashSet<Id> infectedIds) {
        return visit -> infectedIds.contains(visit.getPerson().getId());
    }

    /**
     * {@code Predicate} for filtering high risk locations
     * If user does not specify the number of high risk locations, then -1 is passed in
     * for {@code highRiskLocationNumber} as argument.
     */
    public static Predicate<Location> getPredicateForHighRiskLocations(Model model, boolean userSpecified,
                                                                       int highRiskLocationNumber)
            throws CommandException {
        Optional<Predicate<? super Person>> lastUsedPersonPredicate = model.getPersonPredicate();
        Optional<Predicate<? super Visit>> lastUsedVisitPredicate = model.getVisitPredicate();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        ObservableList<Person> allInfectedPersons = model.getSortedPersonList();

        HashSet<Id> infectedPersonIds = InfoHandler.getIdHashSetFromPersonsList(allInfectedPersons);

        model.updateFilteredVisitList(getPredicateForInfectedVisits(infectedPersonIds));
        ObservableList<Visit> allInfectedVisits = model.getSortedVisitList();

        ArrayList<Id> infectedLocationIds = InfoHandler.getLocationIdsFromInfectedVisitList(allInfectedVisits);

        if (highRiskLocationNumber > infectedLocationIds.size()) {
            // Restore back the filterPersonList predicate and filterVisitList predicate before throwing exception
            model.updateFilteredPersonList(lastUsedPersonPredicate.orElse(PREDICATE_SHOW_ALL_PERSONS));
            model.updateFilteredVisitList(lastUsedVisitPredicate.orElse(PREDICATE_SHOW_ALL_VISITS));
            throw new CommandException(String.format(INVALID_HIGH_RISK_LOCATIONS_NUMBER, infectedLocationIds.size()));
        }

        model.updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
        int numberOfTotalLocations = model.getSortedLocationList().size();
        int numberOfHighRiskLocations = userSpecified ? highRiskLocationNumber
            : InfoHandler.getNumberOfHighRiskLocations(infectedLocationIds.size(), numberOfTotalLocations);

        ArrayList<Id> highRiskLocationIds =
                new ArrayList<>(infectedLocationIds.subList(0, numberOfHighRiskLocations));

        // Restore back the filterPersonList predicate and filterVisitList predicate
        model.updateFilteredPersonList(lastUsedPersonPredicate.orElse(PREDICATE_SHOW_ALL_PERSONS));
        model.updateFilteredVisitList(lastUsedVisitPredicate.orElse(PREDICATE_SHOW_ALL_VISITS));

        return location -> highRiskLocationIds.contains(location.getId());
    }

    /** {@code Predicate} to generate a predicate for whether a person's Id is included in the list of person Ids  */
    public static Predicate<Person> getPredicateShowPeopleById(List<Id> personIds) {
        return person -> {
            boolean isIncluded = person.getId().equals(personIds.get(0));
            for (int i = 1; i < personIds.size(); i++) {
                isIncluded = isIncluded || person.getId().equals(personIds.get(i));
            }
            return isIncluded;
        };
    }

    /** {@code Predicate} to generate predicate for whether a location's Id is included in the list of location Ids */
    public static Predicate<Location> getPredicateShowLocationsById(List<Id> locationIds) {
        return location -> {
            boolean isIncluded = location.getId().equals(locationIds.get(0));
            for (int i = 1; i < locationIds.size(); i++) {
                isIncluded = isIncluded || location.getId().equals(locationIds.get(i));
            }
            return isIncluded;
        };
    }
}
