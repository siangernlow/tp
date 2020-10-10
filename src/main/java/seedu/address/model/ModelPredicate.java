package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

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

    /** {@code Predicate} to generate predicate for whether a location's id is included in the list of location Ids */
    public static Predicate<Location> getPredicateShowLocationsByPerson(List<Integer> locationIds) {
        return location -> {
            boolean isIncluded = location.getId().getZeroBased() == locationIds.get(0);
            for (int i = 1; i < locationIds.size(); i++) {
                isIncluded = isIncluded || location.getId().getZeroBased() == locationIds.get(i);
            }
            return isIncluded;
        };
    }
}
