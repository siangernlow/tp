package seedu.address.model.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.BOB_LOCATION;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;
import static seedu.address.testutil.TypicalVisits.SECOND_VISIT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.visit.exceptions.DuplicateVisitException;
import seedu.address.model.visit.exceptions.VisitNotFoundException;
import seedu.address.testutil.VisitBuilder;

public class UniqueVisitListTest {
    private final UniqueVisitList uniqueVisitList = new UniqueVisitList();

    @Test
    public void contains_nullLocation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.contains(null));
    }

    @Test
    public void contains_visitNotInList_returnsFalse() {
        assertFalse(uniqueVisitList.contains(FIRST_VISIT));
    }

    @Test
    public void contains_visitInList_returnsTrue() {
        uniqueVisitList.add(FIRST_VISIT);
        assertTrue(uniqueVisitList.contains(FIRST_VISIT));
    }

    @Test
    public void contains_visitWithSameIdentityFieldsInList_returnsFalse() {
        uniqueVisitList.add(FIRST_VISIT);
        Visit editedVisit = new VisitBuilder(FIRST_VISIT).withPerson(BOB)
                .withLocation(BOB_LOCATION).build();
        assertFalse(uniqueVisitList.contains(editedVisit));
    }

    @Test
    public void add_nullVisit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.add(null));
    }

    @Test
    public void add_duplicateVisit_throwsDuplicateVisitException() {
        uniqueVisitList.add(FIRST_VISIT);
        assertThrows(DuplicateVisitException.class, () -> uniqueVisitList.add(FIRST_VISIT));
    }

    @Test
    public void setVisit_nullTargetVisit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.setVisit(null, FIRST_VISIT));
    }

    @Test
    public void setVisit_nullEditedVisit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.setVisit(FIRST_VISIT, null));
    }

    @Test
    public void setVisit_targetVisitNotInList_throwsVisitNotFoundException() {
        assertThrows(VisitNotFoundException.class, () ->
                uniqueVisitList.setVisit(FIRST_VISIT, FIRST_VISIT));
    }

    @Test
    public void setVisit_editedVisitIsSameVisit_success() {
        uniqueVisitList.add(FIRST_VISIT);
        uniqueVisitList.setVisit(FIRST_VISIT, FIRST_VISIT);
        UniqueVisitList expectedUniqueVisitList = new UniqueVisitList();
        expectedUniqueVisitList.add(FIRST_VISIT);
        assertEquals(expectedUniqueVisitList, uniqueVisitList);
    }

    @Test
    public void setVisit_editedVisitHasSameIdentity_success() {
        Visit editedVisit = new VisitBuilder(FIRST_VISIT).withPerson(BOB)
                .withLocation(BOB_LOCATION).build();
        uniqueVisitList.add(editedVisit);
        UniqueVisitList expectedUniqueVisitList = new UniqueVisitList();
        expectedUniqueVisitList.add(editedVisit);
        assertEquals(expectedUniqueVisitList, uniqueVisitList);
    }

    @Test
    public void setVisit_duplicateVisit_throwsDuplicateVisitException() {
        uniqueVisitList.add(FIRST_VISIT);
        uniqueVisitList.add(SECOND_VISIT);
        assertThrows(DuplicateVisitException.class, () ->
                uniqueVisitList.setVisit(SECOND_VISIT, FIRST_VISIT));
    }

    @Test
    public void remove_nullVisit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.remove(null));
    }

    @Test
    public void remove_visitDoesNotExist_throwsVisitNotFoundException() {
        assertThrows(VisitNotFoundException.class, () -> uniqueVisitList.remove(FIRST_VISIT));
    }

    @Test
    public void remove_existingVisit_removesVisit() {
        uniqueVisitList.add(FIRST_VISIT);
        uniqueVisitList.remove(FIRST_VISIT);
        UniqueVisitList expectedUniqueLocationList = new UniqueVisitList();
        assertEquals(expectedUniqueLocationList, uniqueVisitList);
    }

    @Test
    public void setLocations_nullUniqueVisitList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.setVisits((UniqueVisitList) null));
    }

    @Test
    public void setVisit_uniqueVisitList_replacesOwnListWithProvidedUniqueVisitList() {

        UniqueVisitList expectedUniqueVisitList = new UniqueVisitList();
        expectedUniqueVisitList.add(FIRST_VISIT);
        uniqueVisitList.setVisits(expectedUniqueVisitList);
        assertEquals(expectedUniqueVisitList, uniqueVisitList);
    }

    @Test
    public void setVisits_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVisitList.setVisits((List<Visit>) null));
    }

    @Test
    public void setVisits_list_replacesOwnListWithProvidedList() {
        uniqueVisitList.add(FIRST_VISIT);
        List<Visit> visitList = Collections.singletonList(FIRST_VISIT);
        uniqueVisitList.setVisits(visitList);
        UniqueVisitList expectedUniqueVisitList = new UniqueVisitList();
        expectedUniqueVisitList.add(FIRST_VISIT);
        assertEquals(expectedUniqueVisitList, uniqueVisitList);
    }

    @Test
    public void setVisits_listWithDuplicateVisits_throwsDuplicateVisitException() {
        List<Visit> listWithDuplicateVisits = Arrays.asList(FIRST_VISIT, FIRST_VISIT);
        assertThrows(DuplicateVisitException.class, () ->
                uniqueVisitList.setVisits(listWithDuplicateVisits));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueVisitList.asUnmodifiableObservableList().remove(0));
    }
}
