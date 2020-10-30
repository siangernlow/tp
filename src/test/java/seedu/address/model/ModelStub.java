package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Person Book ========================================================================================

    @Override
    public Path getPersonBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPersonBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyPersonBook getPersonBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPersonBook(ReadOnlyPersonBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPersonId(Id id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Predicate<? super Person>> getPersonPredicate() {
        throw new AssertionError("This method should not be called.");
    }
    //=========== Location Book ========================================================================================

    @Override
    public Path getLocationBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLocationBookFilePath(Path locationBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyLocationBook getLocationBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLocationBook(ReadOnlyLocationBook locationBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLocation(Location location) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLocationId(Id id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLocation(Location location) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteLocation(Location target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLocation(Location target, Location editedLocation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Location> getSortedLocationList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLocationList(Predicate<? super Location> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Predicate<? super Location>> getLocationPredicate() {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Visit Book ========================================================================================

    @Override
    public Path getVisitBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVisitBookFilePath(Path visitBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyVisitBook getVisitBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVisitBook(ReadOnlyVisitBook visitBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateVisitBookWithEditedPerson(Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateVisitBookWithEditedLocation(Location editedLocation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasVisit(Visit visit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addVisit(Visit visit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVisit(Visit visit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVisitsWithPerson(Person personToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVisitsWithLocation(Location locationToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVisit(Visit target, Visit editedVisit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Visit> getSortedVisitList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredVisitList(Predicate<? super Visit> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Predicate<? super Visit>> getVisitPredicate() {
        throw new AssertionError("This method should not be called.");
    }
    //=========== Info Handler ========================================================================================

    @Override
    public Person getPersonById(Id id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Location getLocationById(Id id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getPersonFromIndex(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Location getLocationFromIndex(Index index) {
        throw new AssertionError("This method should not be called.");
    }
}
