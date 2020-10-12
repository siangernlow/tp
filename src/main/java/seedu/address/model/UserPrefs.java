package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path personBookFilePath = Paths.get("data" , "personbook.json");
    private Path locationBookFilePath = Paths.get("data", "locationbook.json");
    private Path visitBookFilePath = Paths.get("data", "visitbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setPersonBookFilePath(newUserPrefs.getPersonBookFilePath());
        setLocationBookFilePath(newUserPrefs.getLocationBookFilePath());
        setVisitBookFilePath(newUserPrefs.getVisitBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPersonBookFilePath() {
        return personBookFilePath;
    }

    public void setPersonBookFilePath(Path personBookFilePath) {
        requireNonNull(personBookFilePath);
        this.personBookFilePath = personBookFilePath;
    }

    public Path getLocationBookFilePath() {
        return locationBookFilePath;
    }

    public void setLocationBookFilePath(Path locationBookFilePath) {
        requireNonNull(locationBookFilePath);
        this.locationBookFilePath = locationBookFilePath;
    }

    public Path getVisitBookFilePath() {
        return visitBookFilePath;
    }

    public void setVisitBookFilePath(Path visitBookFilePath) {
        requireNonNull(visitBookFilePath);
        this.visitBookFilePath = visitBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && personBookFilePath.equals(o.personBookFilePath)
                && locationBookFilePath.equals(o.locationBookFilePath)
                && visitBookFilePath.equals(o.visitBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, personBookFilePath, locationBookFilePath, visitBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal person data file location : " + personBookFilePath);
        sb.append("\nLocal location data file location : " + locationBookFilePath);
        sb.append("\nLocal visit data file location : " + visitBookFilePath);
        return sb.toString();
    }

}
