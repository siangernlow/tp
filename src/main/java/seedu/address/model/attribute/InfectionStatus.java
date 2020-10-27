package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's infection status in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidInfectionStatus(String)}
 */
public class InfectionStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Infection status should either be true or false, and it should not be blank.";

    /*
     * The infection status can only be specified as
     * true or false, case-insensitive.
     */
    public static final String VALIDATION_REGEX = "(?i)(true|false)";

    private final boolean isInfected;

    /**
     * Constructs an {@code InfectionStatus}.
     *
     * @param infectionStatus A valid infection status.
     */
    public InfectionStatus(String infectionStatus) {
        requireNonNull(infectionStatus);
        checkArgument(isValidInfectionStatus(infectionStatus), MESSAGE_CONSTRAINTS);
        isInfected = Boolean.parseBoolean(infectionStatus);
    }

    /**
     * Returns true if a given string is a valid infection status.
     */
    public static boolean isValidInfectionStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the boolean format of the infection status
     * @return a boolean indicating whether the person is infected or not
     */
    public boolean getStatusAsBoolean() {
        return isInfected;
    }

    @Override
    public String toString() {
        return String.valueOf(isInfected);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InfectionStatus // instanceof handles nulls
                && isInfected == ((InfectionStatus) other).isInfected); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.valueOf(isInfected).hashCode();
    }

}
