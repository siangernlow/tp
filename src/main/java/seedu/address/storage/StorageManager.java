package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.*;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private LocationBookStorage locationBookStorage;
    private VisitBookStorage visitBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage}, {@code LocationBookStorage},
     * {@code VisitBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, LocationBookStorage locationBookStorage,
                          UserPrefsStorage userPrefsStorage, VisitBookStorage visitBookStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.locationBookStorage = locationBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.visitBookStorage = visitBookStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ LocationBook methods ==============================

    @Override
    public Path getLocationBookFilePath() {
        return locationBookStorage.getLocationBookFilePath();
    }

    @Override
    public Optional<ReadOnlyLocationBook> readLocationBook() throws DataConversionException, IOException {
        return readLocationBook(locationBookStorage.getLocationBookFilePath());
    }

    @Override
    public Optional<ReadOnlyLocationBook> readLocationBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return locationBookStorage.readLocationBook(filePath);
    }

    @Override
    public void saveLocationBook(ReadOnlyLocationBook locationBook) throws IOException {
        saveLocationBook(locationBook, locationBookStorage.getLocationBookFilePath());
    }

    @Override
    public void saveLocationBook(ReadOnlyLocationBook locationBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        locationBookStorage.saveLocationBook(locationBook, filePath);
    }

    // ================ VisitBook methods ==============================

    @Override
    public Path getVisitBookFilePath() {
        return visitBookStorage.getVisitBookFilePath();
    }

    @Override
    public Optional<ReadOnlyVisitBook> readVisitBook() throws DataConversionException, IOException {
        return readVisitBook(visitBookStorage.getVisitBookFilePath());
    }

    @Override
    public Optional<ReadOnlyVisitBook> readVisitBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return visitBookStorage.readVisitBook(filePath);
    }

    @Override
    public void saveVisitBook(ReadOnlyVisitBook visitBook) throws IOException {
        saveVisitBook(visitBook, visitBookStorage.getVisitBookFilePath());
    }

    @Override
    public void saveVisitBook(ReadOnlyVisitBook visitBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        visitBookStorage.saveVisitBook(visitBook, filePath);
    }
}
