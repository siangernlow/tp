package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExportToCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportToCsvCommand object.
 */
public class ExportToCsvCommandParser implements Parser<ExportToCsvCommand> {
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String MESSAGE_INVALID_FILE_EXTENSION = "Invalid file extension."
            + " Ensure that the file has the extension '%1$s'";
    public static final String MESSAGE_FILE_CANNOT_BE_CREATED = "The file cannot be created at the specified "
            + "file path. Please try another file path.";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportToCsvCommand
     * and returns a ExportToCsvCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportToCsvCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIST);
        if (argMultimap.getValue(PREFIX_LIST).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportToCsvCommand.MESSAGE_USAGE));
        }

        ListType listType;
        try {
            listType = ParserUtil.parseListType(argMultimap.getValue(PREFIX_LIST).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_TYPE, ExportToCsvCommand.MESSAGE_USAGE));
        }

        String filepath = argMultimap.getPreamble();

        File file = new File(filepath);

        // This method below also creates the file if possible.
        if (!checkIfValidFilePath(file)) {
            throw new ParseException(MESSAGE_FILE_CANNOT_BE_CREATED);
        }

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new ParseException(MESSAGE_FILE_CANNOT_BE_CREATED);
        }

        return new ExportToCsvCommand(fileWriter, listType);
    }

    /**
     * Checks if the provided file is a valid file, or can be created.
     *
     * @param file The file which can either exist or be created.
     * @return True if the file path is valid, false otherwise
     * @throws ParseException as specified in the {@code checkIfValidCsvExtension} method.
     */
    private boolean checkIfValidFilePath(File file) throws ParseException {
        try {
            /* Check if the file to be created is a csv file AND
               either the file exists OR the file can be created at the file path */
            return checkIfValidCsvExtension(file.getPath())
                    && (file.exists() || file.createNewFile());
        } catch (IOException e) {
            throw new ParseException(MESSAGE_FILE_CANNOT_BE_CREATED);
        }
    }

    /**
     * Checks if the file path has the ".csv" extension.
     *
     * @param filepath The absolute file path of the file.
     * @return true if the specified file is a CSV file, false otherwise.
     * @throws ParseException if the filepath is too short to have a csv extension behind it.
     */
    private boolean checkIfValidCsvExtension(String filepath) throws ParseException {
        int pathLength = filepath.length();
        int startIndex = pathLength - CSV_FILE_EXTENSION.length();

        if (startIndex < 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
        }
        return filepath.substring(startIndex, pathLength).equals(CSV_FILE_EXTENSION);
    }

}
