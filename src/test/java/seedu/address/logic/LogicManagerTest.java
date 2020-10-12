package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INFECTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUARANTINE_STATUS_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.WrongDateFormatException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.storage.JsonLocationBookStorage;
import seedu.address.storage.JsonPersonBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonVisitBookStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonPersonBookStorage addressBookStorage =
                new JsonPersonBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonLocationBookStorage locationBookStorage =
                new JsonLocationBookStorage(temporaryFolder.resolve("locationBook.json"));
        JsonVisitBookStorage visitBookStorage =
                new JsonVisitBookStorage(temporaryFolder.resolve("visitBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, locationBookStorage,
                userPrefsStorage, visitBookStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String clearCommand = ClearCommand.COMMAND_WORD;
        assertCommandSuccess(clearCommand, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonPersonBookStorage addressBookStorage =
                new JsonPersonBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonLocationBookStorage locationBookStorage =
                new JsonLocationBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionLocationBook.json"));
        JsonVisitBookStorage visitBookStorage =
                new JsonVisitBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionVisitBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, locationBookStorage,
                userPrefsStorage, visitBookStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddPersonCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + QUARANTINE_STATUS_DESC_AMY + INFECTION_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        Person.setPersonCount(expectedPerson.getId().getOneBased());
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredLocationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredLocationList().remove(0));
    }

    @Test
    public void getFilteredVisitList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredVisitList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException, WrongDateFormatException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getPersonBook(), model.getLocationBook(),
                model.getVisitBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonPersonBookIoExceptionThrowingStub extends JsonPersonBookStorage {
        private JsonPersonBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyPersonBook personBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonLocationBookIoExceptionThrowingStub extends JsonLocationBookStorage {
        private JsonLocationBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLocationBook(ReadOnlyLocationBook locationBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonVisitBookIoExceptionThrowingStub extends JsonVisitBookStorage {
        private JsonVisitBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveVisitBook(ReadOnlyVisitBook visitBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
