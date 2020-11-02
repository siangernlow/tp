package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "The input index is out of range for the current list!";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Wrong input date format";

    /**
     * Parses {@code id} into an {@code Id} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified id is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Id.isValidId(trimmedId)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(trimmedId);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        // parse exception to be implemented in later stage
        requireNonNull(date);
        String trimmedDate = date.trim();
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(trimmedDate, inputFormat);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
        return parsedDate;
    }


    /**
     * Parses a {@code String quarantineStatus} into a {@code QuarantineStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quarantineStatus} is invalid.
     */
    public static QuarantineStatus parseQuarantineStatus(String quarantineStatus) throws ParseException {
        requireNonNull(quarantineStatus);
        String trimmedQuarantineStatus = quarantineStatus.trim();
        if (!QuarantineStatus.isValidQuarantineStatus(trimmedQuarantineStatus)) {
            throw new ParseException(QuarantineStatus.MESSAGE_CONSTRAINTS);
        }
        return new QuarantineStatus(trimmedQuarantineStatus);
    }

    /**
     * Parses a {@code String infectionStatus} into an {@code InfectionStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code infectionStatus} is invalid.
     */
    public static InfectionStatus parseInfectionStatus(String infectionStatus) throws ParseException {
        requireNonNull(infectionStatus);
        String trimmedInfectionStatus = infectionStatus.trim();
        if (!InfectionStatus.isValidInfectionStatus(trimmedInfectionStatus)) {
            throw new ParseException(InfectionStatus.MESSAGE_CONSTRAINTS);
        }
        return new InfectionStatus(trimmedInfectionStatus);
    }

    /**
     * Parses the given {@code listType} and returns the enum representing it.
     * @param listType The type of list.
     * @return The enum representing the {@code listType}.
     * @throws ParseException When invalid type is given
     */
    public static ListType parseListType(String listType) throws ParseException {
        requireNonNull(listType);

        String trimmedListType = listType.trim().toLowerCase();

        switch (trimmedListType) {
        case "people":
            return ListType.ALL_PEOPLE;
        case "locations":
            return ListType.ALL_LOCATIONS;
        case "visits":
            return ListType.ALL_VISITS;
        case "infected":
            return ListType.ALL_INFECTED;
        case "quarantined":
            return ListType.ALL_QUARANTINED;
        case "stats":
            return ListType.STATISTICS;
        case "high-risk-locations":
            return ListType.HIGH_RISK_LOCATIONS;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
