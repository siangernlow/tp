package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

public class JsonAdaptedVisitTest {
    private static final Person INVALID_PERSON = null;
    private static final Location INVALID_LOCATION = null;
    private static final String INVALID_DATE = " ";
    private static final String VALID_DATE = "2020-09-12";

    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(BENSON, BENSON_LOCATION, VALID_DATE);
        assertEquals(FIRST_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(INVALID_PERSON, BENSON_LOCATION, VALID_DATE);
        String expectedMessage = "Visit's person field is missing!";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(BENSON, INVALID_LOCATION, VALID_DATE);
        String expectedMessage = "Visit's location field is missing!";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(BENSON, BENSON_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
