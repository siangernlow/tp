package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.logic.parser.AddFromCsvCommandParser.CSV_FILE_EXTENSION;
import static seedu.address.logic.parser.AddFromCsvCommandParser.MESSAGE_INVALID_FILE_EXTENSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ExportToCsvCommandParser.MESSAGE_FILE_CANNOT_BE_CREATED;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportToCsvCommand;

public class ExportToCsvCommandParserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "ExportToCsvCommandTest");

    private static final String INVALID_FILE_EXTENSION = "validFileNameButWrongExtension.xlsx";
    private static final String INVALID_FILE_PATH = ".questio/nMa:rk?InName.csv";

    private static final String FILE_PATH_TOO_SHORT = ".cs";
    private static final String FILE_EXISTS_AND_IS_WRITABLE =
            TEST_DATA_FOLDER.resolve("dummyFile.csv").toString();
    private static final String FILE_HAS_TO_BE_CREATED =
            TEST_DATA_FOLDER.resolve("fileDeletedAfterTest.csv").toString();

    private static final ExportToCsvCommandParser parser = new ExportToCsvCommandParser();

    private static final ListType VALID_LIST_TYPE = ListType.ALL_PEOPLE;
    private static FileWriter fileWriter;

    @Test
    public void parse_validArgsPeople_returnsExportToCsvCommand() {
        try {
            fileWriter = new FileWriter(FILE_EXISTS_AND_IS_WRITABLE);
            ExportToCsvCommand expectedExportToCsvCommand =
                    new ExportToCsvCommand(fileWriter, FILE_EXISTS_AND_IS_WRITABLE, VALID_LIST_TYPE);
            String userInput = FILE_EXISTS_AND_IS_WRITABLE + " " + PREFIX_LIST + "people";
            assertParseSuccess(parser, userInput, expectedExportToCsvCommand);
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
    }

    @Test
    public void parse_fileExistsCanBeWrittenTo_returnsExportToCsvCommand() {
        try {
            File file = new File(FILE_EXISTS_AND_IS_WRITABLE);
            assertTrue(file.exists());

            fileWriter = new FileWriter(FILE_EXISTS_AND_IS_WRITABLE);
            ExportToCsvCommand expectedExportToCsvCommand =
                    new ExportToCsvCommand(fileWriter, FILE_EXISTS_AND_IS_WRITABLE, VALID_LIST_TYPE);
            String userInput = FILE_EXISTS_AND_IS_WRITABLE + " " + PREFIX_LIST + "people";
            assertParseSuccess(parser, userInput, expectedExportToCsvCommand);

        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
    }

    @Test
    public void parse_fileDoesNotExistButCanBeCreated_returnsExportToCsvCommand() {
        try {
            // The file is deleted after this test to ensure future tests have the same behaviour.
            File file = new File(FILE_HAS_TO_BE_CREATED);
            file.deleteOnExit();

            // As fileWriter is not used for equality checks, we use an existing file here to prevent it from
            // creating the file that has to be created within the command.
            fileWriter = new FileWriter(FILE_EXISTS_AND_IS_WRITABLE);
            ExportToCsvCommand expectedExportToCsvCommand =
                    new ExportToCsvCommand(fileWriter, FILE_HAS_TO_BE_CREATED, VALID_LIST_TYPE);
            String userInput = FILE_HAS_TO_BE_CREATED + " " + PREFIX_LIST + "people";
            assertParseSuccess(parser, userInput, expectedExportToCsvCommand);
            assertTrue(file.exists());
        } catch (IOException e) {
            // File path should be valid, should not reach here.
            fail();
        }
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportToCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPreamble_throwsParseException() {
        String userInput = PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportToCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixMissing_throwsParseException() {
        String userInput = INVALID_FILE_EXTENSION + " " + "people";
        assertParseFailure(parser, " people", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportToCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_filePathTooShort_throwsParseException() {
        String userInput = FILE_PATH_TOO_SHORT + " " + PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
    }

    @Test
    public void parse_invalidFileExtension_throwsParseException() {
        String userInput = INVALID_FILE_EXTENSION + " " + PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
    }

    @Test
    public void parse_invalidListType_throwsParseException() {
        String userInput = FILE_EXISTS_AND_IS_WRITABLE + " " + PREFIX_LIST + "invalidInput";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_DATA_TYPE, ExportToCsvCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFilePath_throwsParseException() {
        String userInput = INVALID_FILE_PATH + " " + PREFIX_LIST + "people";
        assertParseFailure(parser, userInput, MESSAGE_FILE_CANNOT_BE_CREATED);
    }

}
