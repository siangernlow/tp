package seedu.address.model.location;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.Name;

/**
 * Represents a Location in the VirusTracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Location implements Comparable<Location> {

    // Unique Identifier
    private final Id id;

    // Identity fields
    private final Address address;

    // Data fields
    private final Name name;

    /**
     * Every field must be present and not null. Id must be unique.
     */
    public Location(Id id, Name name, Address address) {
        requireAllNonNull(id, name, address);

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

    public Id getId() {
        return id;
    }

    //===================== For String conversions ============================

    public String getIdAsString() {
        return id.toString();
    }

    public String getNameAsString() {
        return name.toString();
    }

    public String getAddressAsString() {
        return address.toString();
    }

    /**
     * Returns true if both locations have the same Id.
     */
    public boolean isSameId(Location otherLocation) {
        if (otherLocation == this) {
            return true;
        }
        return otherLocation != null
                && otherLocation.getId().equals(getId());
    }

    /**
     * Returns true if both locations have the same address.
     * This defines a weaker notion of equality between two locations.
     */
    public boolean isSameLocation(Location otherLocation) {
        if (otherLocation == this) {
            return true;
        }

        return otherLocation != null
                && otherLocation.getAddress().equals(getAddress());
    }

    /**
     * Returns true if both locations have the same identity and Id.
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
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

    @Override
    public int compareTo(Location location) {
        if (getName().compareTo(location.getName()) == 0) {
            return getId().compareTo(location.getId());
        }
        return getName().compareTo(location.getName());
    }
}
