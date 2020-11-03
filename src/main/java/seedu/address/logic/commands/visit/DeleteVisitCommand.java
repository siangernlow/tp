package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.visit.Visit;


/**
 * Deletes the visit identified using its displayed index from the visit book.
 */

public class DeleteVisitCommand extends Command {

    public static final String COMMAND_WORD = "deleteVisit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the visit .\n"
            + "Parameters: INDEX (must be a positive integer and within the range of displayed visit list)\n"
            + "Example: " + COMMAND_WORD + " " + " 1\n" + "Deletes the first visit inside the visit list";
    public static final String MESSAGE_DELETE_VISIT_SUCCESS = "Deleted Visit: %1$s";

    private final Index targetIndex;

    /**
     * @param index contains the index the visit to be deleted.
     */

    public DeleteVisitCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Visit> lastShownList = model.getSortedVisitList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VISIT_INDEX);
        }
        Visit visitToDelete = lastShownList.get(targetIndex.getZeroBased());
        assert visitToDelete != null;
        model.deleteVisit(visitToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_VISIT_SUCCESS, visitToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVisitCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteVisitCommand) other).targetIndex)); // state check
    }
}
