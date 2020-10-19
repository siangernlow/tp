package seedu.address.logic.parser.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.location.DeleteLocationCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Id;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LOCATION_ID);

        if (argMultimap.getValue(PREFIX_LOCATION_ID).isPresent() && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLocationCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_LOCATION_ID).isPresent()) {
            Id id = ParserUtil.parseId(argMultimap.getValue(PREFIX_LOCATION_ID).get());
            return new DeleteLocationCommand(id);
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLocationCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLocationCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
