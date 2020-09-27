package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;

/**
 * Parses input arguments and creates a new AddVisitCommand object
 */

public class AddVisitCommandParser implements Parser<AddVisitCommand>{

    public AddVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index personId;
        Index locationId;
        String date =  "2020-09-09";
        try {
            personId = ParserUtil.parseIndex(argMultimap.getPreamble());
            locationId = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,  AddVisitCommand.MESSAGE_USAGE), ive);
        }

        return new AddVisitCommand(personId, locationId, date);
    }

}
