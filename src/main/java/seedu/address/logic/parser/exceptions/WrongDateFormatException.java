package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

public class WrongDateFormatException extends IllegalValueException {

    public WrongDateFormatException(String message) {
        super(message);
    }

    public WrongDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
