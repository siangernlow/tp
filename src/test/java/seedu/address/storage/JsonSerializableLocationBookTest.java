package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.location.LocationBook;
import seedu.address.testutil.TypicalLocations;

public class JsonSerializableLocationBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLocationBookTest");
    private static final Path TYPICAL_LOCATIONS_FILE = TEST_DATA_FOLDER.resolve("typicalLocationsLocationBook.json");
    private static final Path INVALID_LOCATION_FILE = TEST_DATA_FOLDER.resolve("invalidLocationLocationBook.json");
    private static final Path DUPLICATE_LOCATION_FILE = TEST_DATA_FOLDER.resolve("duplicateLocationLocationBook.json");

    @Test
    public void toModelType_typicalLocationsFile_success() throws Exception {
        JsonSerializableLocationBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_LOCATIONS_FILE,
                JsonSerializableLocationBook.class).get();
        LocationBook locationBookFromFile = dataFromFile.toModelType();
        LocationBook typicalLocationsLocationBook = TypicalLocations.getTypicalLocationBook();
        assertEquals(locationBookFromFile, typicalLocationsLocationBook);
    }

    @Test
    public void toModelType_invalidLocationFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLocationBook dataFromFile = JsonUtil.readJsonFile(INVALID_LOCATION_FILE,
                JsonSerializableLocationBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLocations_throwsIllegalValueException() throws Exception {
        JsonSerializableLocationBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LOCATION_FILE,
                JsonSerializableLocationBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableLocationBook.MESSAGE_DUPLICATE_LOCATION,
                dataFromFile::toModelType);
    }
}
