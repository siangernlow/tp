package seedu.address.model.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalLocations.BOB_LOCATION;
import static seedu.address.testutil.TypicalLocations.CARL_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getMoreThanSixtyPercentVisitBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;
import static seedu.address.testutil.TypicalVisits.getWholeVisitBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.LocationBuilder;

public class LocationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                new UserPrefs(), getTypicalVisitBook());
    }

    @Test
    public void isSameLocation() {
        // same object -> returns true
        assertTrue(ALICE_LOCATION.isSameLocation(ALICE_LOCATION));

        // null -> returns false
        assertFalse(ALICE_LOCATION.isSameLocation(null));

        // different name, same address -> returns false
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_LOCATION.isSameLocation(editedAlice));

        // same name, different address -> returns true
        editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE_LOCATION.isSameLocation(editedAlice));
    }

    @Test
    public void isSameId() {
        // same object -> returns true
        assertTrue(ALICE_LOCATION.isSameId(ALICE_LOCATION));

        // null -> returns false
        assertFalse(ALICE_LOCATION.isSameId(null));

        // different id, same name, same address -> returns false
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        assertFalse(ALICE_LOCATION.isSameId(editedAlice));

        // same id, different name, different address -> returns true
        editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_BOB).build();
        assertTrue(ALICE_LOCATION.isSameId(editedAlice));
    }

    @Test
    public void getPredicateForHighRiskLocations() {
        // Infected Locations(3) are less than 60% of total locations(7)
        model.setVisitBook(getWholeVisitBook());
        Predicate<Location> actualPredicate = Location.getPredicateForHighRiskLocations(model);
        model.updateFilteredLocationList(actualPredicate);
        ObservableList<Location> actualList = model.getFilteredLocationList();

        ObservableList<Location> expectedList = FXCollections.observableArrayList();
        expectedList.add(ALICE_LOCATION);
        expectedList.add(BENSON_LOCATION);
        expectedList.add(CARL_LOCATION);

        assertEquals(expectedList, actualList);

        // Infected Locations(6) are more than 60% of total locations(8)
        model.setVisitBook(getMoreThanSixtyPercentVisitBook());
        actualPredicate = Location.getPredicateForHighRiskLocations(model);
        model.updateFilteredLocationList(actualPredicate);
        actualList = model.getFilteredLocationList();

        expectedList.clear();
        expectedList.add(ALICE_LOCATION);
        expectedList.add(BENSON_LOCATION);
        expectedList.add(CARL_LOCATION);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void equals() {
        // same values -> returns true
        Location aliceCopy = new LocationBuilder(ALICE_LOCATION).build();
        assertTrue(ALICE_LOCATION.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_LOCATION.equals(ALICE_LOCATION));

        // null -> returns false
        assertFalse(ALICE_LOCATION.equals(null));

        // different type -> returns false
        assertFalse(ALICE_LOCATION.equals(5));

        // different location -> returns false
        assertFalse(ALICE_LOCATION.equals(BOB_LOCATION));

        // different name -> returns false
        Location editedAlice = new LocationBuilder(ALICE_LOCATION).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_LOCATION.equals(editedAlice));

        // different address -> returns false
        editedAlice = new LocationBuilder(ALICE_LOCATION).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE_LOCATION.equals(editedAlice));

        // different id -> returns false
        editedAlice = new LocationBuilder(ALICE_LOCATION).withId(VALID_ID_BOB_LOCATION).build();
        assertFalse(ALICE_LOCATION.equals(editedAlice));
    }
}
