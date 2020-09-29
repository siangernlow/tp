package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.Visit;

import java.time.LocalDate;

/**
 * Jackson-friendly version of {@link Visit}.
 */
public class JsonAdaptedVisit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final Index personId;
    private final Index locationId;
    private final LocalDate dateOfVisit;

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("personId") Index personId,
                            @JsonProperty("locationId") Index locationId,
                            @JsonProperty("dateOfVisit") LocalDate date) {
       this.personId = personId;
       this.locationId = locationId;
       this.dateOfVisit = date;
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        personId = source.getPersonId();
        locationId = source.getLocationId();
        dateOfVisit = source.getDate();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        //to be further implemented

        if (personId == null) {
            throw new IllegalValueException((MISSING_FIELD_MESSAGE_FORMAT));
        }

        final Index modelPersonId = personId;

        if (locationId == null) {
            throw new IllegalValueException((MISSING_FIELD_MESSAGE_FORMAT));
        }

        final Index modelLocationId = locationId;

        if (dateOfVisit == null) {
            throw new IllegalValueException((MISSING_FIELD_MESSAGE_FORMAT));
        }

        final LocalDate modelDate = dateOfVisit;

        return new Visit(modelPersonId, modelLocationId, modelDate);
    }
}
