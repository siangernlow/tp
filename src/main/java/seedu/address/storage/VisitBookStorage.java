package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.VisitBook;

/**
 * Represents a storage for {@link VisitBook}.
 */

public interface VisitBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getVisitBookFilePath();

    /**
     * Returns VisitBook data as a {@link ReadOnlyVisitBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyVisitBook> readVisitBook() throws DataConversionException, IOException;

    /**
     * @see #getVisitBookFilePath()
     */
    Optional<ReadOnlyVisitBook> readVisitBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyVisitBook} to the storage.
     * @param visitBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveVisitBook(ReadOnlyVisitBook visitBook) throws IOException;

    /**
     * @see #saveVisitBook(ReadOnlyVisitBook)
     */
    void saveVisitBook(ReadOnlyVisitBook visitBook, Path filePath) throws IOException;
}
