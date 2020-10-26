package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.IndexIdPair.checkIndexOrIdOnly;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_ID);

        if (!checkIndexOrIdOnly(argMultimap, PREFIX_PERSON_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GeneratePeopleCommand.MESSAGE_USAGE));
        }

        IndexIdPair pair = new IndexIdPair(argMultimap, PREFIX_PERSON_ID);
        return new GeneratePeopleCommand(pair);
    }
}
