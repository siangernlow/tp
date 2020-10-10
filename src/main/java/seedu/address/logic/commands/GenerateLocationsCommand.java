package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GenerateLocationsCommand extends Command {

    public static final String COMMAND_WORD = "generateLocations";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all locations which a person of "
            + "the specified id (case-insensitive) visited and displays them as a list of locations.\n"
            + "Parameters: PERSONID ...\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index personId;

    public GenerateLocationsCommand(Index personId) {
        this.personId = personId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Integer> locationIds = CommandUtil.generateLocationIdsByPerson(model, personId);
        model.updateFilteredLocationList(ModelPredicate.getPredicateShowLocationsById(locationIds));
        return new CommandResult(
                "Generated locations for: " + model.getAddressBook()
                        .getPersonList().get(personId.getZeroBased()).getName(),
                false, false, CommandResult.SWITCH_TO_VIEW_ALL_LOCATIONS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateLocationsCommand // instanceof handles nulls
                && personId.equals(((GenerateLocationsCommand) other).personId)); // state check
    }
}
