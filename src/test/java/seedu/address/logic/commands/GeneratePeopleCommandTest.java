package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandUtil.MESSAGE_PERSON_HAS_NO_VISITS;
import static seedu.address.logic.commands.CommandUtil.MESSAGE_PERSON_IS_NOT_INFECTED;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_NO_PEOPLE_FOUND;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

import java.util.function.Predicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class GeneratePeopleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            new UserPrefs(), getTypicalVisitBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
            new UserPrefs(), getTypicalVisitBook());

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
        Index index = Index.fromOneBased(6);
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
    public void execute_validInput_success() {
        String expectedMessage = "Generated people for: Daniel Meier";
        Model expectedModelForGenerate = expectedModel;
        Predicate<Person> personPredicate = person -> person.getId().getOneBased() == 3;
        expectedModelForGenerate.updateFilteredPersonList(personPredicate);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(index);
        assertCommandSuccess(command, model, expectedMessage, expectedModelForGenerate);
    }
}
