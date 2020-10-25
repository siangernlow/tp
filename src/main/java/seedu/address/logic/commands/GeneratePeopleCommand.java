package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IndexIdPair;
import seedu.address.model.InfoHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelPredicate;
import seedu.address.model.attribute.Id;
import seedu.address.model.person.Person;
import seedu.address.model.visit.VisitBook;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GeneratePeopleCommand extends Command {

    public static final String COMMAND_WORD = "generatePeople";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all people which a person of the specified"
            + "id (case-insensitive) have been in contact with and displays them as a list of locations.\n"
            + "Parameters: PERSON_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: idp/PERSON_ID\n"
            + "Example: " + COMMAND_WORD + " idp/S123A";

    public static final String MESSAGE_NO_PEOPLE_FOUND = "There were no people in contact with the given person";
    public static final String MESSAGE_PERSON_HAS_NO_VISITS = "This person is not associated with any visits";
    public static final String MESSAGE_PERSON_IS_NOT_INFECTED = "This person is not infected";

    private final IndexIdPair pair;

    public GeneratePeopleCommand(IndexIdPair pair) {
        this.pair = pair;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        InfoHandler infoHandler = new InfoHandler(model);
        Person infectedPerson = pair.getPersonFromPair(model);

        if (!infectedPerson.isInfected()) {
            throw new CommandException(MESSAGE_PERSON_IS_NOT_INFECTED);
        }
        Id personIdFromBook = infectedPerson.getId();
        VisitBook visitsByPerson = infoHandler.generateVisitsByPerson(personIdFromBook);
        if (visitsByPerson.getVisitList().isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_HAS_NO_VISITS);
        }
        List<Id> locationIds = infoHandler.generateLocationIdsByVisitBook(visitsByPerson);
        VisitBook affectedVisits = infoHandler.generateVisitsByLocationIds(locationIds);
        List<Id> personIds = infoHandler.generatePersonIdsByVisitBook(affectedVisits, personIdFromBook);
        if (personIds.isEmpty()) {
            throw new CommandException(MESSAGE_NO_PEOPLE_FOUND);
        }
        model.updateFilteredPersonList(ModelPredicate.getPredicateShowPeopleById(personIds));
        return new CommandResult(
                "Generated people for: " + infectedPerson.getName(),
                false, false, CommandResult.SWITCH_TO_VIEW_PEOPLE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GeneratePeopleCommand // instanceof handles nulls
                && pair.equals(((GeneratePeopleCommand) other).pair)); // state check
    }
}
