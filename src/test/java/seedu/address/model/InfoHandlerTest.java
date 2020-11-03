package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFECTION_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUARANTINE_STATUS_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;
import static seedu.address.model.InfoHandler.INVALID_PERCENTAGE_STRING;
import static seedu.address.model.InfoHandler.INVALID_RATIO;
import static seedu.address.model.InfoHandler.getIdHashSetFromPersonsList;
import static seedu.address.model.InfoHandler.getLocationIdsFromInfectedVisitList;
import static seedu.address.model.InfoHandler.getNumberOfHighRiskLocations;
import static seedu.address.model.InfoHandler.sortByValues;
import static seedu.address.testutil.TypicalLocations.AMY_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocations;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalVisits;


public class InfoHandlerTest {
    private final ModelManager modelManager = new ModelManager();
    private final InfoHandler infoHandler = new InfoHandler(modelManager);
    private final List<Person> typicalPersons;
    private final List<Location> typicalLocations;

    // Set up test model
    public InfoHandlerTest() {
        typicalPersons = getTypicalPersons();
        typicalLocations = getTypicalLocations();

        for (Person person : typicalPersons) {
            modelManager.addPerson(person);
        }
        for (Location location: typicalLocations) {
            modelManager.addLocation(location);
        }
    }

    @Test
    public void getPersonList_validList_returnTrue() {
        assertEquals(modelManager.getSortedPersonList(), infoHandler.getPersonList());
    }

    @Test
    public void getLocationList_validList_returnTrue() {
        assertEquals(modelManager.getSortedLocationList(), infoHandler.getLocationList());
    }

    @Test
    public void getVisitList_validList_returnTrue() {
        assertEquals(modelManager.getSortedVisitList(), infoHandler.getVisitList());
    }

    @Test
    public void updateModelPersonList_dummyPredicate_returnsTrue() {
        Predicate<Person> predicateAlwaysTrue = unused -> true;
        modelManager.updateFilteredPersonList(predicateAlwaysTrue);
        assertEquals(modelManager.getSortedPersonList(),
                infoHandler.getPersonList());
    }

    @Test
    public void getTotalPeople_sizeIsCorrect_returnsTrue() {
        assertEquals(typicalPersons.size(), infoHandler.getTotalPeople());
    }

    @Test
    public void getTotalLocations_sizeIsCorrect_returnsTrue() {
        assertEquals(typicalLocations.size(), infoHandler.getTotalLocations());
    }

    @Test
    public void getTotalInfected_sizeIsCorrect_returnsTrue() {
        int numOfInfected = 0;
        for (Person p : typicalPersons) {
            if (p.getInfectionStatus().getStatusAsBoolean()) {
                numOfInfected++;
            }
        }
        assertEquals(numOfInfected, infoHandler.getTotalInfected());
    }

    @Test
    public void getTotalQuarantined_sizeIsCorrect_returnsTrue() {
        int numOfQuarantined = 0;
        for (Person p : typicalPersons) {
            if (p.getQuarantineStatus().getStatusAsBoolean()) {
                numOfQuarantined++;
            }
        }
        assertEquals(numOfQuarantined, infoHandler.getTotalQuarantined());
    }

    @Test
    public void getInfectedOverPeople_correctRatio_returnsTrue() {
        int numOfInfected = 0;
        for (Person p : typicalPersons) {
            if (p.getInfectionStatus().getStatusAsBoolean()) {
                numOfInfected++;
            }
        }
        assert(typicalPersons.size() > 0);
        assertEquals((double) numOfInfected / typicalPersons.size(),
                infoHandler.getInfectedOverPeople());
    }

    @Test
    public void getQuarantinedOverPeople_correctRatio_returnsTrue() {
        int numOfQuarantined = 0;
        for (Person p : typicalPersons) {
            if (p.getQuarantineStatus().getStatusAsBoolean()) {
                numOfQuarantined++;
            }
        }
        assert(typicalPersons.size() > 0);
        assertEquals((double) numOfQuarantined / typicalPersons.size(),
                infoHandler.getQuarantinedOverPeople());
    }

    @Test
    public void getRatioAsPercentage() {
        // valid ratio -> returns 50%
        double ratio = 0.5;
        assertEquals("50.00%", infoHandler.getRatioAsPercentage(ratio));

        // invalid ratio from dividing by zero -> returns error string
        assertEquals(INVALID_PERCENTAGE_STRING, infoHandler.getRatioAsPercentage(INVALID_RATIO));

        // invalid ratio, more than 1 -> returns error string
        double invalidRatio = 1.5;
        assertEquals(INVALID_PERCENTAGE_STRING, infoHandler.getRatioAsPercentage(invalidRatio));

        // invalid ratio, negative number -> returns error string
        invalidRatio = -1.5;
        assertEquals(INVALID_PERCENTAGE_STRING, infoHandler.getRatioAsPercentage(invalidRatio));
    }

    @Test
    public void getIdHashSetFromPersonsList_success() {
        List<Person> typicalPersons = TypicalPersons.getTypicalPersons();
        HashSet<Id> expectedPersonsIds = TypicalPersons.getIdsOfTypicalPersonsAsHashSet();
        assertEquals(expectedPersonsIds, getIdHashSetFromPersonsList(typicalPersons));
    }

    @Test
    public void getLocationIdsFromVisitList_success() {
        List<Visit> typicalVisits = TypicalVisits.getVisitsForTest();
        List<Id> expectedLocationIds = TypicalVisits.getLocationsIdsFromVisitsForTest();
        assertEquals(expectedLocationIds, getLocationIdsFromInfectedVisitList(typicalVisits));
    }

