package seedu.address.storage;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

=======
>>>>>>> 4ceb7393511898b4c475e051038271372eedc2f3
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedVisitTest {
    private static final String INVALID_PERSONID = "- 1";
    private static final String INVALID_LOCATIONID = " ";
    private static final String INVALID_DATE = " ";
    private static final String VALID_PERSONID = "1";
    private static final String VALID_LOCATIONID = "2";
    private static final String VALID_DATE = "2020-09-12";

    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_PERSONID, VALID_LOCATIONID, VALID_DATE);
        assertEquals(FIRST_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(INVALID_PERSONID, VALID_LOCATIONID, VALID_DATE);
        String expectedMessage = "Please enter the correct personId";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_PERSONID, INVALID_LOCATIONID, VALID_DATE);
        String expectedMessage = "Please enter the correct locationId";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    // To be further implemented
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_PERSONID, VALID_LOCATIONID, INVALID_DATE);
        String expectedMessage = "Please enter the correct date format";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullPersonId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(null, VALID_LOCATIONID, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
    @Test
    public void toModelType_nullLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_PERSONID, null, INVALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "locationId is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_PERSONID, VALID_LOCATIONID, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
