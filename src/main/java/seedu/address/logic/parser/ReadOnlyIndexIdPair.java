package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of IndexIdPair.
 */
public abstract class ReadOnlyIndexIdPair {

    protected Optional<Index> indexOpt;
    protected Optional<Id> idOpt;

    public abstract Person getPersonFromPair(Model model) throws CommandException;
    public abstract Location getLocationFromPair(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReadOnlyIndexIdPair)) {
            return false;
        }

        ReadOnlyIndexIdPair o = (ReadOnlyIndexIdPair) other;
        return idOpt.equals(o.idOpt)
                && indexOpt.equals(o.indexOpt);
    }
}
