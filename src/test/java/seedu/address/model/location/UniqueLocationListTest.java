package seedu.address.model.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.AMY_LOCATION;
import static seedu.address.testutil.TypicalLocations.BOB_LOCATION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.location.exceptions.DuplicateLocationException;
import seedu.address.model.location.exceptions.LocationNotFoundException;
import seedu.address.model.location.exceptions.LocationNotIdentifiableException;
import seedu.address.testutil.LocationBuilder;

public class UniqueLocationListTest {
    private final UniqueLocationList uniqueLocationList = new UniqueLocationList();

    @Test
    public void contains_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.contains(null));
    }

    @Test
    public void contains_locationNotInList_returnsFalse() {
        assertFalse(uniqueLocationList.contains(ALICE_LOCATION));
    }

    @Test
    public void contains_locationInList_returnsTrue() {
        Location alice = new LocationBuilder().withName("Alice Pauline House")
                .withAddress("123, Jurong West Ave 6, #08-111").withId("L1234").build();
        uniqueLocationList.add(ALICE_LOCATION);
        assertTrue(uniqueLocationList.contains(ALICE_LOCATION));
    }

    @Test
    public void contains_locationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLocationList.add(ALICE_LOCATION);
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        assertTrue(uniqueLocationList.contains(editedAlice));
    }

    @Test
    public void containsSameIdLocation_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.containsLocationId(null));
    }

    @Test
    public void containsSameIdLocation_locationNotInList_returnsFalse() {
        assertFalse(uniqueLocationList.containsLocationId(ALICE_LOCATION.getId()));
    }

    @Test
    public void containsSameIdLocation_sameIdInList_returnsTrue() {
        uniqueLocationList.add(ALICE_LOCATION);
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_BOB).build();
        assertTrue(uniqueLocationList.containsLocationId(editedAlice.getId()));
    }

    @Test
    public void containsSameIdLocation_sameIdentityDifferentId_returnsFalse() {
        uniqueLocationList.add(ALICE_LOCATION);
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        assertFalse(uniqueLocationList.containsLocationId(editedAlice.getId()));
    }

    @Test
    public void getLocationById_validId_success() {
        uniqueLocationList.add(ALICE_LOCATION);
        assertEquals(ALICE_LOCATION, uniqueLocationList.getLocationById(ALICE_LOCATION.getId()));
    }

    @Test
    public void getLocationById_locationNotFound_throwsLocationNotFoundException() {
        assertThrows(LocationNotFoundException.class, () -> uniqueLocationList.getLocationById(ALICE_LOCATION.getId()));
    }

    @Test
    public void add_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.add(null));
    }

    @Test
    public void add_duplicateLocation_throwsDuplicateLocationException() {
        uniqueLocationList.add(ALICE_LOCATION);
        assertThrows(DuplicateLocationException.class, () -> uniqueLocationList.add(ALICE_LOCATION));
    }

    @Test
    public void add_unidentifiableLocation_throwsLocationNotIdentifiableException() {
        uniqueLocationList.add(ALICE_LOCATION);
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_BOB).build();
        assertThrows(LocationNotIdentifiableException.class, () -> uniqueLocationList.add(editedAlice));
    }

    @Test
    public void setLocation_nullTargetLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.setLocation(null, ALICE_LOCATION));
    }

    @Test
    public void setLocation_nullEditedLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.setLocation(ALICE_LOCATION, null));
    }

    @Test
    public void setLocation_targetLocationNotInList_throwsLocationNotFoundException() {
        assertThrows(LocationNotFoundException.class, () ->
                uniqueLocationList.setLocation(ALICE_LOCATION, ALICE_LOCATION));
    }

    @Test
    public void setLocation_editedLocationIsSameLocation_success() {
        uniqueLocationList.add(ALICE_LOCATION);
        uniqueLocationList.setLocation(ALICE_LOCATION, ALICE_LOCATION);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        expectedUniqueLocationList.add(ALICE_LOCATION);
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocation_editedLocationHasSameIdentity_success() {
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withId(VALID_ID_BOB_LOCATION).build();
        uniqueLocationList.add(editedAlice);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        expectedUniqueLocationList.add(editedAlice);
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocation_editedLocationHasDifferentIdentity_success() {
        uniqueLocationList.add(ALICE_LOCATION);
        Location editedLocation = new LocationBuilder().withId("L1234").build();
        uniqueLocationList.setLocation(ALICE_LOCATION, editedLocation);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        expectedUniqueLocationList.add(editedLocation);
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocation_editedLocationHasNonUniqueIdentity_throwsDuplicateLocationException() {
        uniqueLocationList.add(AMY_LOCATION);
        uniqueLocationList.add(BOB_LOCATION);
        Location editedAlice = new LocationBuilder(AMY_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_BOB).build();
        assertThrows(DuplicateLocationException.class, () ->
                uniqueLocationList.setLocation(AMY_LOCATION, editedAlice));
    }

    @Test
    public void setLocation_editedLocationIdExistsButTargetDifferentId_throwsLocationNotIdentifiableException() {
        uniqueLocationList.add(AMY_LOCATION);
        uniqueLocationList.add(BOB_LOCATION);
        Location editedAlice = new LocationBuilder(AMY_LOCATION).withId(VALID_ID_BOB_LOCATION)
                .withName(VALID_NAME_BOB).build();
        assertThrows(LocationNotIdentifiableException.class, () ->
                uniqueLocationList.setLocation(AMY_LOCATION, editedAlice));
    }

    @Test
    public void remove_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.remove(null));
    }

    @Test
    public void remove_locationDoesNotExist_throwsLocationNotFoundException() {
        assertThrows(LocationNotFoundException.class, () -> uniqueLocationList.remove(ALICE_LOCATION));
    }

    @Test
    public void remove_existingLocation_removesLocation() {
        uniqueLocationList.add(ALICE_LOCATION);
        uniqueLocationList.remove(ALICE_LOCATION);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocations_nullUniqueLocationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.setLocations((UniqueLocationList) null));
    }

    @Test
    public void setLocations_uniqueLocationList_replacesOwnListWithProvidedUniqueLocationList() {
        uniqueLocationList.add(ALICE_LOCATION);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        expectedUniqueLocationList.add(BOB_LOCATION);
        uniqueLocationList.setLocations(expectedUniqueLocationList);
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocations_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLocationList.setLocations((List<Location>) null));
    }

    @Test
    public void setLocations_list_replacesOwnListWithProvidedList() {
        uniqueLocationList.add(ALICE_LOCATION);
        List<Location> locationList = Collections.singletonList(BOB_LOCATION);
        uniqueLocationList.setLocations(locationList);
        UniqueLocationList expectedUniqueLocationList = new UniqueLocationList();
        expectedUniqueLocationList.add(BOB_LOCATION);
        assertEquals(expectedUniqueLocationList, uniqueLocationList);
    }

    @Test
    public void setLocations_listWithDuplicateLocations_throwsDuplicateLocationException() {
        List<Location> listWithDuplicateLocations = Arrays.asList(ALICE_LOCATION, ALICE_LOCATION);
        assertThrows(DuplicateLocationException.class, () ->
                uniqueLocationList.setLocations(listWithDuplicateLocations));
    }

    @Test
    public void setLocations_listWithUnidentifiableLocations_throwsLocationNotIdentifiableException() {
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_BOB).build();
        List<Location> listWithUnidentifiableLocations = Arrays.asList(ALICE_LOCATION, editedAlice);
        assertThrows(LocationNotIdentifiableException.class, () ->
                uniqueLocationList.setLocations(listWithUnidentifiableLocations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueLocationList.asUnmodifiableObservableList().remove(0));
    }

}
