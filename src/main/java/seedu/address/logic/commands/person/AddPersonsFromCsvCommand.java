package seedu.address.logic.commands.person;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.logic.commands.CommandResult.SWITCH_TO_VIEW_PEOPLE;

public class AddPersonsFromCsvCommand extends AddFromCsvCommand {

    public AddPersonsFromCsvCommand(List<Person> personsList) {
        super();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("AddPersonsFromCsvCommand executed",
                false, false, SWITCH_TO_VIEW_PEOPLE);
    }
}
