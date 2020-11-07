package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.VirusTrackerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final VirusTrackerParser virusTrackerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        virusTrackerParser = new VirusTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = virusTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePersonBook(model.getPersonBook());
            storage.saveLocationBook(model.getLocationBook());
            storage.saveVisitBook(model.getVisitBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //=========== Person Book =======================================================================================

    @Override
    public ReadOnlyPersonBook getPersonBook() {
        return model.getPersonBook();
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        return model.getSortedPersonList();
    }

    @Override
    public Path getPersonBookFilePath() {
        return model.getPersonBookFilePath();
    }

    //=========== Location Book =====================================================================================

    @Override
    public ReadOnlyLocationBook getLocationBook() {
        return model.getLocationBook();
    }

    @Override
    public ObservableList<Location> getSortedLocationList() {
        return model.getSortedLocationList();
    }

    @Override
    public Path getLocationBookFilePath() {
        return model.getLocationBookFilePath();
    }

    //=========== Visit Book ========================================================================================

    @Override
    public ReadOnlyVisitBook getVisitBook() {
        return model.getVisitBook();
    }

    @Override
    public ObservableList<Visit> getSortedVisitList() {
        return model.getSortedVisitList();
    }

    @Override
    public Path getVisitBookFilePath() {
        return model.getVisitBookFilePath();
    }

    //=========== GUI Settings ======================================================================================

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
