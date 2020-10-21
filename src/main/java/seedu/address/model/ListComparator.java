package seedu.address.model;

import java.util.Comparator;

import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Contains comparators used by the model to sort
 * the relevant lists.
 */
public class ListComparator {
    public static final Comparator<Person> SORT_ASCENDING_PERSON_NAME = Comparator.comparing(Person::getName);
    public static final Comparator<Location> SORT_ASCENDING_LOCATION_NAME = Comparator.comparing(Location::getName);
    public static final Comparator<Visit> SORT_DESCENDING_VISIT_DATE = Comparator.comparing(Visit::getDate).reversed();
}
