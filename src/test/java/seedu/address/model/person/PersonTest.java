package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFECTION_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUARANTINE_STATUS_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.VisitBuilder.DEFAULT_PERSON;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void isSameId() {
        // same object -> returns true
        assertTrue(ALICE.isSameId(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameId(null));

        // different id -> returns false;
        Person editedAlice = new PersonBuilder(ALICE).withId(VALID_ID_BOB).build();
        assertFalse(ALICE.isSameId(editedAlice));

        // same id, different phone and email -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameId(editedAlice));

        // same id, different name -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameId(editedAlice));

        // different id, same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).withId(VALID_ID_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same id, same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same id, same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
                .withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different quarantine status -> returns false
        editedAlice = new PersonBuilder(ALICE).withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different infection status -> returns false
        editedAlice = new PersonBuilder(ALICE).withInfectionStatus(VALID_INFECTION_STATUS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashCode_success() {
        Person person = DEFAULT_PERSON;
        int hashCode = Objects.hash(
            person.getId(),
            person.getName(),
            person.getPhone(),
            person.getEmail(),
            person.getAddress(),
            person.getQuarantineStatus(),
            person.getInfectionStatus()
        );
        assertEquals(hashCode, person.hashCode());
    }

    @Test
    public void compareTo() {
        Person smaller = ALICE;
        Person bigger = BENSON;
        Person sameNameDiffId = new PersonBuilder(ALICE).withId(BENSON.getId().toString()).build();

        // smaller Person compare to bigger Person -> negative
        assertTrue(smaller.compareTo(bigger) < 0);

        // bigger Person compare to smaller Person -> positive
        assertTrue(bigger.compareTo(smaller) > 0);

        // same Person comparison -> 0
        assertTrue(bigger.compareTo(bigger) == 0);

        // same name, smaller id -> negative
        assertTrue(smaller.compareTo(sameNameDiffId) < 0);

        // same name, bigger id -> positive
        assertTrue(sameNameDiffId.compareTo(smaller) > 0);
    }
}
