package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.LocationBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLocationBook;
import seedu.address.model.ReadOnlyVisitBook;
import seedu.address.model.VisitBook;
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

/**
 * Contains utility methods for populating {@code AddressBook} and {@code LocationBook} with sample data.
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
                    new Address("1 HarbourFront Walk, Singapore 098585"), Index.fromOneBased(2))
        };
    }

    public static Visit[] getSampleVisits() {
        Index samplePersonId = Index.fromOneBased(Integer.parseInt("1"));
        Index sampleLocationId = Index.fromOneBased(Integer.parseInt("2"));
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sampleDate = LocalDate.parse("2020-02-02", inputFormat);
        return new Visit[] { new Visit(samplePersonId, sampleLocationId, sampleDate) };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
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
