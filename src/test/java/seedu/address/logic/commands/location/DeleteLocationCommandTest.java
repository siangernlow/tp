package seedu.address.logic.commands.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLocationAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.ID_NOT_IN_TYPICAL_LOCATIONS;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;

public class DeleteLocationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Location locationToDelete = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(new IndexIdPairStub(INDEX_FIRST, null));

        String expectedMessage = String.format(DeleteLocationCommand.MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deleteLocation(locationToDelete);
        expectedModel.deleteVisitsWithLocation(locationToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deleteLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedLocationList().size() + 1);
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(
                new IndexIdPairStub(outOfBoundIndex, null));

        assertCommandFailure(deleteLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLocationAtIndex(model, INDEX_FIRST);

        Location locationToDelete = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(new IndexIdPairStub(INDEX_FIRST, null));

        String expectedMessage = String.format(DeleteLocationCommand.MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete);

        Model expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deleteLocation(locationToDelete);
        expectedModel.deleteVisitsWithLocation(locationToDelete);
        showNoLocation(expectedModel);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deleteLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showLocationAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of location book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLocationBook().getLocationList().size());

        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(
                new IndexIdPairStub(outOfBoundIndex, null));

        assertCommandFailure(deleteLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_INDEX);
    }

    @Test
    public void execute_validId_success() {
        Location locationToDelete = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(
                new IndexIdPairStub(null, locationToDelete.getId()));

        String expectedMessage = String.format(DeleteLocationCommand.MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deleteLocation(locationToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deleteLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(
                new IndexIdPair(null, ID_NOT_IN_TYPICAL_LOCATIONS, PREFIX_LOCATION_ID));
        assertCommandFailure(deleteLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_ID);
    }

    @Test
    public void equals() {
        DeleteLocationCommand deleteFirstIndexCommand = new DeleteLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
        DeleteLocationCommand deleteSecondIndexCommand = new DeleteLocationCommand(
                new IndexIdPairStub(INDEX_SECOND, null));

        // same object -> returns true
        assertTrue(deleteFirstIndexCommand.equals(deleteFirstIndexCommand));

        // same values -> returns true
        DeleteLocationCommand deleteFirstIndexCommandCopy = new DeleteLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
        assertTrue(deleteFirstIndexCommand.equals(deleteFirstIndexCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstIndexCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstIndexCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstIndexCommand.equals(deleteSecondIndexCommand));

        DeleteLocationCommand deleteFirstIdCommand = new DeleteLocationCommand(
                new IndexIdPairStub(null, new Id("L1234")));
        DeleteLocationCommand deleteSecondIdCommand = new DeleteLocationCommand(
                new IndexIdPairStub(null, new Id("L2345")));

        // same object -> returns true
        assertTrue(deleteFirstIdCommand.equals(deleteFirstIdCommand));

        // same values -> returns true
        DeleteLocationCommand deleteFirstIdCommandCopy = new DeleteLocationCommand(
                new IndexIdPairStub(null, new Id("L1234")));
        assertTrue(deleteFirstIdCommand.equals(deleteFirstIdCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstIdCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstIdCommand.equals(null));

        // different id -> returns false
        assertFalse(deleteFirstIdCommand.equals(deleteSecondIdCommand));

        // different identification -> returns false
        assertFalse(deleteFirstIdCommand.equals(deleteFirstIndexCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no location.
     */
    private void showNoLocation(Model model) {
        model.updateFilteredLocationList(p -> false);

        assertTrue(model.getSortedLocationList().isEmpty());
    }
}
