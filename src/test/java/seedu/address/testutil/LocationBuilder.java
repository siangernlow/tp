package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Name;
import seedu.address.model.location.Location;

/**
 * A utility class to help with building Location objects.
 */
public class LocationBuilder {
    public static final String DEFAULT_NAME = "Vivocity";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ID = "1";

    private Name name;
    private Address address;
    private Index id;

    /**
     * Creates a {@code LocationBuilder} with the default details.
     */
    public LocationBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        id = Index.fromOneBased(Integer.parseInt(DEFAULT_ID));
    }

    /**
     * Initializes the LocationBuilder with the data of {@code locationToCopy}.
     */
    public LocationBuilder(Location locationToCopy) {
        name = locationToCopy.getName();
        address = locationToCopy.getAddress();
        id = locationToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Location} that we are building.
     */
    public LocationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Location} that we are building.
     */
    public LocationBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the Id of the {@code Location} that we are building.
     */
    public LocationBuilder withId(Index id) {
        this.id = id;
        return this;
    }

    public Location build() {
        return new Location(name, address, id);
    }
}
