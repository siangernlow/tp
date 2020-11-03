package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;
import static seedu.address.logic.parser.IndexIdPair.checkIndexOrIdOnly;

import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPersonCommand object
 */
public class EditPersonCommandParser implements Parser<EditPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPersonCommand
     * and returns an EditPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_QUARANTINE_STATUS, PREFIX_INFECTION_STATUS);

        if (!checkIndexOrIdOnly(argMultimap, PREFIX_PERSON_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));
        }
        IndexIdPair pair = new IndexIdPair(argMultimap, PREFIX_PERSON_ID);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_QUARANTINE_STATUS).isPresent()) {
            editPersonDescriptor.setQuarantineStatus(ParserUtil.parseQuarantineStatus(
                    argMultimap.getValue(PREFIX_QUARANTINE_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_INFECTION_STATUS).isPresent()) {
            editPersonDescriptor.setInfectionStatus(
                    ParserUtil.parseInfectionStatus(argMultimap.getValue(PREFIX_INFECTION_STATUS).get())
            );
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPersonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPersonCommand(pair, editPersonDescriptor);
    }
}
