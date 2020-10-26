package seedu.address.logic.parser;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of IndexIdPair.
 */
public interface ReadOnlyIndexIdPair {

    Person getPersonFromPair(Model model) throws CommandException;
    Location getLocationFromPair(Model model) throws CommandException;
}
