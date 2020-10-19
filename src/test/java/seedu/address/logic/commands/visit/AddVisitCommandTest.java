package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLocationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalLocations.FIONA_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.INFECTED_AND_QUARANTINED_PERSON;
import static seedu.address.testutil.TypicalPersons.INFECTED_PERSON;
import static seedu.address.testutil.TypicalPersons.QUARANTINED_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE_STRING;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION_INDEX;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON_INDEX;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.VisitBuilder;

public class AddVisitCommandTest {

    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(DEFAULT_PERSON_INDEX,
                DEFAULT_LOCATION_INDEX, null));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(DEFAULT_PERSON_INDEX,
                null, DEFAULT_DATE));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null,
                DEFAULT_LOCATION_INDEX, DEFAULT_DATE));
    }

    @Test
    public void execute_unfilteredList_addSuccessful() {
        ModelStubAcceptingVisitAdded modelStub =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        try {
            CommandResult commandResult = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX,
                    DEFAULT_DATE).execute(modelStub);

            assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                    commandResult.getFeedbackToUser());
            assertEquals(Arrays.asList(validVisit), modelStub.visitsAdded);
            assertEquals(commandResult.getSwitchState(), CommandResult.SWITCH_TO_VIEW_VISITS);
        } catch (CommandException e) {
            assert false : "Command Exception not expected.";
        }
    }

    @Test
    public void execute_filteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                new VisitBook(), new UserPrefs());

        showLocationAtIndex(model, INDEX_THIRD);
        showPersonAtIndex(model, INDEX_FOURTH);

        Person person = model.getFilteredPersonList().get(0);
        Location location = model.getFilteredLocationList().get(0);
        Visit validVisit = new VisitBuilder().withDate(DEFAULT_DATE_STRING)
                .withLocation(location)
                .withPerson(person)
                .build();

        try {
            CommandResult commandResult = new AddVisitCommand(INDEX_FIRST, INDEX_FIRST, DEFAULT_DATE).execute(model);
            assertEquals(model.getFilteredVisitList().get(0), validVisit);
            assertEquals(commandResult.getSwitchState(), CommandResult.SWITCH_TO_VIEW_VISITS);
        } catch (CommandException e) {
            assert false : "Command Exception not expected.";
        }
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Visit validVisit = new VisitBuilder().build();
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX,
                DEFAULT_DATE);
        ModelStub modelStub = new AddVisitCommandTest.ModelStubWithVisit(validVisit);

        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_infectedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit visitWithInfected = new VisitBuilder().withPerson(INFECTED_PERSON).build();
        expectedModel.addVisit(visitWithInfected);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_FOURTH, INDEX_SECOND,
                DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_INFECTED_MADE_VISIT, visitWithInfected);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_quarantinedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit visitWithQuarantined = new VisitBuilder().withPerson(QUARANTINED_PERSON).build();
        expectedModel.addVisit(visitWithQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_FIRST, INDEX_SECOND,
                DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_QUARANTINED_MADE_VISIT, visitWithQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_infectedAndQuarantinedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit visitWithInfectedAndQuarantined = new VisitBuilder().withPerson(INFECTED_AND_QUARANTINED_PERSON)
                .withLocation(FIONA_LOCATION).build();
        expectedModel.addVisit(visitWithInfectedAndQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_SECOND, INDEX_SIXTH, DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_INFECTED_AND_QUARANTINED_MADE_VISIT,
                visitWithInfectedAndQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    /**
     * This test ensures that no warning is shown if an infected/quarantined
     * person makes a visit to his own home.
     */
    @Test
    public void execute_infectedAndQuarantinedButStayedHome_successNoWarning() {
        ModelStubAcceptingVisitAdded model =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit visitWithInfectedAndQuarantined = new VisitBuilder().withPerson(INFECTED_AND_QUARANTINED_PERSON).build();
        expectedModel.addVisit(visitWithInfectedAndQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_SECOND, DEFAULT_LOCATION_INDEX, DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_NO_WARNING,
                visitWithInfectedAndQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandResult.SWITCH_TO_VIEW_VISITS);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        Index personAIndex = Index.fromOneBased(Integer.parseInt("1"));
        Index personBIndex = Index.fromOneBased(Integer.parseInt("1"));
        Index locationAIndex = Index.fromOneBased(Integer.parseInt("1"));
        Index locationBIndex = Index.fromOneBased(Integer.parseInt("2"));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateA = LocalDate.parse("2020-09-09", inputFormat);
        LocalDate dateB = LocalDate.parse("2020-09-09", inputFormat);
        AddVisitCommand addSampleACommand = new AddVisitCommand(personAIndex, locationAIndex, dateA);
        AddVisitCommand addSampleBCommand = new AddVisitCommand(personBIndex, locationBIndex, dateB);

        // same object -> returns true
        assertTrue(addSampleACommand.equals(addSampleACommand));

        // same values -> returns true
        AddVisitCommand addSampleACommandCopy = new AddVisitCommand(personBIndex, locationBIndex, dateB);
        assertTrue(addSampleBCommand.equals(addSampleACommandCopy));

        // different types -> returns false
        assertFalse(addSampleBCommand.equals(1));

        // null -> returns false
        assertFalse(addSampleACommand.equals(null));

        // different location -> returns false
        assertFalse(addSampleACommand.equals(addSampleBCommand));
    }

    /**
     * A Model stub that contains a single visit.
     */
    private class ModelStubWithVisit extends ModelStub {
        private final Visit visit;

        ModelStubWithVisit(Visit visit) {
            requireNonNull(visit);
            this.visit = visit;
        }

        @Override
        public Person getPersonFromIndex(Index index) {
            if (index.equals(DEFAULT_PERSON_INDEX)) {
                return DEFAULT_PERSON;
            } else if (index.equals(INDEX_FOURTH)) {
                return INFECTED_PERSON;
            } else if (index.equals(INDEX_FIRST)) {
                return QUARANTINED_PERSON;
            }
            return DEFAULT_PERSON;
        }

        @Override
        public Location getLocationFromIndex(Index index) {

            return DEFAULT_LOCATION;
        }

        @Override
        public boolean hasVisit(Visit visit) {
            requireNonNull(visit);
            return this.visit.equals(visit);
        }
    }

    /**
     * A Model stub that always accept the visit being added.
     */
    private class ModelStubAcceptingVisitAdded extends ModelStub {
        final ArrayList<Visit> visitsAdded = new ArrayList<>();

        @Override
        public boolean hasVisit(Visit visit) {
            requireNonNull(visit);
            return visitsAdded.stream().anyMatch(visit::equals);
        }

        @Override
        public void addVisit(Visit visit) {
            requireNonNull(visit);
            visitsAdded.add(visit);
        }

        @Override
        public Person getPersonFromIndex(Index index) {
            if (index.equals(DEFAULT_PERSON_INDEX)) {
                return DEFAULT_PERSON;
            } else if (index.equals(INDEX_FOURTH)) {
                return INFECTED_PERSON;
            } else if (index.equals(INDEX_FIRST)) {
                return QUARANTINED_PERSON;
            }
            return DEFAULT_PERSON;
        }

        @Override
        public Location getLocationFromIndex(Index index) {
            if (index.equals(INDEX_SIXTH)) {
                return FIONA_LOCATION;
            }
            return DEFAULT_LOCATION;
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            return obj instanceof ModelStubAcceptingVisitAdded;
        }
    }

}
