package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ListType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelPredicate;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
    private static final ListType PEOPLE_LIST = ListType.ALL_PEOPLE;
    private static final ListType LOCATIONS_LIST = ListType.ALL_LOCATIONS;
    private static final ListType VISITS_LIST = ListType.ALL_VISITS;
    private static final ListType INFECTED_LIST = ListType.ALL_INFECTED;
    private static final ListType QUARANTINED_LIST = ListType.ALL_QUARANTINED;
    private static final ListType STATISTICS_LIST = ListType.STATISTICS;
    private static final ListType HIGH_RISK_LOCATIONS_LIST = ListType.HIGH_RISK_LOCATIONS;

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                new UserPrefs(), getTypicalVisitBook());
        expectedModel = new ModelManager(model.getAddressBook(), model.getLocationBook(),
                new UserPrefs(), model.getVisitBook());
    }

    @Test
    public void execute_personsListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PEOPLE_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_PEOPLE, expectedModel);
    }

    @Test
    public void execute_personsListIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(PEOPLE_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_PEOPLE, expectedModel);
    }

    @Test
    public void execute_locationsList_showsSameList() {
        assertCommandSuccess(new ListCommand(LOCATIONS_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_LOCATIONS, expectedModel);
    }

    @Test
    public void execute_visitsList_showsSameList() {
        assertCommandSuccess(new ListCommand(VISITS_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_VISITS, expectedModel);
    }

    @Test
    public void execute_infectedList_showsSameList() {
        Model expectedModelInfected = expectedModel;
        expectedModelInfected.updateFilteredPersonList(ModelPredicate.PREDICATE_SHOW_ALL_INFECTED);
        assertCommandSuccess(new ListCommand(INFECTED_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_INFECTED, expectedModelInfected);
    }

    @Test
    public void execute_quarantinedList_showsSameList() {
        Model expectedModelQuarantined = expectedModel;
        expectedModelQuarantined.updateFilteredPersonList(ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED);
        assertCommandSuccess(new ListCommand(QUARANTINED_LIST),
                model, ListCommand.MESSAGE_SUCCESS_ALL_QUARANTINED, expectedModelQuarantined);
    }

    @Test
    public void execute_statistics_showsSameList() {
        assertCommandSuccess(new ListCommand(STATISTICS_LIST),
                model, ListCommand.MESSAGE_SUCCESS_STATISTICS, expectedModel);
    }

    @Test
    public void execute_highRiskLocations_showsSameList() {
        Model expectedModelHighRiskLocations = expectedModel;
        expectedModelHighRiskLocations.updateFilteredLocationList(
                ModelPredicate.getPredicateForHighRiskLocations(expectedModelHighRiskLocations));
        assertCommandSuccess(new ListCommand(HIGH_RISK_LOCATIONS_LIST),
                model, ListCommand.MESSAGE_SUCCESS_HIGH_RISK_LOCATIONS, expectedModelHighRiskLocations);
    }

    @Test
    public void equals() {
        ListCommand listPersonsCommand = new ListCommand(PEOPLE_LIST);

        // same object -> returns true
        assertTrue(listPersonsCommand.equals(listPersonsCommand));

        // same values -> returns true
        ListCommand listPersonsCommandCopy = new ListCommand(PEOPLE_LIST);
        assertTrue(listPersonsCommand.equals(listPersonsCommandCopy));

        // different values -> returns false
        ListCommand differentListCommand = new ListCommand(LOCATIONS_LIST);
        assertFalse(listPersonsCommand.equals(differentListCommand));

        // different types -> returns false
        assertFalse(listPersonsCommand.equals(1));

        // null -> returns false
        assertFalse(listPersonsCommand.equals(null));
    }
}
