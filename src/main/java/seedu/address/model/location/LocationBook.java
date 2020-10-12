package seedu.address.model.location;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the location-book level
 * Duplicates are not allowed (by .isSameLocation comparison)
 */
public class LocationBook implements ReadOnlyLocationBook {

    private final UniqueLocationList locations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        locations = new UniqueLocationList();
    }

    public LocationBook() {}

    /**
     * Creates a LocationBook using the Locations in the {@code toBeCopied}
     */
    public LocationBook(ReadOnlyLocationBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the location list with {@code locations}.
     * {@code locations} must not contain duplicate locations.
     */
    public void setLocations(List<Location> locations) {
        this.locations.setLocations(locations);
    }

    /**
     * Resets the existing data of this {@code LocationBook} with {@code newData}.
     */
    public void resetData(ReadOnlyLocationBook newData) {
        requireNonNull(newData);

        setLocations(newData.getLocationList());
    }

    //// location-level operations

    /**
     * Returns true if a location with the same identity as {@code location} exists in the location book.
     */
    public boolean hasLocation(Location location) {
        requireNonNull(location);
        return locations.contains(location);
    }

    /**
     * Adds a location to the location book.
     * The location must not already exist in the location book.
     */
    public void addLocation(Location l) {
        locations.add(l);
    }

    /**
     * Replaces the given location {@code target} in the list with {@code editedLocation}.
     * {@code target} must exist in the location book.
     * The location identity of {@code editedLocation} must not be the same as another existing
     * location in the location book.
     */
    public void setLocation(Location target, Location editedLocation) {
        requireNonNull(editedLocation);

        locations.setLocation(target, editedLocation);
    }

    /**
     * Removes {@code key} from this {@code LocationBook}.
     * {@code key} must exist in the location book.
     */
    public void removeLocation(Location key) {
        locations.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return locations.asUnmodifiableObservableList().size() + " locations";
        // TODO: refine later
    }

    @Override
    public ObservableList<Location> getLocationList() {
        return locations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationBook // instanceof handles nulls
                && locations.equals(((LocationBook) other).locations));
    }

    @Override
    public int hashCode() {
        return locations.hashCode();
    }
}
