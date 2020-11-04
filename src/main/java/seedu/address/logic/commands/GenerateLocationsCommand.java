package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ReadOnlyIndexIdPair;
import seedu.address.model.InfoHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelPredicate;
import seedu.address.model.attribute.Id;
import seedu.address.model.person.Person;
import seedu.address.model.visit.VisitBook;

/**
 * Finds and lists all persons in visit book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GenerateLocationsCommand extends Command {

    public static final String COMMAND_WORD = "generateLocations";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all locations which a person of "
            + "the specified Id (case-insensitive) or index visited and displays them as a list of locations.\n"
            + "Parameters: PERSON_INDEX or "
            + PREFIX_PERSON_ID + "PERSON_ID\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_PERSON_ID + "S123A";

    public static final String MESSAGE_PERSON_HAS_NO_VISITS = "This person is not associated with any visits"
            + " in the past 2 weeks";
    public static final String MESSAGE_PERSON_IS_NOT_INFECTED = "This person is not infected";

    private final ReadOnlyIndexIdPair pair;

    /**
     * Creates a command to generate locations.
     */
    public GenerateLocationsCommand(ReadOnlyIndexIdPair pair) {
        requireNonNull(pair);
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
        model.updateFilteredLocationList(ModelPredicate.getPredicateShowLocationsById(locationIds));
        return new CommandResult(
                "Generated locations for: " + infectedPerson.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateLocationsCommand // instanceof handles nulls
                && pair.equals(((GenerateLocationsCommand) other).pair)); // state check
    }
}
