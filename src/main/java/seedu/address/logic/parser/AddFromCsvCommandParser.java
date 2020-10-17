package seedu.address.logic.parser;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.location.AddLocationsFromCsvCommand;
import seedu.address.logic.commands.person.AddPersonsFromCsvCommand;
import seedu.address.logic.commands.visit.AddVisitsFromCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

public class AddFromCsvCommandParser implements Parser<AddFromCsvCommand> {
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String MESSAGE_INVALID_FILE_EXTENSION = "Invalid file extension."
            + " Ensure that the file has the extension '%1$s'";
    public static final String MESSAGE_INVALID_DATA_TYPE = "That data type is not supported by this command."
            + "\n%1$s";
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

        ListType listType = ParserUtil.parseListType(argMultimap.getValue(PREFIX_LIST).get());
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
            List<DataGenerator.VisitParameters> visitParametersList = DataGenerator.generateVisitsList(filepath);
            return new AddVisitsFromCsvCommand(visitParametersList);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
        }
    }

    private boolean checkIfValidCsvExtension(String filepath) throws ParseException {
        int pathLength = filepath.length();
        int startIndex = pathLength - CSV_FILE_EXTENSION.length();
        int endIndex = pathLength;

        if (startIndex < 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILE_EXTENSION, CSV_FILE_EXTENSION));
        }
        return filepath.substring(startIndex, endIndex).equals(CSV_FILE_EXTENSION);
    }
}
