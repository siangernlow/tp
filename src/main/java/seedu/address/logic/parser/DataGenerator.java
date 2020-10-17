package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InfectionStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.QuarantineStatus;
import seedu.address.model.tag.Tag;
import seedu.address.model.visit.Visit;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class DataGenerator {
    public static final Character DEFAULT_SEPARATOR = ',';
    public static final Character DEFAULT_QUOTE = '"';

    // Minimum number of parameters required to create the object
    public static final int MIN_PERSON_PARAMETERS = 6;
    public static final int MIN_LOCATION_PARAMETERS = 2;
    public static final int MIN_VISIT_PARAMETERS = 3;

    // Prevent instantiation
    private DataGenerator() {};

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

    private static Person generatePerson(List<String> dataValues, int lineNumber) throws ParseException {
        assert MIN_PERSON_PARAMETERS >= 6;

        // Check if enough parameters; 6 compulsory parameters required for adding a Person
        if (dataValues.size() < MIN_PERSON_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_FORMAT, lineNumber));
        }

        Name name = ParserUtil.parseName(dataValues.get(0));
        Phone phone = ParserUtil.parsePhone(dataValues.get(1));
        Email email = ParserUtil.parseEmail(dataValues.get(2));
        Address address = ParserUtil.parseAddress(dataValues.get(3));
        QuarantineStatus quarantineStatus = ParserUtil.parseQuarantineStatus(dataValues.get(4));
        InfectionStatus infectionStatus = ParserUtil.parseInfectionStatus(dataValues.get(5));

        Set<String> tags = new HashSet<>();
        if (dataValues.size() > MIN_PERSON_PARAMETERS) {
            String[] tagsAsString = dataValues.get(6).split(",");
            tags.addAll(Arrays.asList(tagsAsString));
        }
        Set<Tag> tagList = ParserUtil.parseTags(tags);

        return new Person(name, phone, email, address, quarantineStatus, infectionStatus, tagList);
    }



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

    private static Location generateLocation(List<String> dataValues, int lineNumber) throws ParseException {
        assert MIN_LOCATION_PARAMETERS >= 6;

        // Check if enough parameters; 2 compulsory parameters required for adding a Location
        if (dataValues.size() < MIN_LOCATION_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_FORMAT, lineNumber));
        }

        Name name = ParserUtil.parseName(dataValues.get(0));
        Address address = ParserUtil.parseAddress(dataValues.get(1));

        return new Location(name, address);
    }

    public static List<VisitParameters> generateVisitsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<VisitParameters> visitParametersList = new ArrayList<>();

        // Used to detect which line had an error
        int lineNumber = 1;

        while (scanner.hasNext()) {
            List<String> dataValues = generateDataValues(scanner.nextLine());
            VisitParameters visitParameters = generateVisitParameters(dataValues, lineNumber);
            visitParametersList.add(visitParameters);
            lineNumber++;
        }

        return visitParametersList;
    }

    private static VisitParameters generateVisitParameters(List<String> dataValues, int lineNumber)
            throws ParseException {
        assert MIN_VISIT_PARAMETERS >= 3;

        // Check if enough parameters; 3 compulsory parameters required for adding a Visit
        if (dataValues.size() < MIN_VISIT_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_FORMAT, lineNumber));
        }

        Index personIndex = ParserUtil.parseIndex(dataValues.get(0));
        Index locationIndex = ParserUtil.parseIndex(dataValues.get(1));
        LocalDate date = ParserUtil.parseDate(dataValues.get(2));

        return new VisitParameters(personIndex, locationIndex, date);
    }

    /**
     * Generates data values given by the data, delimited by commas.
     * Accurately generates fields which have commas in them.
     *
     * @param data A string representaion of the object's data.
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
        StringBuffer currentValue = new StringBuffer();

        for (Character c : chars) {
            // Reached a delimiter, add the currentValue and reset.
            if (c.equals(DEFAULT_SEPARATOR) && !isInQuotes) {
                dataValues.add(currentValue.toString());
                currentValue = new StringBuffer();
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

        return dataValues;
    }

    private static Scanner readFile(String filepath) throws ParseException {
        try {
            Scanner scanner = new Scanner(new File(filepath));
            return scanner;
        } catch (FileNotFoundException e) {
            throw new ParseException(MESSAGE_INVALID_FILE_PATH);
        }
    }

    public static class VisitParameters {
        private final Index personIndex;
        private final Index locationIndex;
        private final LocalDate date;

        public VisitParameters(Index personIndex, Index locationIndex, LocalDate date) {
            requireAllNonNull(personIndex, locationIndex, date);
            this.personIndex = personIndex;
            this.locationIndex = locationIndex;
            this.date = date;
        }

        public Index getPersonIndex() {
            return personIndex;
        }

        public Index getLocationIndex() {
            return locationIndex;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
