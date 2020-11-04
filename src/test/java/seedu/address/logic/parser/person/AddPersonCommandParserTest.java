package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INFECTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFECTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSON_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUARANTINE_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUARANTINE_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUARANTINE_STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFECTION_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUARANTINE_STATUS_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddPersonCommandParserTest {
    private AddPersonCommandParser parser = new AddPersonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB + INFECTION_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, ID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB + INFECTION_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB + INFECTION_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB + INFECTION_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB + INFECTION_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple quarantine statuses - last quarantine status accepted
        assertParseSuccess(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + QUARANTINE_STATUS_DESC_AMY + QUARANTINE_STATUS_DESC_BOB + ADDRESS_DESC_BOB
                + INFECTION_DESC_BOB, new AddPersonCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE);

        // missing id prefix
        assertParseFailure(parser, VALID_ID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, ID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing quarantine status prefix
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_QUARANTINE_STATUS_BOB + INFECTION_DESC_BOB, expectedMessage);

        // missing infection status prefix
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + VALID_INFECTION_STATUS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ID_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_QUARANTINE_STATUS_BOB + VALID_INFECTION_STATUS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid id
        assertParseFailure(parser, INVALID_PERSON_ID_DESC + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Id.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid infection status
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_INFECTION_DESC + QUARANTINE_STATUS_DESC_BOB, InfectionStatus.MESSAGE_CONSTRAINTS);


        // invalid quarantine status
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_QUARANTINE_STATUS_DESC + INFECTION_DESC_BOB, QuarantineStatus.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + QUARANTINE_STATUS_DESC_BOB + INFECTION_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }
}
