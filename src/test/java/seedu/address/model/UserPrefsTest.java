package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setPersonBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same object -> returns true
        assertEquals(userPrefs, userPrefs);

        // same values -> returns true
        UserPrefs userPrefsCopy = new UserPrefs();
        assertEquals(userPrefs, userPrefsCopy);

        // different GUI -> returns false
        UserPrefs userPrefsDifferentGui = new UserPrefs();
        GuiSettings guiSettings = new GuiSettings(100, 200, 1, 0);
        userPrefsDifferentGui.setGuiSettings(guiSettings);
        assertNotEquals(userPrefs, userPrefsDifferentGui);

        // different person file path -> returns false
        UserPrefs userPrefsDifferentPersonFilePath = new UserPrefs();
        userPrefsDifferentPersonFilePath.setPersonBookFilePath(userPrefs.getLocationBookFilePath());
        assertNotEquals(userPrefs, userPrefsDifferentPersonFilePath);

        // different location file path -> returns false
        UserPrefs userPrefsDifferentLocationFilePath = new UserPrefs();
        userPrefsDifferentLocationFilePath.setLocationBookFilePath(userPrefs.getVisitBookFilePath());
        assertNotEquals(userPrefs, userPrefsDifferentLocationFilePath);

        // different visit file path -> returns false
        UserPrefs userPrefsDifferentVisitFilePath = new UserPrefs();
        userPrefsDifferentVisitFilePath.setVisitBookFilePath(userPrefs.getPersonBookFilePath());
        assertNotEquals(userPrefs, userPrefsDifferentVisitFilePath);

        // different types -> returns false
        assertNotEquals(userPrefs, 1);

        // null -> returns false
        assertNotEquals(userPrefs, null);
    }

    @Test
    public void hashCode_success() {
        UserPrefs userPrefs = new UserPrefs();
        int hashcode = Objects.hash(
                userPrefs.getGuiSettings(),
                userPrefs.getPersonBookFilePath(),
                userPrefs.getLocationBookFilePath(),
                userPrefs.getVisitBookFilePath()
        );

        assertEquals(userPrefs.hashCode(), hashcode);
    }
}
