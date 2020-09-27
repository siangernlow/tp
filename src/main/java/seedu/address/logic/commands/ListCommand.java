package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays a list whose items are the given list type.
 */
public class ListCommand extends Command {
    // INVALID sets a flag used to throw an exception.
    public enum ListType {
        PERSONS,
        INVALID
    }

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a list of items specified by "
            + "the given keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " persons";

    // Possibly extended to different message successes based on what is listed
    public static final String MESSAGE_SUCCESS = "Listed successfully";
    public static final String INVALID_LIST_TYPE = "There is no such type of list.";

    private final ListType listType;

    public ListCommand(String listType) {
        this.listType = parseListType(listType);
    }

    /**
     * Parses the given keyword and gives the enum representing it.
     *
     * @param keyword The keyword to be processed.
     * @return The enum representing the keyword.
     */
    public static ListType parseListType(String keyword) {
        switch (keyword) {
        case "persons":
            return ListType.PERSONS;
        default:
            return ListType.INVALID;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // We anticipate more cases, so keep it as a switch statement.
        switch (listType) {
        case PERSONS:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            break;
        default:
            throw new CommandException(INVALID_LIST_TYPE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && listType.equals(((ListCommand) other).listType)); // state check
    }
}

