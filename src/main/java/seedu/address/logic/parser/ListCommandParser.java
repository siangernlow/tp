package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser implements Parser<ListCommand> {
    private static final String INVALID_LIST_TYPE = "There is no such type of list.";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        ListCommand.ListType listType = parseListType(trimmedArgs);
        return new ListCommand(listType);
    }

    /**
     * Parses the given keyword and gives the enum representing it.
     *
     * @param keyword The keyword to be processed.
     * @return The enum representing the keyword.
     * @throws ParseException if there is no match for the enums.
     */
    private ListCommand.ListType parseListType(String keyword) throws ParseException {
        switch (keyword) {
        case "persons":
            return ListCommand.ListType.PERSONS;
        default:
            throw new ParseException(INVALID_LIST_TYPE);
        }
    }
}
