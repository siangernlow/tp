package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.ID_NOT_IN_TYPICAL_PERSON;
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
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePersonCommand}.
 */
public class DeletePersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(new IndexIdPairStub(INDEX_FIRST, null));

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.deleteVisitsWithPerson(personToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deletePersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedPersonList().size() + 1);
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(new IndexIdPairStub(outOfBoundIndex, null));

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(new IndexIdPairStub(INDEX_FIRST, null));

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.deleteVisitsWithPerson(personToDelete);
        showNoPerson(expectedModel);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deletePersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPersonBook().getPersonList().size());

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(new IndexIdPairStub(outOfBoundIndex, null));

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_validId_success() {
        Person personToDelete = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(
                new IndexIdPairStub(null, personToDelete.getId()));

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.deleteVisitsWithPerson(personToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deletePersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(
                new IndexIdPair(null, ID_NOT_IN_TYPICAL_PERSON, PREFIX_PERSON_ID));

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_ID);
    }

    @Test
    public void equals() {
        DeletePersonCommand deleteFirstIndexCommand = new DeletePersonCommand(new IndexIdPairStub(INDEX_FIRST, null));
        DeletePersonCommand deleteSecondIndexCommand = new DeletePersonCommand(new IndexIdPairStub(INDEX_SECOND, null));

        // same object -> returns true
        assertTrue(deleteFirstIndexCommand.equals(deleteFirstIndexCommand));

        // same values -> returns true
        DeletePersonCommand deleteFirstIndexCommandCopy = new DeletePersonCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
        assertTrue(deleteFirstIndexCommand.equals(deleteFirstIndexCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstIndexCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstIndexCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstIndexCommand.equals(deleteSecondIndexCommand));

        DeletePersonCommand deleteFirstIdCommand = new DeletePersonCommand(
                new IndexIdPairStub(null, new Id("S1234")));
        DeletePersonCommand deleteSecondIdCommand = new DeletePersonCommand(
                new IndexIdPairStub(null, new Id("S2345")));

        // same object -> returns true
        assertTrue(deleteFirstIdCommand.equals(deleteFirstIdCommand));

        // same values -> returns true
        DeletePersonCommand deleteFirstIdCommandCopy = new DeletePersonCommand(
                new IndexIdPairStub(null, new Id("S1234")));
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
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getSortedPersonList().isEmpty());
    }
}
