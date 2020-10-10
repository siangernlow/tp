package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelPredicate;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
import seedu.address.model.visit.Visit;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GeneratePeopleCommand extends Command {

    public static final String COMMAND_WORD = "generatePeople";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all people which a person of the specified"
            + "id (case-insensitive) have been in contact with and displays them as a list of locations.\n"
            + "Parameters: PERSONID ...\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NO_PEOPLE_FOUND = "There were no people in contact with the given person";

    private final Index personId;

    public GeneratePeopleCommand(Index personId) {
        this.personId = personId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Integer> locationIds = CommandUtil.generateLocationIdsByPerson(model, personId);
        List<Integer> personIds = new ArrayList<>();
        ReadOnlyVisitBook tempVisitBook = model.getVisitBook();
        VisitBook affectedVisits = new VisitBook();
        for (Integer locationId : locationIds) {
            Index locationIndex = Index.fromZeroBased(locationId);
            for (int i = 0; i < tempVisitBook.getVisitList().size(); i++) {
                Visit visit = tempVisitBook.getVisitList().get(i);
                if (visit.getLocationId().equals(locationIndex)) {
                    affectedVisits.addVisit(visit);
                }
            }
        }
        for (Visit visit : affectedVisits.getVisitList()) {
            if (visit.getPersonId().getZeroBased() == personId.getZeroBased()) {
                continue;
            }
            personIds.add(visit.getPersonId().getZeroBased());
        }
        if (personIds.isEmpty()) {
            throw new CommandException(MESSAGE_NO_PEOPLE_FOUND);
        }
        model.updateFilteredPersonList(ModelPredicate.getPredicateShowPeopleById(personIds));
        return new CommandResult(
                "Generated people for: " + model.getAddressBook()
                        .getPersonList().get(personId.getZeroBased()).getName(),
                false, false, CommandResult.SWITCH_TO_VIEW_ALL_PEOPLE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GeneratePeopleCommand // instanceof handles nulls
                && personId.equals(((GeneratePeopleCommand) other).personId)); // state check
    }
}
