package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GenerateLocationsCommand.MESSAGE_PERSON_HAS_NO_VISITS;
import static seedu.address.logic.commands.GenerateLocationsCommand.MESSAGE_PERSON_IS_NOT_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.Location;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class GenerateLocationsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        GenerateLocationsCommand firstGenerateLocationsCommand = new GenerateLocationsCommand(firstIndex);
        GenerateLocationsCommand secondGenerateLocationsCommand = new GenerateLocationsCommand(secondIndex);

        // same object -> returns true
        assertTrue(firstGenerateLocationsCommand.equals(firstGenerateLocationsCommand));

        // same values -> returns true
        GenerateLocationsCommand copyOfGenerateLocationsCommand = new GenerateLocationsCommand(firstIndex);
        assertTrue(firstGenerateLocationsCommand.equals(copyOfGenerateLocationsCommand));

        // different types -> returns false
        assertFalse(firstGenerateLocationsCommand.equals(1));

        // null -> returns false
        assertFalse(firstGenerateLocationsCommand.equals(null));

        // different person -> returns false
        assertFalse(firstGenerateLocationsCommand.equals(secondGenerateLocationsCommand));
    }

    @Test
    public void execute_indexOutOfBounds_throwCommandException() {
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        Index index = Index.fromOneBased(100);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_personAtIndexNotInfected_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_IS_NOT_INFECTED;
        Index index = Index.fromOneBased(1);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_noVisitsFound_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        model.deleteVisit(model.getVisitBook().getVisitList().get(6));
        Index index = Index.fromOneBased(5);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_validInputFromViewingAllPeople_success() {
        String expectedMessage = "Generated locations for: Daniel Meier";
        Model expectedModelForGenerate = expectedModel;
        Predicate<Location> locationPredicate = location -> location.getId().getOneBased() == 6;
        expectedModelForGenerate.updateFilteredLocationList(locationPredicate);
        Index index = Index.fromOneBased(4);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_LOCATIONS);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_validInputFromViewingAllInfected_success() {
        CommandResult expectedCommand = new CommandResult("Generated locations for: Benson Meier",
                false, false, CommandResult.SWITCH_TO_VIEW_LOCATIONS);
        Model modelForAllInfected = model;
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);

        Model expectedModelForGenerate = expectedModel;
        Predicate<Location> locationPredicate = location -> location.getId().getOneBased() == 6;
        expectedModelForGenerate.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        expectedModelForGenerate.updateFilteredLocationList(locationPredicate);

        Index index = Index.fromOneBased(1);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        assertCommandSuccess(command, modelForAllInfected, expectedCommand, expectedModelForGenerate);
    }

    @Test
    public void execute_invalidInputFromViewingAllQuarantined_throwCommandException() {
        Model modelForAllQuarantined = model;
        modelForAllQuarantined.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        Index index = Index.fromOneBased(1);
        GenerateLocationsCommand command = new GenerateLocationsCommand(index);
        assertThrows(CommandException.class, () -> command.execute(modelForAllQuarantined));
    }
}
