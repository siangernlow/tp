package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.Visit;

/**
 * Jackson-friendly version of {@link Visit}.
 */
public class JsonAdaptedVisit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final String personId;
    private final String locationId;
    private final String dateOfVisit;


    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("personId") String personId,
                            @JsonProperty("locationId") String locationId,
                            @JsonProperty("dateOfVisit") String date) {
        this.personId = personId;
        this.locationId = locationId;
        this.dateOfVisit = date;
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        personId = source.getPersonId().toString();
        locationId = source.getLocationId().toString();
        dateOfVisit = source.getDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        //to be further implemented


        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId is missing"));
        }

<<<<<<< HEAD

        final Index modelPersonId = personId;

=======
>>>>>>> 4ceb7393511898b4c475e051038271372eedc2f3
        if (locationId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "locationId is missing"));
        }

        if (dateOfVisit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date is missing"));
        }

        if (personId.trim().equals("")) {
            throw new IllegalValueException("Please enter the correct personId");
        }

        if (locationId.trim().equals("")) {
            throw new IllegalValueException("Please enter the correct locationId");
        }

        if (dateOfVisit.trim().equals("")) {
            throw new IllegalValueException("Please enter the correct date format");
        }

        final Index modelPersonId = Index.fromOneBased(Integer.parseInt(personId));
        final Index modelLocationId = Index.fromOneBased(Integer.parseInt(locationId));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate modelDate = LocalDate.parse(dateOfVisit, inputFormat);
        return new Visit(modelPersonId, modelLocationId, modelDate);
    }
}
