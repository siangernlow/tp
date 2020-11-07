package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ListComparator.SORT_ASCENDING_LOCATION_NAME;
import static seedu.address.model.ListComparator.SORT_ASCENDING_PERSON_NAME;
import static seedu.address.model.ListComparator.SORT_VISITS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_LOCATIONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_VISITS;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;
import seedu.address.model.person.ReadOnlyPersonBook;
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
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Location> filteredLocations;
    private final FilteredList<Visit> filteredVisits;
    private final SortedList<Person> sortedPersons;
    private final SortedList<Location> sortedLocations;
    private final SortedList<Visit> sortedVisits;

    /**
     * Initializes a ModelManager with the given personBook, locationBook, visitBook and userPrefs.
     */
    public ModelManager(ReadOnlyPersonBook personBook, ReadOnlyLocationBook locationBook,
                        ReadOnlyVisitBook visitBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(personBook, locationBook, visitBook, userPrefs);

        logger.fine("Initializing with person book: " + personBook + " and location book: " + locationBook
                + " and visit book: " + visitBook + " and user prefs " + userPrefs);

        this.personBook = new PersonBook(personBook);
        this.locationBook = new LocationBook(locationBook);
        this.visitBook = new VisitBook(visitBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.personBook.getPersonList());
        filteredLocations = new FilteredList<>(this.locationBook.getLocationList());
        filteredVisits = new FilteredList<>(this.visitBook.getVisitList());

        sortedPersons = new SortedList<>(filteredPersons);
        sortedPersons.setComparator(SORT_ASCENDING_PERSON_NAME);

        sortedLocations = new SortedList<>(filteredLocations);
        sortedLocations.setComparator(SORT_ASCENDING_LOCATION_NAME);

        sortedVisits = new SortedList<>(filteredVisits);
        sortedVisits.setComparator(SORT_VISITS);
    }

    public ModelManager() {
        this(new PersonBook(), new LocationBook(), new VisitBook(), new UserPrefs());
    }

    //=========== Settings ========================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
        requireNonNull(personBook);
        this.personBook.resetData(personBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personBook.hasPerson(person);
    }

    @Override
    public boolean hasPersonId(Id id) {
        requireNonNull(id);
        return personBook.hasPersonId(id);
    }

    @Override
    public Person getPersonById(Id id) {
        return personBook.getPersonById(id);
    }

    @Override
    public Person getPersonFromIndex(Index index) {
        return getSortedPersonList().get(index.getZeroBased());
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
     * Returns an unmodifiable view of the list of {@code Person}.
     */
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public Optional<Predicate<? super Person>> getPersonPredicate() {
        return Optional.ofNullable(filteredPersons.getPredicate());
    }

    //=========== Location Book ===================================================================================

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
        requireNonNull(locationBook);
        this.locationBook.resetData(locationBook);
    }

    @Override
    public boolean hasLocation(Location location) {
        requireNonNull(location);
        return locationBook.hasLocation(location);
    }

    @Override
    public boolean hasLocationId(Id id) {
        requireNonNull(id);
        return locationBook.hasLocationId(id);
    }

    @Override
    public Location getLocationById(Id id) {
        return locationBook.getLocationById(id);
    }

    @Override
    public Location getLocationFromIndex(Index index) {
        return getSortedLocationList().get(index.getZeroBased());
    }

    @Override
    public void addLocation(Location location) {
        locationBook.addLocation(location);
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
     * Returns an unmodifiable view of the list of {@code Location}.
     */
    @Override
    public ObservableList<Location> getSortedLocationList() {
        return sortedLocations;
    }

    @Override
    public void updateFilteredLocationList(Predicate<? super Location> predicate) {
        requireNonNull(predicate);
        filteredLocations.setPredicate(predicate);
    }

    @Override
    public Optional<Predicate<? super Location>> getLocationPredicate() {
        return Optional.ofNullable(filteredLocations.getPredicate());
    }

    //=========== Visit Book ======================================================================================

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
    public void deleteVisitsWithPerson(Person personToDelete) {
        visitBook.deleteVisitsWithPerson(personToDelete);
    }

    @Override
    public void deleteVisitsWithLocation(Location locationToDelete) {
        visitBook.deleteVisitsWithLocation(locationToDelete);
    }

    /**
     * Replaces the given visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the visit book.
     * The identities of {@code editedVisit} must not be the same as another existing
     * visit in the visit book.
     */
    @Override
    public void setVisit(Visit target, Visit editedVisit) {
        requireNonNull(editedVisit);
        visitBook.setVisit(target, editedVisit);
    }

    @Override
    public void updateVisitBookWithEditedPerson(Person editedPerson) {
        visitBook.updateWithEditedPerson(editedPerson);
    }

    @Override
    public void updateVisitBookWithEditedLocation(Location editedLocation) {
        visitBook.updateWithEditedLocation(editedLocation);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Visit}.
     */
    @Override
    public ObservableList<Visit> getSortedVisitList() {
        return sortedVisits;
    }

    @Override
    public void updateFilteredVisitList(Predicate<? super Visit> predicate) {
        requireNonNull(predicate);
        filteredVisits.setPredicate(predicate);
    }

    @Override
    public Optional<Predicate<? super Visit>> getVisitPredicate() {
        return Optional.ofNullable(filteredVisits.getPredicate());
    }

    //=========== InfoHandler ====================================================================================

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
                && filteredPersons.equals(other.filteredPersons)
                && filteredLocations.equals(other.filteredLocations)
                && filteredVisits.equals(other.filteredVisits)
                && sortedPersons.equals(other.sortedPersons)
                && sortedLocations.equals(other.sortedLocations)
                && sortedVisits.equals(other.sortedVisits);
    }
}
