package seedu.address.model.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VisitBuilder;

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
        Visit sample = new VisitBuilder().withPerson(ALICE).withLocation(ALICE_LOCATION).withDate("2020-09-12").build();
        assertFalse(visitBook.hasVisit(sample));
    }

    @Test
    public void hasVisit_visitInVisitBook_returnsTrue() {
        Visit sample = new VisitBuilder().withPerson(ALICE).withLocation(ALICE_LOCATION).withDate("2020-09-12").build();
        visitBook.addVisit(sample);
        assertTrue(visitBook.hasVisit(sample));
    }

    @Test
    public void hasVisit_visitWithSameIdentityFieldsInVisitBook_returnsTrue() {
        Visit sample = new VisitBuilder().withPerson(ALICE).withLocation(ALICE_LOCATION).withDate("2020-09-13").build();
        visitBook.addVisit(sample);
        Visit editedSample = new VisitBuilder(sample).withPerson(ALICE).build();
        assertTrue(visitBook.hasVisit(editedSample));
    }

    @Test
    public void getVisitList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> visitBook.getVisitList().remove(0));
    }
}
