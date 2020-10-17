package seedu.address.logic.commands.visit;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DataGenerator;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandResult.SWITCH_TO_VIEW_VISITS;

public class AddVisitsFromCsvCommand extends AddFromCsvCommand {
    private final List<DataGenerator.VisitParametersContainer> visitParametersContainersToAdd;

    public static final String VISITS = "visit(s)";
    public static final String MESSAGE_EMPTY_LIST = "There are no visits to be added into VirusTracker.";

    /**
     * Creates an AddVisitsFromCsvCommand to add the specified list of {@code VisitParametersContainer}.
     */
    public AddVisitsFromCsvCommand(List<DataGenerator.VisitParametersContainer> visitParametersContainerList) {
        requireNonNull(visitParametersContainerList);
        visitParametersContainersToAdd = visitParametersContainerList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (visitParametersContainersToAdd.size() == 0) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        StringBuilder linesWithDuplicates = new StringBuilder();
        int successfulAdditions = 0;

        for (int i = 0; i < visitParametersContainersToAdd.size(); i++) {
            DataGenerator.VisitParametersContainer vpc = visitParametersContainersToAdd.get(i);

            Person person = model.getPersonFromIndex(vpc.getPersonIndex());
            Location location = model.getLocationFromIndex(vpc.getLocationIndex());
            Visit visit = new Visit(person, location, vpc.getDate());

            // Duplicate location found
            if (model.hasVisit(visit)) {
                linesWithDuplicates.append(i + 1).append(" ");
                continue;
            }

            model.addVisit(visit);
            successfulAdditions++;
        }

        String successMessage = createSuccessMessage(successfulAdditions, linesWithDuplicates.toString());
        return new CommandResult(successMessage, false, false, SWITCH_TO_VIEW_VISITS);
    }

    /**
     * Creates the correct success message.
     *
     * @param numOfUniqueAdditions Number of non duplicate visits added to the VirusTracker.
     * @param linesWithDuplicates Line numbers of the duplicate visits.
     * @return A success message with number of duplicates detected, if any.
     */
    private String createSuccessMessage(int numOfUniqueAdditions, String linesWithDuplicates) {
        assert numOfUniqueAdditions <= visitParametersContainersToAdd.size();

        StringBuilder successMessage = new StringBuilder(String.format(MESSAGE_SUCCESS, numOfUniqueAdditions));

        // There are duplicates
        if (numOfUniqueAdditions < visitParametersContainersToAdd.size()) {
            successMessage.append(String.format(MESSAGE_DUPLICATES_NOT_ADDED, VISITS, linesWithDuplicates));
        }

        return successMessage.toString();
    }
}
