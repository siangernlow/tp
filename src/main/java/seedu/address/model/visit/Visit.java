package seedu.address.model.visit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.commons.core.index.Index;

public class Visit {

    private final Index personId;
    private final Index locationId;
    private final LocalDate date;
    /**
     * Every field must be present and not null.
     */
    public Visit(Index personId, Index locationId, LocalDate date) {
        requireAllNonNull(personId, locationId, date);
        this.personId = personId;
        this.locationId = locationId;
        this.date = date;
    }

    public Index getPersonId() {
        return personId;
    }

    //to be implemented
    public String getPersonName(Index personId) {
        return "personName";
    }

    public Index getLocationId() {
        return locationId;
    }

    //to be implemented
    public String getLocationName(Index locationId) {
        return "locationName";
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns true if the date of the visit corresponds to the entered date
     */
    public boolean isSameDate(LocalDate date) {
        return date == this.date;
    }

    /**
     * Returns true if the personId of the visit corresponds to the entered personId
     */
    public boolean isSamePersonId(Index personId) {
        return personId == this.personId;
    }

    /**
     * Returns true if the personId of the visit corresponds to the entered personId
     */
    public boolean isSameLocationId(Index personId) {
        return personId == this.personId;
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
        return otherVisit.getPersonId().equals(getPersonId())
                && otherVisit.getLocationId().equals(getLocationId())
                && otherVisit.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personId, locationId, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PersonId: ")
                .append(getPersonId())
                .append("PersonName: ")
                .append(getPersonName(personId))
                .append("LocationId: ")
                .append(getLocationId())
                .append("PersonName: ")
                .append(getLocationName(locationId))
                .append(" Date: ")
                .append(getDate().format(DateTimeFormatter.ofPattern("MMM dd yyyy")));

        return builder.toString();
    }
}
