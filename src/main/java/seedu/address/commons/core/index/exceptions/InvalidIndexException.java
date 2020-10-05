package seedu.address.commons.core.index.exceptions;

/**
 * Signals that the operation will result in an invalid index.
 */
public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException() {
        super("Operation would result in invalid index.");
    }
}
