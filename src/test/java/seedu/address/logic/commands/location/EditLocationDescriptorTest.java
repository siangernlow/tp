package seedu.address.logic.commands.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_VIVOCITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_VIVOCITY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.testutil.EditLocationDescriptorBuilder;

public class EditLocationDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditLocationDescriptor descriptorWithSameValues = new EditLocationDescriptor(DESC_NUS);
        assertTrue(DESC_NUS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_NUS.equals(DESC_NUS));

        // null -> returns false
        assertFalse(DESC_NUS.equals(null));

        // different types -> returns false
        assertFalse(DESC_NUS.equals(5));

        // different values -> returns false
        assertFalse(DESC_NUS.equals(DESC_VIVOCITY));

        // different name -> returns false
        EditLocationDescriptor editedNus = new EditLocationDescriptorBuilder(DESC_NUS)
                .withName(VALID_NAME_VIVOCITY).build();
        assertFalse(DESC_NUS.equals(editedNus));

        // different address -> returns false
        editedNus = new EditLocationDescriptorBuilder(DESC_NUS).withAddress(VALID_ADDRESS_VIVOCITY).build();
        assertFalse(DESC_NUS.equals(editedNus));
    }
}
