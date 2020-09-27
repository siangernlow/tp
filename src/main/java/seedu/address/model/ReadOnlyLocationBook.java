package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.location.Location;

/**
 * Unmodifiable view of a location book
 */
public interface ReadOnlyLocationBook {
    /**
     * Returns an unmodifiable view of the locations list.
     * This list will not contain any duplicate locations.
     */
    ObservableList<Location> getLocationList();

}
