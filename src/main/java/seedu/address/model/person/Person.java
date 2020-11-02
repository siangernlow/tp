package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;

/**
 * Represents a Person in the VirusTracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    // Unique Identifier
    private final Id id;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final QuarantineStatus quarantineStatus;
    private final InfectionStatus infectionStatus;

    /**
     * Every field must be present and not null. Id must be unique.
     */
    public Person(Id id, Name name, Phone phone, Email email, Address address, QuarantineStatus quarantineStatus,
                  InfectionStatus infectionStatus) {
        requireAllNonNull(id, name, phone, email, address, quarantineStatus, infectionStatus);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.quarantineStatus = quarantineStatus;
        this.infectionStatus = infectionStatus;
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public QuarantineStatus getQuarantineStatus() {
        return quarantineStatus;
    }

    public InfectionStatus getInfectionStatus() {
        return infectionStatus;
    }

    public Id getId() {
        return id;
    }

    //===================== For String conversions ============================

    public String getIdAsString() {
        return id.toString();
    }

    public String getNameAsString() {
        return name.toString();
    }

    public String getPhoneAsString() {
        return phone.toString();
    }

    public String getEmailAsString() {
        return email.toString();
    }

    public String getAddressAsString() {
        return address.toString();
    }

    public String getQuarantineStatusAsString() {
        return quarantineStatus.toString();
    }

    public String getInfectionStatusAsString() {
        return infectionStatus.toString();
    }

    /**
     * Returns true if person is infected.
     */
    public boolean isInfected() {
        return infectionStatus.getStatusAsBoolean();
    }

    /**
     * Returns true if person is quarantined.
     */
    public boolean isQuarantined() {
        return quarantineStatus.getStatusAsBoolean();
    }

    /**
     * Returns true if both person have the same Id.
     */
    public boolean isSameId(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getId().equals(getId());

    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same Id, identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getQuarantineStatus().equals(getQuarantineStatus())
                && otherPerson.getInfectionStatus().equals(getInfectionStatus())
                && otherPerson.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, phone, email, address, quarantineStatus, infectionStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Quarantined: ")
                .append(getQuarantineStatus())
                .append(" Infected: ")
                .append(getInfectionStatus());
        return builder.toString();
    }

    @Override
    public int compareTo(Person person) {
        if (getName().compareTo(person.getName()) == 0) {
            return getId().compareTo(person.getId());
        }
        return getName().compareTo(person.getName());
    }
}
