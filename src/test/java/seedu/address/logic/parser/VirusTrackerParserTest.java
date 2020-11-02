package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE;
import static seedu.address.testutil.VisitBuilder.DEFAULT_DATE_STRING;
import static seedu.address.testutil.VisitBuilder.DEFAULT_LOCATION_INDEX;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON_INDEX;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddFromCsvCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportToCsvCommand;
import seedu.address.logic.commands.GenerateLocationsCommand;
import seedu.address.logic.commands.GeneratePeopleCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.location.AddLocationCommand;
import seedu.address.logic.commands.location.AddLocationsFromCsvCommand;
import seedu.address.logic.commands.location.DeleteLocationCommand;
import seedu.address.logic.commands.location.EditLocationCommand;
import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.AddPersonsFromCsvCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.visit.AddVisitCommand;
import seedu.address.logic.commands.visit.AddVisitsFromCsvCommand;
import seedu.address.logic.commands.visit.DeleteVisitCommand;
import seedu.address.logic.commands.visit.DeleteVisitsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonNameContainsKeywordsPredicate;
import seedu.address.testutil.EditLocationDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LocationBuilder;
import seedu.address.testutil.LocationUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class VirusTrackerParserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "AddFromCsvCommandTest");
    private static final String EMPTY_CSV_FILE = TEST_DATA_FOLDER.resolve("EmptyCsvFile.csv").toString();

    private final VirusTrackerParser parser = new VirusTrackerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_addLocation() throws Exception {
        Location location = new LocationBuilder().build();
        AddLocationCommand command =
                (AddLocationCommand) parser.parseCommand(LocationUtil.getAddLocationCommand(location));
        assertEquals(new AddLocationCommand(location), command);
    }

    @Test
    public void parseCommand_addVisit() throws Exception {
        String addVisitString = AddVisitCommand.COMMAND_WORD + " "
                + DEFAULT_PERSON_INDEX.getOneBased() + " "
                + DEFAULT_LOCATION_INDEX.getOneBased() + " "
                + PREFIX_DATE + DEFAULT_DATE_STRING;
        AddVisitCommand command =
                (AddVisitCommand) parser.parseCommand(addVisitString);
        assertEquals(new AddVisitCommand(DEFAULT_PERSON_INDEX, DEFAULT_LOCATION_INDEX, DEFAULT_DATE), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeletePersonCommand(new IndexIdPairStub(INDEX_FIRST, null)), command);
    }

    @Test
    public void parseCommand_deleteLocation() throws Exception {
        DeleteLocationCommand command = (DeleteLocationCommand) parser.parseCommand(
                DeleteLocationCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteLocationCommand(new IndexIdPairStub(INDEX_FIRST, null)), command);
    }

    @Test
    public void parseCommand_deleteVisit() throws Exception {
        DeleteVisitCommand command = (DeleteVisitCommand) parser.parseCommand(
                DeleteVisitCommand.COMMAND_WORD + " " + "1");
        assertEquals(new DeleteVisitCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteVisits() throws Exception {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DeleteVisitsCommand command = (DeleteVisitsCommand) parser.parseCommand(
                DeleteVisitsCommand.COMMAND_WORD + " " + "d/2020-09-12");
        assertEquals(new DeleteVisitsCommand(LocalDate.parse("2020-09-12", inputFormat)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(new IndexIdPairStub(INDEX_FIRST, null), descriptor), command);
    }

    @Test
    public void parseCommand_editLocation() throws Exception {
        Location location = new LocationBuilder().build();
        EditLocationDescriptor descriptor = new EditLocationDescriptorBuilder(location).build();
        EditLocationCommand command = (EditLocationCommand) parser.parseCommand(EditLocationCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + LocationUtil.getEditLocationDescriptorDetails(descriptor));
        assertEquals(new EditLocationCommand(new IndexIdPairStub(INDEX_FIRST, null), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new PersonNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_generateLocations() throws Exception {
        String userInput = GenerateLocationsCommand.COMMAND_WORD + " 1";
        GenerateLocationsCommand command = (GenerateLocationsCommand) parser.parseCommand(userInput);
        assertEquals(new GenerateLocationsCommand(new IndexIdPair(INDEX_FIRST, null, PREFIX_PERSON_ID)), command);
    }

    @Test
    public void parseCommand_generatePeople() throws Exception {
        String userInput = GeneratePeopleCommand.COMMAND_WORD + " 1";
        GeneratePeopleCommand command = (GeneratePeopleCommand) parser.parseCommand(userInput);
        assertEquals(new GeneratePeopleCommand(new IndexIdPair(INDEX_FIRST, null, PREFIX_PERSON_ID)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        String commandAndPrefix = ListCommand.COMMAND_WORD + " " + PREFIX_LIST;
        assertTrue(parser.parseCommand(commandAndPrefix + "people") instanceof ListCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "locations") instanceof ListCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "visits") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addFromCsv() throws Exception {
        String commandAndPrefix = AddFromCsvCommand.COMMAND_WORD + " " + EMPTY_CSV_FILE + " " + PREFIX_LIST;
        assertTrue(parser.parseCommand(commandAndPrefix + "people") instanceof AddPersonsFromCsvCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "locations") instanceof AddLocationsFromCsvCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "visits") instanceof AddVisitsFromCsvCommand);
    }

    @Test
    public void parseCommand_exportToCsv() throws Exception {
        String commandAndPrefix = ExportToCsvCommand.COMMAND_WORD + " " + EMPTY_CSV_FILE + " " + PREFIX_LIST;
        assertTrue(parser.parseCommand(commandAndPrefix + "people") instanceof ExportToCsvCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "locations") instanceof ExportToCsvCommand);
        assertTrue(parser.parseCommand(commandAndPrefix + "visits") instanceof ExportToCsvCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
