package seedu.address.model.location;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * Represents a Location in the location book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Location {

    // Identity fields
    private final Name name;

    // Data fields
    private final Address address;
    private final String id;

    /**
     * Every field must be present and not null. Id must be unique.
     */
    public Location(String id, Name name, Address address) {
        requireAllNonNull(name, address, id);

        this.name = name;
        this.address = address;
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns true if both locations have the same name.
     * This defines a weaker notion of equality between two locations.
     */
    public boolean isSameLocation(Location otherLocation) {
        if (otherLocation == this) {
            return true;
        }

        return otherLocation != null
                && otherLocation.getName().equals(getName());
    }

    /**
     * Returns true if both locations have the same id.
     */
    public boolean isSameId(Location otherLocation) {
        if (otherLocation == this) {
            return true;
        }
        return otherLocation != null
                && otherLocation.getId().equals(getId());
    }

    /**
     * Returns true if both locations have the same identity and data fields.
     * This defines a stronger notion of equality between two locations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return otherLocation.getName().equals(getName())
                && otherLocation.getAddress().equals(getAddress())
                && otherLocation.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, id);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }
}
