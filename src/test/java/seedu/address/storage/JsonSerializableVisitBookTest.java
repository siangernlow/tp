package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.TypicalVisits;

public class JsonSerializableVisitBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableVisitBookTest");
    private static final Path TYPICAL_VISITS_FILE = TEST_DATA_FOLDER.resolve("typicalVisitVisitBook.json");
    private static final Path INVALID_VISIT_FILE = TEST_DATA_FOLDER.resolve("invalidVisitVisitBook.json");
    private static final Path DUPLICATE_VISIT_FILE = TEST_DATA_FOLDER.resolve("duplicateVisitVisitBook.json");

    @Test
    public void toModelType_typicalVisitsFile_success() throws Exception {
        JsonSerializableVisitBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_VISITS_FILE,
                JsonSerializableVisitBook.class).get();
        VisitBook visitBookFromFile = dataFromFile.toModelType();
        VisitBook typicalVisitVisitBook = TypicalVisits.getTypicalVisitBook();
        assertEquals(visitBookFromFile, typicalVisitVisitBook);
    }

    @Test
    public void toModelType_invalidVisitFile_throwsIllegalValueException() throws Exception {
        JsonSerializableVisitBook dataFromFile = JsonUtil.readJsonFile(INVALID_VISIT_FILE,
                JsonSerializableVisitBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateVisits_throwsIllegalValueException() throws Exception {
        JsonSerializableVisitBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VISIT_FILE,
                JsonSerializableVisitBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableVisitBook.MESSAGE_DUPLICATE_VISIT,
                dataFromFile::toModelType);
    }
}
