package seedu.address.testutil;

import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_QUARANTINE_STATUS = "false";
    public static final String DEFAULT_INFECTION_STATUS = "false";
    public static final String DEFAULT_ID = "S1234";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private QuarantineStatus quarantineStatus;
    private InfectionStatus infectionStatus;
    private Id id;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        quarantineStatus = new QuarantineStatus(DEFAULT_QUARANTINE_STATUS);
        infectionStatus = new InfectionStatus(DEFAULT_INFECTION_STATUS);
        id = new Id(DEFAULT_ID);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        quarantineStatus = personToCopy.getQuarantineStatus();
        infectionStatus = personToCopy.getInfectionStatus();
        id = personToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code QuarantineStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withQuarantineStatus(String quarantineStatus) {
        this.quarantineStatus = new QuarantineStatus(quarantineStatus);
        return this;
    }

    /**
     * Sets the {@code InfectionStatus} of the {@code Person} that we are building.
     *
     * @param infectionStatus The new infection status.
     * @return The updated PersonBuilder.
     */
    public PersonBuilder withInfectionStatus(String infectionStatus) {
        this.infectionStatus = new InfectionStatus(infectionStatus);
        return this;
    }

    /**
     * Sets the {@code id} of the {@code Person} that we are building.
     */
    public PersonBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    public Person build() {
        return new Person(id, name, phone, email, address, quarantineStatus, infectionStatus);
    }
}
