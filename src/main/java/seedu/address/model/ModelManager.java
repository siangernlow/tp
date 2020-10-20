package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_LOCATIONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_VISITS;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.location.exceptions.LocationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;

/**
 * Represents the in-memory model of the VirusTracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PersonBook personBook;
    private final LocationBook locationBook;
    private final VisitBook visitBook;
    private final UserPrefs userPrefs;
    private final InfoHandler infoHandler;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Location> filteredLocations;
    private final FilteredList<Visit> filteredVisits;

    /**
     * Initializes a ModelManager with the given personBook, locationBook, visitBook and userPrefs.
     */
    public ModelManager(ReadOnlyPersonBook personBook, ReadOnlyLocationBook locationBook,
                        ReadOnlyVisitBook visitBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(personBook, locationBook, visitBook, userPrefs);

        logger.fine("Initializing with person book: " + personBook + " and user prefs " + userPrefs
                + " and location book: " + locationBook + " and visit book: " + visitBook);

        this.personBook = new PersonBook(personBook);
        this.locationBook = new LocationBook(locationBook);
        this.visitBook = new VisitBook(visitBook);
        this.infoHandler = new InfoHandler(this);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.personBook.getPersonList());
        filteredLocations = new FilteredList<>(this.locationBook.getLocationList());
        filteredVisits = new FilteredList<>(this.visitBook.getVisitList());
    }

    public ModelManager() {
        this(new PersonBook(), new LocationBook(), new VisitBook(), new UserPrefs());
    }

    //=========== Settings ========================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== Person Book =====================================================================================

    @Override
    public Path getPersonBookFilePath() {
        return userPrefs.getPersonBookFilePath();
    }

    @Override
    public void setPersonBookFilePath(Path personBookFilePath) {
        requireNonNull(personBookFilePath);
        userPrefs.setPersonBookFilePath(personBookFilePath);
    }

    @Override
    public ReadOnlyPersonBook getPersonBook() {
        return personBook;
    }

    @Override
    public void setPersonBook(ReadOnlyPersonBook personBook) {
        this.personBook.resetData(personBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personBook.hasPerson(person);
    }

    @Override
    public boolean hasSameIdPerson(Person person) {
        requireNonNull(person);
        return personBook.hasSameIdPerson(person);
    }

    @Override
    public boolean hasSameIdentityExceptId(Person person) {
        requireNonNull(person);
        return personBook.hasSameIdentityExceptId(person);
    }

    @Override
    public void addPerson(Person person) {
        personBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deletePerson(Person target) {
        personBook.removePerson(target);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        personBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Person> getUnfilteredPersonList() {
        return personBook.getPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== LocationBook ====================================================================================

    @Override
    public Path getLocationBookFilePath() {
        return userPrefs.getLocationBookFilePath();
    }

    @Override
    public void setLocationBookFilePath(Path locationBookFilePath) {
        requireNonNull(locationBookFilePath);
        userPrefs.setLocationBookFilePath(locationBookFilePath);
    }

    @Override
    public ReadOnlyLocationBook getLocationBook() {
        return locationBook;
    }

    @Override
    public void setLocationBook(ReadOnlyLocationBook locationBook) {
        this.locationBook.resetData(locationBook);
    }

    @Override
    public boolean hasLocation(Location location) {
        requireNonNull(location);
        return locationBook.hasLocation(location);
    }

    @Override
    public void addLocation(Location location) {
        locationBook.addLocation(location);
        // needs to be updated to persons when doing list command
        updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
    }

    @Override
    public void deleteLocation(Location target) {
        locationBook.removeLocation(target);
    }

    @Override
    public void setLocation(Location target, Location editedLocation) {
        requireAllNonNull(target, editedLocation);
        locationBook.setLocation(target, editedLocation);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Visit} backed by the internal list of
     * {@code versionedVisitBook}
     */
    @Override
    public ObservableList<Location> getFilteredLocationList() {
        return filteredLocations;
    }

    @Override
    public ObservableList<Location> getUnfilteredLocationList() {
        return locationBook.getLocationList();
    }

    @Override
    public void updateFilteredLocationList(Predicate<Location> predicate) {
        requireNonNull(predicate);
        filteredLocations.setPredicate(predicate);
    }

    //=========== VisitBook =======================================================================================

    @Override
    public Path getVisitBookFilePath() {
        return userPrefs.getVisitBookFilePath();
    }

    @Override
    public void setVisitBookFilePath(Path visitBookFilePath) {
        requireNonNull(visitBookFilePath);
        userPrefs.setVisitBookFilePath(visitBookFilePath);
    }

    @Override
    public ReadOnlyVisitBook getVisitBook() {
        return visitBook;
    }

    @Override
    public void setVisitBook(ReadOnlyVisitBook visitBook) {
        this.visitBook.resetData(visitBook);
    }

    @Override
    public void updateVisitBookWithEditedLocation(Location editedLocation) {
        this.visitBook.updateWithEditedLocation(editedLocation);
    }

    @Override
    public boolean hasVisit(Visit visit) {
        requireNonNull(visit);
        return visitBook.hasVisit(visit);
    }

    @Override
    public void addVisit(Visit visit) {
        visitBook.addVisit(visit);
        updateFilteredVisitList(PREDICATE_SHOW_ALL_VISITS); // needs to be updated to persons when doing list command
    }

    @Override
    public void deleteVisit(Visit visit) {
        requireNonNull(visit);
        visitBook.removeVisit(visit);
    }

    @Override
    public void deleteVisitsWithLocation(Location locationToDelete) {
        visitBook.deleteVisitsWithLocation(locationToDelete);
    }

    public void setVisit(Visit target, Visit editedVisit) {
        requireAllNonNull(target, editedVisit);
        visitBook.setVisit(target, editedVisit);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Visit} backed by the internal list of
     * {@code versionedVisitBook}
     */
    @Override
    public ObservableList<Visit> getFilteredVisitList() {
        return filteredVisits;
    }

    @Override
    public void updateFilteredVisitList(Predicate<Visit> predicate) {
        requireNonNull(predicate);
        filteredVisits.setPredicate(predicate);
    }

    //=========== InfoHandler ====================================================================================
    @Override
    public InfoHandler getInfoHandler() {
        return infoHandler;
    }

    @Override
    public Person getPersonFromId(Id id) {
        for (Person p : getUnfilteredPersonList()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new PersonNotFoundException();
    }

    @Override
    public Location getLocationFromId(Id id) {
        for (Location l : getUnfilteredLocationList()) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        throw new LocationNotFoundException();
    }

    @Override
    public Person getPersonFromIndex(Index index) {
        return getFilteredPersonList().get(index.getZeroBased());
    }

    @Override
    public Location getLocationFromIndex(Index index) {
        return getFilteredLocationList().get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return personBook.equals(other.personBook)
                && locationBook.equals(other.locationBook)
                && visitBook.equals(other.visitBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }
}
