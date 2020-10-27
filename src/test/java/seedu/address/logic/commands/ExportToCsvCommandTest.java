package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCsvFilesAreEqual;
import static seedu.address.logic.commands.ExportToCsvCommand.LOCATIONS;
import static seedu.address.logic.commands.ExportToCsvCommand.MESSAGE_NO_OBJECTS_TO_ADD;
import static seedu.address.logic.commands.ExportToCsvCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.ExportToCsvCommand.PERSONS;
import static seedu.address.logic.commands.ExportToCsvCommand.VISITS;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ListType;
import seedu.address.model.InfoHandler;
import seedu.address.model.ModelStub;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.TypicalLocations;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalVisits;

/**
 * These tests work under the assumption that the InfoHandler class performs correctly.
 * They serve as an integration test between the ExportToCsvCommand and InfoHandler classes.
 */
public class ExportToCsvCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "ExportToCsvCommandTest");

    private static final String OUTPUT_CSV_FILE =
            TEST_DATA_FOLDER.resolve("output.csv").toString();
    private static final String OUTPUT_CSV_FILE_ALT =
            TEST_DATA_FOLDER.resolve("outputAlt.csv").toString();
    private static final String PERSONS_LIST_FILE =
            TEST_DATA_FOLDER.resolve("TypicalShortenedPersonsList.csv").toString();
    private static final String LOCATIONS_LIST_FILE =
            TEST_DATA_FOLDER.resolve("TypicalShortenedLocationsList.csv").toString();
    private static final String VISITS_LIST_FILE =
            TEST_DATA_FOLDER.resolve("TypicalShortenedVisitsList.csv").toString();
    private static final ListType VALID_LIST_TYPE = ListType.ALL_PEOPLE;
    private static final ListType INVALID_LIST_TYPE = ListType.ALL_INFECTED;
    private static FileWriter fileWriter;

    /**
     * Clear the output file after each test.
     */
    @AfterEach
    public void resetFile() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
            fileWriter.close();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ExportToCsvCommand(null, OUTPUT_CSV_FILE, VALID_LIST_TYPE));
        assertThrows(NullPointerException.class, () ->
                new ExportToCsvCommand(fileWriter, null, VALID_LIST_TYPE));
        assertThrows(NullPointerException.class, () ->
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, null));
    }

    @Test
    public void execute_argsValidListTypePeople_exportSuccessful() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(TypicalPersons.getShortenedTypicalPersons(),
                null, null);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, VALID_LIST_TYPE);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, PERSONS));
        assertCommandSuccess(command, model, commandResult, model);
        assertCsvFilesAreEqual(PERSONS_LIST_FILE, OUTPUT_CSV_FILE);
    }

    @Test
    public void execute_argsValidListTypeLocation_exportSuccessful() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(null,
                TypicalLocations.getShortenedTypicalLocations(), null);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, ListType.ALL_LOCATIONS);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, LOCATIONS));
        assertCommandSuccess(command, model, commandResult, model);
        assertCsvFilesAreEqual(LOCATIONS_LIST_FILE, OUTPUT_CSV_FILE);
    }

    @Test
    public void execute_argsValidListTypeVisit_exportSuccessful() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(null, null,
                TypicalVisits.getVisitsForTest());
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, ListType.ALL_VISITS);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, VISITS));
        assertCommandSuccess(command, model, commandResult, model);
        assertCsvFilesAreEqual(VISITS_LIST_FILE, OUTPUT_CSV_FILE);
    }

    @Test
    public void execute_personsListIsEmpty_throwsCommandException() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        List<Person> emptyPersonList = new ArrayList<>();
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(emptyPersonList, null, null);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, VALID_LIST_TYPE);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_OBJECTS_TO_ADD, PERSONS), ()
            -> command.execute(model));
    }

    @Test
    public void execute_locationsListIsEmpty_throwsCommandException() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        List<Location> emptyLocationList = new ArrayList<>();
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(null, emptyLocationList, null);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, ListType.ALL_LOCATIONS);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_OBJECTS_TO_ADD, LOCATIONS), ()
            -> command.execute(model));
    }

    @Test
    public void execute_visitsListIsEmpty_throwsCommandException() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        List<Visit> emptyVisitList = new ArrayList<>();
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(null, null, emptyVisitList);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, ListType.ALL_VISITS);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_OBJECTS_TO_ADD, VISITS), ()
            -> command.execute(model));
    }

    @Test
    public void execute_invalidListType_throwsCommandException() {
        try {
            fileWriter = new FileWriter(OUTPUT_CSV_FILE);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
        ModelStubHasInfoHandler model = new ModelStubHasInfoHandler(null, null, null);
        ExportToCsvCommand command =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, INVALID_LIST_TYPE);
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_DATA_TYPE,
            ExportToCsvCommand.MESSAGE_USAGE), () -> command.execute(model));
    }

    @Test
    public void equals() {
        // The file writer is not considered in equality checks.

        ExportToCsvCommand exportPersonCsvCommand =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, VALID_LIST_TYPE);
        ExportToCsvCommand exportLocationCsvCommand =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, ListType.ALL_LOCATIONS);

        // same object -> returns true
        assertEquals(exportPersonCsvCommand, exportPersonCsvCommand);

        // same values -> returns true
        ExportToCsvCommand exportToCsvCommandCopy =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE, VALID_LIST_TYPE);
        assertEquals(exportToCsvCommandCopy, exportPersonCsvCommand);

        // different types -> returns false
        assertNotEquals(exportPersonCsvCommand, 1);

        // null -> returns false
        assertNotEquals(exportPersonCsvCommand, null);

        // different type of lists -> returns false
        assertNotEquals(exportLocationCsvCommand, exportPersonCsvCommand);

        // different output paths -> returns false
        ExportToCsvCommand exportPersonCsvCommandDifferentOutputFile =
                new ExportToCsvCommand(fileWriter, OUTPUT_CSV_FILE_ALT, VALID_LIST_TYPE);
        assertNotEquals(exportPersonCsvCommandDifferentOutputFile, exportPersonCsvCommand);
    }

    /**
     * A Model stub that has a InfoHandler alongside a list for each data type.
     */
    private static class ModelStubHasInfoHandler extends ModelStub {
        private final InfoHandler infoHandler = new InfoHandler(this);
        private ObservableList<Person> personsList = null;
        private ObservableList<Location> locationsList = null;
        private ObservableList<Visit> visitsList = null;

        public ModelStubHasInfoHandler(List<Person> personsList, List<Location> locationsList,
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

            return obj instanceof ModelStubHasInfoHandler;
        }
    }
}
