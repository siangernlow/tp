package seedu.address.model.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.ALICE_LOCATION;
import static seedu.address.testutil.TypicalLocations.AMY_LOCATION;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalLocations.HOON_LOCATION;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;
import static seedu.address.testutil.TypicalVisits.THIRD_VISIT;
import static seedu.address.testutil.TypicalVisits.getNonUniqueLocationsVisitBook;
import static seedu.address.testutil.TypicalVisits.getNonUniquePersonsVisitBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.testutil.LocationBuilder;
import seedu.address.testutil.PersonBuilder;
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
    public void updateWithEditedPerson_success() {
        VisitBook expectedVisitBook = getNonUniquePersonsVisitBook();
        VisitBook actualVisitBook = getNonUniquePersonsVisitBook();

        Visit secondVisit = expectedVisitBook.getVisitList().get(1);
        Person editedPerson = new PersonBuilder(secondVisit.getPerson())
                .withName(CARL.getName().toString()).build();
        expectedVisitBook.setVisit(secondVisit,
                new Visit(editedPerson, secondVisit.getLocation(), secondVisit.getDate()));

        actualVisitBook.updateWithEditedPerson(editedPerson);

        assertEquals(expectedVisitBook, actualVisitBook);
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
    public void updateWithEditedLocation_success() {
        VisitBook expectedVisitBook = getNonUniqueLocationsVisitBook();
        VisitBook actualVisitBook = getNonUniqueLocationsVisitBook();

        Visit secondVisit = expectedVisitBook.getVisitList().get(1);
        Visit thirdVisit = expectedVisitBook.getVisitList().get(2);
        Location editedLocation = new LocationBuilder(secondVisit.getLocation())
                .withName(ALICE_LOCATION.getName().toString()).build();
        expectedVisitBook.setVisit(secondVisit,
                new Visit(secondVisit.getPerson(), editedLocation, secondVisit.getDate()));
        expectedVisitBook.setVisit(thirdVisit,
                new Visit(thirdVisit.getPerson(), editedLocation, thirdVisit.getDate()));

        actualVisitBook.updateWithEditedLocation(editedLocation);

        assertEquals(expectedVisitBook, actualVisitBook);
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
    public void deleteVisitsWithPerson_success() {
        VisitBook expectedVisitBook = getTypicalVisitBook();
        VisitBook actualVisitBook = getTypicalVisitBook();

        expectedVisitBook.removeVisit(SECOND_VISIT);
        actualVisitBook.deleteVisitsWithPerson(AMY);
        assertEquals(expectedVisitBook, actualVisitBook);

        expectedVisitBook.removeVisit(THIRD_VISIT);
        actualVisitBook.deleteVisitsWithPerson(ALICE);
        assertEquals(expectedVisitBook, actualVisitBook);
    }

    @Test
    public void getVisitList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> visitBook.getVisitList().remove(0));
    }

    @Test
    public void hashCode_success() {
        List<Visit> visitsList = visitBook.getVisitList();
        assertEquals(visitsList.hashCode(), visitBook.hashCode());
    }
}
