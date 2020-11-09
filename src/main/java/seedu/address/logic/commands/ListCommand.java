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
            + "For high-risk-locations only:\n"
            + "Parameters: [HIGH_RISK_LOCATIONS_NUMBER] l/high-risk-locations\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_LIST + "people\n"
            + "Example: " + COMMAND_WORD + " 3 l/high-risk-locations";

    public static final String MESSAGE_SUCCESS_ALL_PEOPLE = "Listed all people";
    public static final String MESSAGE_SUCCESS_ALL_LOCATIONS = "Listed all locations";
    public static final String MESSAGE_SUCCESS_ALL_VISITS = "Listed all visits";
    public static final String MESSAGE_SUCCESS_ALL_INFECTED = "Listed all infected people";
    public static final String MESSAGE_SUCCESS_ALL_QUARANTINED = "Listed all quarantined people";
    public static final String MESSAGE_SUCCESS_STATISTICS = "Listed the statistics for the day";
    public static final String MESSAGE_SUCCESS_HIGH_RISK_LOCATIONS = "Listed high risk locations";
    public static final String INVALID_LIST_TYPE = "There is no such list type.";
    public static final String INVALID_HIGH_RISK_LOCATIONS_NUMBER = "Number for high risk locations should be a"
            + " non-negative integer and should not be larger than the total number of locations.";

    private final ListType listType;
    private final boolean userSpecified;
    private final int highRiskLocationNumber;

    /**
     * Creates a ListCommand with {@code listType} and {@code highRiskLocationNumber} as specified in the argument
     * @param listType The type of information to be listed.
     * @param userSpecified Whether user specifies a number for listing high risk locations. False for all other
     *                      listing commands.
     * @param highRiskLocationNumber The number of high risk locations specified by users. The value of this argument
     *                               is -1 iff user does not specify the number of high risk locations for listing high
     *                               risk location command or commands that are not listing high risk locations.
     */
    public ListCommand(ListType listType, boolean userSpecified, int highRiskLocationNumber) {
        this.listType = listType;
        this.userSpecified = userSpecified;
        this.highRiskLocationNumber = highRiskLocationNumber;
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
            if (userSpecified
                && (highRiskLocationNumber > model.getLocationBook().getLocationList().size()
                    || highRiskLocationNumber < 0)) {
                throw new CommandException(INVALID_HIGH_RISK_LOCATIONS_NUMBER);
            }

            Predicate<Location> predicateForHighRiskLocations;
            try {
                predicateForHighRiskLocations =
                        ModelPredicate.getPredicateForHighRiskLocations(model, userSpecified, highRiskLocationNumber);
                model.updateFilteredLocationList(predicateForHighRiskLocations);
            } catch (CommandException e) {
                throw new CommandException(e.getMessage());
            }
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

