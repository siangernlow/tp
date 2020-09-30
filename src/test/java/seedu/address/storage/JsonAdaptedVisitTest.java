package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;

public class JsonAdaptedVisitTest {
    private static final String INVALID_PERSONID = "- 1";
    private static final String INVALID_LOCATIONID = " ";
    private static final String INVALID_DATE = " ";
    private static final String VALID_PERSONID = "1";
    private static final String VALID_LOCATIONID = "2";
    private static final String VALID_DATE = "2020-02-02";
    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(FIRST_VISIT);
        assertEquals(FIRST_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        Index personId = Index.fromOneBased(Integer.parseInt(INVALID_PERSONID));
        Index locationId = Index.fromOneBased(Integer.parseInt(VALID_LOCATIONID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfVisit = LocalDate.parse(VALID_DATE, inputFormat);

        JsonAdaptedVisit visit = new JsonAdaptedVisit(personId, locationId, dateOfVisit);
        String expectedMessage = "Please enter the correct personId";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationId_throwsIllegalValueException() {
        Index personId = Index.fromOneBased(Integer.parseInt(VALID_PERSONID));
        Index locationId = Index.fromOneBased(Integer.parseInt(INVALID_LOCATIONID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfVisit = LocalDate.parse(VALID_DATE, inputFormat);

        JsonAdaptedVisit visit = new JsonAdaptedVisit(personId, locationId, dateOfVisit);
        String expectedMessage = "Please enter the correct locationId";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    // To be further implemented
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        Index personId = Index.fromOneBased(Integer.parseInt(VALID_PERSONID));
        Index locationId = Index.fromOneBased(Integer.parseInt(VALID_LOCATIONID));
        JsonAdaptedVisit visit = new JsonAdaptedVisit(personId, locationId, null);
        String expectedMessage = "Please enter the correct date format";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullPersonId_throwsIllegalValueException() {

        Index locationId = Index.fromOneBased(Integer.parseInt(VALID_LOCATIONID));

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfVisit = LocalDate.parse(VALID_DATE, inputFormat);

        JsonAdaptedVisit visit = new JsonAdaptedVisit(null, locationId, dateOfVisit);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
    @Test
    public void toModelType_nullLocationId_throwsIllegalValueException() {

        Index personId = Index.fromOneBased(Integer.parseInt(VALID_PERSONID));

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfVisit = LocalDate.parse(VALID_DATE, inputFormat);

        JsonAdaptedVisit visit = new JsonAdaptedVisit(personId, null, dateOfVisit);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "locationId is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {

        Index personId = Index.fromOneBased(Integer.parseInt(VALID_PERSONID));
        Index locationId = Index.fromOneBased(Integer.parseInt(VALID_LOCATIONID));

        JsonAdaptedVisit visit = new JsonAdaptedVisit(personId, locationId, null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date is missing");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
