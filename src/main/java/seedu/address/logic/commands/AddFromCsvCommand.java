package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

/**
 * Encapsulates the behaviour of commands which add objects from
 * CSV files.
 */
public abstract class AddFromCsvCommand extends Command {
    public static final String COMMAND_WORD = "addFromCsv";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reads and adds the data from the specified CSV file "
            + "to VirusTracker. "
            + "Parameters: FILE_PATH "
            + PREFIX_LIST + "DATA_TYPE\n"
            + "FILE_PATH is the absolute file path of the .csv file.\n"
            + "DATA_TYPE refers to either people, locations or visits.\n"
            + "The CSV file should have columns corresponding to each parameter required "
            + "in the respective add functions. Refer to the user guide for more details.\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:/Users/admin/peopleData.csv l/people";

}
