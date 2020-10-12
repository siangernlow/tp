package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;

/**
 * Represents a storage for {@link LocationBook}.
 */
public interface LocationBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLocationBookFilePath();

    /**
     * Returns LocationBook data as a {@link ReadOnlyLocationBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLocationBook> readLocationBook() throws DataConversionException, IOException;

    /**
     * @see #getLocationBookFilePath()
     */
    Optional<ReadOnlyLocationBook> readLocationBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLocationBook} to the storage.
     * @param locationBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLocationBook(ReadOnlyLocationBook locationBook) throws IOException;

    /**
     * @see #saveLocationBook(ReadOnlyLocationBook)
     */
    void saveLocationBook(ReadOnlyLocationBook locationBook, Path filePath) throws IOException;
}
