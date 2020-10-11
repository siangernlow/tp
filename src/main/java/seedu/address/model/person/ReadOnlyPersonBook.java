package seedu.address.model.person;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a person book
 */
public interface ReadOnlyPersonBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
