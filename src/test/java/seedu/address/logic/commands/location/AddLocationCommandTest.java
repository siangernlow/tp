package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;
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
        ModelStub modelStub = new AddLocationCommandTest.ModelStubWithLocation(validLocation);

        assertThrows(CommandException.class, AddLocationCommand.MESSAGE_DUPLICATE_LOCATION, () ->
                addLocationCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateLocationId_throwsCommandException() {
        Location validLocation = new LocationBuilder().build();
        AddLocationCommand addLocationCommand = new AddLocationCommand(validLocation);
        Location validLocationSameId =
                new LocationBuilder().withId(LocationBuilder.DEFAULT_ID).withName("another name")
                        .withAddress("another address").build();
        ModelStub modelStub = new AddLocationCommandTest.ModelStubWithLocation(validLocationSameId);

        assertThrows(CommandException.class, AddLocationCommand.MESSAGE_DUPLICATE_LOCATION_ID, () ->
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
     * A Model stub that contains a single location.
     */
    private class ModelStubWithLocation extends ModelStub {
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

        @Override
        public boolean hasLocationId(Id id) {
            requireNonNull(id);
            return location.getId().equals(id);
        }
    }

    /**
     * A Model stub that always accept the location being added.
     */
    private class ModelStubAcceptingLocationAdded extends ModelStub {
        final ArrayList<Location> locationsAdded = new ArrayList<>();

        @Override
        public boolean hasLocation(Location location) {
            requireNonNull(location);
            return locationsAdded.stream().anyMatch(location::isSameLocation);
        }

        @Override
        public boolean hasLocationId(Id id) {
            requireNonNull(id);
            return locationsAdded.stream().anyMatch(l -> l.getId().equals(id));
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
