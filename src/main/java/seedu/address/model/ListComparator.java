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
    // Since Person and Location classes are comparable by themselves, we can use their natural order.
    public static final Comparator<Person> SORT_ASCENDING_PERSON_NAME = Comparator.naturalOrder();
    public static final Comparator<Location> SORT_ASCENDING_LOCATION_NAME = Comparator.naturalOrder();
    public static final Comparator<Visit> SORT_VISITS = Comparator.comparing(Visit::getDate).reversed()
                                                            .thenComparing(Visit::getPerson)
                                                            .thenComparing(Visit::getLocation);
}
