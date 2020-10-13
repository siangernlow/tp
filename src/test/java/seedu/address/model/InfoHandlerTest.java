package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.InfoHandler.getIdHashSetFromPersonsList;
import static seedu.address.model.InfoHandler.getLocationIdsFromInfectedVisitList;
import static seedu.address.model.InfoHandler.getNumberOfHighRiskLocations;
import static seedu.address.model.InfoHandler.sortByValues;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLocations.getTypicalLocations;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalVisits;

public class InfoHandlerTest {
    private ModelManager modelManager = new ModelManager();
    private InfoHandler infoHandler = new InfoHandler(modelManager);
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
        assertEquals(modelManager.getFilteredPersonList(), infoHandler.getPersonList());
    }

    @Test
    public void getLocationList_validList_returnTrue() {
        assertEquals(modelManager.getFilteredLocationList(), infoHandler.getLocationList());
    }

    @Test
    public void getVisitList_validList_returnTrue() {
        assertEquals(modelManager.getFilteredVisitList(), infoHandler.getVisitList());
    }

    @Test
    public void updateModelPersonList_dummyPredicate_returnsTrue() {
        Predicate<Person> predicateAlwaysTrue = unused -> true;
        modelManager.updateFilteredPersonList(predicateAlwaysTrue);
        assertEquals(modelManager.getFilteredPersonList(),
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
    public void getIdHashSetFromPersonsList_success() {
        List<Person> typicalPersons = TypicalPersons.getTypicalPersons();
        HashSet<Index> expectedPersonsIds = TypicalPersons.getIdsOfTypicalPersonsAsHashSet();
        assertEquals(expectedPersonsIds, getIdHashSetFromPersonsList(typicalPersons));
    }

    @Test
    public void getLocationIdsFromVisitList_success() {
        List<Visit> typicalVisits = TypicalVisits.getVisitsForTest();
        List<Index> expectedLocationIds = TypicalVisits.getLocationsIdsFromVisitsForTest();
        assertEquals(expectedLocationIds, getLocationIdsFromInfectedVisitList(typicalVisits));
    }

    @Test
    public void sortByValues_success() {
        List<Visit> visits = TypicalVisits.getVisitsForTest();
        HashMap<Index, Integer> locations = new HashMap<>();
        for (Visit visit : visits) {
            Index id = visit.getLocation().getId();
            if (locations.containsKey(id)) {
                locations.put(id, locations.get(id) + 1);
            } else {
                locations.put(id, 1);
            }
        }
        HashMap<Index, Integer> actualHashMap = sortByValues(locations);
        HashMap<Index, Integer> expectedHashMap = new LinkedHashMap<>();
        expectedHashMap.put(INDEX_FIRST, 3);
        expectedHashMap.put(INDEX_SECOND, 1);
        expectedHashMap.put(INDEX_FIFTH, 1);
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
}
