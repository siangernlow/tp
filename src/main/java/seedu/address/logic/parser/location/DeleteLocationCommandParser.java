package seedu.address.logic.parser.location;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.IndexIdPair.checkIndexOrIdOnly;

import seedu.address.logic.commands.location.DeleteLocationCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.logic.parser.Parser;
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
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LOCATION_ID);

        if (!checkIndexOrIdOnly(argMultimap, PREFIX_LOCATION_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLocationCommand.MESSAGE_USAGE));
        }

        IndexIdPair pair = new IndexIdPair(argMultimap, PREFIX_LOCATION_ID);
        return new DeleteLocationCommand(pair);
    }
}
