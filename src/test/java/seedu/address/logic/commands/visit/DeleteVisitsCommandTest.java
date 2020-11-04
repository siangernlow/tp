package seedu.address.logic.commands.visit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.visit.DeleteVisitsCommand.MESSAGE_DELETE_VISIT_FAILED;
import static seedu.address.logic.commands.visit.DeleteVisitsCommand.MESSAGE_DELETE_VISIT_SUCCESS;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.visit.Visit;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteVisitsCommand}.
 */
public class DeleteVisitsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void execute_validDateUnfilteredList_success() {
        StringBuilder expectedMessage = new StringBuilder(MESSAGE_DELETE_VISIT_SUCCESS);

        List<Visit> visits = model.getSortedVisitList();

        for (int i = visits.size() - 4; i < visits.size(); i++) {
            expectedMessage.append(i - 4).append(". ").append(visits.get(i)).append(" \n");
        }

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DeleteVisitsCommand deleteVisitsCommand =
                new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat));

        ModelManager expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());

        for (int i = visits.size() - 1; i > visits.size() - 5; i--) {
            expectedModel.deleteVisit(visits.get(i));
        }

        String expectedResult = expectedMessage.toString();
        CommandResult expectedCommandResult = new CommandResult(expectedResult);
        assertCommandSuccess(deleteVisitsCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidDateUnfilteredList_throwsCommandException() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DeleteVisitsCommand deleteVisitsCommand =
                new DeleteVisitsCommand(LocalDate.parse("2020-08-12", inputFormat));
        assertCommandFailure(deleteVisitsCommand, model, MESSAGE_DELETE_VISIT_FAILED);
    }

    @Test
    public void execute_validDateFilteredList_success() {
        StringBuilder expectedMessage = new StringBuilder(MESSAGE_DELETE_VISIT_SUCCESS);

        List<Visit> visits = model.getSortedVisitList();

        for (int i = visits.size() - 4; i < visits.size(); i++) {
            expectedMessage.append(i - 4).append(". ").append(visits.get(i)).append(" \n");
        }

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DeleteVisitsCommand deleteVisitsCommand =
                new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat));

        Model expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());

        for (int i = visits.size() - 1; i > visits.size() - 5; i--) {
            expectedModel.deleteVisit(visits.get(i));
        }

        String expectedResult = expectedMessage.toString();

        CommandResult expectedCommandResult = new CommandResult(expectedResult);
        assertCommandSuccess(deleteVisitsCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DeleteVisitsCommand deleteFirstVisitCommand =
                new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat));
        DeleteVisitsCommand deleteSecondVisitCommand =
                new DeleteVisitsCommand(LocalDate.parse("2020-09-13", inputFormat));

        // same object -> returns true
        assertTrue(deleteFirstVisitCommand.equals(deleteFirstVisitCommand));

        // same values -> returns true
        DeleteVisitsCommand deleteFirstVisitCommandCopy =
                new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat));
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
