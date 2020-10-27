package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.visit.AddVisitCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Id;

/**
 * Parses input arguments and creates a new AddVisitCommand object
 */
public class AddVisitCommandParser implements Parser<AddVisitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddVisitCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddVisitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_LOCATION_ID, PREFIX_PERSON_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }

        if ((!arePrefixesPresent(argMultimap, PREFIX_LOCATION_ID)
                && !arePrefixesPresent(argMultimap, PREFIX_PERSON_ID)
                && argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }

        if ((arePrefixesPresent(argMultimap, PREFIX_LOCATION_ID) || arePrefixesPresent(argMultimap, PREFIX_PERSON_ID))
                && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }

        AddVisitCommand addVisitCommand = null;
        if (arePrefixesPresent(argMultimap, PREFIX_LOCATION_ID, PREFIX_PERSON_ID)) {
            addVisitCommand = parseId(argMultimap);
        } else if (!argMultimap.getPreamble().isEmpty()) {
            addVisitCommand = parseIndex(argMultimap);
        }
        assert addVisitCommand != null : "All arguments should have been either IDs or indexes.";
        return addVisitCommand;
    }

    private AddVisitCommand parseId(ArgumentMultimap argMultimap) throws ParseException {
        LocalDate date;
        Id personId;
        Id locationId;
        try {
            personId = ParserUtil.parseId(argMultimap.getValue(PREFIX_PERSON_ID).get());
            locationId = ParserUtil.parseId(argMultimap.getValue(PREFIX_LOCATION_ID).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE), pe);
        }

        try {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, AddVisitCommand.MESSAGE_USAGE), pe);
        }

        return new AddVisitCommand(personId, locationId, date);
    }

    private AddVisitCommand parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        String[] indexes = argMultimap.getPreamble().split("\\s+");

        if (indexes.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        Index locationIndex;

        try {
            personIndex = ParserUtil.parseIndex(indexes[0]);
            locationIndex = ParserUtil.parseIndex(indexes[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE), pe);
        }

        LocalDate date;

        try {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, AddVisitCommand.MESSAGE_USAGE), pe);
        }

        return new AddVisitCommand(personIndex, locationIndex, date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
