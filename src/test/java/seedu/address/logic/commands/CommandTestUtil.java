package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.location.EditLocationCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.LocationNameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;
import seedu.address.model.person.PersonNameContainsKeywordsPredicate;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.EditLocationDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_NUS = "NUS";
    public static final String VALID_NAME_VIVOCITY = "Vivocity";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_NUS = "21 Lower Kent Ridge Rd, Singapore 119077";
    public static final String VALID_ADDRESS_VIVOCITY = "1 HarbourFront Walk, Singapore 098585";
    public static final String VALID_QUARANTINE_STATUS_AMY = "2020-02-02";
    public static final String VALID_QUARANTINE_STATUS_BOB = "false";
    public static final String VALID_INFECTION_STATUS_AMY = "false";
    public static final String VALID_INFECTION_STATUS_BOB = "2020-02-02";
    public static final String VALID_ID_AMY_LOCATION = "L9101112";
    public static final String VALID_ID_BOB_LOCATION = "L10111213";
    public static final String VALID_ID_AMY = "S9101112";
    public static final String VALID_ID_BOB = "S10111213";
    public static final String VALID_ID_NUS = "L1234";
    public static final String VALID_ID_VIVOCITY = "L2345";

    public static final String ID_DESC_AMY = " " + PREFIX_PERSON_ID + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_PERSON_ID + VALID_ID_BOB;
    public static final String ID_DESC_AMY_LOCATION = " " + PREFIX_LOCATION_ID + VALID_ID_AMY_LOCATION;
    public static final String ID_DESC_BOB_LOCATION = " " + PREFIX_LOCATION_ID + VALID_ID_BOB_LOCATION;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_NUS = " " + PREFIX_NAME + VALID_NAME_NUS;
    public static final String NAME_DESC_VIVOCITY = " " + PREFIX_NAME + VALID_NAME_VIVOCITY;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_NUS = " " + PREFIX_ADDRESS + VALID_ADDRESS_NUS;
    public static final String ADDRESS_DESC_VIVOCITY = " " + PREFIX_ADDRESS + VALID_ADDRESS_VIVOCITY;
    public static final String QUARANTINE_STATUS_DESC_AMY = " " + PREFIX_QUARANTINE_STATUS
            + VALID_QUARANTINE_STATUS_AMY;
    public static final String QUARANTINE_STATUS_DESC_BOB = " " + PREFIX_QUARANTINE_STATUS
            + VALID_QUARANTINE_STATUS_BOB;
    public static final String INFECTION_DESC_AMY = " " + PREFIX_INFECTION_STATUS + VALID_INFECTION_STATUS_AMY;
    public static final String INFECTION_DESC_BOB = " " + PREFIX_INFECTION_STATUS + VALID_INFECTION_STATUS_BOB;

    public static final String INVALID_PERSON_ID_DESC = " " + PREFIX_PERSON_ID + " "; // '&' not allowed in names
    public static final String INVALID_LOCATION_ID_DESC = " " + PREFIX_LOCATION_ID + " "; // '&' not allowed in names
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_QUARANTINE_STATUS_DESC = " "
            + PREFIX_QUARANTINE_STATUS + "quarantined"; // only booleans allowed
    public static final String INVALID_INFECTION_DESC =
            " " + PREFIX_INFECTION_STATUS + "nope"; // only true or false allowed
    public static final String INVALID_ID_LOCATION = " -1";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;
    public static final EditLocationCommand.EditLocationDescriptor DESC_NUS;
    public static final EditLocationCommand.EditLocationDescriptor DESC_VIVOCITY;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_AMY).withInfectionStatus(VALID_INFECTION_STATUS_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB).withInfectionStatus(VALID_INFECTION_STATUS_BOB)
                .build();
    }

    static {
        DESC_NUS = new EditLocationDescriptorBuilder().withName(VALID_NAME_NUS)
                .withAddress(VALID_ADDRESS_NUS).build();
        DESC_VIVOCITY = new EditLocationDescriptorBuilder().withName(VALID_NAME_VIVOCITY)
                .withAddress(VALID_ADDRESS_VIVOCITY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PersonBook expectedPersonBook = new PersonBook(actualModel.getPersonBook());
        List<Person> expectedFilteredPersonList = new ArrayList<>(actualModel.getSortedPersonList());

        LocationBook expectedLocationBook = new LocationBook(actualModel.getLocationBook());
        List<Location> expectedLocationFilteredList = new ArrayList<>(actualModel.getSortedLocationList());

        VisitBook expectedVisitBook = new VisitBook(actualModel.getVisitBook());
        List<Visit> expectedFilteredVisitList = new ArrayList<>(actualModel.getSortedVisitList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPersonBook, actualModel.getPersonBook());
        assertEquals(expectedFilteredPersonList, actualModel.getSortedPersonList());
        assertEquals(expectedLocationBook, actualModel.getLocationBook());
        assertEquals(expectedLocationFilteredList, actualModel.getSortedLocationList());
        assertEquals(expectedVisitBook, actualModel.getVisitBook());
        assertEquals(expectedFilteredVisitList, actualModel.getSortedVisitList());
    }

    /**
     * Compares the two strings read from the provided CSV files.
     * The CSV files are equal if the strings are equal.
     *
     * @param expectedCsv The first file to compare.
     * @param actualCsv The second file to compare.
     * @throws FileNotFoundException if the provided files are invalid.
     */
    public static void assertCsvFilesAreEqual(String expectedCsv, String actualCsv) {
        try {
            Scanner sc1 = new Scanner(new File(expectedCsv));
            Scanner sc2 = new Scanner(new File(actualCsv));

            assertTrue(sc1.hasNext());
            assertTrue(sc2.hasNext());

            while (sc1.hasNext()) {
                // The files do not have equal number of lines
                if (!sc2.hasNext()) {
                    fail();
                }
                assertEquals(sc1.nextLine(), sc2.nextLine());
            }

            // Both files should not have any lines left
            if (sc2.hasNext()) {
                fail();
            }
        } catch (FileNotFoundException e) {
            fail();
        }


    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getSortedPersonList().size());

        Person person = model.getSortedPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new PersonNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getSortedPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the location at the given {@code targetIndex} in the
     * {@code model}'s location book.
     */
    public static void showLocationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getSortedLocationList().size());

        Location location = model.getSortedLocationList().get(targetIndex.getZeroBased());
        final String[] splitName = location.getName().fullName.split("\\s+");
        model.updateFilteredLocationList(new LocationNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getSortedLocationList().size());
    }
}
