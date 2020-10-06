package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLocationBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.VisitBuilder;

public class AddVisitCommandTest {
    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null));
    }

    @Test
    public void execute_visitAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVisitAdded modelStub =
                new AddVisitCommandTest.ModelStubAcceptingVisitAdded();
        Visit validVisit = new VisitBuilder().build();

        CommandResult commandResult = new AddVisitCommand(validVisit).execute(modelStub);

        assertEquals(String.format(AddVisitCommand.MESSAGE_SUCCESS, validVisit),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVisit), modelStub.visitsAdded);
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Visit validVisit = new VisitBuilder().build();
        AddVisitCommand addvisitCommand = new AddVisitCommand(validVisit);
        ModelStub modelStub = new AddVisitCommandTest.ModelStubWithVisit(validVisit);

        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, () ->
                addvisitCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Visit sampleA = new VisitBuilder().withPersonId("1").withLocationId("1").withDate("2020-09-09").build();
        Visit sampleB = new VisitBuilder().withPersonId("1").withLocationId("2").withDate("2020-09-09").build();
        AddVisitCommand addSampleACommand = new AddVisitCommand(sampleA);
        AddVisitCommand addSampleBCommand = new AddVisitCommand(sampleB);

        // same object -> returns true
        assertTrue(addSampleACommand.equals(addSampleACommand));

        // same values -> returns true
        AddVisitCommand addSampleACommandCopy = new AddVisitCommand(sampleB);
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
        public ReadOnlyVisitBook getVisitBook() {
            return new VisitBook();
        }
    }

}
