package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_DATA_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.attribute.Tag;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;

/**
 * Generates objects using data provided in CSV files.
 */
public class DataGenerator {
    public static final Character DEFAULT_SEPARATOR = ',';
    public static final Character DEFAULT_QUOTE = '"';

    // Minimum number of parameters required to create the object
    public static final int MIN_PERSON_PARAMETERS = 7;
    public static final int MIN_LOCATION_PARAMETERS = 3;
    public static final int MIN_VISIT_PARAMETERS = 3;

    public static final String INVALID_ROW_FORMAT =
            "Invalid format detected on line %d. %s";

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

        while (scanner.hasNext()) {
            List<String> dataValues = generateDataValues(scanner.nextLine());
            Person person = generatePerson(dataValues, lineNumber);
            personsList.add(person);
            lineNumber++;
        }

        return personsList;
    }

    /**
     * Generates a {@code Person} from the given parameters.
     *
     * @param dataValues A list containing fields to create a {@code Person}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @return A person with the given parameters.
     * @throws ParseException if there are not enough parameters to create a person.
     */
    private static Person generatePerson(List<String> dataValues, int lineNumber) throws ParseException {
        assert MIN_PERSON_PARAMETERS >= 6;

        // Check if enough parameters; 6 compulsory parameters required for adding a Person
        if (dataValues.size() < MIN_PERSON_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }

        try {
            Id id = ParserUtil.parseId(dataValues.get(0));
            Name name = ParserUtil.parseName(dataValues.get(1));
            Phone phone = ParserUtil.parsePhone(dataValues.get(2));
            Email email = ParserUtil.parseEmail(dataValues.get(3));
            Address address = ParserUtil.parseAddress(dataValues.get(4));
            QuarantineStatus quarantineStatus = ParserUtil.parseQuarantineStatus(dataValues.get(5));
            InfectionStatus infectionStatus = ParserUtil.parseInfectionStatus(dataValues.get(6));

            Set<String> tags = new HashSet<>();
            if (dataValues.size() > MIN_PERSON_PARAMETERS) {
                String[] tagsAsString = dataValues.get(7).split(",");
                tags.addAll(Arrays.asList(tagsAsString));
            }
            Set<Tag> tagList = ParserUtil.parseTags(tags);

            return new Person(id, name, phone, email, address, quarantineStatus, infectionStatus, tagList);
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

        while (scanner.hasNext()) {
            List<String> dataValues = generateDataValues(scanner.nextLine());
            Location location = generateLocation(dataValues, lineNumber);
            locationsList.add(location);
            lineNumber++;
        }

        return locationsList;
    }

    /**
     * Generates a {@code Location} from the given parameters.
     *
     * @param dataValues A list containing fields to create a {@code Location}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @return A location with the given parameters.
     * @throws ParseException if there are not enough parameters to create a location.
     */
    private static Location generateLocation(List<String> dataValues, int lineNumber) throws ParseException {
        assert MIN_LOCATION_PARAMETERS >= 2;

        // Check if enough parameters; 2 compulsory parameters required for adding a Location
        if (dataValues.size() < MIN_LOCATION_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }

        try {
            Id id = ParserUtil.parseId(dataValues.get(0));
            Name name = ParserUtil.parseName(dataValues.get(1));
            Address address = ParserUtil.parseAddress(dataValues.get(2));

            return new Location(id, name, address);
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

        while (scanner.hasNext()) {
            List<String> dataValues = generateDataValues(scanner.nextLine());
            VisitParametersContainer visitParameters = generateVisitParametersContainer(dataValues, lineNumber);
            visitParametersList.add(visitParameters);
            lineNumber++;
        }

        return visitParametersList;
    }

    /**
     * Generates a {@code VisitParametersContainer} from the given parameters.
     *
     * @param dataValues A list containing fields to create a {@code VisitParametersContainer}.
     * @param lineNumber The current line number of the CSV that we are on. Used for error handling.
     * @return A VisitParametersContainer storing the given parameters.
     * @throws ParseException if there are not enough parameters to create a VisitParametersContainer.
     */
    private static VisitParametersContainer generateVisitParametersContainer(List<String> dataValues, int lineNumber)
            throws ParseException {
        assert MIN_VISIT_PARAMETERS >= 3;

        // Check if enough parameters; 3 compulsory parameters required for adding a Visit
        if (dataValues.size() < MIN_VISIT_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_MISSING_DATA_FORMAT, lineNumber));
        }
        try {
            Id personIndex = ParserUtil.parseId(dataValues.get(0));
            Id locationIndex = ParserUtil.parseId(dataValues.get(1));
            LocalDate date = ParserUtil.parseDate(dataValues.get(2));

            return new VisitParametersContainer(personIndex, locationIndex, date);
        } catch (ParseException pe) {
            throw new ParseException(String.format(INVALID_ROW_FORMAT, lineNumber, pe.getMessage()));
        }
    }

    /**
     * Generates data values given by the data, delimited by commas.
     * Accurately generates fields which have commas in them.
     *
     * @param data A string representation of the object's data.
     * @return A list with String fields which would be used to create the required object.
     */
    private static List<String> generateDataValues(String data) {
        requireNonNull(data);
        assert !data.isEmpty();
        List<String> dataValues = new ArrayList<>();
        // As certain fields may contain commas, we cannot simply split the string using a comma separator.
        char[] chars = data.toCharArray();

        // If true, then any DEFAULT_SEPARATOR is to be treated as a normal character and not a delimiter.
        boolean isInQuotes = false;
        StringBuilder currentValue = new StringBuilder();

        for (Character c : chars) {
            // Reached a delimiter, add the currentValue and reset.
            if (c.equals(DEFAULT_SEPARATOR) && !isInQuotes) {
                dataValues.add(currentValue.toString());
                currentValue = new StringBuilder();
                continue;
            }

            // Start of a DEFAULT_QUOTE, set flag to ignore any DEFAULT_DELIMITER encountered.
            if (c.equals(DEFAULT_QUOTE) && !isInQuotes) {
                isInQuotes = true;
                continue;
            } else if (c.equals(DEFAULT_QUOTE) && isInQuotes) { // End of DEFAULT_QUOTE
                isInQuotes = false;
                continue;
            }
            currentValue.append(c);
        }

        // Adding the last field
        if (currentValue.length() > 0) {
            dataValues.add(currentValue.toString());
        }

        return dataValues;
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
