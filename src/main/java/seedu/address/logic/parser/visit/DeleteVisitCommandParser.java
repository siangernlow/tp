package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.visit.DeleteVisitCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteVisitCommand object
 */

public class DeleteVisitCommandParser implements Parser<DeleteVisitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteVisitCommand
     * and returns a DeleteVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteVisitCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteVisitCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVisitCommand.MESSAGE_USAGE), pe);
        }
    }
}
