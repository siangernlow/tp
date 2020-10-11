package seedu.address.model.visit;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a visit book
 */
public interface ReadOnlyVisitBook {
    /**
     * Returns an unmodifiable view of the visits list.
     * This list will not contain any duplicate visits.
     */
    ObservableList<Visit> getVisitList();

}
