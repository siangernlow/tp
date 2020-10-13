package seedu.address.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Contains predicates used by the model to filter
 * the relevant lists.
 */
public class ModelPredicate {
    /** {@code Predicate} that always evaluate to true */
    // Code duplications in the three lines below; future refactoring should take note of this.
    public static final Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    public static final Predicate<Location> PREDICATE_SHOW_ALL_LOCATIONS = unused -> true;
    public static final Predicate<Visit> PREDICATE_SHOW_ALL_VISITS = unused -> true;

    /** {@code Predicate} to handle person list */
    public static final Predicate<Person> PREDICATE_SHOW_ALL_INFECTED =
        person -> person.getInfectionStatus().getStatusAsBoolean();
    public static final Predicate<Person> PREDICATE_SHOW_ALL_QUARANTINED =
        person -> person.getQuarantineStatus().getStatusAsBoolean();

    /** {@code Predicate} for filtering out the infected visits from all visits */
    public static Predicate<Visit> getPredicateForInfectedVisits(HashSet<Index> infectedIds) {
        return visit -> infectedIds.contains(visit.getPersonId());
    }

    /** {@code Predicate} for filtering high risk locations */
    public static Predicate<Location> getPredicateForHighRiskLocations(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        ObservableList<Person> allInfectedPersons = model.getFilteredPersonList();

        HashSet<Index> infectedPersonIds = InfoHandler.getIdHashSetFromPersonsList(allInfectedPersons);

        model.updateFilteredVisitList(getPredicateForInfectedVisits(infectedPersonIds));
        ObservableList<Visit> allInfectedVisits = model.getFilteredVisitList();

        ArrayList<Index> infectedLocationIds = InfoHandler.getLocationIdsFromInfectedVisitList(allInfectedVisits);

        model.updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
        int numberOfTotalLocations = model.getFilteredLocationList().size();

        int numberOfHighRiskLocations = InfoHandler.getNumberOfHighRiskLocations(
                infectedLocationIds.size(), numberOfTotalLocations);

        ArrayList<Index> highRiskLocationIds =
                new ArrayList<>(infectedLocationIds.subList(0, numberOfHighRiskLocations));

        return location -> highRiskLocationIds.contains(location.getId());
    }

    /** {@code Predicate} to generate a predicate for whether a person's Id is included in the list of person Ids  */
    public static Predicate<Person> getPredicateShowPeopleById(List<Index> personIds) {
        return person -> {
            boolean isIncluded = person.getId().equals(personIds.get(0));
            for (int i = 1; i < personIds.size(); i++) {
                isIncluded = isIncluded || person.getId().equals(personIds.get(i));
            }
            return isIncluded;
        };
    }

    /** {@code Predicate} to generate predicate for whether a location's Id is included in the list of location Ids */
    public static Predicate<Location> getPredicateShowLocationsById(List<Index> locationIds) {
        return location -> {
            boolean isIncluded = location.getId().equals(locationIds.get(0));
            for (int i = 1; i < locationIds.size(); i++) {
                isIncluded = isIncluded || location.getId().equals(locationIds.get(i));
            }
            return isIncluded;
        };
    }
}
