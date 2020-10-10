package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLocationCommand;
import seedu.address.logic.commands.EditLocationCommand.EditLocationDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLocationCommand object
 */
public class EditLocationCommandParser implements Parser<EditLocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLocationCommand
     * and returns an EditLocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditLocationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLocationCommand.MESSAGE_USAGE),
                    pe);
        }

        EditLocationDescriptor editLocationDescriptor = new EditLocationCommand.EditLocationDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editLocationDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editLocationDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editLocationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLocationCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLocationCommand(index, editLocationDescriptor);
    }
}
