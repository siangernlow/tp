package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showLocationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE_STRING;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION_INDEX;
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
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null, null, null));
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
            new AddVisitCommand(INDEX_FIRST, INDEX_FIRST, DEFAULT_DATE).execute(model);
            assertEquals(model.getFilteredVisitList().get(0), validVisit);
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
        public boolean hasVisit(Visit visit) {
            requireNonNull(visit);
            return this.visit.equals(visit);
        }

        @Override
        public Person getPersonFromIndex(Index index) {
            return Index.fromOneBased(1);
        }

        @Override
        public Location getLocationFromIndex(Index index) {
            return Index.fromOneBased(1);
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
            return model.getPersonFromIndex(index);
        }

        @Override
        public Location getLocationFromIndex(Index index) {
            return Index.fromOneBased(1);
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }
    }

}
