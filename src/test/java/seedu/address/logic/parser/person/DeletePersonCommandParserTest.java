package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.attribute.Id;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePersonCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePersonCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePersonCommandParserTest {

    private DeletePersonCommandParser parser = new DeletePersonCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeletePersonCommand(new IndexIdPair(INDEX_FIRST, null, PREFIX_PERSON_ID)));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validId_returnsDeletePersonCommand() {
        assertParseSuccess(parser, " idp/12345", new DeletePersonCommand(new IndexIdPairStub(null, new Id("12345"))));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        assertParseFailure(parser, " idp/  ", Id.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_containsBothIndexAndId_throwsParseException() {
        String userInput = "1 " + PREFIX_PERSON_ID + "2";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE));
    }
}
