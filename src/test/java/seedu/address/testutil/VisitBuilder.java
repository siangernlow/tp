package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.model.visit.Visit;

/**
 * A utility class to help with building Location objects.
 */
public class VisitBuilder {
    public static final String DEFAULT_PERSON_ID = "1";
    public static final String DEFAULT_LOCATION_ID = "1";
    public static final String DEFAULT_DATE_STRING = "2020-02-01";

    public static final Index DEFAULT_PERSON_INDEX;
    public static final Index DEFAULT_LOCATION_INDEX;
    public static final LocalDate DEFAULT_DATE;

    static {
        DEFAULT_PERSON_INDEX = Index.fromOneBased(Integer.parseInt(DEFAULT_PERSON_ID));
        DEFAULT_LOCATION_INDEX = Index.fromOneBased(Integer.parseInt(DEFAULT_LOCATION_ID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DEFAULT_DATE = LocalDate.parse(DEFAULT_DATE_STRING, inputFormat);
    }

    private Index personId;
    private Index locationId;
    private LocalDate dateOfVisit;

    /**
     * Creates a {@code VisitBuilder} with the default details.
     */
    public VisitBuilder() {
        personId = DEFAULT_PERSON_INDEX;
        locationId = DEFAULT_LOCATION_INDEX;
        dateOfVisit = DEFAULT_DATE;
    }

    /**
     * Initializes the VisitBuilder with the data of {@code visitToCopy}.
     */
    public VisitBuilder(Visit visitToCopy) {
        personId = visitToCopy.getPersonId();
        locationId = visitToCopy.getLocationId();
        dateOfVisit = visitToCopy.getDate();
    }

    /**
     * Sets the {@code personId} of the {@code Visit} that we are building.
     */
    public VisitBuilder withPersonId(Index personId) {
        this.personId = personId;
        return this;
    }

    /**
     * Sets the {@code locationId} of the {@code Visit} that we are building.
     */
    public VisitBuilder withLocationId(Index locationId) {
        this.locationId = locationId;
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Visit} that we are building.
     */
    public VisitBuilder withDate(String date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateOfVisit = LocalDate.parse(date, inputFormat);
        return this;
    }


    public Visit build() {
        return new Visit(personId, locationId, dateOfVisit);
    }
}
