package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import seedu.address.logic.commands.visit.AddVisitCommand;
import seedu.address.model.visit.Visit;

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
        sb.append(PREFIX_PERSON_ID + visit.getPerson().getId().value + " ");
        sb.append(PREFIX_LOCATION_ID + visit.getLocation().getId().value + " ");
        sb.append(PREFIX_DATE + visit.getDate().toString() + " ");
        return sb.toString();
    }


}
