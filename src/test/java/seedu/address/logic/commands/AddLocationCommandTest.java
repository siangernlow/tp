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
import seedu.address.model.LocationBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLocationBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.testutil.LocationBuilder;

public class AddLocationCommandTest {
    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLocationCommand(null));
    }

    @Test
    public void execute_locationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLocationAdded modelStub =
                new AddLocationCommandTest.ModelStubAcceptingLocationAdded();
        Location validLocation = new LocationBuilder().build();

        CommandResult commandResult = new AddLocationCommand(validLocation).execute(modelStub);

        assertEquals(String.format(AddLocationCommand.MESSAGE_SUCCESS, validLocation),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLocation), modelStub.locationsAdded);
    }

    @Test
    public void execute_duplicateLocation_throwsCommandException() {
        Location validLocation = new LocationBuilder().build();
        AddLocationCommand addLocationCommand = new AddLocationCommand(validLocation);
        AddLocationCommandTest.ModelStub modelStub = new AddLocationCommandTest.ModelStubWithLocation(validLocation);

        assertThrows(CommandException.class, AddLocationCommand.MESSAGE_DUPLICATE_LOCATION, () ->
                addLocationCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Location alice = new LocationBuilder().withName("Alice").build();
        Location bob = new LocationBuilder().withName("Bob").build();
        AddLocationCommand addAliceCommand = new AddLocationCommand(alice);
        AddLocationCommand addBobCommand = new AddLocationCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddLocationCommand addAliceCommandCopy = new AddLocationCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different location -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public ObservableList<Location> getFilteredLocationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getLocationBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLocationBookFilePath(Path locationBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLocation(Location location) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLocationBook(ReadOnlyLocationBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLocationBook getLocationBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLocation(Location location) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single location.
     */
    private class ModelStubWithLocation extends AddLocationCommandTest.ModelStub {
        private final Location location;

        ModelStubWithLocation(Location location) {
            requireNonNull(location);
            this.location = location;
        }

        @Override
        public boolean hasLocation(Location location) {
            requireNonNull(location);
            return this.location.isSameLocation(location);
        }
    }

    /**
     * A Model stub that always accept the location being added.
     */
    private class ModelStubAcceptingLocationAdded extends AddLocationCommandTest.ModelStub {
        final ArrayList<Location> locationsAdded = new ArrayList<>();

        @Override
        public boolean hasLocation(Location location) {
            requireNonNull(location);
            return locationsAdded.stream().anyMatch(location::isSameLocation);
        }

        @Override
        public void addLocation(Location location) {
            requireNonNull(location);
            locationsAdded.add(location);
        }

        @Override
        public ReadOnlyLocationBook getLocationBook() {
            return new LocationBook();
        }
    }

}
