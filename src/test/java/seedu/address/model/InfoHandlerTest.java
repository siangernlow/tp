package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLocations.getTypicalLocations;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

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
}
