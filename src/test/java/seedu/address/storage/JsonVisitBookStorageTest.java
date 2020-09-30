package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.VisitBuilder;

public class JsonVisitBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonVisitBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readVisitBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readVisitBook(null));
    }

    private java.util.Optional<ReadOnlyVisitBook> readVisitBook(String filePath) throws Exception {
        return new JsonVisitBookStorage(Paths.get(filePath)).readVisitBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readVisitBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readVisitBook("notJsonFormatVisitBook.json"));
    }

    @Test
    public void readVisitBook_invalidVisitVisitBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readVisitBook("invalidVisitVisitBook.json"));
    }

    @Test
    public void readVisitBook_invalidAndValidVisitVisitBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readVisitBook("invalidAndValidVisitVisitBook.json"));
    }

    @Test
    public void readAndSaveVisitBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempVisitBook.json");
        VisitBook original = getTypicalVisitBook();
        JsonVisitBookStorage jsonVisitBookStorage = new JsonVisitBookStorage(filePath);

        // Save in new file and read back
        jsonVisitBookStorage.saveVisitBook(original, filePath);
        ReadOnlyVisitBook readBack = jsonVisitBookStorage.readVisitBook(filePath).get();
        assertEquals(original, new VisitBook(readBack));

        Visit sampleA = new VisitBuilder().withPersonId("3").withLocationId("1").withDate("2020-09-12").build();
        Visit sampleB = new VisitBuilder().withPersonId("4").withLocationId("1").withDate("2020-09-12").build();
        // Modify data, overwrite exiting file, and read back
        original.addVisit(sampleA);
        original.removeVisit(sampleA);
        jsonVisitBookStorage.saveVisitBook(original, filePath);
        readBack = jsonVisitBookStorage.readVisitBook(filePath).get();
        assertEquals(original, new VisitBook(readBack));

        // Save and read without specifying file path
        original.addVisit(sampleB);
        jsonVisitBookStorage.saveVisitBook(original); // file path not specified
        readBack = jsonVisitBookStorage.readVisitBook().get(); // file path not specified
        assertEquals(original, new VisitBook(readBack));

    }

    @Test
    public void saveVisitBook_nullVisitBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveVisitBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code visitBook} at the specified {@code filePath}.
     */
    private void saveVisitBook(ReadOnlyVisitBook visitBook, String filePath) {
        try {
            new JsonVisitBookStorage(Paths.get(filePath))
                    .saveVisitBook(visitBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveVisitBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveVisitBook(new VisitBook(), null));
    }
}
