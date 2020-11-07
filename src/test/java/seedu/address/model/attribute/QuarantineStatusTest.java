package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class QuarantineStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuarantineStatus(null));
    }

    @Test
    public void constructor_invalidQuarantineStatus_throwsIllegalArgumentException() {
        String invalidQuarantineStatus = "quarantined";
        assertThrows(IllegalArgumentException.class, () -> new QuarantineStatus(invalidQuarantineStatus));
    }

    @Test
    public void isValidQuarantineStatus() {
        // null quarantine status
        assertThrows(NullPointerException.class, () -> QuarantineStatus.isValidQuarantineStatus(null));

        String future = LocalDate.now().plusDays(1).toString();
        String today = LocalDate.now().toString();

        // invalid quarantine status
        assertFalse(QuarantineStatus.isValidQuarantineStatus("")); // empty string
        assertFalse(QuarantineStatus.isValidQuarantineStatus(" ")); // spaces only
        assertFalse(QuarantineStatus.isValidQuarantineStatus("91")); // numbers only
        assertFalse(QuarantineStatus.isValidQuarantineStatus("phone")); // alphabets
        assertFalse(QuarantineStatus.isValidQuarantineStatus("9011p041")); // mix of alphabets and digits
        assertFalse(QuarantineStatus.isValidQuarantineStatus("true")); // true is not allowed
        assertFalse(QuarantineStatus.isValidQuarantineStatus(future)); // future date not allowed

        // valid quarantine status
        assertTrue(QuarantineStatus.isValidQuarantineStatus(today)); // today's date is valid
        assertTrue(QuarantineStatus.isValidQuarantineStatus("2020-02-02")); // date
        assertTrue(QuarantineStatus.isValidQuarantineStatus("false")); // boolean false
        assertTrue(QuarantineStatus.isValidQuarantineStatus("False")); // capitalised false
        assertTrue(QuarantineStatus.isValidQuarantineStatus("FaLSe")); // mix of upper and lower cases
    }

    @Test
    public void getReaderFriendlyDate() {
        // quarantined
        Optional<String> expected = Optional.of("13 Oct 2020");
        QuarantineStatus status = new QuarantineStatus("2020-10-13");
        assertEquals(status.getReaderFriendlyDate(), expected);

        // not quarantined
        expected = Optional.empty();
        status = new QuarantineStatus("false");
        assertEquals(status.getReaderFriendlyDate(), expected);
    }

    @Test
    public void equals() {
        QuarantineStatus quarantineStatus = new QuarantineStatus("false");

        // same object -> returns true
        assertTrue(quarantineStatus.equals(quarantineStatus));

        // same values -> returns true
        QuarantineStatus quarantineStatusCopy = new QuarantineStatus(quarantineStatus.toString());
        assertTrue(quarantineStatus.equals(quarantineStatusCopy));

        // different types -> returns false
        assertFalse(quarantineStatus.equals(1));

        // null -> returns false
        assertFalse(quarantineStatus.equals(null));

        // different value -> returns false
        QuarantineStatus differentQuarantineStatus = new QuarantineStatus("2020-02-02");
        assertFalse(quarantineStatus.equals(differentQuarantineStatus));
    }

    @Test
    public void hashCode_success() {
        QuarantineStatus quarantineStatus = new QuarantineStatus("false");
        int hashCode = Objects.hash(
                quarantineStatus.getStatusAsBoolean(),
                quarantineStatus.getQuarantineDate()
        );
        assertEquals(hashCode, quarantineStatus.hashCode());
    }
}
