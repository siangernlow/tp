package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenerateLocationsCommand;

public class GenerateLocationsCommandParserTest {

    private GenerateLocationsCommandParser parser = new GenerateLocationsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateLocationsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexOutOfBounds_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateLocationsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGenerateLocationsCommand() {
        // no leading and trailing whitespaces
        GenerateLocationsCommand expectedGenerateLocationsCommand =
                new GenerateLocationsCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedGenerateLocationsCommand);
    }

}
