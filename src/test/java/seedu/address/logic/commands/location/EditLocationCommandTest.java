package seedu.address.logic.commands.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NUS;
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
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.person.PersonBook;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.EditLocationDescriptorBuilder;
import seedu.address.testutil.LocationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditLocationCommand.
 */
class EditLocationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Location editedLocation = new LocationBuilder().build();
        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder(editedLocation).build();
        EditLocationCommand editLocationCommand = new EditLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null), descriptor);

        String expectedMessage = String.format(EditLocationCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());

        expectedModel.setLocation(model.getSortedLocationList().get(0), editedLocation);
        expectedModel.updateVisitBookWithEditedLocation(editedLocation);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLocation = Index.fromOneBased(model.getSortedLocationList().size());
        Location lastLocation = model.getSortedLocationList().get(indexLastLocation.getZeroBased());

        LocationBuilder locationInList = new LocationBuilder(lastLocation);
        Location editedLocation = locationInList.withName(VALID_NAME_NUS).build();

        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS).build();
        EditLocationCommand editLocationCommand = new EditLocationCommand(
                new IndexIdPairStub(indexLastLocation, null), descriptor);

        String expectedMessage = String.format(EditLocationCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        expectedModel.setLocation(lastLocation, editedLocation);
        expectedModel.updateVisitBookWithEditedLocation(editedLocation);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLocationCommand editLocationCommand = new EditLocationCommand(new IndexIdPairStub(INDEX_FIRST, null),
                new EditLocationCommand.EditLocationDescriptor());
        Location editedLocation = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditLocationCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        expectedModel.updateVisitBookWithEditedLocation(editedLocation);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLocationAtIndex(model, INDEX_FIRST);

        Location locationInFilteredList = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());
        Location editedLocation = new LocationBuilder(locationInFilteredList).withName(VALID_NAME_NUS).build();
        EditLocationCommand editLocationCommand = new EditLocationCommand(new IndexIdPairStub(INDEX_FIRST, null),
                new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS).build());

        String expectedMessage = String.format(EditLocationCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation);

        Model expectedModel = new ModelManager(new PersonBook(model.getPersonBook()),
                new LocationBook(model.getLocationBook()), new VisitBook(model.getVisitBook()), new UserPrefs());
        showLocationAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.setLocation(model.getSortedLocationList().get(0), editedLocation);
        expectedModel.updateVisitBookWithEditedLocation(editedLocation);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(editLocationCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateLocationUnfilteredList_failure() {
        Location firstLocation = model.getSortedLocationList().get(INDEX_FIRST.getZeroBased());
        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder(firstLocation).build();
        EditLocationCommand editLocationCommand = new EditLocationCommand(
                new IndexIdPairStub(INDEX_SECOND, null), descriptor);

        assertCommandFailure(editLocationCommand, model, EditLocationCommand.MESSAGE_DUPLICATE_LOCATION);
    }

    @Test
    public void execute_duplicateLocationFilteredList_failure() {
        showLocationAtIndex(model, INDEX_FIRST);

        // edit location in filtered list into a duplicate in address book
        Location locationInList = model.getLocationBook().getLocationList().get(INDEX_SECOND.getZeroBased());
        EditLocationCommand editLocationCommand = new EditLocationCommand(new IndexIdPairStub(INDEX_FIRST, null),
                new EditLocationDescriptorBuilder(locationInList).build());

        assertCommandFailure(editLocationCommand, model, EditLocationCommand.MESSAGE_DUPLICATE_LOCATION);
    }

    @Test
    public void execute_invalidLocationIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedLocationList().size() + 1);
        EditLocationCommand.EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder()
                .withName(VALID_NAME_NUS).build();
        EditLocationCommand editLocationCommand = new EditLocationCommand(
                new IndexIdPairStub(outOfBoundIndex, null), descriptor);

        assertCommandFailure(editLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLocationIndexFilteredList_failure() {
        showLocationAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of location book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLocationBook().getLocationList().size());

        EditLocationCommand editLocationCommand = new EditLocationCommand(new IndexIdPairStub(outOfBoundIndex, null),
                new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS).build());

        assertCommandFailure(editLocationCommand, model, Messages.MESSAGE_INVALID_LOCATION_INDEX);
    }

    @Test
    public void equals() {
        final EditLocationCommand standardCommand = new EditLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null), DESC_NUS);

        // same values -> returns true
        EditLocationDescriptor copyDescriptor = new EditLocationCommand.EditLocationDescriptor(DESC_NUS);
        EditLocationCommand commandWithSameValues = new EditLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditLocationCommand(
                new IndexIdPairStub(INDEX_SECOND, null), DESC_NUS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditLocationCommand(
                new IndexIdPairStub(INDEX_FIRST, null), DESC_VIVOCITY)));
    }
}
