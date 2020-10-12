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
import seedu.address.model.location.ReadOnlyLocationBook;

/**
 * A class to access LocationBook data stored as a json file on the hard disk.
 */
public class JsonLocationBookStorage implements LocationBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLocationBookStorage.class);

    private Path filePath;

    public JsonLocationBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLocationBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLocationBook> readLocationBook() throws DataConversionException {
        return readLocationBook(filePath);
    }

    /**
     * Similar to {@link #readLocationBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLocationBook> readLocationBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableLocationBook> jsonLocationBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableLocationBook.class);
        if (!jsonLocationBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLocationBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLocationBook(ReadOnlyLocationBook locationBook) throws IOException {
        saveLocationBook(locationBook, filePath);
    }

    /**
     * Similar to {@link #saveLocationBook(ReadOnlyLocationBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLocationBook(ReadOnlyLocationBook locationBook, Path filePath) throws IOException {
        requireNonNull(locationBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLocationBook(locationBook), filePath);
    }
}
