package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.location.Location;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;

/**
 * The API of the Model component.
 */
public interface Model {

    //=========== Settings ==========================================================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== Person Book =======================================================================================

    /**
     * Returns the user prefs' person book file path.
     */
    Path getPersonBookFilePath();

    /**
     * Sets the user prefs' person book file path.
     */
    void setPersonBookFilePath(Path personBookFilePath);

    /** Returns the PersonBook */
    ReadOnlyPersonBook getPersonBook();

    /**
     * Replaces person book data with the data in {@code personBook}.
     */
    void setPersonBook(ReadOnlyPersonBook personBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the person book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same id as {@code person} exists in the person book.
     */
    boolean hasSameIdPerson(Person person);

    /**
     * Returns true if a person with the same identity except id as {@code person} exists in the person book.
     */
    boolean hasSameIdentityExceptId(Person person);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the person book.
     */
    void addPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the person book.
     */
    void deletePerson(Person target);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the person book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the person book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Location Book =====================================================================================

    /**
     * Returns the user prefs' location book file path.
     */
    Path getLocationBookFilePath();

    /**
     * Sets the user prefs' location book file path.
     */
    void setLocationBookFilePath(Path locationBookFilePath);

    /** Returns the LocationBook */
    ReadOnlyLocationBook getLocationBook();

    /**
     * Replaces location book data with the data in {@code locationBook}.
     */
    void setLocationBook(ReadOnlyLocationBook locationBook);

    /**
     * Returns true if a location with the same identity as {@code location} exists in the virus tracker.
     */
    boolean hasLocation(Location location);

    /**
     * Adds the given location.
     * {@code location} must not already exist in the location book.
     */
    void addLocation(Location location);

    /**
     * Deletes the given location.
     * The location must exist in the location book.
     */
    void deleteLocation(Location target);

    /**
     * Replaces the given location {@code target} with {@code editedLocation}.
     * {@code target} must exist in the location book.
     * The location identity of {@code editedPerson} must not be the same as another existing location in the
     * location book.
     */
    void setLocation(Location target, Location editedLocation);

    /** Returns an unmodifiable view of the filtered location list */
    ObservableList<Location> getFilteredLocationList();

    /**
     * Updates the filter of the filtered location list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLocationList(Predicate<Location> predicate);

    //=========== Visit Book ========================================================================================

    /**
     * Returns the user prefs' visit book file path.
     */
    Path getVisitBookFilePath();

    /**
     * Sets the user prefs' visit book file path.
     */
    void setVisitBookFilePath(Path visitBookFilePath);

    /** Returns the visitBook */
    ReadOnlyVisitBook getVisitBook();

    /**
     * Replaces visit book data with the data in {@code visitBook}.
     */
    void setVisitBook(ReadOnlyVisitBook visitBook);

    /**
     * Returns true if a visit with the same identity as {@code visit} exists in the visit book.
     */
    boolean hasVisit(Visit visit);

    /**
     * Adds the given visit.
     * {@code visit} must not already exist in the visit book.
     */
    void addVisit(Visit visit);

    /**
     * Deletes the given visit.
     * The visit must exist in the visit book.
     */
    void deleteVisit(Visit visit);

    /**
     * Deletes all visits that contain the person as given in the argument
     */
    void deleteVisitsWithPerson(Person personToDelete);

    /** Returns an unmodifiable view of the filtered visit list */
    ObservableList<Visit> getFilteredVisitList();

    /**
     * Updates the filter of the filtered visit list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVisitList(Predicate<Visit> predicate);

    //=========== Info Handler ======================================================================================
    /**
     * @return the {@code InfoHandler} associated with the model.
     */
    InfoHandler getInfoHandler();

    /**
     * Gets the given person using the index.
     * {@code index} must already exist in the person book.
     */
    Person getPersonFromIndex(Index index);

    /**
     * Gets the given location using the index.
     * {@code index} must already exist in the location book.
     */
    Location getLocationFromIndex(Index index);
}
