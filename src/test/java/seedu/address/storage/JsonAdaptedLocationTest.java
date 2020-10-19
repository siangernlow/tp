package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedLocation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.Name;

public class JsonAdaptedLocationTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_ID = " 0";

    private static final String VALID_NAME = BENSON_LOCATION.getName().toString();
    private static final String VALID_ADDRESS = BENSON_LOCATION.getAddress().toString();
    private static final String VALID_ID = BENSON_LOCATION.getId().toString();

    @Test
    public void toModelType_validLocationDetails_returnsLocation() throws Exception {
        JsonAdaptedLocation location = new JsonAdaptedLocation(BENSON_LOCATION);
        assertEquals(BENSON_LOCATION, location.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedLocation location =
                new JsonAdaptedLocation(INVALID_NAME, VALID_ADDRESS, VALID_ID);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedLocation location = new JsonAdaptedLocation(null, VALID_ADDRESS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedLocation location =
                new JsonAdaptedLocation(VALID_NAME, INVALID_ADDRESS, VALID_ID);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedLocation location = new JsonAdaptedLocation(VALID_NAME, null, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedLocation location =
                new JsonAdaptedLocation(VALID_NAME, VALID_ADDRESS, INVALID_ID);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedLocation location = new JsonAdaptedLocation(VALID_NAME, VALID_ADDRESS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, location::toModelType);
    }
}
