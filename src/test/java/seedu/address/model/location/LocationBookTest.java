package seedu.address.model.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB_LOCATION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.location.exceptions.DuplicateLocationException;
import seedu.address.testutil.LocationBuilder;

public class LocationBookTest {
    private final LocationBook locationBook = new LocationBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), locationBook.getLocationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> locationBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyLocationBook_replacesData() {
        LocationBook newData = getTypicalLocationBook();
        locationBook.resetData(newData);
        assertEquals(newData, locationBook);
    }

    @Test
    public void resetData_withDuplicateLocations_throwsDuplicateLocationException() {
        // Two locations with the same identity fields
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        List<Location> newLocations = Arrays.asList(ALICE_LOCATION, editedAlice);
        LocationBookTest.LocationBookStub newData = new LocationBookTest.LocationBookStub(newLocations);

        assertThrows(DuplicateLocationException.class, () -> locationBook.resetData(newData));
    }

    @Test
    public void hasLocation_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> locationBook.hasLocation(null));
    }

    @Test
    public void hasLocation_locationNotInLocationBook_returnsFalse() {
        assertFalse(locationBook.hasLocation(ALICE_LOCATION));
    }

    @Test
    public void hasLocation_locationInLocationBook_returnsTrue() {
        locationBook.addLocation(ALICE_LOCATION);
        assertTrue(locationBook.hasLocation(ALICE_LOCATION));
    }

    @Test
    public void hasLocation_locationWithSameIdentityFieldsInLocationBook_returnsTrue() {
        locationBook.addLocation(ALICE_LOCATION);
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        assertTrue(locationBook.hasLocation(editedAlice));
    }

    @Test
    public void getLocationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> locationBook.getLocationList().remove(0));
    }

    @Test
    public void hashCode_success() {
        List<Location> locationsList = locationBook.getLocationList();
        assertEquals(locationsList.hashCode(), locationBook.hashCode());
    }

    /**
     * A stub ReadOnlyLocationBook whose locations list can violate interface constraints.
     */
    private static class LocationBookStub implements ReadOnlyLocationBook {
        private final ObservableList<Location> locations = FXCollections.observableArrayList();

        LocationBookStub(Collection<Location> locations) {
            this.locations.setAll(locations);
        }

        @Override
        public ObservableList<Location> getLocationList() {
            return locations;
        }
    }
}
