package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalVisits.FIRST_VISIT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;


public class JsonAdaptedVisitTest {
    private static final String INVALID_ID_LOCATION = " 0";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_QUARANTINE_STATUS = "status";
    private static final String INVALID_INFECTION_STATUS = "nope";
    private static final String INVALID_ID = " 0";

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

    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        assertEquals(FIRST_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidPersonName_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullPersonName_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, null, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, null, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidAddressPerson_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullAddressPerson_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, null,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidQuarantineStatus_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                INVALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = QuarantineStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullQuarantineStatus_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                null, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, QuarantineStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidInfectionStatus_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, INVALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = InfectionStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullInfectionStatus_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, null, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InfectionStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, INVALID_ID,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullPersonId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, null,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationName_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                INVALID_NAME, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullLocationName_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                null, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationAddress_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, INVALID_ADDRESS, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullLocationAddress_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, null, VALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, INVALID_ID_LOCATION, VALID_DATE);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullLocationId_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, INVALID_DATE);
        String expectedMessage = "Please enter the correct date format";
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(
                VALID_NAME_PERSON, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_PERSON,
                VALID_QUARANTINE_STATUS, VALID_INFECTION_STATUS, VALID_ID_PERSON,
                VALID_NAME_LOCATION, VALID_ADDRESS_LOCATION, VALID_ID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
