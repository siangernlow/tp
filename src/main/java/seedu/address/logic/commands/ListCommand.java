package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_LOCATIONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_VISITS;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ListType;
import seedu.address.model.InfoHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelPredicate;
import seedu.address.model.location.Location;

/**
 * Displays a list which items are the given list type.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists information based on a given type.\n"
            + "Parameters: l/LIST_TYPE (must be either people, infected, quarantined, locations, visits,"
            + " high-risk-locations or stats)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LIST + "people";

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
            return new CommandResult(MESSAGE_SUCCESS_ALL_PEOPLE);
        case ALL_LOCATIONS:
            model.updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
            return new CommandResult(MESSAGE_SUCCESS_ALL_LOCATIONS);
        case ALL_VISITS:
            model.updateFilteredVisitList(PREDICATE_SHOW_ALL_VISITS);
            return new CommandResult(MESSAGE_SUCCESS_ALL_VISITS);
        case ALL_INFECTED:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_INFECTED);
            return new CommandResult(MESSAGE_SUCCESS_ALL_INFECTED);
        case ALL_QUARANTINED:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
            return new CommandResult(MESSAGE_SUCCESS_ALL_QUARANTINED);
        case STATISTICS:
            InfoHandler infoHandler = new InfoHandler(model);
            String stats = infoHandler.getStatistics();
            return new CommandResult(MESSAGE_SUCCESS_STATISTICS + "\n" + stats);
        case HIGH_RISK_LOCATIONS:
            Predicate<Location> predicateForHighRiskLocations =
                    ModelPredicate.getPredicateForHighRiskLocations(model);
            model.updateFilteredLocationList(predicateForHighRiskLocations);
            return new CommandResult(MESSAGE_SUCCESS_HIGH_RISK_LOCATIONS);
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

