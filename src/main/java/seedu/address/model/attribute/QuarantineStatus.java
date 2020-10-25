package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's quarantine status in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuarantineStatus(String)}
 */
public class QuarantineStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Quarantine status should either be true or false, and it should not be blank.";

    /*
     * The quarantine status can only be specified as
     * true or false, case-insensitive.
     */
    public static final String VALIDATION_REGEX = "(?i)(true|false)";

    public final boolean isQuarantined;

    /**
     * Constructs a {@code QuarantineStatus}.
     *
     * @param quarantineStatus A valid quarantine status.
     */
    public QuarantineStatus(String quarantineStatus) {
        requireNonNull(quarantineStatus);
        checkArgument(isValidQuarantineStatus(quarantineStatus), MESSAGE_CONSTRAINTS);
        isQuarantined = Boolean.parseBoolean(quarantineStatus);
    }

    /**
     * Returns true if a given string is a valid quarantine status.
     */
    public static boolean isValidQuarantineStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the boolean format of the quarantine status
     * @return a boolean indicating whether the person is quarantined or not
     */
    public boolean getStatusAsBoolean() {
        return isQuarantined;
    }

    @Override
    public String toString() {
        return String.valueOf(isQuarantined);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuarantineStatus // instanceof handles nulls
                && isQuarantined == ((QuarantineStatus) other).isQuarantined); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.valueOf(isQuarantined).hashCode();
    }
}
