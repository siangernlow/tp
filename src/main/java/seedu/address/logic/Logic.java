package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.location.Location;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    //=========== Person Book =======================================================================================

    /**
     * Returns the PersonBook.
     *
     * @see seedu.address.model.Model#getPersonBook()
     */
    ReadOnlyPersonBook getPersonBook();

    /** Returns an unmodifiable view of the filtered and sorted list of persons */
    ObservableList<Person> getSortedPersonList();

    /**
     * Returns the user prefs' person book file path.
     */
    Path getPersonBookFilePath();

    //=========== Location Book =====================================================================================

    /**
     * Returns the LocationBook.
     *
     * @see seedu.address.model.Model#getLocationBook()
     */
    ReadOnlyLocationBook getLocationBook();

    /** Returns an unmodifiable view of the filtered and sorted list of locations */
    ObservableList<Location> getSortedLocationList();

    /**
     * Returns the user prefs' location book file path.
     */
    Path getLocationBookFilePath();

    //=========== Visit Book ========================================================================================

    /**
     * Returns the VisitBook.
     *
     * @see seedu.address.model.Model#getVisitBook()
     */
    ReadOnlyVisitBook getVisitBook();

    /** Returns an unmodifiable view of the filtered and sorted list of visits */
    ObservableList<Visit> getSortedVisitList();

    /**
     * Returns the user prefs' visit book file path.
     */
    Path getVisitBookFilePath();

    //=========== GUI Settings ======================================================================================

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
