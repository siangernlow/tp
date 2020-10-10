package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_LOCATIONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_VISITS;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ListType;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

/**
 * Displays a list which items are the given list type.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists information based on a given type.\n"
            + "Parameters: l/LIST_TYPE (must be either people, locations, high-risk-locations, visits,"
            + " infected or quarantined)\n"
            + "Example: " + COMMAND_WORD + " people";

    public static final String MESSAGE_SUCCESS_ALL_PEOPLE = "Listed all people";
    public static final String MESSAGE_SUCCESS_ALL_LOCATIONS = "Listed all locations";
    public static final String MESSAGE_SUCCESS_ALL_VISITS = "Listed all visits";
    public static final String MESSAGE_SUCCESS_ALL_INFECTED = "Listed all infected people";
    public static final String MESSAGE_SUCCESS_ALL_QUARANTINED = "Listed all quarantined people";
    public static final String MESSAGE_SUCCESS_STATISTICS = "Listed the statistics for the day";
    public static final String MESSAGE_SUCCESS_HIGH_RISK_LOCATIONS = "Listed high risk locations";
    public static final String INVALID_LIST_TYPE = "There is no such list type.";

    private final ListType listType;

    public ListCommand(ListType listType) {
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (listType) {
        case ALL_PEOPLE:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_ALL_PEOPLE, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_PEOPLE);
        case ALL_LOCATIONS:
            model.updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
            return new CommandResult(MESSAGE_SUCCESS_ALL_LOCATIONS, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_LOCATIONS);
        case ALL_VISITS:
            model.updateFilteredVisitList(PREDICATE_SHOW_ALL_VISITS);
            return new CommandResult(MESSAGE_SUCCESS_ALL_VISITS, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_VISITS);
        case ALL_INFECTED:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
            return new CommandResult(MESSAGE_SUCCESS_ALL_INFECTED, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_INFECTED);
        case ALL_QUARANTINED:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
            return new CommandResult(MESSAGE_SUCCESS_ALL_QUARANTINED, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_QUARANTINED);
        case STATISTICS:
            // Retrieves the stats, currently displayed as console output
            String stats = model.getInfoHandler().getStatistics();
            System.out.println(stats);
            return new CommandResult(MESSAGE_SUCCESS_STATISTICS, false, false,
                    CommandResult.SWITCH_TO_VIEW_STATISTICS);
        case HIGH_RISK_LOCATIONS:
            Predicate<Location> predicateForHighRiskLocations =
                    Location.getPredicateForHighRiskLocations(model);
            model.updateFilteredLocationList(predicateForHighRiskLocations);
            return new CommandResult(MESSAGE_SUCCESS_HIGH_RISK_LOCATIONS, false, false,
                    CommandResult.SWITCH_TO_VIEW_ALL_LOCATIONS);
        default:
            throw new CommandException(INVALID_LIST_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && listType.equals(((ListCommand) other).listType)); // state check
    }
}

