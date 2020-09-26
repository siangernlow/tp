package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Displays a list whose items are the given list type.
 */
public class ListCommand extends Command {

    public enum ListType {
        PERSONS
    }

    public static final String COMMAND_WORD = "list";

    // Possibly extended to different message successes based on what is listed
    public static final String MESSAGE_SUCCESS = "Listed successfully";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a list of items specified by "
            + "the given keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " persons";

    private final ListType listType;

    public ListCommand(ListType listType) {
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // We anticipate more cases, so keep it as a switch statement.
        switch (listType) {
        case PERSONS:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            break;
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

