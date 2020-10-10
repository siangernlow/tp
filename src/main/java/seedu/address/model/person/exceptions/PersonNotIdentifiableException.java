package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in unidentifiable Person (Person are considered unidentifiable if they
 * have the same id value).
 */
public class PersonNotIdentifiableException extends RuntimeException {
    public PersonNotIdentifiableException() {
        super("Operation would result in unidentifiable persons due to duplicate id value");
    }
}