    @Test
    public void sortByValues_success() {
        List<Visit> visits = TypicalVisits.getVisitsForTest();
        HashMap<Id, Integer> locations = new HashMap<>();
        for (Visit visit : visits) {
            Id id = visit.getLocation().getId();
            if (locations.containsKey(id)) {
                locations.put(id, locations.get(id) + 1);
            } else {
                locations.put(id, 1);
            }
        }
        HashMap<Id, Integer> actualHashMap = sortByValues(locations);
        HashMap<Id, Integer> expectedHashMap = new LinkedHashMap<>();
        expectedHashMap.put(new Id("L9101112"), 3);
        expectedHashMap.put(new Id("L3456"), 1);
        expectedHashMap.put(new Id("L10111213"), 1);
        assertEquals(expectedHashMap, actualHashMap);
    }

    @Test
    public void getNumberOfHighRiskLocations_moreThanSixtyPercent_success() {
        assertEquals(40, getNumberOfHighRiskLocations(89, 100));
        assertEquals((int) (101 * 0.4), getNumberOfHighRiskLocations(101, 101));
        assertEquals((int) (29 * 0.4), getNumberOfHighRiskLocations((int) Math.ceil(29 * 0.6), 29));
    }

    @Test
    public void getNumberOfHighRiskLocations_lessThanOrEqualToFortyPercent_success() {
        assertEquals(1, getNumberOfHighRiskLocations(1, 100));
        assertEquals(40, getNumberOfHighRiskLocations(40, 100));
        assertEquals(0, getNumberOfHighRiskLocations(0, 4));
    }

    //====== Tests below use their own InfoHandler and ModelStub classes ======================

    @Test
    public void getPersonListAsString_success() {
        List<Person> onePersonList = new ArrayList<>();
        onePersonList.add(AMY);
        ModelStubListsWithOneObject model =
                new ModelStubListsWithOneObject(onePersonList, null, null);
        InfoHandler infoHandler = new InfoHandler(model);

        String expectedMessage =
            PREFIX_PERSON_ID + VALID_ID_AMY + ","
            + PREFIX_NAME + VALID_NAME_AMY + ","
            + PREFIX_PHONE + VALID_PHONE_AMY + ","
            + PREFIX_EMAIL + VALID_EMAIL_AMY + ","
            + "\"" + PREFIX_ADDRESS + VALID_ADDRESS_AMY + "\","
            + PREFIX_QUARANTINE_STATUS + VALID_QUARANTINE_STATUS_AMY + ","
            + PREFIX_INFECTION_STATUS + VALID_INFECTION_STATUS_AMY + "\n";

        assertEquals(expectedMessage, infoHandler.getPersonListAsString());
    }

    @Test
    public void getLocationListAsString_success() {
        List<Location> oneLocationList = new ArrayList<>();
        oneLocationList.add(AMY_LOCATION);
        ModelStubListsWithOneObject model =
                new ModelStubListsWithOneObject(null, oneLocationList, null);
        InfoHandler infoHandler = new InfoHandler(model);

        String expectedMessage =
            PREFIX_LOCATION_ID + VALID_ID_AMY_LOCATION + ","
            + PREFIX_NAME + VALID_NAME_AMY + ","
            + "\"" + PREFIX_ADDRESS + VALID_ADDRESS_AMY + "\"\n";

        assertEquals(expectedMessage, infoHandler.getLocationListAsString());
    }

    @Test
    public void getVisitListAsString_success() {
        List<Visit> oneVisitList = new ArrayList<>();
        oneVisitList.add(SECOND_VISIT);
        ModelStubListsWithOneObject model =
                new ModelStubListsWithOneObject(null, null, oneVisitList);
        InfoHandler infoHandler = new InfoHandler(model);

        String expectedMessage =
            PREFIX_PERSON_ID + VALID_ID_AMY + ","
            + PREFIX_LOCATION_ID + VALID_ID_AMY_LOCATION + ","
            + PREFIX_DATE + "2020-09-12" + "\n";

        assertEquals(expectedMessage, infoHandler.getVisitListAsString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(infoHandler, infoHandler);

        // same values -> returns true
        InfoHandler infoHandlerCopy = new InfoHandler(modelManager);
        assertEquals(infoHandler, infoHandlerCopy);

        // different model -> returns false
        Model newModel = new ModelManager();
        GuiSettings guiSettings = new GuiSettings(100, 200, 1, 0);
        newModel.setGuiSettings(guiSettings);
        InfoHandler infoHandlerDifferentModel = new InfoHandler(newModel);
        assertNotEquals(infoHandler, infoHandlerDifferentModel);

        // different types -> returns false
        assertNotEquals(infoHandler, 1);

        // null -> returns false
        assertNotEquals(infoHandler, null);
    }

    /**
     * A Model stub that has a InfoHandler alongside a list for each data type.
     */
    private static class ModelStubListsWithOneObject extends ModelStub {
        private final InfoHandler infoHandler = new InfoHandler(this);
        private ObservableList<Person> personsList = null;
        private ObservableList<Location> locationsList = null;
        private ObservableList<Visit> visitsList = null;

        public ModelStubListsWithOneObject(List<Person> personsList, List<Location> locationsList,
                                       List<Visit> visitsList) {
            if (personsList != null) {
                this.personsList = FXCollections.observableList(personsList);
            }

            if (locationsList != null) {
                this.locationsList = FXCollections.observableList(locationsList);
            }

            if (visitsList != null) {
                this.visitsList = FXCollections.observableList(visitsList);
            }
        }

        @Override
        public ObservableList<Person> getSortedPersonList() {
            return personsList;
        }

        @Override
        public ObservableList<Location> getSortedLocationList() {
            return locationsList;
        }

        @Override
        public ObservableList<Visit> getSortedVisitList() {
            return visitsList;
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            return obj instanceof ModelStubListsWithOneObject;
        }
    }
}
