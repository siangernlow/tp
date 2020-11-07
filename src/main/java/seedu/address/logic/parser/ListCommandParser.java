package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.ListType.HIGH_RISK_LOCATIONS;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIST);
        if (argMultimap.getValue(PREFIX_LIST).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        ListType listType = ParserUtil.parseListType(argMultimap.getValue(PREFIX_LIST).get());

        if (listType != HIGH_RISK_LOCATIONS && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // highRiskLocationNumber would equal to user specified number iff listType is HIGH_RISK_LOCATIONS and
        // user specify the number of high risk locations. Otherwise, the value of highRiskLocationNumber is -1.
        int highRiskLocationNumber;
        if (listType == HIGH_RISK_LOCATIONS) {
            if (argMultimap.getPreamble().isEmpty()) {
                highRiskLocationNumber = -1;
            } else {
                try {
                    highRiskLocationNumber = Integer.parseInt(argMultimap.getPreamble());
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
                }
            }
        } else {
            highRiskLocationNumber = -1;
        }

        return new ListCommand(listType, !argMultimap.getPreamble().isEmpty(), highRiskLocationNumber);
    }
}
