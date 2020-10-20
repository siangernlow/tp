package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.ListType.ALL_LOCATIONS;
import static seedu.address.logic.parser.ListType.ALL_PEOPLE;
import static seedu.address.logic.parser.ListType.ALL_VISITS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.location.AddLocationsFromCsvCommand;
import seedu.address.logic.commands.person.AddPersonsFromCsvCommand;
import seedu.address.logic.commands.visit.AddVisitsFromCsvCommand;
import seedu.address.logic.parser.DataGenerator;
import seedu.address.logic.parser.ListType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.InfoHandler;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

public class ExportToCsvCommand extends Command {
    public static final String COMMAND_WORD = "exportToCsv";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the object attributes to the "
            + "specified CSV file. "
            + "Parameters: FILE_PATH "
            + PREFIX_LIST + "DATA_TYPE\n"
            + "FILE_PATH is the absolute file path where the .csv file will be created, including the file itself.\n"
            + "DATA_TYPE refers to either people, locations or visits.\n"
            + "The CSV file created will have the correct format to add the objects to VirusTracker if needed.\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:/Users/admin/peopleData.csv " + PREFIX_LIST + "people";

    public static final String MESSAGE_UNABLE_TO_WRITE_TO_FILE = "Unable to write to the file specified.\n"
            + "Please ensure that your file has proper permissions to be written to.";
    public static final String MESSAGE_NO_OBJECTS_TO_ADD = "The %1$s list is empty.\n"
            + "The export operation will be terminated.";
    public static final String PERSONS = "persons";
    public static final String LOCATIONS = "locations";
    public static final String VISITS = "visits";

    public static final String MESSAGE_SUCCESS = "The %1$s attributes CSV file has been successfully created.\n";

    private final FileWriter fileWriter;
    private final ListType listType;

    public ExportToCsvCommand(FileWriter fileWriter, ListType listType) {
        requireAllNonNull(fileWriter, listType);
        this.fileWriter = fileWriter;
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        InfoHandler infoHandler = model.getInfoHandler();
        try {
            switch (listType) {
            case ALL_PEOPLE:
                String personListAttributeString = infoHandler.getPersonListAsString();

                if (personListAttributeString.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_NO_OBJECTS_TO_ADD, PERSONS));
                }
                System.out.println(personListAttributeString);
                fileWriter.append(personListAttributeString);
                fileWriter.close();

                return new CommandResult(String.format(MESSAGE_SUCCESS, PERSONS));
            case ALL_LOCATIONS:
                String locationListAttributeString = infoHandler.getLocationListAsString();

                if (locationListAttributeString.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_NO_OBJECTS_TO_ADD, LOCATIONS));
                }

                fileWriter.append(locationListAttributeString);
                fileWriter.close();

                return new CommandResult(String.format(MESSAGE_SUCCESS, LOCATIONS));
            case ALL_VISITS:
                String visitListAttributeString = infoHandler.getVisitListAsString();

                if (visitListAttributeString.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_NO_OBJECTS_TO_ADD, VISITS));
                }

                fileWriter.append(visitListAttributeString);
                fileWriter.close();

                return new CommandResult(String.format(MESSAGE_SUCCESS, VISITS));
            default:
                throw new CommandException(String.format(MESSAGE_INVALID_DATA_TYPE, AddFromCsvCommand.MESSAGE_USAGE));
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_WRITE_TO_FILE);
        }
    }
}
