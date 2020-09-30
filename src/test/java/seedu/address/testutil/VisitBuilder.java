package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.model.visit.Visit;

/**
 * A utility class to help with building Location objects.
 */
public class VisitBuilder {
    public static final String DEFAULT_PERSONID = "1";
    public static final String DEFAULT_LOCATIONID = "1";
    public static final String DEFAULT_DATE = "2020-02-01";

    private Index personId;
    private Index locationId;
    private LocalDate dateOfVisit;

    /**
     * Creates a {@code VisitBuilder} with the default details.
     */
    public VisitBuilder() {
        this.personId = Index.fromOneBased(Integer.parseInt(DEFAULT_PERSONID));
        this.locationId = Index.fromOneBased(Integer.parseInt(DEFAULT_LOCATIONID));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateOfVisit = LocalDate.parse(DEFAULT_DATE, inputFormat);
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
    public VisitBuilder withPersonId(String personId) {
        this.personId = Index.fromOneBased(Integer.parseInt(personId));
        return this;
    }

    /**
     * Sets the {@code locationId} of the {@code Visit} that we are building.
     */
    public VisitBuilder withLocationId(String locationId) {
        this.locationId = Index.fromOneBased(Integer.parseInt(locationId));
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
