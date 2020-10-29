package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * This represents an identifier that can identify a Person or a Location.
 * The identifier may be using either an Id or an index from user input.
 */
public class IndexIdPair extends ReadOnlyIndexIdPair {

    public static final String MESSAGE_INVALID_PERSON_COMMAND_USE = "This pair refers to a location, not a person.";
    public static final String MESSAGE_INVALID_LOCATION_COMMAND_USE = "This pair refers to a person, not a location.";
    public static final String MESSAGE_PREFIX_NOT_SUPPORTED = "This prefix is not supported.";
    public static final String MESSAGE_NO_ID_NOR_INDEX = "User input has neither Id nor index.";

    private Prefix prefix;

    /**
     * Creates an IndexIdPair that can identify either a person or a location.
     * The {@code ArgumentMultimap} should contain either index or Id user input but not both.
     * @param argMultimap contains the Id or index user input.
     * @param prefix determines if this IndexIdPair identifies a person or a location.
     * @throws ParseException if unable to parse the Id or the index.
     */
    public IndexIdPair(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        requireAllNonNull(argMultimap, prefix);
        if (prefix.equals(PREFIX_LOCATION_ID) || prefix.equals(PREFIX_PERSON_ID)) {
            this.prefix = prefix;
        } else {
            throw new ParseException(MESSAGE_PREFIX_NOT_SUPPORTED);
        }

        if (argMultimap.getValue(prefix).isPresent()) {
            Id id = ParserUtil.parseId(argMultimap.getValue(prefix).get());
            indexOpt = Optional.empty();
            idOpt = Optional.of(id);
        } else if (!argMultimap.getPreamble().isBlank()) {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            indexOpt = Optional.of(index);
            idOpt = Optional.empty();
        } else {
            throw new ParseException(MESSAGE_NO_ID_NOR_INDEX);
        }
    }

    /** Used for testing purposes only */
    public IndexIdPair(Index index, Id id, Prefix prefix) {
        indexOpt = Optional.ofNullable(index);
        idOpt = Optional.ofNullable(id);
        this.prefix = prefix;
    }

    /**
     * Checks that {@code ArgumentMultimap} contains only one of Id and Index strings.
     */
    public static boolean checkIndexOrIdOnly(ArgumentMultimap argMultimap, Prefix prefix) {
        boolean hasId = argMultimap.getValue(prefix).isPresent();
        boolean hasIndex = !argMultimap.getPreamble().isBlank();
        return (hasId && !hasIndex) || (!hasId && hasIndex);
    }

    /**
     * Gets the person that is being referred to from the {@code Model}.
     * @throws CommandException if the person cannot be found from the model
     *                          or if this pair is referring to a location.
     */
    @Override
    public Person getPersonFromPair(Model model) throws CommandException {
        requireNonNull(model);
        if (!prefix.equals(PREFIX_PERSON_ID)) {
            throw new CommandException(MESSAGE_INVALID_PERSON_COMMAND_USE);
        }

        if (idOpt.isEmpty()) {
            List<Person> lastShownList = model.getSortedPersonList();
            if (indexOpt.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDEX);
            }

            return model.getPersonFromIndex(indexOpt.get());
        } else {
            assert indexOpt.isEmpty();
            if (!model.hasPersonId(idOpt.get())) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_ID);
            }

            return model.getPersonById(idOpt.get());
        }
    }

    /**
     * Gets the location that is being referred to from the {@code Model}.
     * @throws CommandException if the location cannot be found from the model
     *                          or if this pair is referring to a person.
     */
    @Override
    public Location getLocationFromPair(Model model) throws CommandException {
        requireNonNull(model);
        if (!prefix.equals(PREFIX_LOCATION_ID)) {
            throw new CommandException(MESSAGE_INVALID_LOCATION_COMMAND_USE);
        }

        if (idOpt.isEmpty()) {
            List<Location> lastShownList = model.getSortedLocationList();
            if (indexOpt.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_INDEX);
            }

            return model.getLocationFromIndex(indexOpt.get());
        } else {
            assert indexOpt.isEmpty();
            if (!model.hasLocationId(idOpt.get())) {
                throw new CommandException(Messages.MESSAGE_INVALID_LOCATION_ID);
            }

            return model.getLocationById(idOpt.get());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IndexIdPair)) {
            return false;
        }

        IndexIdPair o = (IndexIdPair) other;
        return prefix.equals(o.prefix);
    }
}
