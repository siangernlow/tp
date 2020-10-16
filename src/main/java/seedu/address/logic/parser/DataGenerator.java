package seedu.address.logic.parser;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATA_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;

public class DataGenerator {
    public static final Character DEFAULT_SEPARATOR = ',';
    public static final Character DEFAULT_QUOTE = '"';
    public static List<Person> generatePersonsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<Person> personsList = new ArrayList<>();

        // Used for error output
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
        // Check if enough parameters; 6 compulsory parameters required for adding a Person
        if (dataValues.size() < 6) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATA_FORMAT, lineNumber));
        }

        Name name = ParserUtil.parseName(dataValues.get(0));
        Phone phone = ParserUtil.parsePhone(dataValues.get(1));
        Email email = ParserUtil.parseEmail(dataValues.get(2));
        Address address = ParserUtil.parseAddress(dataValues.get(3));
        QuarantineStatus quarantineStatus = ParserUtil.parseQuarantineStatus(dataValues.get(4));
        InfectionStatus infectionStatus = ParserUtil.parseInfectionStatus(dataValues.get(5));

        Set<String> tags = new HashSet<>();
        if (dataValues.size() > 6) {
            String[] tagsAsString = dataValues.get(6).split(",");
            tags.addAll(Arrays.asList(tagsAsString));
        }
        Set<Tag> tagList = ParserUtil.parseTags(tags);

        return new Person(name, phone, email, address, quarantineStatus, infectionStatus, tagList);
    }

    /**
     * Generates a person from the given personData
     * @param personData A string representing a person's information.
     * @return A person with the values in personData.
     * @throws ParseException if any arguments are not in the required format.
     */
    private static List<String> generateDataValues(String personData) throws ParseException {
        requireNonNull(personData);
        assert !personData.isEmpty();

        List<String> dataValues = new ArrayList<>();

        // As certain fields may contain commas, we cannot simply split the string using a comma separator.
        char[] chars = personData.toCharArray();

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

    public static List<Location> generateLocationsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<Location> personsList = new ArrayList<>();
        return null;
    }

    public static List<Visit> generateVisitsList(String filepath) throws ParseException {
        Scanner scanner = readFile(filepath);
        List<Visit> personsList = new ArrayList<>();
        return null;
    }

    private static Scanner readFile(String filepath) throws ParseException {
        try {
            Scanner scanner = new Scanner(new File(filepath));
            return scanner;
        } catch (FileNotFoundException e) {
            throw new ParseException(MESSAGE_INVALID_FILE_PATH);
        }
    }
}
