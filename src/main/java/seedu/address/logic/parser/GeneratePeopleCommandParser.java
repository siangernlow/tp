package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GeneratePeopleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GenerateLocationsCommand object
 */
public class GeneratePeopleCommandParser implements Parser<GeneratePeopleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GeneratePeopleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.length() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GeneratePeopleCommand.MESSAGE_USAGE));
        }

        try {
            Index personId = Index.fromOneBased(Integer.valueOf(trimmedArgs));
            return new GeneratePeopleCommand(personId);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}
