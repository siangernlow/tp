package seedu.address.model.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLocations.BOB_LOCATION;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;

import java.time.LocalDate;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VisitBuilder;

public class VisitTest {

    @Test
    public void isSameDate() {
        LocalDate date = LocalDate.of(2020, 9, 9);
        Visit visit = new VisitBuilder().withDate("2020-09-09").build();
        assertTrue(visit.isSameDate(date));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Visit firstVisitCopy = new VisitBuilder(FIRST_VISIT).build();
        assertEquals(FIRST_VISIT, firstVisitCopy);

        // same object -> returns true
        assertEquals(FIRST_VISIT, FIRST_VISIT);

        // null -> returns false
        assertNotEquals(null, FIRST_VISIT);

        // different type -> returns false
        assertNotEquals(FIRST_VISIT, 5);

        // different visit -> returns false
        assertNotEquals(FIRST_VISIT, SECOND_VISIT);

        // different person -> returns false
        Visit editedVisit = new VisitBuilder(FIRST_VISIT).withPerson(BOB).build();
        assertNotEquals(FIRST_VISIT, editedVisit);

        // different location -> returns false
        Visit editedVisitLocation = new VisitBuilder(FIRST_VISIT).withLocation(BOB_LOCATION).build();
        assertNotEquals(FIRST_VISIT, editedVisitLocation);

        // different date -> returns false
        Visit editedVisitDate = new VisitBuilder(FIRST_VISIT).withDate("2020-08-08").build();
        assertNotEquals(FIRST_VISIT, editedVisitDate);
    }

    @Test
    public void hashCode_success() {
        Visit visit = FIRST_VISIT;
        int hashCode = Objects.hash(
                visit.getPerson(),
                visit.getLocation(),
                visit.getDate()
        );

        assertEquals(hashCode, visit.hashCode());
    }
}
