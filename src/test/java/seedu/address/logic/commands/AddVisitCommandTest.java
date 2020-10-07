package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLocationBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.VisitBuilder;

public class AddVisitCommandTest {
    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null));
    }

    @Test
    public void execute_visitAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVisitAdded modelStub =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        CommandResult commandResult = new AddVisitCommand(validVisit).execute(modelStub);

        assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVisit), modelStub.visitsAdded);
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Visit validVisit = new VisitBuilder().build();
        AddVisitCommand addvisitCommand = new AddVisitCommand(validVisit);
        AddVisitCommandTest.ModelStub modelStub = new AddVisitCommandTest.ModelStubWithVisit(validVisit);

        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Visit sampleA = new VisitBuilder().withPersonId("1").withLocationId("1").withDate("2020-09-09").build();
        Visit sampleB = new VisitBuilder().withPersonId("1").withLocationId("2").withDate("2020-09-09").build();
        AddVisitCommand addSampleACommand = new AddVisitCommand(sampleA);
        AddVisitCommand addSampleBCommand = new AddVisitCommand(sampleB);

        // same object -> returns true
        assertTrue(addSampleACommand.equals(addSampleACommand));

        // same values -> returns true
        AddVisitCommand addSampleACommandCopy = new AddVisitCommand(sampleB);
        assertTrue(addSampleBCommand.equals(addSampleACommandCopy));

        // different types -> returns false
        assertFalse(addSampleBCommand.equals(1));

        // null -> returns false
        assertFalse(addSampleACommand.equals(null));

        // different location -> returns false
        assertFalse(addSampleACommand.equals(addSampleBCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
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

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameIdPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameIdentityExceptId(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public boolean hasLocation(Location location) {
            return false;
        }

        @Override
        public Path getLocationBookFilePath() {
            return null;
        }

        @Override
        public void setLocationBookFilePath(Path locationBookFilePath) {

        }

        @Override
        public void setLocationBook(ReadOnlyLocationBook locationBook) {

        }

        @Override
        public ReadOnlyLocationBook getLocationBook() {
            return null;
        }

        @Override
        public void addLocation(Location location) {

        }

        @Override
        public ObservableList<Location> getFilteredLocationList() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredLocationList(Predicate<Location> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getVisitBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVisitBookFilePath(Path visitBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVisit(Visit visit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVisit(Visit visit) {

        }

        @Override
        public void setVisitBook(ReadOnlyVisitBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Visit> getFilteredVisitList() {
            return null;
        }

        @Override
        public void updateFilteredVisitList(Predicate<Visit> predicate) {

        }

        @Override
        public boolean hasVisit(Visit visit) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single visit.
     */
    private class ModelStubWithVisit extends AddVisitCommandTest.ModelStub {
        private final Visit visit;

        ModelStubWithVisit(Visit visit) {
            requireNonNull(visit);
            this.visit = visit;
        }

        @Override
        public boolean hasVisit(Visit visit) {
            requireNonNull(visit);
            return this.visit.equals(visit);
        }
    }

    /**
     * A Model stub that always accept the visit being added.
     */
    private class ModelStubAcceptingVisitAdded extends AddVisitCommandTest.ModelStub {
        final ArrayList<Visit> visitsAdded = new ArrayList<>();

        @Override
        public boolean hasVisit(Visit visit) {
            requireNonNull(visit);
            return visitsAdded.stream().anyMatch(visit::equals);
        }

        @Override
        public void addVisit(Visit visit) {
            requireNonNull(visit);
            visitsAdded.add(visit);
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }
    }

}
