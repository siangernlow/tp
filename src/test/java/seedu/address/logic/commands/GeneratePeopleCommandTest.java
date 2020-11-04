package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_NO_PEOPLE_FOUND;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_PERSON_HAS_NO_VISITS;
import static seedu.address.logic.commands.GeneratePeopleCommand.MESSAGE_PERSON_IS_NOT_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.DANIEL_LOCATION;
import static seedu.address.testutil.TypicalLocations.ELLE_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attribute.Id;
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
        GeneratePeopleCommand firstGeneratePeopleCommand = new GeneratePeopleCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
        GeneratePeopleCommand secondGeneratePeopleCommand = new GeneratePeopleCommand(
                new IndexIdPairStub(INDEX_SECOND, null));

        // same object -> returns true
        assertTrue(firstGeneratePeopleCommand.equals(firstGeneratePeopleCommand));

        // same values -> returns true
        GeneratePeopleCommand copyOfGeneratePeopleCommand = new GeneratePeopleCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
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
        String expectedMessage = MESSAGE_INVALID_PERSON_INDEX;
        Index index = Index.fromOneBased(100);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_personAtIndexNotInfected_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_IS_NOT_INFECTED;
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(INDEX_FIRST, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_noVisitsFound_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        model.deleteVisit(model.getVisitBook().getVisitList().get(6));
        Index index = Index.fromOneBased(5);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_noPeopleFound_throwCommandException() {
        String expectedMessage = MESSAGE_NO_PEOPLE_FOUND;
        Visit testVisit = new Visit(new PersonBuilder().withId("S5678").build(),
                ELLE_LOCATION, LocalDate.now());
        model.addVisit(testVisit);
        Index index = Index.fromOneBased(5);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_validInputFromViewingAllPeople_success() {
        String expectedMessage = "Generated people for: Daniel Meier";
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, LocalDate.now());
        Visit testVisitTwo = new Visit(ELLE, DANIEL_LOCATION, LocalDate.now());
        model.addVisit(testVisitOne);
        model.addVisit(testVisitTwo);
        Model expectedModelForGenerate = expectedModel;
        expectedModelForGenerate.addVisit(testVisitOne);
        expectedModelForGenerate.addVisit(testVisitTwo);
        Predicate<Person> personPredicate = person -> person.getId().equals(new Id("S5678"));
        expectedModelForGenerate.updateFilteredPersonList(personPredicate);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_validInputFromViewingAllInfected_noPeopleFound() {
        String expectedMessage = MESSAGE_NO_PEOPLE_FOUND;
        Visit testVisit = new Visit(DANIEL, DANIEL_LOCATION, LocalDate.now());
        Model modelForAllInfected = model;
        modelForAllInfected.addVisit(testVisit);
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(INDEX_SECOND, null));
        assertThrows(CommandException.class, () -> command.execute(modelForAllInfected), expectedMessage);
    }

    @Test
    public void execute_invalidInputFromViewingAllQuarantined_throwCommandException() {
        Model modelForAllQuarantined = model;
        modelForAllQuarantined.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(INDEX_FIRST, null));
        assertThrows(CommandException.class, () -> command.execute(modelForAllQuarantined));
    }

    @Test
    public void execute_dateOfVisitIsWithinRange_success() {
        String expectedMessage = "Generated people for: Daniel Meier";
        LocalDate testDate = LocalDate.now().minusDays(7);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        Visit testVisitTwo = new Visit(ELLE, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        model.addVisit(testVisitTwo);
        Model expectedModelForGenerate = expectedModel;
        expectedModelForGenerate.addVisit(testVisitOne);
        expectedModelForGenerate.addVisit(testVisitTwo);
        Predicate<Person> personPredicate = person -> person.getId().equals(new Id("S5678"));
        expectedModelForGenerate.updateFilteredPersonList(personPredicate);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_dateOfVisitMoreThanTwoWeeksAgo_noPeopleFound() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        LocalDate testDate = LocalDate.now().minusDays(14);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_dateOfVisitInTheFuture_noPeopleFound() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        LocalDate testDate = LocalDate.now().plusDays(1);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        Index index = Index.fromOneBased(4);
        GeneratePeopleCommand command = new GeneratePeopleCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }
}
