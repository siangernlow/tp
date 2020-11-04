package seedu.address.logic.commands.visit;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.visit.Visit;


/**
 * Deletes Visits identified using their displayed dates from the visit book.
 * All the Visits before and including the date parsed will be deleted from the visit book
 */

public class DeleteVisitsCommand extends Command {

    public static final String COMMAND_WORD = "deleteVisits";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the visits before and up to the dates used in the displayed visit list.\n"
            + "Parameters: Date (must be in the format of yyyy-MM-DD)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DATE + " 2020-09-09 ";
    public static final String MESSAGE_DELETE_VISIT_SUCCESS = "Deleted Visits: \n";
    public static final String MESSAGE_DELETE_VISIT_FAILED = "All visits were after the date, hence not deleted.";
    private final LocalDate targetDate;

    public DeleteVisitsCommand(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * Deletes Visits identified using their displayed dates from the visit list.
     * Returns the list of deleted visits using a string
     * All the visits that have the date equals or before the parsed date will be removed from the list.
     * @param model manages the action of deleting the visits.
     * @param lastShownList is the list the visit will be removed from.
     * @param targetDate is the date to be compared with.
     */

    public String deleteVisit(Model model, List<Visit> lastShownList, LocalDate targetDate) throws CommandException {
        assert targetDate != null;
        assert lastShownList != null;
        // display the visits deleted
        StringBuilder visitsToDelete = new StringBuilder(MESSAGE_DELETE_VISIT_SUCCESS);
        // count how many visits are deleted and display them in an order
        int order = 1;
        List<Visit> visitsToBeDeleted = new ArrayList<>();
        for (Visit visit: lastShownList) {
            if (visit.getDate().isBefore(targetDate) || visit.getDate().isEqual(targetDate)) {
                visitsToBeDeleted.add(visit);
                visitsToDelete.append(order).append(". ").append(visit).append(" \n");
                order++;
            }
        }

        if (order > 1) {
            for (Visit visit: visitsToBeDeleted) {
                model.deleteVisit(visit);
            }
        }

        if (order == 1) {
            throw new CommandException(MESSAGE_DELETE_VISIT_FAILED);
        } else {
            return visitsToDelete.toString();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Visit> lastShownList = model.getSortedVisitList();
        String result = deleteVisit(model, lastShownList, targetDate);
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVisitsCommand // instanceof handles nulls
                && targetDate.equals(((DeleteVisitsCommand) other).targetDate)); // state check
    }
}
