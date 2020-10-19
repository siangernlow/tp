package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.Name;



public class JsonAdaptedVisitTest {
    private static final String INVALID_ID_LOCATION = " 0";
    private static final String INVALID_NAME_PERSON = "R@chel";
    private static final String VALID_NAME_LOCATION = BENSON_LOCATION.getName().toString();
    private static final String VALID_ADDRESS_LOCATION = BENSON_LOCATION.getAddress().toString();
    private static final String VALID_ID_LOCATION = BENSON_LOCATION.getId().toString();
    private static final String INVALID_DATE = " ";
    private static final String VALID_DATE = "2020-09-12";
    private static final String VALID_NAME_PERSON = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS_PERSON = BENSON.getAddress().toString();
    private static final String VALID_QUARANTINE_STATUS = BENSON.getQuarantineStatus().toString();
    private static final String VALID_INFECTION_STATUS = BENSON.getInfectionStatus().toString();
    private static final String VALID_ID_PERSON = BENSON.getId().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON, VALID_TAGS,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        assertEquals(FIRST_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidPersonName_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                INVALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON, VALID_TAGS,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON, VALID_TAGS,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, INVALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON, VALID_TAGS,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, INVALID_DATE);
        String expectedMessage = "Please enter the correct date format";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON, VALID_TAGS,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
