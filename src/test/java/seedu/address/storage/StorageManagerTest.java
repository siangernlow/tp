package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.LocationBook;
import seedu.address.model.PersonBook;
import seedu.address.model.ReadOnlyLocationBook;
import seedu.address.model.ReadOnlyPersonBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonLocationBookStorage locationBookStorage = new JsonLocationBookStorage(getTempFilePath("lb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonVisitBookStorage visitBookStorage = new JsonVisitBookStorage(getTempFilePath("vb"));
        storageManager = new StorageManager(addressBookStorage, locationBookStorage, userPrefsStorage,
                visitBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPersonBookStorageTest} class.
         */
        PersonBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyPersonBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new PersonBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void locationBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonLocationBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonLocationBookStorageTest} class.
         */
        LocationBook original = getTypicalLocationBook();
        storageManager.saveLocationBook(original);
        ReadOnlyLocationBook retrieved = storageManager.readLocationBook().get();
        assertEquals(original, new LocationBook(retrieved));
    }

    @Test
    public void getLocationBookFilePath() {
        assertNotNull(storageManager.getLocationBookFilePath());
    }

}
