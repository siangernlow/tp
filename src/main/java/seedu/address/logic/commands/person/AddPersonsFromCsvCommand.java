package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class AddPersonsFromCsvCommand extends AddFromCsvCommand {
    public static final String PERSONS = "person(s)";
    public static final String MESSAGE_EMPTY_LIST = "There are no people to be added into VirusTracker.";

    private final List<Person> peopleToAdd;

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
            if (model.hasPerson(person) || model.hasPersonId(person.getId())) {
                linesWithDuplicates.append(i + 1).append(" ");
                continue;
            }

            model.addPerson(person);
            successfulAdditions++;
        }

        String successMessage = createSuccessMessage(successfulAdditions, linesWithDuplicates.toString());
        return new CommandResult(successMessage);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPersonsFromCsvCommand) // instanceof handles nulls
                && peopleToAdd.equals(((AddPersonsFromCsvCommand) other).peopleToAdd); // state check
    }
}
