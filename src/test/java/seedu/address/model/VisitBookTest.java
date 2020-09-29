package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.model.location.Location;
import seedu.address.model.location.exceptions.DuplicateLocationException;
import seedu.address.model.visit.Visit;
import seedu.address.testutil.LocationBuilder;
import seedu.address.testutil.VisitBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

public class VisitBookTest {
    private final VisitBook visitBook = new VisitBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), visitBook.getVisitList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> visitBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyVisitBook_replacesData() {
        VisitBook newData = getTypicalVisitBook();
        visitBook.resetData(newData);
        assertEquals(newData, visitBook);
    }

    @Test
    public void hasVisit_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> visitBook.hasVisit(null));
    }

    @Test
    public void hasVisit_visitNotInVisitBook_returnsFalse() {
        Visit sample = new VisitBuilder().withPersonId("1").withLocationId("1").withDate("2020-09-12").build();
        assertFalse(visitBook.hasVisit(sample));
    }

    @Test
    public void hasVisit_visitInVisitBook_returnsTrue() {
        Visit sample = new VisitBuilder().withPersonId("1").withLocationId("1").withDate("2020-09-12").build();
        visitBook.addVisit(sample);
        assertTrue(visitBook.hasVisit(sample));
    }

    @Test
    public void hasVisit_visitWithSameIdentityFieldsInVisitBook_returnsTrue() {
        Visit sample = new VisitBuilder().withPersonId("1").withLocationId("1").withDate("2020-09-13").build();
        visitBook.addVisit(sample);
        Visit editedSample = new VisitBuilder(sample).withPersonId("1").build();
        assertTrue(visitBook.hasVisit(editedSample));
    }

    @Test
    public void getVisitList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> visitBook.getVisitList().remove(0));
    }
}
