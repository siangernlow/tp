package seedu.address.testutil;

import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;

/**
 * A utility class to help with building Locationbook objects.
 * Example usage: <br>
 *     {@code LocationBook lb = new LocationBookBuilder().withName("Vivocity").build();}
 */
public class LocationBookBuilder {

    private LocationBook locationBook;

    public LocationBookBuilder() {
        locationBook = new LocationBook();
    }

    public LocationBookBuilder(LocationBook locationBook) {
        this.locationBook = locationBook;
    }

    /**
     * Adds a new {@code Location} to the {@code LocationBook} that we are building.
     */
    public LocationBookBuilder withLocation(Location location) {
        locationBook.addLocation(location);
        return this;
    }

    public LocationBook build() {
        return locationBook;
    }
}
