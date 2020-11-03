package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFECTION_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFECTION_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUARANTINE_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUARANTINE_STATUS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.attribute.Id;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withQuarantineStatus("2020-02-02")
            .withInfectionStatus("false").withId("S1234").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withQuarantineStatus("2020-02-02")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withInfectionStatus("2020-02-02")
            .withId("S2345").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withInfectionStatus("false")
            .withQuarantineStatus("false").withId("S3456").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withQuarantineStatus("false")
            .withInfectionStatus("2020-02-02").withId("S4567").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withQuarantineStatus("false")
            .withInfectionStatus("2020-02-02").withId("S5678").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withQuarantineStatus("false")
            .withInfectionStatus("2020-02-02").withId("S6789").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withQuarantineStatus("false")
            .withInfectionStatus("2020-02-02").withId("S78910").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withInfectionStatus("false")
            .withId("S891011").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withInfectionStatus("2020-02-02")
            .withId("S9101112").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withQuarantineStatus(VALID_QUARANTINE_STATUS_AMY)
            .withInfectionStatus(VALID_INFECTION_STATUS_AMY)
            .withId(VALID_ID_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withInfectionStatus(VALID_INFECTION_STATUS_BOB)
            .withQuarantineStatus(VALID_QUARANTINE_STATUS_BOB)
            .withId(VALID_ID_BOB).build();

    // HashSet of Indexes corresponding to typical persons (ALICE to GEORGE)
    public static final List<Id> ID_LIST_OF_TYPICAL_PERSONS = Arrays.asList(new Id("S1234"),
            new Id("S2345"), new Id("S3456"), new Id("S4567"), new Id("S5678"), new Id("S6789"), new Id("S78910"));
    public static final HashSet<Id> ID_HASHSET_OF_TYPICAL_PERSONS = new HashSet<>(ID_LIST_OF_TYPICAL_PERSONS);
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    // Persons with specific properties
    public static final Person HEALTHY_PERSON = CARL;
    public static final Person INFECTED_PERSON = DANIEL;
    public static final Person QUARANTINED_PERSON = ALICE;
    public static final Person INFECTED_AND_QUARANTINED_PERSON = BENSON;

    public static final Id ID_NOT_IN_TYPICAL_PERSON = new Id("S11111");

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code PersonBook} with all the typical persons.
     */
    public static PersonBook getTypicalAddressBook() {
        PersonBook pb = new PersonBook();
        for (Person person : getTypicalPersons()) {
            pb.addPerson(person);
        }
        return pb;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * @return A shorter list of persons meant for simple data entry.
     */
    public static List<Person> getShortenedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    public static HashSet<Id> getIdsOfTypicalPersonsAsHashSet() {
        return ID_HASHSET_OF_TYPICAL_PERSONS;
    }
}
