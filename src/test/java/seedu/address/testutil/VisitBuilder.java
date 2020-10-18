package seedu.address.testutil;

import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * A utility class to help with building Location objects.
 */
public class VisitBuilder {
    public static final String DEFAULT_DATE_STRING = "2020-09-12";

    public static final Person DEFAULT_PERSON = BENSON;
    public static final Location DEFAULT_LOCATION = BENSON_LOCATION;
    public static final LocalDate DEFAULT_DATE;
    public static final Id DEFAULT_PERSON_ID = DEFAULT_PERSON.getId();
    public static final Index DEFAULT_PERSON_INDEX = Index.fromOneBased(2);
    public static final Id DEFAULT_LOCATION_ID = DEFAULT_LOCATION.getId();
    public static final Index DEFAULT_LOCATION_INDEX = Index.fromOneBased(2);

    static {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DEFAULT_DATE = LocalDate.parse(DEFAULT_DATE_STRING, inputFormat);
    }

    private Person person;
    private Location location;
    private LocalDate dateOfVisit;

    /**
     * Creates a {@code VisitBuilder} with the default details.
     */
    public VisitBuilder() {
        person = DEFAULT_PERSON;
        location = DEFAULT_LOCATION;
        dateOfVisit = DEFAULT_DATE;
    }

    /**
     * Initializes the VisitBuilder with the data of {@code visitToCopy}.
     */
    public VisitBuilder(Visit visitToCopy) {
        person = visitToCopy.getPerson();
        location = visitToCopy.getLocation();
        dateOfVisit = visitToCopy.getDate();
    }

    /**
     * Sets the {@code person} of the {@code Visit} that we are building.
     */
    public VisitBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Visit} that we are building.
     */
    public VisitBuilder withLocation(Location location) {
        this.location = location;
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
        return new Visit(person, location, dateOfVisit);
    }
}
