package seedu.address.model.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Person's quarantine status in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuarantineStatus(String)}
 */
public class QuarantineStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Quarantine status should either be false or if infected, the quarantine start date.\n"
            + "The start date cannot be from the future.\n"
            + "e.g. 2020-02-02 and false are valid values.";

    /*
     * The quarantine status can only be specified as
     * a date or false, case-insensitive.
     * Checks that quarantine status is false.
     */
    public static final String VALIDATION_REGEX = "(?i)(false)";

    public final boolean isQuarantined;
    public final Optional<LocalDate> quarantineDate;

    /**
     * Constructs a {@code QuarantineStatus}.
     *
     * @param quarantineStatus A valid quarantine status.
     */
    public QuarantineStatus(String quarantineStatus) {
        requireNonNull(quarantineStatus);
        checkArgument(isValidQuarantineStatus(quarantineStatus), MESSAGE_CONSTRAINTS);
        if (quarantineStatus.matches(VALIDATION_REGEX)) {
            isQuarantined = false;
            quarantineDate = Optional.empty();
        } else {
            isQuarantined = true;
            LocalDate date = LocalDate.parse(quarantineStatus);
            quarantineDate = Optional.of(date);
        }
    }

    /**
     * Returns true if a given string is a valid quarantine status.
     */
    public static boolean isValidQuarantineStatus(String test) {
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
     * Returns the boolean format of the quarantine status
     * @return a boolean indicating whether the person is quarantined or not
     */
    public boolean getStatusAsBoolean() {
        return isQuarantined;
    }

    /**
     * Returns the quarantine date as an optional.
     * May contain a null if not quarantined.
     */
    public Optional<LocalDate> getQuarantineDate() {
        return quarantineDate;
    }

    public Optional<String> getReaderFriendlyDate() {
        if (quarantineDate.isEmpty()) {
            return Optional.empty();
        }

        LocalDate date = quarantineDate.get();

        String day = String.valueOf(date.getDayOfMonth());
        String month = date.getMonth().toString();
        month = month.substring(0, 1).toUpperCase() + month.substring(1, 3).toLowerCase();
        String year = String.valueOf(date.getYear());
        String readerFriendlyDate = day + " " + month + " " + year;

        return Optional.of(readerFriendlyDate);
    }

    @Override
    public String toString() {
        if (isQuarantined) {
            return quarantineDate.get().toString();
        } else {
            return String.valueOf(false);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QuarantineStatus)) {
            return false;
        }

        QuarantineStatus otherStatus = (QuarantineStatus) other;
        return otherStatus.isQuarantined == isQuarantined
                && otherStatus.quarantineDate.equals(quarantineDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isQuarantined, quarantineDate);
    }
}
