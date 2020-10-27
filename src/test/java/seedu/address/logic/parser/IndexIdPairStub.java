package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

public class IndexIdPairStub extends ReadOnlyIndexIdPair {

    /**
     * Creates a stub of IndexIdPair for testing purposes.
     * Can be initialised with Index or Id.
     */
    public IndexIdPairStub(Index index, Id id) {
        this.indexOpt = Optional.ofNullable(index);
        this.idOpt = Optional.ofNullable(id);
    }

    @Override
    public Person getPersonFromPair(Model model) throws CommandException {
        try {
            if (idOpt.isPresent()) {
                return model.getPersonById(idOpt.get());
            }
            if (indexOpt.isPresent()) {
                return model.getPersonFromIndex(indexOpt.get());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDEX);
        }
        throw new CommandException("Test fails.");
    }

    @Override
    public Location getLocationFromPair(Model model) throws CommandException {
        try {
            if (idOpt.isPresent()) {
                return model.getLocationById(idOpt.get());
            }
            if (indexOpt.isPresent()) {
                return model.getLocationFromIndex(indexOpt.get());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_INDEX);
        }
        throw new CommandException("Test fails.");
    }
}
