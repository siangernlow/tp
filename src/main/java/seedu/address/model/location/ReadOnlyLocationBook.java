package seedu.address.model.location;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a location book
 */
public interface ReadOnlyLocationBook {

    /**
     * Returns an unmodifiable view of the locations list.
     * This list will not contain any duplicate locations nor locations with duplicate Ids.
     */
    ObservableList<Location> getLocationList();

}
