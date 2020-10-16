package seedu.address.logic.commands.location;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;

import java.util.List;

import static seedu.address.logic.commands.CommandResult.SWITCH_TO_VIEW_LOCATIONS;

public class AddLocationsFromCsvCommand extends AddFromCsvCommand {
    public AddLocationsFromCsvCommand(List<Location> locationsList) {
        super();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("AddLocationsFromCsvCommand executed",
                false, false, SWITCH_TO_VIEW_LOCATIONS);
    }
}
