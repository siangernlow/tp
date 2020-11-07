package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;

/**
 * API of the Storage component
 */
public interface Storage extends PersonBookStorage, LocationBookStorage, UserPrefsStorage, VisitBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyPersonBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void savePersonBook(ReadOnlyPersonBook addressBook) throws IOException;

    @Override
    Path getLocationBookFilePath();

    @Override
    Optional<ReadOnlyLocationBook> readLocationBook() throws DataConversionException, IOException;

    @Override
    void saveLocationBook(ReadOnlyLocationBook locationBook) throws IOException;

    @Override
    Path getVisitBookFilePath();

    @Override
    Optional<ReadOnlyVisitBook> readVisitBook() throws DataConversionException, IOException;

    @Override
    void saveVisitBook(ReadOnlyVisitBook visitBook) throws IOException;
}
