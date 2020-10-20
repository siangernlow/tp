package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.logic.parser.AddFromCsvCommandParser.CSV_FILE_EXTENSION;
import static seedu.address.logic.parser.AddFromCsvCommandParser.MESSAGE_INVALID_FILE_EXTENSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.location.AddLocationsFromCsvCommand;
import seedu.address.logic.commands.person.AddPersonsFromCsvCommand;
import seedu.address.logic.commands.visit.AddVisitsFromCsvCommand;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

public class AddFromCsvCommandParserTest {

    private static final String INVALID_FILE_EXTENSION = "validFileNameButWrongExtension.xlsx";

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "AddFromCsvCommandTest");
    private static final String EMPTY_CSV_FILE = TEST_DATA_FOLDER.resolve("EmptyCsvFile.csv").toString();

    private final AddFromCsvCommandParser parser = new AddFromCsvCommandParser();

    @Test
    public void parse_validArgsPeople_returnsAddPersonsFromCsvCommand() {
        List<Person> dummyPersonsList = new ArrayList<>();
        AddPersonsFromCsvCommand expectedAddPersonsFromCsvCommand = new AddPersonsFromCsvCommand(dummyPersonsList);
        String userInput = EMPTY_CSV_FILE + " " + PREFIX_LIST + "people";
        assertParseSuccess(parser, userInput, expectedAddPersonsFromCsvCommand);
    }

    @Test
    public void parse_validArgsLocations_returnsAddLocationsFromCsvCommand() {
        List<Location> dummyLocationsList = new ArrayList<>();
        AddLocationsFromCsvCommand expectedAddLocationsFromCsvCommand =
                new AddLocationsFromCsvCommand(dummyLocationsList);
        String userInput = EMPTY_CSV_FILE + " " + PREFIX_LIST + "locations";
        assertParseSuccess(parser, userInput, expectedAddLocationsFromCsvCommand);
    }

    @Test
    public void parse_validArgsVisits_returnsAddVisitsFromCsvCommand() {
        List<DataGenerator.VisitParametersContainer> dummyVisitsList = new ArrayList<>();
        AddVisitsFromCsvCommand expectedAddVisitsFromCsvCommand = new AddVisitsFromCsvCommand(dummyVisitsList);
        String userInput = EMPTY_CSV_FILE + " " + PREFIX_LIST + "visits";
        assertParseSuccess(parser, userInput, expectedAddVisitsFromCsvCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFromCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPreamble_throwsParseException() {
        String userInput = PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFromCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixMissing_throwsParseException() {
        String userInput = INVALID_FILE_EXTENSION + " " + "people";
        assertParseFailure(parser, " people", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFromCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFileExtension_throwsParseException() {
        String userInput = INVALID_FILE_EXTENSION + " " + PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
    }

    @Test
    public void parse_filePathTooShort_throwsParseException() {
        String userInput = "not" + " " + PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
    }

    @Test
    public void parse_invalidListtype_throwsParseException() {
        String userInput = EMPTY_CSV_FILE + " " + PREFIX_LIST + "invalidInput";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unavailableListType_throwsParseException() {
        // infected is a valid list type, but not supported by this command.
        String userInput = EMPTY_CSV_FILE + " " + PREFIX_LIST + "infected";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
    }
}
