package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.TypicalLocations;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalVisitParametersContainers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_DATA_FORMAT;
import static seedu.address.logic.parser.DataGenerator.INVALID_ROW_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;


public class DataGeneratorTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "AddFromCsvCommandTest");
    private static final String INVALID_FILE_PATH = "validFileNameButFileDoesNotExist.csv";

    private static final String VALID_PERSONS_CSV_FILE = TEST_DATA_FOLDER.resolve("PersonsList.csv").toString();
    private static final String PERSONS_LIST_INVALID_PHONE_SECOND_ROW =
            TEST_DATA_FOLDER.resolve("PersonsListInvalidPhoneSecondRow.csv").toString();
    private static final String PERSONS_LIST_MISSING_ADDRESS_FIRST_ROW =
            TEST_DATA_FOLDER.resolve("PersonsListMissingNameFirstRow.csv").toString();

    private static final String VALID_LOCATIONS_CSV_FILE = TEST_DATA_FOLDER.resolve("LocationsList.csv").toString();
    private static final String LOCATIONS_LIST_INVALID_NAME_SECOND_ROW =
            TEST_DATA_FOLDER.resolve("LocationsListInvalidNameSecondRow.csv").toString();
    private static final String LOCATIONS_LIST_MISSING_ADDRESS_SECOND_ROW =
            TEST_DATA_FOLDER.resolve("LocationsListMissingAddressSecondRow.csv").toString();

    private static final String VALID_VISITS_CSV_FILE = TEST_DATA_FOLDER.resolve("VisitsList.csv").toString();
    private static final String VISITS_LIST_INVALID_DATE_SECOND_ROW =
            TEST_DATA_FOLDER.resolve("VisitsListInvalidDateSecondRow.csv").toString();
    private static final String VISITS_LIST_MISSING_DATE_FIRST_ROW =
            TEST_DATA_FOLDER.resolve("VisitsListMissingDateFirstRow.csv").toString();

    //======= Custom equality checks to avoid comparing id ==========
    private static final BiFunction<Person, Person, Boolean> personEqualityCheckWithoutId = (person, otherPerson) ->
            otherPerson.getName().equals(person.getName())
            && otherPerson.getPhone().equals(person.getPhone())
            && otherPerson.getEmail().equals(person.getEmail())
            && otherPerson.getAddress().equals(person.getAddress())
            && otherPerson.getQuarantineStatus().equals(person.getQuarantineStatus())
            && otherPerson.getInfectionStatus().equals(person.getInfectionStatus())
            && otherPerson.getTags().equals(person.getTags());

    private static final BiFunction<Location, Location, Boolean> locationEqualityCheckWithoutId =
        (location, otherLocation) ->
            otherLocation.getName().equals(location.getName())
            && otherLocation.getAddress().equals(location.getAddress());

     // ============ Generating People Tests ======================================
    @Test
    public void generatePersonsList_validFilePath_success() throws Exception {
        List<Person> actualPersonsList = DataGenerator.generatePersonsList(VALID_PERSONS_CSV_FILE);
        List<Person> expectedPersonsList = TypicalPersons.getTypicalPersons();

        assertEquals(expectedPersonsList.size(), actualPersonsList.size());

        for (int i = 0; i < expectedPersonsList.size(); i++) {
            assertTrue(personEqualityCheckWithoutId.apply(expectedPersonsList.get(i), actualPersonsList.get(i)));
        }
    }

    @Test
    public void generatePersonsList_invalidFilePath_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_FILE_PATH, ()
            -> DataGenerator.generatePersonsList(INVALID_FILE_PATH));
    }

    @Test
    public void generatePersonsList_invalidRow_throwsParseException() {
        String invalidPhone = "not a valid phone number";

        try {
            ParserUtil.parsePhone(invalidPhone);
        } catch (ParseException pe) {
            String expectedErrorMessage = String.format(INVALID_ROW_FORMAT, 2, pe.getMessage());
            assertThrows(ParseException.class, expectedErrorMessage, ()
                    -> DataGenerator.generatePersonsList(PERSONS_LIST_INVALID_PHONE_SECOND_ROW));
        }
    }

    @Test
    public void generatePersonsList_missingCompulsoryField_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_MISSING_DATA_FORMAT, 1);
        assertThrows(ParseException.class, expectedErrorMessage, ()
                -> DataGenerator.generatePersonsList(PERSONS_LIST_MISSING_ADDRESS_FIRST_ROW));
    }

    // ============ Generating Locations Tests ======================================
    @Test
    public void generateLocationsList_validFilePath_success() throws Exception {
        List<Location> actualLocationsList = DataGenerator.generateLocationsList(VALID_LOCATIONS_CSV_FILE);
        List<Location> expectedLocationsList = TypicalLocations.getTypicalLocations();

        assertEquals(expectedLocationsList.size(), actualLocationsList.size());

        for (int i = 0; i < expectedLocationsList.size(); i++) {
            assertTrue(locationEqualityCheckWithoutId.apply(expectedLocationsList.get(i), actualLocationsList.get(i)));
        }
    }

    @Test
    public void generateLocationsList_invalidFilePath_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_FILE_PATH, ()
                -> DataGenerator.generateLocationsList(INVALID_FILE_PATH));
    }

    @Test
    public void generateLocationsList_invalidRow_throwsParseException() {
        String invalidName = "M@rina Bay Sands";

        try {
            ParserUtil.parseName(invalidName);
        } catch (ParseException pe) {
            String expectedErrorMessage = String.format(INVALID_ROW_FORMAT, 2, pe.getMessage());
            assertThrows(ParseException.class, expectedErrorMessage, ()
                    -> DataGenerator.generateLocationsList(LOCATIONS_LIST_INVALID_NAME_SECOND_ROW));
        }
    }

    @Test
    public void generateLocationsList_missingCompulsoryField_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_MISSING_DATA_FORMAT, 2);
        assertThrows(ParseException.class, expectedErrorMessage, ()
                -> DataGenerator.generateLocationsList(LOCATIONS_LIST_MISSING_ADDRESS_SECOND_ROW));
    }

    // ============ Generating Visits Tests ======================================
    @Test
    public void generateVisitsList_validFilePath_success() throws Exception {
        List<DataGenerator.VisitParametersContainer> actualVisitsParametersContainersList =
                DataGenerator.generateVisitsList(VALID_VISITS_CSV_FILE);

        List<DataGenerator.VisitParametersContainer> expectedVisitsParametersContainersList =
                TypicalVisitParametersContainers.getTypicalVpc();

        assertEquals(expectedVisitsParametersContainersList, actualVisitsParametersContainersList);
    }

    @Test
    public void generateVisitsList_invalidFilePath_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_FILE_PATH, ()
                -> DataGenerator.generateVisitsList(INVALID_FILE_PATH));
    }

    @Test
    public void generateVisitsList_invalidRow_throwsParseException() {
        String invalidDate = "2020-90-90";

        try {
            ParserUtil.parseDate(invalidDate);
        } catch (ParseException pe) {
            String expectedErrorMessage = String.format(INVALID_ROW_FORMAT, 2, pe.getMessage());
            assertThrows(ParseException.class, expectedErrorMessage, ()
                    -> DataGenerator.generateVisitsList(VISITS_LIST_INVALID_DATE_SECOND_ROW));
        }
    }

    @Test
    public void generateVisitsList_missingCompulsoryField_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_MISSING_DATA_FORMAT, 1);
        assertThrows(ParseException.class, expectedErrorMessage, ()
                -> DataGenerator.generateVisitsList(VISITS_LIST_MISSING_DATE_FIRST_ROW));
    }
}
