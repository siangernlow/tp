package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser implements Parser<ListCommand> {

    public static final String VALIDATION_REGEX = "^[a-zA-Z]*$";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Checks if the args are just whitespace, or if they are not purely alphabetic.
        if (trimmedArgs.isEmpty() || !trimmedArgs.matches(VALIDATION_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        ListType listType = ParserUtil.parseListType(trimmedArgs.toLowerCase());
        return new ListCommand(listType);
    }
}
