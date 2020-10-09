package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private static final ListType LOCATIONS_LIST = ListType.ALL_LOCATIONS;
    private static final ListType PEOPLE_LIST = ListType.ALL_PEOPLE;
    private static final ListType VISITS_LIST = ListType.ALL_VISITS;
    private static final ListType INFECTED_LIST = ListType.ALL_INFECTED;
    private static final ListType QUARANTINED_LIST = ListType.ALL_QUARANTINED;
    private static final ListType STATISTICS_LIST = ListType.STATISTICS;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parseListType_people() {
        ListCommand expectedListCommand =
                new ListCommand(PEOPLE_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "people", expectedListCommand);
    }

    @Test
    public void parseListType_locations() {
        ListCommand expectedListCommand =
                new ListCommand(LOCATIONS_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "locations", expectedListCommand);
    }

    @Test
    public void parseListType_visits() {
        ListCommand expectedListCommand =
                new ListCommand(VISITS_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "visits", expectedListCommand);
    }

    @Test
    public void parseListType_infected() {
        ListCommand expectedListCommand =
                new ListCommand(INFECTED_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "infected", expectedListCommand);
    }

    @Test
    public void parseListType_quarantined() {
        ListCommand expectedListCommand =
                new ListCommand(QUARANTINED_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "quarantined", expectedListCommand);
    }

    @Test
    public void parseListType_stats() {
        ListCommand expectedListCommand =
                new ListCommand(STATISTICS_LIST);
        assertParseSuccess(parser, " " + PREFIX_LIST + "stats", expectedListCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixMissing_throwsParseException() {
        assertParseFailure(parser, " people", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        ListCommand expectedListCommand =
                new ListCommand(PEOPLE_LIST);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_LIST + "        people    ", expectedListCommand);

        // all upper case
        assertParseSuccess(parser, " " + PREFIX_LIST + "PEOPLE", expectedListCommand);

        // mixed case
        assertParseSuccess(parser, " " + PREFIX_LIST + "PEopLE", expectedListCommand);
    }

}
