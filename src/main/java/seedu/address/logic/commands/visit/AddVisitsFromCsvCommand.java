package seedu.address.logic.commands.visit;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DataGenerator;
import seedu.address.model.Model;

import java.util.List;

import static seedu.address.logic.commands.CommandResult.SWITCH_TO_VIEW_VISITS;

public class AddVisitsFromCsvCommand extends AddFromCsvCommand {
    public AddVisitsFromCsvCommand(List<DataGenerator.VisitParameters> visitsList) {
        super();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("AddVisitsFromCsvCommand executed",
                false, false, SWITCH_TO_VIEW_VISITS);
    }
}
