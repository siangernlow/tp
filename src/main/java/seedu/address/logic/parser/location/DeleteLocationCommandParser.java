package seedu.address.logic.parser.location;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.location.DeleteLocationCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLocationCommand object
 */
public class DeleteLocationCommandParser implements Parser<DeleteLocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLocationCommand
     * and returns a DeleteLocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteLocationCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLocationCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLocationCommand.MESSAGE_USAGE), pe);
        }

    }
}
