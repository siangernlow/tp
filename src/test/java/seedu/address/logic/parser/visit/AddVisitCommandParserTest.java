package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.visit.AddVisitCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteVisitsCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class AddVisitCommandParserTest {

    private AddVisitCommandParser parser = new AddVisitCommandParser();

    @Test
    public void parse_invalidDate_throwsWrongDateFormatException() {
        assertParseFailure(parser, "1 2 d/2020/09/09",
                String.format(MESSAGE_INVALID_DATE_FORMAT, AddVisitCommand.MESSAGE_USAGE));
    }
}
