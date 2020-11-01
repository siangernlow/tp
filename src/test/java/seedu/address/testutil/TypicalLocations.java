package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;

/**
 * A utility class containing a list of {@code Location} objects to be used in tests.
 */
public class TypicalLocations {
    public static final Location ALICE_LOCATION = new LocationBuilder().withName("Alice Pauline House")
            .withAddress("123, Jurong West Ave 6, #08-111").withId("L1234").build();
    public static final Location BENSON_LOCATION = new LocationBuilder().withName("Benson Meier House")
            .withAddress("311, Clementi Ave 2, #02-25").withId("L2345").build();
    public static final Location CARL_LOCATION = new LocationBuilder().withName("Carl Kurz House")
            .withAddress("wall street").withId("L3456").build();
    public static final Location DANIEL_LOCATION = new LocationBuilder().withName("Daniel Meier House")
            .withAddress("10th street").withId("L4567").build();
    public static final Location ELLE_LOCATION = new LocationBuilder().withName("Elle Meyer House")
            .withAddress("michegan ave").withId("L5678").build();
    public static final Location FIONA_LOCATION = new LocationBuilder().withName("Fiona Kunz House")
            .withAddress("little tokyo").withId("L6789").build();
    public static final Location GEORGE_LOCATION = new LocationBuilder().withName("George Best House")
            .withAddress("4th street").withId("L78910").build();

    // Manually added
    public static final Location HOON_LOCATION = new LocationBuilder().withName("Hoon Meier House")
            .withAddress("little india").withId("L891011").build();
    public static final Location IDA_LOCATION = new LocationBuilder().withName("Ida Mueller House")
            .withAddress("chicago ave").withId("L9101112").build();

    // Manually added - Location's details found in {@code CommandTestUtil}
    public static final Location AMY_LOCATION = new LocationBuilder().withName(VALID_NAME_AMY)
            .withAddress(VALID_ADDRESS_AMY).withId(VALID_ID_AMY_LOCATION).build();
    public static final Location BOB_LOCATION = new LocationBuilder().withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withId(VALID_ID_BOB_LOCATION).build();

    public static final Id ID_NOT_IN_TYPICAL_LOCATIONS = new Id("L11111");

    private TypicalLocations() {} // prevents instantiation

    /**
     * Returns a {@code LocationBook} with all the typical locations.
     */
    public static LocationBook getTypicalLocationBook() {
        LocationBook lb = new LocationBook();
        for (Location location : getTypicalLocations()) {
            lb.addLocation(location);
        }
        return lb;
    }

    public static LocationBook getUnorderedTypicalLocationBook() {
        LocationBook lb = new LocationBook();
        for (Location location : getUnorderedTypicalLocations()) {
            lb.addLocation(location);
        }
        return lb;
    }

    public static List<Location> getTypicalLocations() {
        return new ArrayList<>(Arrays.asList(ALICE_LOCATION, BENSON_LOCATION, CARL_LOCATION, DANIEL_LOCATION,
                ELLE_LOCATION, FIONA_LOCATION, GEORGE_LOCATION));
    }

    /**
     * @return A shorter list of locations meant for simple data entry.
     */
    public static List<Location> getShortenedTypicalLocations() {
        return new ArrayList<>(Arrays.asList(ALICE_LOCATION, BENSON_LOCATION, CARL_LOCATION));
    }

    public static List<Location> getUnorderedTypicalLocations() {
        return new ArrayList<>(Arrays.asList(IDA_LOCATION, BENSON_LOCATION, CARL_LOCATION, DANIEL_LOCATION,
                ELLE_LOCATION, FIONA_LOCATION, GEORGE_LOCATION));
    }
}
