package seedu.address.logic.commands.person;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandResult.SWITCH_TO_VIEW_PEOPLE;

public class AddPersonsFromCsvCommand extends AddFromCsvCommand {
    private final List<Person> peopleToAdd;

    public static final String PERSONS = "person(s)";
    public static final String MESSAGE_EMPTY_LIST = "There are no people to be added into VirusTracker.";

    /**
     * Creates an AddPersonsFromCsvCommand to add the specified list of {@code Person}.
     */
    public AddPersonsFromCsvCommand(List<Person> personsList) {
        requireNonNull(personsList);
        peopleToAdd = personsList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (peopleToAdd.size() == 0) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        StringBuilder linesWithDuplicates = new StringBuilder();
        int successfulAdditions = 0;

        for (int i = 0; i < peopleToAdd.size(); i++) {
            Person person = peopleToAdd.get(i);

            // Duplicate person found
            if (model.hasPerson(person)) {
                linesWithDuplicates.append(i + 1).append(" ");
                continue;
            }

            model.addPerson(person);
            successfulAdditions++;
        }

        String successMessage = createSuccessMessage(successfulAdditions, linesWithDuplicates.toString());
        return new CommandResult(successMessage, false, false, SWITCH_TO_VIEW_PEOPLE);
    }

    /**
     * Creates the correct success message.
     *
     * @param numOfUniqueAdditions Number of non duplicate persons added to the VirusTracker.
     * @param linesWithDuplicates Line numbers of the duplicate persons.
     * @return A success message with number of duplicates detected, if any.
     */
    private String createSuccessMessage(int numOfUniqueAdditions, String linesWithDuplicates) {
        assert numOfUniqueAdditions <= peopleToAdd.size();

        StringBuilder successMessage = new StringBuilder(
                String.format(MESSAGE_SUCCESS, numOfUniqueAdditions, PERSONS));

        // There are duplicates
        if (numOfUniqueAdditions < peopleToAdd.size()) {
            successMessage.append(String.format(MESSAGE_DUPLICATES_NOT_ADDED, PERSONS, linesWithDuplicates));
        }

        return successMessage.toString();
    }
}
