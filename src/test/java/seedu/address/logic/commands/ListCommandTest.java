package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_personsListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand("persons"), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_personsListIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand("persons"), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listPersonsCommand = new ListCommand("persons");

        // same object -> returns true
        assertTrue(listPersonsCommand.equals(listPersonsCommand));

        // same values -> returns true
        ListCommand listPersonsCommandCopy = new ListCommand("persons");
        assertTrue(listPersonsCommand.equals(listPersonsCommandCopy));

        // different values -> returns false
        ListCommand differentListCommand = new ListCommand("invalid");
        assertFalse(listPersonsCommand.equals(differentListCommand));

        // different types -> returns false
        assertFalse(listPersonsCommand.equals(1));

        // null -> returns false
        assertFalse(listPersonsCommand.equals(null));
    }
}
