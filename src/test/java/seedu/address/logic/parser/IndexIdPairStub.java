package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

public class IndexIdPairStub implements ReadOnlyIndexIdPair {

    private Optional<Index> indexOpt;
    private Optional<Id> idOpt;

    /**
     * Creates a stub of IndexIdPair for testing purposes.
     * Can be initialised with Index or Id.
     */
    public IndexIdPairStub(Index index, Id id) {
        this.indexOpt = Optional.of(index);
        this.idOpt = Optional.of(id);
    }

    @Override
    public Person getPersonFromPair(Model model) throws CommandException {
        if (idOpt.isPresent()) {
            return model.getPersonById(idOpt.get());
        }
        if (indexOpt.isPresent()) {
            return model.getPersonFromIndex(indexOpt.get());
        }
        throw new CommandException("Test fails.");
    }

    @Override
    public Location getLocationFromPair(Model model) throws CommandException {
        if (idOpt.isPresent()) {
            return model.getLocationById(idOpt.get());
        }
        if (indexOpt.isPresent()) {
            return model.getLocationFromIndex(indexOpt.get());
        }
        throw new CommandException("Test fails.");
    }
}
