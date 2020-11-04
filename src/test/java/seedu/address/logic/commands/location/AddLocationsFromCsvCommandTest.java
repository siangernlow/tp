package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_DUPLICATES_NOT_ADDED;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.location.AddLocationsFromCsvCommand.LOCATIONS;
import static seedu.address.logic.commands.location.AddLocationsFromCsvCommand.MESSAGE_EMPTY_LIST;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.testutil.LocationBuilder;
import seedu.address.testutil.TypicalLocations;


public class AddLocationsFromCsvCommandTest {

    @Test
    public void constructor_nullLocations_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLocationsFromCsvCommand(null));
    }

    @Test
    public void execute_locationsAcceptedByModel_addSuccessful() {
        ModelStubAcceptingLocationsAdded expectedModel =
                new ModelStubAcceptingLocationsAdded();

        ModelStubAcceptingLocationsAdded actualModel =
                new ModelStubAcceptingLocationsAdded();

        List<Location> locationsToAdd = TypicalLocations.getTypicalLocations();
        for (Location location : locationsToAdd) {
            expectedModel.addLocation(location);
        }

        AddLocationsFromCsvCommand actualCommand = new AddLocationsFromCsvCommand(locationsToAdd);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, locationsToAdd.size(), LOCATIONS));
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }

    @Test
    public void execute_emptyLocationsList_throwsCommandException() {
        ModelStubAcceptingLocationsAdded model =
                new ModelStubAcceptingLocationsAdded();

        List<Location> locationsToAdd = new ArrayList<>();

        AddLocationsFromCsvCommand actualCommand = new AddLocationsFromCsvCommand(locationsToAdd);
        assertThrows(CommandException.class, MESSAGE_EMPTY_LIST, ()
            -> actualCommand.execute(model));
    }

    @Test
    public void execute_duplicateLocations_successWithWarning() {
        ModelStubAcceptingLocationsAdded expectedModel =
                new ModelStubAcceptingLocationsAdded();

        ModelStubAcceptingLocationsAdded actualModel =
                new ModelStubAcceptingLocationsAdded();

        List<Location> locationsToAdd = TypicalLocations.getTypicalLocations();
        for (Location location : locationsToAdd) {
            expectedModel.addLocation(location);
        }

        int numOfUniqueAdditions = locationsToAdd.size();

        // Duplicate locations
        locationsToAdd.add(ALICE_LOCATION);
        locationsToAdd.add(BENSON_LOCATION);

        AddLocationsFromCsvCommand actualCommand = new AddLocationsFromCsvCommand(locationsToAdd);

        String linesWithDuplicates = String.format("%d %d ", locationsToAdd.size() - 1, locationsToAdd.size());
        String expectedMessage = String.format(MESSAGE_SUCCESS, numOfUniqueAdditions, LOCATIONS)
                + String.format(MESSAGE_DUPLICATES_NOT_ADDED, LOCATIONS, linesWithDuplicates);
        CommandResult commandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }


    @Test
    public void equals() {
        Location house = new LocationBuilder().withName("house").build();
        Location school = new LocationBuilder().withName("school").build();

        List<Location> locationsAddHouse = new ArrayList<>();
        locationsAddHouse.add(house);
        List<Location> locationsAddHouseAndSchool = new ArrayList<>();
        locationsAddHouseAndSchool.add(house);
        locationsAddHouseAndSchool.add(school);

        AddLocationsFromCsvCommand addHouseCommand = new AddLocationsFromCsvCommand(locationsAddHouse);
        AddLocationsFromCsvCommand addHouseAndSchoolCommand =
                new AddLocationsFromCsvCommand(locationsAddHouseAndSchool);

        // same object -> returns true
        assertEquals(addHouseCommand, addHouseCommand);

        // same values -> returns true
        AddLocationsFromCsvCommand addHouseCommandCopy = new AddLocationsFromCsvCommand(locationsAddHouse);
        assertEquals(addHouseCommandCopy, addHouseCommand);

        // different types -> returns false
        assertNotEquals(addHouseCommand, 1);

        // null -> returns false
        assertNotEquals(addHouseCommand, null);

        // different location lists -> returns false
        assertNotEquals(addHouseAndSchoolCommand, addHouseCommand);
    }

    /**
     * A Model stub that always accepts the locations being added.
     */
    private static class ModelStubAcceptingLocationsAdded extends ModelStub {
        private final ArrayList<Location> locationsAdded = new ArrayList<>();

        public ArrayList<Location> getLocationsAdded() {
            return locationsAdded;
        }

        @Override
        public boolean hasLocation(Location location) {
            return locationsAdded.contains(location);
        }

        @Override
        public boolean hasLocationId(Id id) {
            return locationsAdded.stream().anyMatch(l -> l.getId().equals(id));
        }

        @Override
        public void addLocation(Location location) {
            requireNonNull(location);
            locationsAdded.add(location);
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelStubAcceptingLocationsAdded)) {
                return false;
            }

            ModelStubAcceptingLocationsAdded other =
                    (ModelStubAcceptingLocationsAdded) obj;
            return locationsAdded.equals(other.getLocationsAdded());
        }
    }
}
