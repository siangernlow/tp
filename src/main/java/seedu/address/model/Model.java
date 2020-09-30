package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ALL_INFECTED = person -> person.getInfectionStatus().getStatusAsBoolean();
    Predicate<Person> PREDICATE_SHOW_ALL_QUARANTINED = person -> person.getQuarantineStatus().getStatusAsBoolean();

    /** {@code Predicate} that always evaluate to true */
    Predicate<Visit> PREDICATE_SHOW_ALL_VISITS = unused -> true;

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

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a location with the same identity as {@code location} exists in the virus tracker.
     */
    boolean hasLocation(Location location);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getLocationBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setLocationBookFilePath(Path locationBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setLocationBook(ReadOnlyLocationBook locationBook);

    /** Returns the AddressBook */
    ReadOnlyLocationBook getLocationBook();

    /**
     * Adds the given location.
     * {@code location} must not already exist in the address book.
     */
    void addLocation(Location location);

    /**
     * Returns true if a visit with the same identity as {@code visit} exists in the address book.
     */
    boolean hasVisit(Visit visit);

    /**
     * Deletes the given visit.
     * The visit must exist in the visit book.
     */
    void deleteVisit(Visit visit);

    /**
     * Adds the given visit.
     * {@code visit} must not already exist in the visit book.
     */
    void addVisit(Visit visit);

    /**
     * Returns the user prefs' visit book file path.
     */
    Path getVisitBookFilePath();

    /**
     * Sets the user prefs' visit book file path.
     */
    void setVisitBookFilePath(Path visitBookFilePath);

    /**
     * Replaces visit book data with the data in {@code visitBook}.
     */
    void setVisitBook(ReadOnlyVisitBook visitBook);

    /** Returns the visitBook */
    ReadOnlyVisitBook getVisitBook();

    /** Returns an unmodifiable view of the filtered visit list */
    ObservableList<Visit> getFilteredVisitList();

    /**
     * Updates the filter of the filtered visit list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVisitList(Predicate<Visit> predicate);

}
