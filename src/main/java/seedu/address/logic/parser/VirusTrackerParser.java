package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportToCsvCommand;
import seedu.address.logic.commands.GenerateLocationsCommand;
import seedu.address.logic.commands.GeneratePeopleCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.location.AddLocationCommand;
import seedu.address.logic.commands.location.DeleteLocationCommand;
import seedu.address.logic.commands.location.EditLocationCommand;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.visit.AddVisitCommand;
import seedu.address.logic.commands.visit.DeleteVisitCommand;
import seedu.address.logic.commands.visit.DeleteVisitsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.location.AddLocationCommandParser;
import seedu.address.logic.parser.location.DeleteLocationCommandParser;
import seedu.address.logic.parser.location.EditLocationCommandParser;
import seedu.address.logic.parser.person.AddPersonCommandParser;
import seedu.address.logic.parser.person.DeletePersonCommandParser;
import seedu.address.logic.parser.person.EditPersonCommandParser;
import seedu.address.logic.parser.person.FindPersonCommandParser;
import seedu.address.logic.parser.visit.AddVisitCommandParser;
import seedu.address.logic.parser.visit.DeleteVisitCommandParser;
import seedu.address.logic.parser.visit.DeleteVisitsCommandParser;

/**
 * Parses user input.
 */
public class VirusTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case AddLocationCommand.COMMAND_WORD:
            return new AddLocationCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditPersonCommandParser().parse(arguments);

        case EditLocationCommand.COMMAND_WORD:
            return new EditLocationCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case DeleteLocationCommand.COMMAND_WORD:
            return new DeleteLocationCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case GenerateLocationsCommand.COMMAND_WORD:
            return new GenerateLocationsCommandParser().parse(arguments);

        case GeneratePeopleCommand.COMMAND_WORD:
            return new GeneratePeopleCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddVisitCommand.COMMAND_WORD:
            return new AddVisitCommandParser().parse(arguments);

        case DeleteVisitCommand.COMMAND_WORD:
            return new DeleteVisitCommandParser().parse(arguments);

        case DeleteVisitsCommand.COMMAND_WORD:
            return new DeleteVisitsCommandParser().parse(arguments);

        case AddFromCsvCommand.COMMAND_WORD:
            return new AddFromCsvCommandParser().parse(arguments);

        case ExportToCsvCommand.COMMAND_WORD:
            return new ExportToCsvCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
