package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GenerateLocationsCommand.MESSAGE_PERSON_HAS_NO_VISITS;
import static seedu.address.logic.commands.GenerateLocationsCommand.MESSAGE_PERSON_IS_NOT_INFECTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.DANIEL_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.visit.Visit;

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
        GenerateLocationsCommand firstGenerateLocationsCommand = new GenerateLocationsCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
        GenerateLocationsCommand secondGenerateLocationsCommand = new GenerateLocationsCommand(
                new IndexIdPairStub(INDEX_SECOND, null));

        // same object -> returns true
        assertTrue(firstGenerateLocationsCommand.equals(firstGenerateLocationsCommand));

        // same values -> returns true
        GenerateLocationsCommand copyOfGenerateLocationsCommand = new GenerateLocationsCommand(
                new IndexIdPairStub(INDEX_FIRST, null));
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
        String expectedMessage = MESSAGE_INVALID_PERSON_INDEX;
        Index index = Index.fromOneBased(100);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_personAtIndexNotInfected_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_IS_NOT_INFECTED;
        Index index = Index.fromOneBased(1);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_noVisitsFound_throwCommandException() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        model.deleteVisit(model.getVisitBook().getVisitList().get(6));
        Index index = Index.fromOneBased(5);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_validInputFromViewingAllPeople_success() {
        String expectedMessage = "Generated locations for: Daniel Meier";
        Visit testVisit = new Visit(DANIEL, DANIEL_LOCATION, LocalDate.now());
        model.addVisit(testVisit);
        Model expectedModelForGenerate = expectedModel;
        expectedModelForGenerate.addVisit(testVisit);
        Predicate<Location> locationPredicate = location -> location.getId().equals(new Id("L4567"));
        expectedModelForGenerate.updateFilteredLocationList(locationPredicate);
        Index index = Index.fromOneBased(4);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPair(index, null, PREFIX_PERSON_ID));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_validInputFromViewingAllInfected_success() {
        CommandResult expectedCommand = new CommandResult("Generated locations for: Benson Meier");
        Visit testVisit = new Visit(BENSON, DANIEL_LOCATION, LocalDate.now());
        Model modelForAllInfected = model;
        modelForAllInfected.addVisit(testVisit);
        modelForAllInfected.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);

        Model expectedModelForGenerate = expectedModel;
        Predicate<Location> locationPredicate = location -> location.getId().equals(new Id("L4567"));
        expectedModelForGenerate.addVisit(testVisit);
        expectedModelForGenerate.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
        expectedModelForGenerate.updateFilteredLocationList(locationPredicate);

        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(INDEX_FIRST, null));
        assertCommandSuccess(command, modelForAllInfected, expectedCommand, expectedModelForGenerate);
    }

    @Test
    public void execute_invalidInputFromViewingAllQuarantined_throwCommandException() {
        Model modelForAllQuarantined = model;
        modelForAllQuarantined.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(INDEX_FIRST, null));
        assertThrows(CommandException.class, () -> command.execute(modelForAllQuarantined));
    }

    @Test
    public void execute_dateOfVisitIsWithinRange_success() {
        String expectedMessage = "Generated locations for: Daniel Meier";
        LocalDate testDate = LocalDate.now().minusDays(7);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        Model expectedModelForGenerate = expectedModel;
        expectedModelForGenerate.addVisit(testVisitOne);
        Predicate<Location> locationPredicate = location -> location.getId().equals(new Id("L4567"));
        expectedModelForGenerate.updateFilteredLocationList(locationPredicate);
        Index index = Index.fromOneBased(4);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModelForGenerate);
    }

    @Test
    public void execute_dateOfVisitMoreThanTwoWeeksAgo_noVisitsFound() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        LocalDate testDate = LocalDate.now().minusDays(14);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        Index index = Index.fromOneBased(4);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }

    @Test
    public void execute_dateOfVisitInTheFuture_noVisitsFound() {
        String expectedMessage = MESSAGE_PERSON_HAS_NO_VISITS;
        LocalDate testDate = LocalDate.now().plusDays(1);
        Visit testVisitOne = new Visit(DANIEL, DANIEL_LOCATION, testDate);
        model.addVisit(testVisitOne);
        Index index = Index.fromOneBased(4);
        GenerateLocationsCommand command = new GenerateLocationsCommand(new IndexIdPairStub(index, null));
        assertThrows(CommandException.class, () -> command.execute(model), expectedMessage);
    }
}
