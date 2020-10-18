package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.attribute.Tag;
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
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new QuarantineStatus("true"),
                    new InfectionStatus("false"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new QuarantineStatus("true"),
                    new InfectionStatus("false"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new QuarantineStatus("false"),
                    new InfectionStatus("false"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new QuarantineStatus("false"),
                    new InfectionStatus("true"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new QuarantineStatus("false"),
                    new InfectionStatus("true"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new QuarantineStatus("false"),
                    new InfectionStatus("true"), getTagSet("colleagues"))
        };
    }

    public static Location[] getSampleLocations() {
        return new Location[] {
            new Location(new Name("School of Computing"),
                    new Address("NUS School of Computing COM1 13 Computing Dr, 117417"), Index.fromOneBased(1)),
            new Location(new Name("VivoCity"),
                    new Address("1 HarbourFront Walk, Singapore 098585"), Index.fromOneBased(2)),
            new Location(new Name("Stephen Riady Centre"),
                    new Address("2 College Ave West, Singapore 138607"), Index.fromOneBased(3)),
            new Location(new Name("ION Orchard"),
                    new Address("2 Orchard Turn, Singapore 238801"), Index.fromOneBased(4)),
            new Location(new Name("Plaza Singapura"),
                    new Address("68 Orchard Rd, Singapore 238839"), Index.fromOneBased(5)),
            new Location(new Name("Singapore Zoo"),
                    new Address("80 Mandai Lake Rd, 729826"), Index.fromOneBased(6)),
        };
    }

    public static Visit[] getSampleVisits() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Visit[] {
            new Visit(new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new QuarantineStatus("true"),
                        new InfectionStatus("false"), Index.fromOneBased(1), getTagSet("friends")),
                    new Location(new Name("School of Computing"),
                        new Address("NUS School of Computing COM1 13 Computing Dr, 117417"), Index.fromOneBased(1)),
                    LocalDate.parse("2020-02-02", inputFormat)),
            new Visit(new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new QuarantineStatus("true"),
                        new InfectionStatus("false"), Index.fromOneBased(2),
                        getTagSet("colleagues", "friends")),
                    new Location(new Name("VivoCity"),
                        new Address("1 HarbourFront Walk, Singapore 098585"), Index.fromOneBased(2)),
                    LocalDate.parse("2020-02-02", inputFormat)),
            new Visit(new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new QuarantineStatus("false"),
                        new InfectionStatus("false"), Index.fromOneBased(3), getTagSet("neighbours")),
                    new Location(new Name("Stephen Riady Centre"),
                        new Address("2 College Ave West, Singapore 138607"), Index.fromOneBased(3)),
                    LocalDate.parse("2020-02-02", inputFormat)),
            new Visit(new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new QuarantineStatus("false"),
                        new InfectionStatus("true"), Index.fromOneBased(4), getTagSet("family")),
                    new Location(new Name("ION Orchard"),
                        new Address("2 Orchard Turn, Singapore 238801"), Index.fromOneBased(4)),
                    LocalDate.parse("2020-02-02", inputFormat)),
            new Visit(new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new QuarantineStatus("false"),
                        new InfectionStatus("true"), Index.fromOneBased(5), getTagSet("classmates")),
                    new Location(new Name("Plaza Singapura"),
                        new Address("68 Orchard Rd, Singapore 238839"), Index.fromOneBased(5)),
                    LocalDate.parse("2020-02-02", inputFormat)),
            new Visit(new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new QuarantineStatus("false"),
                        new InfectionStatus("true"), Index.fromOneBased(6), getTagSet("colleagues")),
                    new Location(new Name("Singapore Zoo"),
                        new Address("80 Mandai Lake Rd, 729826"), Index.fromOneBased(6)),
                    LocalDate.parse("2020-02-02", inputFormat))
        };
    }

    public static ReadOnlyPersonBook getSampleAddressBook() {
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
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
