package seedu.address.logic.commands.location;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

public class AddLocationsFromCsvCommand extends AddFromCsvCommand {
    public static final String LOCATIONS = "location(s)";
    public static final String MESSAGE_EMPTY_LIST = "There are no locations to be added into VirusTracker.";

    private final List<Location> locationsToAdd;

    /**
     * Creates an AddLocationsFromCsvCommand to add the specified list of {@code Location}.
     */
    public AddLocationsFromCsvCommand(List<Location> locationsList) {
        requireNonNull(locationsList);
        locationsToAdd = locationsList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (locationsToAdd.size() == 0) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        StringBuilder linesWithDuplicates = new StringBuilder();
        int successfulAdditions = 0;

        for (int i = 0; i < locationsToAdd.size(); i++) {
            Location location = locationsToAdd.get(i);

            // Duplicate location found
            if (model.hasLocation(location) || model.hasLocationId(location.getId())) {
                linesWithDuplicates.append(i + 1).append(" ");
                continue;
            }

            model.addLocation(location);
            successfulAdditions++;
        }

        String successMessage = createSuccessMessage(successfulAdditions, linesWithDuplicates.toString());
        return new CommandResult(successMessage);
    }

    /**
     * Creates the correct success message.
     *
     * @param numOfUniqueAdditions Number of non duplicate locations added to the VirusTracker.
     * @param linesWithDuplicates Line numbers of the duplicate locations.
     * @return A success message with number of duplicates detected, if any.
     */
    private String createSuccessMessage(int numOfUniqueAdditions, String linesWithDuplicates) {
        assert numOfUniqueAdditions <= locationsToAdd.size();

        StringBuilder successMessage = new StringBuilder(String.format(MESSAGE_SUCCESS, numOfUniqueAdditions,
                LOCATIONS));

        // There are duplicates
        if (numOfUniqueAdditions < locationsToAdd.size()) {
            successMessage.append(String.format(MESSAGE_DUPLICATES_NOT_ADDED, LOCATIONS, linesWithDuplicates));
        }

        return successMessage.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLocationsFromCsvCommand) // instanceof handles nulls
                && locationsToAdd.equals(((AddLocationsFromCsvCommand) other).locationsToAdd); // state check
    }
}
