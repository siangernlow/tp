package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GeneratePeopleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GeneratePeopleCommand object
 */
public class GeneratePeopleCommandParser implements Parser<GeneratePeopleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GeneratePeopleCommand
     * and returns a GeneratePeopleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GeneratePeopleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GeneratePeopleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GeneratePeopleCommand.MESSAGE_USAGE), pe);
        }
    }

}
