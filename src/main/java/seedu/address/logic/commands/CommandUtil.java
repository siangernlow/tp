package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;

public class CommandUtil {
    public static final String MESSAGE_PERSON_HAS_NO_VISITS = "This person is not associated with any visits";
    public static final String MESSAGE_PERSON_IS_NOT_INFECTED = "This person is not infected";

    public static List<Integer> generateLocationIdsByPerson(Model model, Index personId) throws CommandException {
        ReadOnlyVisitBook tempVisitBook = model.getVisitBook();
        if (personId.getZeroBased() >= model.getAddressBook().getPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (!model.getAddressBook().getPersonList()
                .get(personId.getZeroBased()).getInfectionStatus().getStatusAsBoolean()) {
            throw new CommandException(MESSAGE_PERSON_IS_NOT_INFECTED);
        }
        VisitBook visitsByPerson = new VisitBook();
        for (int i = 0; i < tempVisitBook.getVisitList().size(); i++) {
            if (tempVisitBook.getVisitList().get(i).getPersonId().equals(personId)) {
                visitsByPerson.addVisit(tempVisitBook.getVisitList().get(i));
            }
        }
        if (visitsByPerson.getVisitList().isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_HAS_NO_VISITS);
        }
        List<Integer> locationIds = new ArrayList<>();
        for (int i = 0; i < visitsByPerson.getVisitList().size(); i++) {
            locationIds.add(visitsByPerson.getVisitList().get(i).getLocationId().getZeroBased());
        }
        return locationIds;
    }
}
