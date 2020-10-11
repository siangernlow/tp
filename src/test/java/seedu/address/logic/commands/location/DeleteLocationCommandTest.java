package seedu.address.logic.commands.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLocationAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.Location;

public class DeleteLocationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            new UserPrefs(), getTypicalVisitBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Location locationToDelete = model.getFilteredLocationList().get(INDEX_FIRST.getZeroBased());
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLocationCommand.MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                new UserPrefs(), model.getVisitBook());
        expectedModel.deleteLocation(locationToDelete);

        assertCommandSuccess(deleteLocationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLocationList().size() + 1);
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(outOfBoundIndex);

        assertCommandFailure(deleteLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLocationAtIndex(model, INDEX_FIRST);

        Location locationToDelete = model.getFilteredLocationList().get(INDEX_FIRST.getZeroBased());
        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLocationCommand.MESSAGE_DELETE_LOCATION_SUCCESS, locationToDelete);

        Model expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                new UserPrefs(), model.getVisitBook());
        expectedModel.deleteLocation(locationToDelete);
        showNoLocation(expectedModel);

        assertCommandSuccess(deleteLocationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showLocationAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of location book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLocationBook().getLocationList().size());

        DeleteLocationCommand deleteLocationCommand = new DeleteLocationCommand(outOfBoundIndex);

        assertCommandFailure(deleteLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLocationCommand deleteFirstCommand = new DeleteLocationCommand(INDEX_FIRST);
        DeleteLocationCommand deleteSecondCommand = new DeleteLocationCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLocationCommand deleteFirstCommandCopy = new DeleteLocationCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different location -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no location.
     */
    private void showNoLocation(Model model) {
        model.updateFilteredLocationList(p -> false);

        assertTrue(model.getFilteredLocationList().isEmpty());
    }
}
