package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_DATA_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PREAMBLE_SHOULD_BE_EMPTY;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.ArgumentTokenizer.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * Generates objects using data provided in CSV files.
 */
public class DataGenerator {
    public static final String DEFAULT_SEPARATOR = ",";
    public static final String DEFAULT_QUOTE = "\"";

    public static final String INVALID_ROW_FORMAT =
            "Invalid format detected on line %d. %s";
    // For logging purposes
    private static final Logger logger = Logger.getLogger(DataGenerator.class.getName());

    // Prevent instantiation
    private DataGenerator() {};

    /**
     * Generates a list of {@code Person} from the given CSV file.
     *
     * @param filepath The absolute file path of the CSV file.
     * @return A list of {@code Person}, one for each line in the CSV file.
     * @throws ParseException if there is any formatting error present.
     */
    public static List<Person> generatePersonsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<Person> personsList = new ArrayList<>();

        // Used to detect which line had an error
        int lineNumber = 1;

        // The prefixes of the fields required to build a person
        Prefix[] prefixes = new Prefix[]{PREFIX_PERSON_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_QUARANTINE_STATUS, PREFIX_INFECTION_STATUS};

        while (scanner.hasNext()) {
            ArgumentMultimap dataValues = tokenizeData(scanner.nextLine(), prefixes);
            // Leave out prefix tag as it is not compulsory
            Person person = generatePerson(dataValues, lineNumber,
                    Arrays.copyOfRange(prefixes, 0, prefixes.length));
            personsList.add(person);
            lineNumber++;
        }

