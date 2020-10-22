package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

import java.util.List;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.location.AddLocationsFromCsvCommand;
import seedu.address.logic.commands.person.AddPersonsFromCsvCommand;
import seedu.address.logic.commands.visit.AddVisitsFromCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new AddFromCsvCommand object.
 * The created object is a child class of the AddFromCsvCommand class,
 * handling either adding people, locations or visits depending on the
 * input arguments.
 */
public class AddFromCsvCommandParser implements Parser<AddFromCsvCommand> {
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String MESSAGE_INVALID_FILE_EXTENSION = "Invalid file extension."
            + " Ensure that the file has the extension '%1$s'";

    /**
     * Parses the given {@code String} of arguments in the context of the AddFromCsvCommand
     * and returns a AddFromCsvCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFromCsvCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIST);
        if (argMultimap.getValue(PREFIX_LIST).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFromCsvCommand.MESSAGE_USAGE));
        }

        ListType listType;
        try {
            listType = ParserUtil.parseListType(argMultimap.getValue(PREFIX_LIST).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
        }
        String filepath = argMultimap.getPreamble();

        if (!checkIfValidCsvExtension(filepath)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
        }

        switch (listType) {
        case ALL_PEOPLE:
            List<Person> personsList = DataGenerator.generatePersonsList(filepath);
            return new AddPersonsFromCsvCommand(personsList);
        case ALL_LOCATIONS:
            List<Location> locationsList = DataGenerator.generateLocationsList(filepath);
            return new AddLocationsFromCsvCommand(locationsList);
        case ALL_VISITS:
            List<DataGenerator.VisitParametersContainer> visitParametersContainersList =
                    DataGenerator.generateVisitsList(filepath);
            return new AddVisitsFromCsvCommand(visitParametersContainersList);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
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
