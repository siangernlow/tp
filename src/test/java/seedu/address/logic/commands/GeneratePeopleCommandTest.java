package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_NO_PEOPLE_FOUND;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_PERSON_HAS_NO_VISITS;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_PERSON_IS_NOT_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.testutil.TypicalLocations.DANIEL_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class GeneratePeopleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            getTypicalVisitBook(), new UserPrefs());

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        GeneratePeopleCommand firstGeneratePeopleCommand = new GeneratePeopleCommand(firstIndex);
        GeneratePeopleCommand secondGeneratePeopleCommand = new GeneratePeopleCommand(secondIndex);

        // same object -> returns true
        assertTrue(firstGeneratePeopleCommand.equals(firstGeneratePeopleCommand));

        // same values -> returns true
        GeneratePeopleCommand copyOfGeneratePeopleCommand = new GeneratePeopleCommand(firstIndex);
        assertTrue(firstGeneratePeopleCommand.equals(copyOfGeneratePeopleCommand));

        // different types -> returns false
        assertFalse(firstGeneratePeopleCommand.equals(1));

        // null -> returns false
        assertFalse(firstGeneratePeopleCommand.equals(null));

        // different person -> returns false
        assertFalse(firstGeneratePeopleCommand.equals(secondGeneratePeopleCommand));
    }

    @Test
    public void execute_indexOutOfBounds_throwCommandException() {
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        Index index = Index.fromOneBased(100);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
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
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
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
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_noPeopleFound_throwCommandException() {
        String expectedMessage = MESSAGE_NO_PEOPLE_FOUND;
        Index index = Index.fromOneBased(5);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_validInputFromViewingAllPeople_success() {
        String expectedMessage = "Generated people for: Daniel Meier";
        Visit testVisit = new Visit(new PersonBuilder().withId(Index.fromOneBased(8)).build(),
                DANIEL_LOCATION, DEFAULT_DATE);
        model.addVisit(testVisit);
        Model expectedModelForGenerate = expectedModel;
        expectedModelForGenerate.addVisit(testVisit);
        Predicate<Person> personPredicate = person -> person.getId().getOneBased() == 8;
        expectedModelForGenerate.updateFilteredPersonList(personPredicate);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_PEOPLE);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_validInputFromViewingAllInfected_noPeopleFound() {
        String expectedMessage = MESSAGE_NO_PEOPLE_FOUND;
        Model modelForAllInfected = model;
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        Index index = Index.fromOneBased(1);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertThrows(CommandException.class, () -> command.execute(model));
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_invalidInputFromViewingAllQuarantined_throwCommandException() {
        Model modelForAllQuarantined = model;
        modelForAllQuarantined.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        Index index = Index.fromOneBased(1);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertThrows(CommandException.class, () -> command.execute(modelForAllQuarantined));
    }

    @Test
    public void execute_validInputFromViewingAllInfected_success() {
        String expectedMessage = "Generated people for: Daniel Meier";
        Model modelForAllInfected = model;
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        Model expectedModelForGenerate = expectedModel;
        Predicate<Person> expectedPersonPredicate = person -> person.getId().getOneBased() == 3;
        expectedModelForGenerate.updateFilteredPersonList(expectedPersonPredicate);
        Index index = Index.fromOneBased(1);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertCommandSuccess(command, modelForAllInfected, expectedMessage, expectedModelForGenerate);
    }

    @Test
    public void execute_invalidInputFromViewingAllQuarantined_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_IS_NOT_INFECTED;
        Model modelForAllInfected = model;
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        Index index = Index.fromOneBased(1);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        try {
            command.execute(modelForAllInfected);
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }
}
