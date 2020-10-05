package seedu.address.model.location;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * Represents a Location in the location book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Location {

    // Used to create unique identifiers for Locations by counting the number created
    private static int locationCount = 1;

    // Identity fields
    private final Name name;

    // Data fields
    private final Address address;
    private final Index id; // Id is not used when checking duplicates

    /**
     * Every field must be present and not null. The Id is obtained from the class itself.
     */
    public Location(Name name, Address address) {
        requireAllNonNull(name, address);
        this.name = name;
        this.address = address;
        this.id = Index.fromOneBased(locationCount);
        locationCount += 1;
    }

    /**
     * This constructor is used when creating copies of Locations due to edits or initialization.
     */
    public Location(Name name, Address address, Index id) {
        requireAllNonNull(name, address, id);

        // This update to location count is needed during initialization.
        if (id.getOneBased() >= locationCount) {
            locationCount = id.getOneBased() + 1;
        }
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

    public Index getId() {
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
