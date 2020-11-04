package seedu.address.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBook;
import seedu.address.model.person.ReadOnlyPersonBook;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;

/**
 * Contains utility methods for populating {@code PersonBook}, {@code LocationBook}
 * and {@code VisitBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Id("S123A"), new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new QuarantineStatus("2020-02-02"),
                    new InfectionStatus("false")),
            new Person(new Id("S234B"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new QuarantineStatus("2020-02-05"), new InfectionStatus("false")),
            new Person(new Id("S345C"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new QuarantineStatus("false"), new InfectionStatus("false")),
            new Person(new Id("S456D"), new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new QuarantineStatus("false"),
                    new InfectionStatus("2020-04-02")),
            new Person(new Id("S567E"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new QuarantineStatus("false"), new InfectionStatus("2020-10-10")),
            new Person(new Id("S678F"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new QuarantineStatus("2020-10-02"), new InfectionStatus("2020-10-02"))
        };
    }

    public static Location[] getSampleLocations() {
        return new Location[] {
            new Location(new Id("L123A"), new Name("School of Computing"),
                    new Address("NUS School of Computing COM1 13 Computing Dr, 117417")),
            new Location(new Id("L234B"), new Name("VivoCity"),
                    new Address("1 HarbourFront Walk, Singapore 098585")),
            new Location(new Id("L345C"), new Name("Stephen Riady Centre"),
                    new Address("2 College Ave West, Singapore 138607")),
            new Location(new Id("L456D"), new Name("ION Orchard"),
                    new Address("2 Orchard Turn, Singapore 238801")),
            new Location(new Id("L567E"), new Name("Plaza Singapura"),
                    new Address("68 Orchard Rd, Singapore 238839")),
            new Location(new Id("L678F"), new Name("Singapore Zoo"),
                    new Address("80 Mandai Lake Rd, 729826"))
        };
    }

    public static List<Visit> getSampleVisits() {
        LocalDate date = LocalDate.now();
        List<Visit> visits = new ArrayList<>();
        int size = Math.min(getSampleLocations().length, getSamplePersons().length);
        for (int i = 0; i < size; i++) {
            visits.add(new Visit(getSamplePersons()[i], getSampleLocations()[size - 1 - i], date));
            visits.add(new Visit(getSamplePersons()[size - 1 - i], getSampleLocations()[i], date));
            date = date.minusDays(1);
        }
        return visits;
    }

    public static ReadOnlyPersonBook getSamplePersonBook() {
        PersonBook samplePb = new PersonBook();
        for (Person samplePerson : getSamplePersons()) {
            samplePb.addPerson(samplePerson);
        }
        return samplePb;
    }

    public static ReadOnlyLocationBook getSampleLocationBook() {
        LocationBook sampleLb = new LocationBook();
        for (Location sampleLocation : getSampleLocations()) {
            sampleLb.addLocation(sampleLocation);
        }
        return sampleLb;
    }

    public static ReadOnlyVisitBook getSampleVisitBook() {
        VisitBook sampleVb = new VisitBook();
        for (Visit sampleVisit : getSampleVisits()) {
            sampleVb.addVisit(sampleVisit);
        }
        return sampleVb;
    }
}
