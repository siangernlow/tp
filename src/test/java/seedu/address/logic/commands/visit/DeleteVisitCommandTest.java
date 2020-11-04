package seedu.address.logic.commands.visit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_TENTH;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.visit.Visit;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteVisitCommand}.
 */
public class DeleteVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Visit visitToDelete = model.getSortedVisitList().get(INDEX_FIRST.getZeroBased());
        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteVisitCommand.MESSAGE_DELETE_VISIT_SUCCESS, visitToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());

        expectedModel.deleteVisit(visitToDelete);

        assertCommandSuccess(deleteVisitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedVisitList().size() + 1);
        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(outOfBoundIndex);

        assertCommandFailure(deleteVisitCommand, model, Messages.MESSAGE_INVALID_VISIT_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Visit visitToDelete = model.getSortedVisitList().get(INDEX_FIRST.getZeroBased());
        DeleteVisitCommand deleteVisitCommand = new DeleteVisitCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteVisitCommand.MESSAGE_DELETE_VISIT_SUCCESS, visitToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());

        expectedModel.deleteVisit(visitToDelete);

        assertCommandSuccess(deleteVisitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteVisitCommand deleteFirstVisitCommand = new DeleteVisitCommand(INDEX_FIRST);
        DeleteVisitCommand deleteSecondVisitCommand = new DeleteVisitCommand(INDEX_TENTH);

        // same object -> returns true
        assertTrue(deleteFirstVisitCommand.equals(deleteFirstVisitCommand));

        // same values -> returns true
        DeleteVisitCommand deleteFirstVisitCommandCopy = new DeleteVisitCommand(INDEX_FIRST);
        assertTrue(deleteFirstVisitCommand.equals(deleteFirstVisitCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstVisitCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstVisitCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstVisitCommand.equals(deleteSecondVisitCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoVisit(Model model) {
        model.updateFilteredVisitList(p -> false);

        assertTrue(model.getSortedVisitList().isEmpty());
    }
}
