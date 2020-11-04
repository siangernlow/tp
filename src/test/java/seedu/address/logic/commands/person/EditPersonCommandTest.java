package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.LocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditPersonCommand.
 */
public class EditPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(new IndexIdPairStub(INDEX_FIRST, null), descriptor);

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        expectedModel.setPerson(model.getSortedPersonList().get(0), editedPerson);
        expectedModel.updateVisitBookWithEditedPerson(editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(editPersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getSortedPersonList().size());
        Person lastPerson = model.getSortedPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(
                new IndexIdPairStub(indexLastPerson, null), descriptor);

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);
        expectedModel.updateVisitBookWithEditedPerson(editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editPersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPersonCommand editPersonCommand = new EditPersonCommand(
                new IndexIdPairStub(INDEX_FIRST, null), new EditPersonDescriptor());
        Person editedPerson = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        expectedModel.updateVisitBookWithEditedPerson(editedPerson);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editPersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personInFilteredList = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(new IndexIdPairStub(INDEX_FIRST, null),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setPerson(model.getSortedPersonList().get(0), editedPerson);
        expectedModel.updateVisitBookWithEditedPerson(editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editPersonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getSortedPersonList().get(INDEX_FIRST.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(
                new IndexIdPairStub(INDEX_SECOND, null), descriptor);

        assertCommandFailure(editPersonCommand, model, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getPersonBook().getPersonList().get(INDEX_SECOND.getZeroBased());
        EditPersonCommand editPersonCommand = new EditPersonCommand(new IndexIdPairStub(INDEX_FIRST, null),
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editPersonCommand, model, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(
                new IndexIdPairStub(outOfBoundIndex, null), descriptor);

        assertCommandFailure(editPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPersonBook().getPersonList().size());

        EditPersonCommand editPersonCommand = new EditPersonCommand(new IndexIdPairStub(outOfBoundIndex, null),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void equals() {
        final EditPersonCommand standardCommand = new EditPersonCommand(
                new IndexIdPairStub(INDEX_FIRST, null), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditPersonCommand commandWithSameValues = new EditPersonCommand(
                new IndexIdPairStub(INDEX_FIRST, null), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(new IndexIdPairStub(INDEX_SECOND, null), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(new IndexIdPairStub(INDEX_FIRST, null), DESC_BOB)));
    }

}
