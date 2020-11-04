package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_DUPLICATES_NOT_ADDED;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.visit.AddVisitsFromCsvCommand.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.commands.visit.AddVisitsFromCsvCommand.VISITS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DataGenerator;
import seedu.address.model.ModelStub;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.TypicalVisitParametersContainers;
import seedu.address.testutil.TypicalVisits;

public class AddVisitsFromCsvCommandTest {

    @Test
    public void constructor_nullVisits_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitsFromCsvCommand(null));
    }

    @Test
    public void execute_visitsAcceptedByModel_addSuccessful() {
        List<Visit> visitsToAdd = TypicalVisits.getTypicalVisits();
        List<DataGenerator.VisitParametersContainer> vpcsToAdd =
                TypicalVisitParametersContainers.getVisitsAsVpc(visitsToAdd);

        AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded expectedModel =
                new AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded();
        AddVisitsFromCsvCommandTest.ModelStubThatChecksIndexes actualModel =
                new AddVisitsFromCsvCommandTest.ModelStubThatChecksIndexes();

        for (Visit visit : visitsToAdd) {
            expectedModel.addVisit(visit);
            actualModel.addPersonInVisit(visit);
            actualModel.addLocationInVisit(visit);
        }

        AddVisitsFromCsvCommand actualCommand = new AddVisitsFromCsvCommand(vpcsToAdd);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, vpcsToAdd.size(), VISITS));
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }

    @Test
    public void execute_emptyVpcsList_throwsCommandException() {
        AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded model =
                new AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded();

        List<DataGenerator.VisitParametersContainer> visitsToAdd = new ArrayList<>();

        AddVisitsFromCsvCommand actualCommand = new AddVisitsFromCsvCommand(visitsToAdd);
        assertThrows(CommandException.class, MESSAGE_EMPTY_LIST, ()
            -> actualCommand.execute(model));
    }

    @Test
    public void execute_duplicatePersons_successWithWarning() {
        List<Visit> visitsToAdd = TypicalVisits.getTypicalVisits();

        AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded expectedModel =
                new AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded();
        AddVisitsFromCsvCommandTest.ModelStubThatChecksIndexes actualModel =
                new AddVisitsFromCsvCommandTest.ModelStubThatChecksIndexes();

        for (Visit visit : visitsToAdd) {
            expectedModel.addVisit(visit);
            actualModel.addPersonInVisit(visit);
            actualModel.addLocationInVisit(visit);
        }

        int numOfUniqueAdditions = visitsToAdd.size();

        // Duplicate visits
        visitsToAdd.add(FIRST_VISIT);
        visitsToAdd.add(SECOND_VISIT);

        List<DataGenerator.VisitParametersContainer> vpcsToAdd =
                TypicalVisitParametersContainers.getVisitsAsVpc(visitsToAdd);

        AddVisitsFromCsvCommand actualCommand = new AddVisitsFromCsvCommand(vpcsToAdd);

        String linesWithDuplicates = String.format("%d %d ", vpcsToAdd.size() - 1, vpcsToAdd.size());
        String expectedMessage = String.format(MESSAGE_SUCCESS, numOfUniqueAdditions, VISITS)
                + String.format(MESSAGE_DUPLICATES_NOT_ADDED, VISITS, linesWithDuplicates);
        CommandResult commandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }


    @Test
    public void equals() {
        DataGenerator.VisitParametersContainer septemberVisit =
                new TypicalVisitParametersContainers.VpcBuilder().withDate("2020-09-09").build();
        DataGenerator.VisitParametersContainer octoberVisit =
                new TypicalVisitParametersContainers.VpcBuilder().withDate("2020-10-10").build();

        List<DataGenerator.VisitParametersContainer> visitsAddSept = new ArrayList<>();
        visitsAddSept.add(septemberVisit);
        List<DataGenerator.VisitParametersContainer> visitsAddSeptAndOct = new ArrayList<>();
        visitsAddSeptAndOct.add(septemberVisit);
        visitsAddSeptAndOct.add(octoberVisit);

        AddVisitsFromCsvCommand addSeptVisitCommand = new AddVisitsFromCsvCommand(visitsAddSept);
        AddVisitsFromCsvCommand addSeptAndOctVisitsCommand =
                new AddVisitsFromCsvCommand(visitsAddSeptAndOct);

        // same object -> returns true
        assertEquals(addSeptVisitCommand, addSeptVisitCommand);

        // same values -> returns true
        AddVisitsFromCsvCommand addHouseCommandCopy = new AddVisitsFromCsvCommand(visitsAddSept);
        assertEquals(addHouseCommandCopy, addSeptVisitCommand);

        // different types -> returns false
        assertNotEquals(addSeptVisitCommand, 1);

        // null -> returns false
        assertNotEquals(addSeptVisitCommand, null);

        // different visit lists -> returns false
        assertNotEquals(addSeptAndOctVisitsCommand, addSeptVisitCommand);
    }

    /**
     * A Model stub that always accepts the visit being added.
     */
    private static class ModelStubAcceptingVisitsAdded extends ModelStub {
        private final ArrayList<Visit> visitsAdded = new ArrayList<>();

        public ArrayList<Visit> getVisitsAdded() {
            return visitsAdded;
        }

        @Override
        public boolean hasVisit(Visit visit) {
            return visitsAdded.contains(visit);
        }

        @Override
        public void addVisit(Visit visit) {
            requireNonNull(visit);
            visitsAdded.add(visit);
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded)) {
                return false;
            }

            AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded other =
                    (AddVisitsFromCsvCommandTest.ModelStubAcceptingVisitsAdded) obj;
            return visitsAdded.equals(other.getVisitsAdded());
        }
    }

    /**
     * This class further implements the functionality of retrieving
     * Person and Location objects given their indexes.
     * Requires the person and location lists used in the test case.
     */
    private static class ModelStubThatChecksIndexes extends ModelStubAcceptingVisitsAdded {

        // Used to retrieve the person and location using indexes
        private final List<Person> personListToCheck;
        private final List<Location> locationListToCheck;

        public ModelStubThatChecksIndexes() {
            personListToCheck = new ArrayList<>();
            locationListToCheck = new ArrayList<>();
        }

        public void addPersonInVisit(Visit visit) {
            Person p = visit.getPerson();
            if (!personListToCheck.contains(p)) {
                personListToCheck.add(p);
            }
        }

        public void addLocationInVisit(Visit visit) {
            Location loc = visit.getLocation();
            if (!locationListToCheck.contains(loc)) {
                locationListToCheck.add(loc);
            }
        }

        @Override
        public Person getPersonById(Id id) {
            for (Person person : personListToCheck) {
                if (person.getId().equals(id)) {
                    return person;
                }
            }
            return null;
        }

        @Override
        public Location getLocationById(Id id) {
            for (Location location : locationListToCheck) {
                if (location.getId().equals(id)) {
                    return location;
                }
            }
            return null;
        }
    }
}
