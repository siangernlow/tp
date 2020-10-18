package seedu.address.logic.parser.location;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalLocations.BOB_LOCATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.location.AddLocationCommand;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.Name;
import seedu.address.model.location.Location;
import seedu.address.testutil.LocationBuilder;

public class AddLocationCommandParserTest {
    private AddLocationCommandParser parser = new AddLocationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Location expectedLocation = new LocationBuilder(BOB_LOCATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ID_DESC_BOB_LOCATION + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                new AddLocationCommand(expectedLocation));

        // multiple ids - last id accepted
        assertParseSuccess(parser, ID_DESC_AMY_LOCATION + ID_DESC_BOB_LOCATION + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                new AddLocationCommand(expectedLocation));

        // multiple names - last name accepted
        assertParseSuccess(parser, ID_DESC_BOB_LOCATION + NAME_DESC_AMY + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                new AddLocationCommand(expectedLocation));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, ID_DESC_BOB_LOCATION + NAME_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB,
                new AddLocationCommand(expectedLocation));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLocationCommand.MESSAGE_USAGE);

        // missing id prefix
        assertParseFailure(parser, VALID_ID_BOB + NAME_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, ID_DESC_BOB_LOCATION + VALID_NAME_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, ID_DESC_BOB_LOCATION + NAME_DESC_BOB + VALID_ADDRESS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ID_BOB + VALID_NAME_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid id
        assertParseFailure(parser, INVALID_LOCATION_ID_DESC + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                Id.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, ID_DESC_BOB_LOCATION + INVALID_NAME_DESC + ADDRESS_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, ID_DESC_BOB_LOCATION + NAME_DESC_BOB + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, ID_DESC_BOB_LOCATION + INVALID_NAME_DESC + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ID_DESC_BOB_LOCATION + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLocationCommand.MESSAGE_USAGE));
    }
}
