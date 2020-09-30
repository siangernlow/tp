package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.visit.Visit;

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
