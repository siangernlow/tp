package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.visit.ReadOnlyVisitBook;


/**
 * A class to access VisitBook data stored as a json file on the hard disk.
 */
public class JsonVisitBookStorage implements VisitBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonVisitBookStorage.class);

    private Path filePath;

    public JsonVisitBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getVisitBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyVisitBook> readVisitBook() throws DataConversionException {
        return readVisitBook(filePath);
    }

    /**
     * Similar to {@link #readVisitBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyVisitBook> readVisitBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableVisitBook> jsonVisitBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableVisitBook.class);
        if (jsonVisitBook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonVisitBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveVisitBook(ReadOnlyVisitBook visitBook) throws IOException {
        saveVisitBook(visitBook, filePath);
    }

    /**
     * Similar to {@link #saveVisitBook(ReadOnlyVisitBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveVisitBook(ReadOnlyVisitBook visitBook, Path filePath) throws IOException {
        requireNonNull(visitBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableVisitBook(visitBook), filePath);
    }
}
