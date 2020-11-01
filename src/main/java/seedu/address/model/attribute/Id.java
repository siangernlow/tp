package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Id that uniquely identifies either a {@code #Person} or {@code Location}.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id implements Comparable<Id> {

    public static final String MESSAGE_CONSTRAINTS = "Ids can take any values, "
            + "and it should be at least 5 characters long.";

    /*
     * The first character of the Id must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".{5,}";

    public final String value;

    /**
     * Constructs an {@code Id}.
     *
     * @param id A valid Id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if a given string is a valid Id.
     */
    public static boolean isValidId(String test) {
        String trimmedTest = test.trim();
        return trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Id id) {
        return value.compareTo(id.value);
    }
}
