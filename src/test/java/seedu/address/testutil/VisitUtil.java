package seedu.address.testutil;

import seedu.address.logic.commands.AddVisitCommand;
import seedu.address.model.visit.Visit;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;


/**
 * A utility class for Visit.
 */
public class VisitUtil {
    /**
     * Returns an add visit command string for adding the {@code visit}.
     */
    public static String getAddVisitCommand(Visit visit) {
        return AddVisitCommand.COMMAND_WORD + " " + getVisitDetails(visit);
    }

    /**
     * Returns the part of command string for the given {@code visit}'s details.
     */
    public static String getVisitDetails(Visit visit) {
        StringBuilder sb = new StringBuilder();
        sb.append(visit.getPersonId() + " ");
        sb.append(visit.getLocationId() + " ");
        sb.append(PREFIX_DATE + visit.getDate().toString() + " ");
        return sb.toString();
    }
}
