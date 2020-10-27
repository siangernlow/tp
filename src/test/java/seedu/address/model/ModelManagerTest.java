package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.AMY_LOCATION;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalLocations.CARL_LOCATION;
import static seedu.address.testutil.TypicalLocations.DANIEL_LOCATION;
import static seedu.address.testutil.TypicalLocations.HOON_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.FIFTH_VISIT;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;
import static seedu.address.testutil.TypicalVisits.THIRD_VISIT;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.person.PersonBook;
import seedu.address.model.person.PersonNameContainsKeywordsPredicate;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.LocationBookBuilder;
import seedu.address.testutil.LocationBuilder;
import seedu.address.testutil.VisitBookBuilder;
import seedu.address.testutil.VisitBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PersonBook(), new PersonBook(modelManager.getPersonBook()));
        assertEquals(new LocationBook(), new LocationBook(modelManager.getLocationBook()));
        assertEquals(new VisitBook(), new VisitBook(modelManager.getVisitBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPersonBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPersonBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPersonBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPersonBookFilePath(path);
        assertEquals(path, modelManager.getPersonBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getSortedPersonList().remove(0));
    }

    @Test
    public void setLocationBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLocationBookFilePath(null));
    }

    @Test
    public void setLocationBookFilePath_validPath_setsLocationBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setLocationBookFilePath(path);
        assertEquals(path, modelManager.getLocationBookFilePath());
    }

    @Test
    public void hasLocation_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasLocation(null));
    }

    @Test
    public void hasLocation_locationNotInLocationBook_returnsFalse() {
        assertFalse(modelManager.hasLocation(CARL_LOCATION));
    }

    @Test
    public void hasLocation_locationInAddressBook_returnsTrue() {
        modelManager.addLocation(CARL_LOCATION);
        assertTrue(modelManager.hasLocation(CARL_LOCATION));
    }

    @Test
    public void getFilteredLocationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getSortedLocationList().remove(0));
    }

    @Test
    public void setVisitBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLocationBookFilePath(null));
    }

    @Test
    public void setVisitBookFilePath_validPath_setsVisitBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setVisitBookFilePath(path);
        assertEquals(path, modelManager.getVisitBookFilePath());
    }

    @Test
    public void hasVisit_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasVisit(null));
    }

    @Test
    public void hasVisit_visitNotInVisitBook_returnsFalse() {
        Visit sampleA = new VisitBuilder().withPerson(ALICE)
                .withLocation(ALICE_LOCATION).withDate("2020-09-09").build();
        assertFalse(modelManager.hasVisit(sampleA));
    }

    @Test
    public void hasVisit_visitInAddressBook_returnsTrue() {
        Visit sampleA = new VisitBuilder().withPerson(ALICE)
                .withLocation(ALICE_LOCATION).withDate("2020-02-09").build();
        modelManager.addVisit(sampleA);
        assertTrue(modelManager.hasVisit(sampleA));
    }

    @Test
    public void deleteVisitsWithPerson_visitsContainDeletedPerson_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());
        Model actualModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());

        expectedModel.deleteVisit(SECOND_VISIT);
        actualModel.deleteVisitsWithPerson(AMY);
        assertEquals(expectedModel, actualModel);

        expectedModel.deleteVisit(FIFTH_VISIT);
        actualModel.deleteVisitsWithPerson(CARL);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void deleteVisitsWithPerson_visitsDoNotContainDeletedPerson_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());
        Model actualModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());

        actualModel.deleteVisitsWithPerson(HOON);
        assertEquals(expectedModel, actualModel);

        actualModel.deleteVisitsWithPerson(IDA);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void deleteVisitsWithLocation_visitsContainDeletedLocation_success() {
        VisitBook expectedVisitBook = getTypicalVisitBook();
        VisitBook actualVisitBook = getTypicalVisitBook();

        expectedVisitBook.removeVisit(SECOND_VISIT);
        expectedVisitBook.removeVisit(THIRD_VISIT);
        actualVisitBook.deleteVisitsWithLocation(AMY_LOCATION);
        assertEquals(expectedVisitBook, actualVisitBook);

        expectedVisitBook.removeVisit(FIRST_VISIT);
        actualVisitBook.deleteVisitsWithLocation(BENSON_LOCATION);
        assertEquals(expectedVisitBook, actualVisitBook);
    }

    @Test
    public void deleteVisitsWithLocation_visitsDoNotContainDeletedLocation_success() {
        VisitBook expectedVisitBook = getTypicalVisitBook();
        VisitBook actualVisitBook = getTypicalVisitBook();

        actualVisitBook.deleteVisitsWithLocation(ALICE_LOCATION);
        assertEquals(expectedVisitBook, actualVisitBook);

        actualVisitBook.deleteVisitsWithLocation(HOON_LOCATION);
        assertEquals(expectedVisitBook, actualVisitBook);
    }

    @Test
    public void updateVisitBookWithEditedLocation_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());
        Model actualModel = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                getTypicalVisitBook(), new UserPrefs());

        Location editedLocation = new LocationBuilder(BENSON_LOCATION).withName("benson location")
                .build();
        Visit editedVisit = new VisitBuilder(FIRST_VISIT).withLocation(editedLocation).build();

        expectedModel.setVisit(FIRST_VISIT, editedVisit);
        actualModel.updateVisitBookWithEditedLocation(editedLocation);
        assertEquals(expectedModel, actualModel);

        Location editedSecondLocation = new LocationBuilder(AMY_LOCATION).withName("amy location").build();
        Visit editedSecondVisit = new VisitBuilder(SECOND_VISIT).withLocation(editedSecondLocation).build();
        Visit editedThirdVisit = new VisitBuilder(THIRD_VISIT).withLocation(editedSecondLocation).build();

        expectedModel.setVisit(SECOND_VISIT, editedSecondVisit);
        expectedModel.setVisit(THIRD_VISIT, editedThirdVisit);
        actualModel.updateVisitBookWithEditedLocation(editedSecondLocation);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void equals() {
        PersonBook personBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        PersonBook differentPersonBook = new PersonBook();
        LocationBook locationBook = new LocationBookBuilder().withLocation(CARL_LOCATION).withLocation(DANIEL_LOCATION)
                .build();
        LocationBook differentLocationBook = new LocationBook();

        Visit sampleB = new VisitBuilder().withPerson(ALICE)
                .withLocation(BENSON_LOCATION).withDate("2020-02-09").build();
        VisitBook visitBook = new VisitBookBuilder().withVisit(sampleB).build();
        VisitBook differentVisitBook = new VisitBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(personBook, locationBook, visitBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(personBook, locationBook, visitBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different personBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPersonBook, locationBook, visitBook, userPrefs)));

        // different locationBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(personBook, differentLocationBook, visitBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new PersonNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(personBook, locationBook, visitBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPersonBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(personBook, locationBook, visitBook, differentUserPrefs)));
    }
}
