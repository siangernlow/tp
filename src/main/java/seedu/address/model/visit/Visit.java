package seedu.address.model.visit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.model.location.Location;
import seedu.address.model.person.Person;


/**
 * Represents a Visit in the visit book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Visit {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Person person;
    private final Location location;
    private final LocalDate date;

    /**
     * Every field must be present and not null.
     */
    public Visit(Person person, Location location, LocalDate date) {
        requireAllNonNull(person, location, date);
        this.person = person;
        this.location = location;
        this.date = date;
    }

    //===================== For comparing =====================================

    public LocalDate getDate() {
        return date;
    }

    public Person getPerson() {
        return person;
    }

    public Location getLocation() {
        return location;
    }

    //===================== For String conversions ============================

    public String getPersonIdAsString() {
        return getPerson().getId().toString();
    }

    public String getLocationIdAsString() {
        return getLocation().getId().toString();
    }

    public String getDateAsString() {
        return getDate().format(dateTimeFormatter);
    }

    /**
     * Returns true if the date of the visit corresponds to the entered date
     */
    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    /**
     * Returns true if the person of the visit corresponds to the entered person
     */
    public boolean isSamePerson(Person person) {
        return this.person.equals(person);
    }

    /**
     * Returns true if the location of the visit corresponds to the entered location
     */
    public boolean isSameLocation(Location location) {
        return this.location.equals(location);
    }

    /**
     * Returns true if both visits have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Visit)) {
            return false;
        }

        Visit otherVisit = (Visit) other;
        return otherVisit.getPerson().equals(getPerson())
                && otherVisit.getLocation().equals(getLocation())
                && otherVisit.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(person, location, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Person: ")
                .append(getPerson().getName())
                .append(" Location: ")
                .append(getLocation().getName())
                .append(" Date: ")
                .append(getDate().format(DateTimeFormatter.ofPattern("MMM dd yyyy")));

        return builder.toString();
    }
}
