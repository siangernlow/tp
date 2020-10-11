package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.visit.DeleteVisitsCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteVisitsCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class DeleteVisitsCommandParserTest {

    private DeleteVisitsCommandParser parser = new DeleteVisitsCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteVisitsCommand() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertParseSuccess(parser, " d/2020-09-12",
                new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat)));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVisitsCommand.MESSAGE_USAGE));
    }
}
