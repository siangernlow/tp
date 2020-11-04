package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LOCATION_ID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LOCATION_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_ID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLocationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.visit.AddVisitCommand.MESSAGE_FUTURE_VISIT;
import static seedu.address.logic.commands.visit.AddVisitCommand.MESSAGE_NO_WARNING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalLocations.FIONA_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.HEALTHY_PERSON;
import static seedu.address.testutil.TypicalPersons.INFECTED_AND_QUARANTINED_PERSON;
import static seedu.address.testutil.TypicalPersons.INFECTED_PERSON;
import static seedu.address.testutil.TypicalPersons.QUARANTINED_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE_STRING;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION_ID;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION_INDEX;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON_ID;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON_INDEX;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.attribute.Id;
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
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(DEFAULT_PERSON_ID,
                DEFAULT_LOCATION_ID, null));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(DEFAULT_PERSON_ID,
                null, DEFAULT_DATE));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null,
                DEFAULT_LOCATION_ID, DEFAULT_DATE));

    }

    @Test
    public void execute_unfilteredListIndex_success() {
        ModelStubAcceptingVisitAdded modelStub =
                new ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        try {
            CommandResult commandResult = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX,
                    DEFAULT_DATE).execute(modelStub);

            assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                    commandResult.getFeedbackToUser());
            assertEquals(Collections.singletonList(validVisit), modelStub.visitsAdded);
        } catch (CommandException e) {
            assert false : "Command Exception not expected.";
        }
    }

    @Test
    public void execute_unfilteredListId_success() {
        ModelStubAcceptingVisitAdded modelStub =
                new ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        try {
            CommandResult commandResult = new AddVisitCommand(DEFAULT_PERSON_ID, DEFAULT_LOCATION_ID,
                    DEFAULT_DATE).execute(modelStub);

            assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                    commandResult.getFeedbackToUser());
            assertEquals(Collections.singletonList(validVisit), modelStub.visitsAdded);
        } catch (CommandException e) {
            assert false : "Command Exception not expected.";
        }
    }

    @Test
    public void execute_filteredListIndex_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                new VisitBook(), new UserPrefs());

        showLocationAtIndex(model, INDEX_THIRD);
        showPersonAtIndex(model, INDEX_FOURTH);

        Person person = model.getSortedPersonList().get(0);
        Location location = model.getSortedLocationList().get(0);
        Visit validVisit = new VisitBuilder().withDate(DEFAULT_DATE_STRING)
                .withLocation(location)
                .withPerson(person)
                .build();

        try {
            CommandResult commandResult = new AddVisitCommand(INDEX_FIRST, INDEX_FIRST, DEFAULT_DATE).execute(model);
            assertEquals(model.getSortedVisitList().get(0), validVisit);
        } catch (CommandException e) {
            assert false : "Command Exception not expected.";
        }
    }

    @Test
    public void execute_validDate_success() {
        // testing with current date
        LocalDate todayDate = LocalDate.now();
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX, todayDate);
        ModelStub modelStub = new ModelStubAcceptingVisitAdded();
        Visit visit = new VisitBuilder().withDate(todayDate.toString())
                .withLocation(DEFAULT_LOCATION).withPerson(DEFAULT_PERSON).build();
        Model expectedModel = new ModelStubAcceptingVisitAdded();
        expectedModel.addVisit(visit);
        String expectedMsg = String.format(MESSAGE_NO_WARNING, visit);
        CommandResult expectedCommandResult = new CommandResult(expectedMsg);
        assertCommandSuccess(addvisitCommand, modelStub, expectedCommandResult, expectedModel);

        // testing with yesterday date
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX, yesterdayDate);
        modelStub = new ModelStubAcceptingVisitAdded();
        visit = new VisitBuilder().withDate(yesterdayDate.toString())
                .withLocation(DEFAULT_LOCATION).withPerson(DEFAULT_PERSON).build();
        expectedModel = new ModelStubAcceptingVisitAdded();
        expectedModel.addVisit(visit);
        expectedMsg = String.format(MESSAGE_NO_WARNING, visit);
        expectedCommandResult = new CommandResult(expectedMsg);
        assertCommandSuccess(addvisitCommand, modelStub, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidPersonId_throwsCommandException() {
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_ID, DEFAULT_LOCATION_ID,
                DEFAULT_DATE);
        ModelStub modelStub = new ModelStubInvalidPersonId();

        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_ID, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidLocationId_throwsCommandException() {
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_ID, DEFAULT_LOCATION_ID,
                DEFAULT_DATE);
        ModelStub modelStub = new ModelStubInvalidLocationId();

        assertThrows(CommandException.class, MESSAGE_INVALID_LOCATION_ID, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX,
                DEFAULT_DATE);
        ModelStub modelStub = new ModelStubInvalidPersonIndex();

        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_INDEX, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidLocationIndex_throwsCommandException() {
        AddVisitCommand addvisitCommand = new AddVisitCommand(INDEX_FIRST, DEFAULT_LOCATION_INDEX,
                DEFAULT_DATE);
        ModelStub modelStub = new ModelStubInvalidLocationIndex();

        assertThrows(CommandException.class, MESSAGE_INVALID_LOCATION_INDEX, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateVisitIndex_throwsCommandException() {
        Visit validVisit = new VisitBuilder().build();
        AddVisitCommand addvisitCommand = new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX,
                DEFAULT_DATE);
        ModelStub modelStub = new ModelStubWithVisit(validVisit);

        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidDate_throwsCommandException() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        AddVisitCommand addvisitCommand = new AddVisitCommand(INDEX_FIRST, DEFAULT_LOCATION_INDEX,
                futureDate);
        ModelStub modelStub = new ModelStubInvalidLocationIndex();

        assertThrows(CommandException.class, MESSAGE_FUTURE_VISIT, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void execute_noInfectedOrQuarantinedVisit_success() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();

        Visit visit = new VisitBuilder().withPerson(HEALTHY_PERSON).build();
        expectedModel.addVisit(visit);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_THIRD, INDEX_SECOND,
                DEFAULT_DATE);

        String expectedMessage = String.format(MESSAGE_NO_WARNING, visit);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_infectedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();
        Visit visitWithInfected = new VisitBuilder().withPerson(INFECTED_PERSON).build();
        expectedModel.addVisit(visitWithInfected);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_FOURTH, INDEX_SECOND,
                DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_INFECTED_MADE_VISIT, visitWithInfected);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_quarantinedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();
        Visit visitWithQuarantined = new VisitBuilder().withPerson(QUARANTINED_PERSON).build();
        expectedModel.addVisit(visitWithQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_FIRST, INDEX_SECOND,
                DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_QUARANTINED_MADE_VISIT, visitWithQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_infectedAndQuarantinedVisit_successWithWarning() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();
        Visit visitWithInfectedAndQuarantined = new VisitBuilder().withPerson(INFECTED_AND_QUARANTINED_PERSON)
                .withLocation(FIONA_LOCATION).build();
        expectedModel.addVisit(visitWithInfectedAndQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_SECOND, INDEX_SIXTH, DEFAULT_DATE);
        String expectedMessage = String.format(AddVisitCommand.MESSAGE_INFECTED_AND_QUARANTINED_MADE_VISIT,
                visitWithInfectedAndQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    /**
     * This test ensures that no warning is shown if an infected/quarantined
     * person makes a visit to his own home.
     */
    @Test
    public void execute_infectedAndQuarantinedButStayedHome_successNoWarning() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();
        Visit visitWithInfectedAndQuarantined = new VisitBuilder().withPerson(INFECTED_AND_QUARANTINED_PERSON).build();
        expectedModel.addVisit(visitWithInfectedAndQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_SECOND, DEFAULT_LOCATION_INDEX, DEFAULT_DATE);
        String expectedMessage = String.format(MESSAGE_NO_WARNING,
                visitWithInfectedAndQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(actualCommand, model, expectedCommandResult, expectedModel);
    }

    /**
     * This test ensures that no warning is shown if an infected/quarantined
     * person makes a visit before he was infected and quarantined.
     */
    @Test
    public void execute_infectedAndQuarantinedButVisitBeforeStayHome_successNoWarning() {
        ModelStubAcceptingVisitAdded model =
                new ModelStubAcceptingVisitAdded();

        ModelStubAcceptingVisitAdded expectedModel =
                new ModelStubAcceptingVisitAdded();
        Visit visitWithInfectedAndQuarantined = new VisitBuilder().withPerson(INFECTED_AND_QUARANTINED_PERSON)
                .withDate("2020-11-04").build();
        expectedModel.addVisit(visitWithInfectedAndQuarantined);

        AddVisitCommand actualCommand = new AddVisitCommand(INDEX_SECOND, DEFAULT_LOCATION_INDEX,
                LocalDate.parse("2020-11-04"));
        String expectedMessage = String.format(MESSAGE_NO_WARNING, visitWithInfectedAndQuarantined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

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
        assertEquals(addSampleACommand, addSampleACommand);

        // same values -> returns true
        AddVisitCommand addSampleACommandCopy = new AddVisitCommand(personBIndex, locationBIndex, dateB);
        assertEquals(addSampleACommandCopy, addSampleBCommand);

        // different types -> returns false
        assertNotEquals(addSampleBCommand, 1);

        // null -> returns false
        assertNotEquals(addSampleACommand, null);

        // different location -> returns false
        assertNotEquals(addSampleBCommand, addSampleACommand);
    }

    /**
     * A Model stub that contains a single visit.
     */
    private static class ModelStubWithVisit extends ModelStub {
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

        @Override
        public ObservableList<Person> getSortedPersonList() {
            return getTypicalAddressBook().getPersonList();
        }

        @Override
        public ObservableList<Location> getSortedLocationList() {
            return getTypicalLocationBook().getLocationList();
        }
    }

    /**
     * A Model stub that always accept the visit being added.
     */
    private static class ModelStubAcceptingVisitAdded extends ModelStub {
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
        public boolean hasPersonId(Id id) {
            return true;
        }

        @Override
        public boolean hasLocationId(Id id) {
            return true;
        }

        @Override
        public Person getPersonFromIndex(Index index) {
            if (index.equals(DEFAULT_PERSON_INDEX)) {
                return DEFAULT_PERSON;
            } else if (index.equals(INDEX_FOURTH)) {
                return INFECTED_PERSON;
            } else if (index.equals(INDEX_FIRST)) {
                return QUARANTINED_PERSON;
            } else if (index.equals(INDEX_THIRD)) {
                return HEALTHY_PERSON;
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
        public Location getLocationById(Id id) {
            return DEFAULT_LOCATION;
        }

        @Override
        public Person getPersonById(Id id) {
            return DEFAULT_PERSON;
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }

        @Override
        public ObservableList<Person> getSortedPersonList() {
            return getTypicalAddressBook().getPersonList();
        }

        @Override
        public ObservableList<Location> getSortedLocationList() {
            return getTypicalLocationBook().getLocationList();
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

    /**
     * A Model stub that returns false for every person ID.
     */
    private static class ModelStubInvalidPersonId extends ModelStub {
        @Override
        public boolean hasPersonId(Id id) {
            return false;
        }
    }

    /**
     * A Model stub that returns false for every location ID.
     */
    private static class ModelStubInvalidLocationId extends ModelStub {

        @Override
        public boolean hasPersonId(Id id) {
            // returns true so that execution moves to location id checking
            return true;
        }

        @Override
        public Person getPersonById(Id id) {
            return DEFAULT_PERSON;
        }

        @Override
        public boolean hasLocationId(Id id) {
            return false;
        }
    }

    /**
     * A Model stub that returns false for every person index.
     */
    private static class ModelStubInvalidPersonIndex extends ModelStub {
        @Override
        public ObservableList<Person> getSortedPersonList() {
            return FXCollections.observableList(new ArrayList<>());
        }
    }

    /**
     * A Model stub that returns false for every location ID.
     */
    private static class ModelStubInvalidLocationIndex extends ModelStub {
        @Override
        public ObservableList<Person> getSortedPersonList() {
            List<Person> personList = new ArrayList<>();
            personList.add(DEFAULT_PERSON);
            return FXCollections.observableList(personList);
        }

        @Override
        public ObservableList<Location> getSortedLocationList() {
            return FXCollections.observableList(new ArrayList<>());
        }
    }
}
