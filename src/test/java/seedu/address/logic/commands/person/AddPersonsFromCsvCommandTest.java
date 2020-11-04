package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_DUPLICATES_NOT_ADDED;
import static seedu.address.logic.commands.AddFromCsvCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.person.AddPersonsFromCsvCommand.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.commands.person.AddPersonsFromCsvCommand.PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.attribute.Id;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddPersonsFromCsvCommandTest {

    @Test
    public void constructor_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonsFromCsvCommand(null));
    }

    @Test
    public void execute_personsAcceptedByModel_addSuccessful() {
        AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded expectedModel =
                new AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded();

        AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded actualModel =
                new AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded();

        List<Person> personsToAdd = TypicalPersons.getTypicalPersons();
        for (Person person : personsToAdd) {
            expectedModel.addPerson(person);
        }

        AddPersonsFromCsvCommand actualCommand = new AddPersonsFromCsvCommand(personsToAdd);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, personsToAdd.size(), PERSONS));
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }

    @Test
    public void execute_emptyPersonsList_throwsCommandException() {
        AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded model =
                new AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded();

        List<Person> personsToAdd = new ArrayList<>();

        AddPersonsFromCsvCommand actualCommand = new AddPersonsFromCsvCommand(personsToAdd);
        assertThrows(CommandException.class, MESSAGE_EMPTY_LIST, ()
            -> actualCommand.execute(model));
    }

    @Test
    public void execute_duplicatePersons_successWithWarning() {
        AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded expectedModel =
                new AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded();

        AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded actualModel =
                new AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded();

        List<Person> personsToAdd = TypicalPersons.getTypicalPersons();
        for (Person person : personsToAdd) {
            expectedModel.addPerson(person);
        }

        int numOfUniqueAdditions = personsToAdd.size();

        // Duplicate people
        personsToAdd.add(ALICE);
        personsToAdd.add(BENSON);

        AddPersonsFromCsvCommand actualCommand = new AddPersonsFromCsvCommand(personsToAdd);

        String linesWithDuplicates = String.format("%d %d ", personsToAdd.size() - 1, personsToAdd.size());
        String expectedMessage = String.format(MESSAGE_SUCCESS, numOfUniqueAdditions, PERSONS)
                + String.format(MESSAGE_DUPLICATES_NOT_ADDED, PERSONS, linesWithDuplicates);
        CommandResult commandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(actualCommand, actualModel, commandResult, expectedModel);
    }


    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("alice").build();
        Person bob = new PersonBuilder().withName("bob").build();

        List<Person> personsAddAlice = new ArrayList<>();
        personsAddAlice.add(alice);
        List<Person> personsAddAliceAndBob = new ArrayList<>();
        personsAddAliceAndBob.add(alice);
        personsAddAliceAndBob.add(bob);

        AddPersonsFromCsvCommand addAliceCommand = new AddPersonsFromCsvCommand(personsAddAlice);
        AddPersonsFromCsvCommand addAliceAndBobCommand =
                new AddPersonsFromCsvCommand(personsAddAliceAndBob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddPersonsFromCsvCommand addHouseCommandCopy = new AddPersonsFromCsvCommand(personsAddAlice);
        assertEquals(addHouseCommandCopy, addAliceCommand);

        // different types -> returns false
        assertNotEquals(addAliceCommand, 1);

        // null -> returns false
        assertNotEquals(addAliceCommand, null);

        // different person lists -> returns false
        assertNotEquals(addAliceAndBobCommand, addAliceCommand);
    }

    /**
     * A Model stub that always accepts the person being added.
     */
    private static class ModelStubAcceptingPersonsAdded extends ModelStub {
        private final ArrayList<Person> personsAdded = new ArrayList<>();

        public ArrayList<Person> getPersonsAdded() {
            return personsAdded;
        }

        @Override
        public boolean hasPerson(Person person) {
            return personsAdded.contains(person);
        }

        @Override
        public boolean hasPersonId(Id id) {
            return personsAdded.stream().anyMatch(p -> p.getId().equals(id));
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded)) {
                return false;
            }

            AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded other =
                    (AddPersonsFromCsvCommandTest.ModelStubAcceptingPersonsAdded) obj;
            return personsAdded.equals(other.getPersonsAdded());
        }
    }
}
