package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.HOON_LOCATION;
import static seedu.address.testutil.TypicalLocations.IDA_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;

public class JsonLocationBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLocationBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readLocationBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLocationBook(null));
    }

    private java.util.Optional<ReadOnlyLocationBook> readLocationBook(String filePath) throws Exception {
        return new JsonLocationBookStorage(Paths.get(filePath)).readLocationBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLocationBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readLocationBook("notJsonFormatLocationBook.json"));
    }

    @Test
    public void readLocationBook_invalidLocationLocationBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLocationBook("invalidLocationLocationBook.json"));
    }

    @Test
    public void readLocationBook_invalidAndValidLocationLocationBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLocationBook("invalidAndValidLocationLocationBook.json"));
    }

    @Test
    public void readAndSaveLocationBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLocationBook.json");
        LocationBook original = getTypicalLocationBook();
        JsonLocationBookStorage jsonLocationBookStorage = new JsonLocationBookStorage(filePath);

        // Save in new file and read back
        jsonLocationBookStorage.saveLocationBook(original, filePath);
        ReadOnlyLocationBook readBack = jsonLocationBookStorage.readLocationBook(filePath).get();
        assertEquals(original, new LocationBook(readBack));

        // Modify data, overwrite existing file, and read back
        original.addLocation(HOON_LOCATION);
        original.removeLocation(ALICE_LOCATION);
        jsonLocationBookStorage.saveLocationBook(original, filePath);
        readBack = jsonLocationBookStorage.readLocationBook(filePath).get();
        assertEquals(original, new LocationBook(readBack));

        // Save and read without specifying file path
        original.addLocation(IDA_LOCATION);
        jsonLocationBookStorage.saveLocationBook(original); // file path not specified
        readBack = jsonLocationBookStorage.readLocationBook().get(); // file path not specified
        assertEquals(original, new LocationBook(readBack));

    }

    @Test
    public void saveLocationBook_nullLocationBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLocationBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code locationBook} at the specified {@code filePath}.
     */
    private void saveLocationBook(ReadOnlyLocationBook locationBook, String filePath) {
        try {
            new JsonLocationBookStorage(Paths.get(filePath))
                    .saveLocationBook(locationBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLocationBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLocationBook(new LocationBook(), null));
    }
}
