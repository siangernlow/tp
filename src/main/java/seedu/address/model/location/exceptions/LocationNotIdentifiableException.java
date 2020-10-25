package seedu.address.model.location.exceptions;

/**
 * Signals that the operation will result in unidentifiable Locations (Locations are considered unidentifiable if they
 * have the same id value).
 */
public class LocationNotIdentifiableException extends RuntimeException {
    public LocationNotIdentifiableException() {
        super("Operation would result in locations with the same Id values.");
    }
}
