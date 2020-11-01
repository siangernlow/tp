package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class InfectionStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InfectionStatus(null));
    }

    @Test
    public void constructor_invalidInfectionStatus_throwsIllegalArgumentException() {
        String invalidInfectionStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new InfectionStatus(invalidInfectionStatus));
    }

    @Test
    public void isValidInfectionStatus() {
        // null infection status
        assertThrows(NullPointerException.class, () -> InfectionStatus.isValidInfectionStatus(null));

        // invalid infection status
        assertFalse(InfectionStatus.isValidInfectionStatus("Nope")); // date or false only
        assertFalse(InfectionStatus.isValidInfectionStatus("true")); // true is not allowed

        // valid infection statuses
        assertTrue(InfectionStatus.isValidInfectionStatus("2020-02-02")); // date
        assertTrue(InfectionStatus.isValidInfectionStatus("FALSE")); // Upper case
        assertTrue(InfectionStatus.isValidInfectionStatus("fAlsE")); // Mixed case
    }

    @Test
    public void equals() {
        InfectionStatus infectionStatus = new InfectionStatus("false");

        // same object -> returns true
        assertTrue(infectionStatus.equals(infectionStatus));

        // same values -> returns true
        InfectionStatus infectionStatusCopy = new InfectionStatus(infectionStatus.toString());
        assertTrue(infectionStatus.equals(infectionStatusCopy));

        // different types -> returns false
        assertFalse(infectionStatus.equals(1));

        // null -> returns false
        assertFalse(infectionStatus.equals(null));

        // different value -> returns false
        InfectionStatus differentInfectionStatus = new InfectionStatus("2020-02-02");
        assertFalse(infectionStatus.equals(differentInfectionStatus));
    }

    @Test
    public void hashCode_success() {
        InfectionStatus infectionStatus = new InfectionStatus("false");
        int hashCode = Objects.hash(
                infectionStatus.getStatusAsBoolean(),
                infectionStatus.getInfectionDate()
        );
        assertEquals(hashCode, infectionStatus.hashCode());
    }
}




