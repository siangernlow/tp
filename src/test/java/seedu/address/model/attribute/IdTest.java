package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null id
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid id
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId("         ")); // spaces only
        assertFalse(Id.isValidId("1234")); // less than 5 characters

        // valid id
        assertTrue(Id.isValidId("peter")); // exactly 5 characters\
        assertTrue(Id.isValidId("peterJACK123 ^&*(#%")); // alphabets, numbers, special characters
        assertTrue(Id.isValidId("173kbvbjn4o9745ng 54i8ghg45nh 45h5498grn")); // long ids
    }

    @Test
    public void hashCode_success() {
        Id id = new Id("S123A");
        assertEquals("S123A".hashCode(), id.hashCode());
    }

    @Test
    public void compareTo() {
        Id smaller = new Id("S1234");
        Id bigger = new Id("S2345");

        // smaller id compare to bigger id -> negative
        assertTrue(smaller.compareTo(bigger) < 0);

        // same id comparison -> 0
        assertTrue(smaller.compareTo(smaller) == 0);

        // bigger id compare to smaller id -> positive
        assertTrue(bigger.compareTo(smaller) > 0);
    }
}
