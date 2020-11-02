package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.visit.DeleteVisitCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteVisitCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class DeleteVisitCommandParserTest {

    private DeleteVisitCommandParser parser = new DeleteVisitCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteVisitCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVisitCommand.MESSAGE_USAGE));
    }
}
