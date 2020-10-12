package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.LocationBook;
import seedu.address.model.person.PersonBook;
import seedu.address.model.visit.VisitBook;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model expectedModel = new ModelManager(new PersonBook(), new LocationBook(),
                new VisitBook(), new UserPrefs());
        Model model;

        // test that all books are cleared
        model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);

        // test that person book is cleared
        model = new ModelManager(getTypicalAddressBook(), new LocationBook(),
                new VisitBook(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);

        // test that location book is cleared
        model = new ModelManager(new PersonBook(), getTypicalLocationBook(),
                new VisitBook(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);

        // test that visit book is cleared
        model = new ModelManager(new PersonBook(), new LocationBook(),
                getTypicalVisitBook(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