        return personsList;
    }

    /**
     * Generates a {@code Person} from the given parameters.
     * The assertion ensures that the correct prefixes are called in other methods calling this method.
     *
     * @param argMultimap An {@code ArgumentMultimap} containing fields to create a {@code Person}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @param compulsoryPrefixes The prefixes of the values that are compulsory
     * @return A Person with the given parameters.
     * @throws ParseException if there are not enough parameters to create a person.
     */
    private static Person generatePerson(ArgumentMultimap argMultimap, int lineNumber, Prefix... compulsoryPrefixes)
            throws ParseException {
        // Defensive code
        assert Arrays.equals(compulsoryPrefixes, new Prefix[]{PREFIX_PERSON_ID, PREFIX_NAME,
            PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_QUARANTINE_STATUS, PREFIX_INFECTION_STATUS});

        if (!arePrefixesPresent(argMultimap, compulsoryPrefixes)) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_PREAMBLE_SHOULD_BE_EMPTY);
        }

        try {
            Id personId = ParserUtil.parseId(recoverDataFormat(argMultimap.getValue(PREFIX_PERSON_ID).get()));
            Name name = ParserUtil.parseName(recoverDataFormat(argMultimap.getValue(PREFIX_NAME).get()));
            Phone phone = ParserUtil.parsePhone(recoverDataFormat(argMultimap.getValue(PREFIX_PHONE).get()));
            Email email = ParserUtil.parseEmail(recoverDataFormat(argMultimap.getValue(PREFIX_EMAIL).get()));
            Address address = ParserUtil.parseAddress(recoverDataFormat(argMultimap.getValue(PREFIX_ADDRESS).get()));
            QuarantineStatus quarantineStatus = ParserUtil.parseQuarantineStatus(
                    recoverDataFormat(argMultimap.getValue(PREFIX_QUARANTINE_STATUS).get()));
            InfectionStatus infectionStatus = ParserUtil.parseInfectionStatus(
                    recoverDataFormat(argMultimap.getValue(PREFIX_INFECTION_STATUS).get()));

            return new Person(personId, name, phone, email, address, quarantineStatus, infectionStatus);
        } catch (ParseException pe) {
            throw new ParseException(String.format(INVALID_ROW_FORMAT, lineNumber, pe.getMessage()));
        }
    }

    /**
     * Generates a list of {@code Location} from the given CSV file.
     *
     * @param filepath The absolute file path of the CSV file.
     * @return A list of {@code Location}, one for each line in the CSV file.
     * @throws ParseException if there is any formatting error present.
     */
    public static List<Location> generateLocationsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<Location> locationsList = new ArrayList<>();

        // Used to detect which line had an error
        int lineNumber = 1;

        // The prefixes of the fields required to build a location
        Prefix[] prefixes = new Prefix[]{PREFIX_LOCATION_ID, PREFIX_NAME, PREFIX_ADDRESS};

        while (scanner.hasNext()) {
            ArgumentMultimap dataValues = tokenizeData(scanner.nextLine(), prefixes);
            Location location = generateLocation(dataValues, lineNumber, prefixes);
            locationsList.add(location);
            lineNumber++;
        }

        return locationsList;
    }

    /**
     * Generates a {@code Location} from the given parameters.
     * The assertion ensures that the correct prefixes are called in other methods calling this method.
     *
     * @param argMultimap An {@code ArgumentMultimap} containing fields to create a {@code Location}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @param prefixes The prefixes of the fields required to build the location.
     * @return A Location with the given parameters.
     * @throws ParseException if there are not enough parameters to create a location.
     */
    private static Location generateLocation(ArgumentMultimap argMultimap, int lineNumber, Prefix... prefixes)
            throws ParseException {
        // Defensive code
        assert Arrays.equals(prefixes, new Prefix[]{PREFIX_LOCATION_ID, PREFIX_NAME, PREFIX_ADDRESS});

        if (!arePrefixesPresent(argMultimap, prefixes)) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_PREAMBLE_SHOULD_BE_EMPTY);
        }

        try {
            Id locationId = ParserUtil.parseId(recoverDataFormat(argMultimap.getValue(PREFIX_LOCATION_ID).get()));
            Name name = ParserUtil.parseName(recoverDataFormat(argMultimap.getValue(PREFIX_NAME).get()));
            Address address = ParserUtil.parseAddress(recoverDataFormat(argMultimap.getValue(PREFIX_ADDRESS).get()));

            return new Location(locationId, name, address);
        } catch (ParseException pe) {
            throw new ParseException(String.format(INVALID_ROW_FORMAT, lineNumber, pe.getMessage()));
        }
    }

    /**
     * Generates a list of {@code VisitParametersContainer} from the given CSV file.
     * As there is a need to translate from the user input index to the
     * actual id of the {@code Person} and {{@code Location}, a container class
     * {@code VisitParameters} is used to store these indexes to be translated further down the
     * pipeline.
     *
     * @param filepath The absolute file path of the CSV file.
     * @return A list of {@code VisitParametersContainer}, one for each line in the CSV file.
     * @throws ParseException if there is any formatting error present.
     */
    public static List<VisitParametersContainer> generateVisitsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<VisitParametersContainer> visitParametersList = new ArrayList<>();

        // Used to detect which line had an error
        int lineNumber = 1;

        // The prefixes of the fields required to build a vpc
        Prefix[] prefixes = new Prefix[]{PREFIX_PERSON_ID, PREFIX_LOCATION_ID, PREFIX_DATE};

        while (scanner.hasNext()) {
            ArgumentMultimap dataValues = tokenizeData(scanner.nextLine(), prefixes);
            VisitParametersContainer vpc = generateVisitParametersContainer(dataValues, lineNumber, prefixes);
            visitParametersList.add(vpc);
            lineNumber++;
        }

        return visitParametersList;
    }

    /**
     * Generates a {@code VisitParametersContainer} from the given parameters.
     * The assertion ensures that the correct prefixes are called in other methods calling this method.
     *
     * @param argMultimap An {@code ArgumentMultimap} containing fields to create a {@code VisitParameterContainer}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @param prefixes The prefixes of the fields required to build the vpc.
     * @return A VisitParametersContainer storing the given parameters.
     * @throws ParseException if there are not enough parameters to create a VisitParametersContainer.
     */
    private static VisitParametersContainer generateVisitParametersContainer(ArgumentMultimap argMultimap,
                                                                             int lineNumber, Prefix... prefixes)
            throws ParseException {
        // Defensive code
        assert Arrays.equals(prefixes, new Prefix[]{PREFIX_PERSON_ID, PREFIX_LOCATION_ID, PREFIX_DATE});

        if (!arePrefixesPresent(argMultimap, prefixes)) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_PREAMBLE_SHOULD_BE_EMPTY);
        }

        try {
            Id personId = ParserUtil.parseId(recoverDataFormat(argMultimap.getValue(PREFIX_PERSON_ID).get()));
            Id locationId = ParserUtil.parseId(recoverDataFormat(argMultimap.getValue(PREFIX_LOCATION_ID).get()));
            LocalDate date = ParserUtil.parseDate(recoverDataFormat(argMultimap.getValue(PREFIX_DATE).get()));

            return new VisitParametersContainer(personId, locationId, date);
        } catch (ParseException pe) {
            throw new ParseException(String.format(INVALID_ROW_FORMAT, lineNumber, pe.getMessage()));
        }
    }

    /**
     * Tokenizes the given CSV data string to a
     * (if the prefixes are present).
     *
     * @param data A string taken from a line of a CSV file.
     * @return An {@code ArgumentMultimap} containing the tokenized data fields.
     */
    public static ArgumentMultimap tokenizeData(String data, Prefix... prefixes) {
        requireNonNull(data);
        assert !data.isEmpty();

        // Remove any quotes since the data is delimited by their prefixes.
        String processedData = data.replace(DEFAULT_QUOTE, "");

        // Add spaces behind each separator to allow the data to be tokenized.
        processedData = processedData.replace(DEFAULT_SEPARATOR, DEFAULT_SEPARATOR + " ");

        // Finally, add a space at the start of the string so that the first prefix can be tokenized.
        processedData = " " + processedData;

        return ArgumentTokenizer.tokenize(processedData, prefixes);
    }

    /**
     * Removes the formatting done on the data during the
     * tokenizing stage.
     * @param data The data to be reformatted.
     * @return The reformatted data.
     */
    public static String recoverDataFormat(String data) {
        // Remove the double whitespaces inserted from tokenizing.
        String reformattedData = data.replace(",  ", ", ");

        // Remove trailing comma if it exists
        if (reformattedData.endsWith(",")) {
            reformattedData = reformattedData.substring(0, reformattedData.length() - 1);
        }
        return reformattedData;
    }

    /**
     * Reads the file at the given file path.
     *
     * @param filepath The absolute file path of the file.
     * @return A scanner containing the file's data.
     * @throws ParseException if the specified file path is invalid.
     */
    private static Scanner readFile(String filepath) throws ParseException {
        try {
            logger.log(Level.INFO, "Reading from " + filepath);

            Scanner scanner = new Scanner(new File(filepath));
            return scanner;
        } catch (FileNotFoundException e) {
            throw new ParseException(MESSAGE_INVALID_FILE_PATH);
        }
    }

    /**
     * This class serves as a container for Visits.
     * The stored parameters will be processed in the
     * {@code AddVisitsFromCsvCommand}.
     */
    public static class VisitParametersContainer {
        private final Id personIndex;
        private final Id locationIndex;
        private final LocalDate date;

        /**
         * Creates a VisitParametersContainer object from the given args.
         *
         * @param personIndex The index of the person as viewed in the last displayed list.
         * @param locationIndex The index of a location as viewed in the last displayed list.
         * @param date The date of the visit.
         */
        public VisitParametersContainer(Id personIndex, Id locationIndex, LocalDate date) {
            requireAllNonNull(personIndex, locationIndex, date);
            this.personIndex = personIndex;
            this.locationIndex = locationIndex;
            this.date = date;
        }

        public Id getPersonIndex() {
            return personIndex;
        }

        public Id getLocationIndex() {
            return locationIndex;
        }

        public LocalDate getDate() {
            return date;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof VisitParametersContainer)) {
                return false;
            }

            VisitParametersContainer otherVisitParametersContainer = (VisitParametersContainer) other;
            return otherVisitParametersContainer.getPersonIndex().equals(getPersonIndex())
                    && otherVisitParametersContainer.getLocationIndex().equals(getLocationIndex())
                    && otherVisitParametersContainer.getDate().equals(getDate());
        }
    }
}
