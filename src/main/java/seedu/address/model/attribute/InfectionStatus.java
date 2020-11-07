package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Person's infection status in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidInfectionStatus(String)}
 */
public class InfectionStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Infection status should either be false or if infected, the infection start date.\n"
            + "The start date cannot be from the future.\n"
            + "e.g. 2020-02-02 and false are valid values.";

    /*
     * The infection status can only be specified as
     * a date or false, case-insensitive.
     * Checks that infection status is false.
     */
    public static final String VALIDATION_REGEX = "(?i)(false)";

    private final boolean isInfected;
    private final Optional<LocalDate> infectionDate;

    /**
     * Constructs an {@code InfectionStatus}.
     *
     * @param infectionStatus A valid infection status.
     */
    public InfectionStatus(String infectionStatus) {
        requireNonNull(infectionStatus);
        checkArgument(isValidInfectionStatus(infectionStatus), MESSAGE_CONSTRAINTS);
        if (infectionStatus.matches(VALIDATION_REGEX)) {
            isInfected = false;
            infectionDate = Optional.empty();
        } else {
            isInfected = true;
            LocalDate date = LocalDate.parse(infectionStatus);
            infectionDate = Optional.of(date);
        }
    }

    /**
     * Returns true if a given string is a valid infection status.
     */
    public static boolean isValidInfectionStatus(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            try {
                LocalDate date = LocalDate.parse(test);
                if (date.isAfter(LocalDate.now())) {
                    return false;
                }
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the boolean format of the infection status
     * @return a boolean indicating whether the person is infected or not
     */
    public boolean getStatusAsBoolean() {
        return isInfected;
    }

    /**
     * Returns the infection date as an optional.
     * May contain a null if not infected.
     */
    public Optional<LocalDate> getInfectionDate() {
        return infectionDate;
    }

    public Optional<String> getReaderFriendlyDate() {
        if (infectionDate.isEmpty()) {
            return Optional.empty();
        }

        LocalDate date = infectionDate.get();

        String day = String.valueOf(date.getDayOfMonth());
        String month = date.getMonth().toString();
        month = month.substring(0, 1).toUpperCase() + month.substring(1, 3).toLowerCase();
        String year = String.valueOf(date.getYear());
        String readerFriendlyDate = day + " " + month + " " + year;

        return Optional.of(readerFriendlyDate);
    }

    @Override
    public String toString() {
        if (isInfected) {
            return infectionDate.get().toString();
        } else {
            return String.valueOf(false);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InfectionStatus)) {
            return false;
        }

        InfectionStatus otherStatus = (InfectionStatus) other;
        return otherStatus.isInfected == isInfected
                && otherStatus.infectionDate.equals(infectionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isInfected, infectionDate);
    }

}
