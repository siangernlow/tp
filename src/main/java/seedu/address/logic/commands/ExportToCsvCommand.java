package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_TYPE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

import java.io.FileWriter;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ListType;
import seedu.address.model.InfoHandler;
import seedu.address.model.Model;

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
    private final String filepath;
    private final ListType listType;

    /**
     * Creates an ExportToCsvCommand to export the specified list type to a CSV file.
     *
     * @param fileWriter The file writer to write the data to the CSV file.
     * @param filepath The absolute file path of the file. Used for equality checking.
     * @param listType The type of list to be exported.
     */
    public ExportToCsvCommand(FileWriter fileWriter, String filepath, ListType listType) {
        requireAllNonNull(fileWriter, filepath, listType);
        this.fileWriter = fileWriter;
        this.filepath = filepath;
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        InfoHandler infoHandler = new InfoHandler(model);
        try {
            switch (listType) {
            case ALL_PEOPLE:
                String personListAttributeString = infoHandler.getPersonListAsString();

                if (personListAttributeString.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_NO_OBJECTS_TO_ADD, PERSONS));
                }

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
                throw new CommandException(String.format(MESSAGE_INVALID_DATA_TYPE, ExportToCsvCommand.MESSAGE_USAGE));
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_WRITE_TO_FILE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportToCsvCommand)) {
            return false;
        }

        // state check
        ExportToCsvCommand e = (ExportToCsvCommand) other;
        return filepath.equals(e.filepath)
                && listType.equals(e.listType);

    }
}
