package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Jackson-friendly version of {@link Visit}.
 */
public class JsonAdaptedVisit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final Person person;
    private final Location location;
    private final String dateOfVisit;

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("person") Person person,
                            @JsonProperty("location") Location location,
                            @JsonProperty("dateOfVisit") String date) {
        this.person = person;
        this.location = location;
        this.dateOfVisit = date;
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        person = source.getPerson();
        location = source.getLocation();
        dateOfVisit = source.getDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        //to be further implemented

        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
        }

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "location"));
        }

        if (dateOfVisit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        if (dateOfVisit.trim().equals("")) {
            throw new IllegalValueException("Please enter the correct date format");
        }

        final Person modelPerson = person;
        final Location modelLocation = location;
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate modelDate = LocalDate.parse(dateOfVisit, inputFormat);
        return new Visit(modelPerson, modelLocation, modelDate);
    }
}
