package seedu.address.logic.parser.location;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_NUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_VIVOCITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.location.EditLocationCommand;
import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.logic.parser.IndexIdPairStub;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Name;
import seedu.address.testutil.EditLocationDescriptorBuilder;

class EditLocationCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLocationCommand.MESSAGE_USAGE);

    private EditLocationCommandParser parser = new EditLocationCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_NUS, MESSAGE_INVALID_INDEX);

        // no field specified
        assertParseFailure(parser, "1", EditLocationCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_NUS, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_NUS, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 X/ string", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        // invalid name followed by valid address
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + ADDRESS_DESC_NUS, Name.MESSAGE_CONSTRAINTS);

        // valid address followed by invalid address. The test case for invalid address followed by valid address
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ADDRESS_DESC_VIVOCITY + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);
        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_ADDRESS_DESC + VALID_ADDRESS_NUS,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ADDRESS_DESC_NUS + NAME_DESC_NUS;

        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS)
                .withAddress(VALID_ADDRESS_NUS).build();

        EditLocationCommand expectedCommand = new EditLocationCommand(
                new IndexIdPairStub(targetIndex, null), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_NUS;
        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS).build();
        EditLocationCommand expectedCommand = new EditLocationCommand(
                new IndexIdPairStub(targetIndex, null), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_NUS;
        descriptor = new EditLocationDescriptorBuilder().withAddress(VALID_ADDRESS_NUS).build();
        expectedCommand = new EditLocationCommand(new IndexIdPairStub(targetIndex, null), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + ADDRESS_DESC_NUS + ADDRESS_DESC_NUS + ADDRESS_DESC_VIVOCITY;

        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder()
                .withAddress(VALID_ADDRESS_VIVOCITY).build();

        EditLocationCommand expectedCommand = new EditLocationCommand(
                new IndexIdPairStub(targetIndex, null), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_VIVOCITY;
        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder().withName(VALID_NAME_VIVOCITY).build();
        EditLocationCommand expectedCommand = new EditLocationCommand(
                new IndexIdPairStub(targetIndex, null), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + ADDRESS_DESC_VIVOCITY + NAME_DESC_VIVOCITY;
        descriptor = new EditLocationDescriptorBuilder().withAddress(VALID_ADDRESS_VIVOCITY)
                .withName(VALID_NAME_VIVOCITY).build();
        expectedCommand = new EditLocationCommand(new IndexIdPairStub(targetIndex, null), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
