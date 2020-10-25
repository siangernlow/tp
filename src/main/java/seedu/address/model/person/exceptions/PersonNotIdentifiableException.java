package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in unidentifiable Persons (Persons are considered unidentifiable if they
 * have the same id value).
 */
public class PersonNotIdentifiableException extends RuntimeException {
    public PersonNotIdentifiableException() {
        super("Operation would result in persons with the same Id values.");
    }
}
