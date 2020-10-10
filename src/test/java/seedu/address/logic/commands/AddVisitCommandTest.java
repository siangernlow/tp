package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATIONID;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSONID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.VisitBuilder;

public class AddVisitCommandTest {
    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null, null, null));
    }

    @Test
    public void execute_visitAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVisitAdded modelStub =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        Index personIndex = Index.fromOneBased(Integer.parseInt(DEFAULT_PERSONID));
        Index locationIndex = Index.fromOneBased(Integer.parseInt(DEFAULT_LOCATIONID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(DEFAULT_DATE, inputFormat);
        CommandResult commandResult = new AddVisitCommand(personIndex, locationIndex, date).execute(modelStub);

        assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVisit), modelStub.visitsAdded);
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Visit validVisit = new VisitBuilder().build();
        Index personIndex = Index.fromOneBased(Integer.parseInt(DEFAULT_PERSONID));
        Index locationIndex = Index.fromOneBased(Integer.parseInt(DEFAULT_LOCATIONID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(DEFAULT_DATE, inputFormat);
        AddVisitCommand addvisitCommand = new AddVisitCommand(personIndex, locationIndex, date);
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
        public Index getPersonIdFromIndex(Index index) {
            return Index.fromOneBased(1);
        }

        @Override
        public Index getLocationIdFromIndex(Index index) {
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
        public Index getPersonIdFromIndex(Index index) {
            return Index.fromOneBased(1);
        }

        @Override
        public Index getLocationIdFromIndex(Index index) {
            return Index.fromOneBased(1);
        }

        @Override
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }
    }

}
