package seedu.address.model.visit;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;


/**
 * Wraps all data at the visit-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class VisitBook implements ReadOnlyVisitBook {

    private final UniqueVisitList visits;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        visits = new UniqueVisitList();
    }

    public VisitBook() {}

    /**
     * Creates a VisitBook using the Visits in the {@code toBeCopied}
     */
    public VisitBook(ReadOnlyVisitBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the visit list with {@code visits}.
     * {@code visits} must not contain duplicate visits.
     */
    public void setVisits(List<Visit> visits) {
        this.visits.setVisits(visits);
    }

    /**
     * Resets the existing data of this {@code VisitBook} with {@code newData}.
     */
    public void resetData(ReadOnlyVisitBook newData) {
        requireNonNull(newData);

        setVisits(newData.getVisitList());
    }

    /**
     * Update the visit book with edited location
     */
    public void updateWithEditedLocation(Location editedLocation) {
        requireNonNull(editedLocation);

        visits.updateWithEditedLocation(editedLocation);
    }

    //// visit-level operations

    /**
     * Returns true if a visit with the same identity as {@code visit} exists in the visit book.
     */
    public boolean hasVisit(Visit visit) {
        requireNonNull(visit);
        return visits.contains(visit);
    }

    /**
     * Adds a Visit to the visit book.
     * The visit must not already exist in the visit book.
     */
    public void addVisit(Visit l) {
        visits.add(l);
    }

    /**
     * Replaces the given visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the visit book.
     * The identities of {@code editedVisit} must not be the same as another existing
     * visit in the visit book.
     */
    public void setVisit(Visit target, Visit editedVisit) {
        requireNonNull(editedVisit);

        visits.setVisit(target, editedVisit);
    }

    /**
     * Update the visit book with edited person
     */
    public void updateWithEditedPerson(Person editedPerson) {
        requireNonNull(editedPerson);

        visits.updateWithEditedPerson(editedPerson);
    }

    /**
     * Removes {@code key} from this {@code VisitBook}.
     * {@code key} must exist in the visit book.
     */
    public void removeVisit(Visit key) {
        visits.remove(key);
    }

    /**
     * Removes all visits that contain the person as given in the argument
     */
    public void deleteVisitsWithPerson(Person personToDelete) {
        visits.removeVisitsWithPerson(personToDelete);
    }

    /**
     * Removes all visits that contains the location as given in the argument.
     */
    public void deleteVisitsWithLocation(Location locationToDelete) {
        visits.removeVisitsWithLocation(locationToDelete);
    }

    //// util methods

    @Override
    public String toString() {
        return visits.asUnmodifiableObservableList().size() + " visits";
        // TODO: refine later
    }

    @Override
    public ObservableList<Visit> getVisitList() {
        return visits.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitBook // instanceof handles nulls
                && visits.equals(((VisitBook) other).visits));
    }

    @Override
    public int hashCode() {
        return visits.hashCode();
    }
}
