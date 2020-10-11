package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.location.Location;
import seedu.address.model.location.LocationBook;
import seedu.address.model.location.ReadOnlyLocationBook;

/**
 * An Immutable LocationBook that is serializable to JSON format.
 */
@JsonRootName(value = "locationbook")
class JsonSerializableLocationBook {

    public static final String MESSAGE_DUPLICATE_LOCATION = "Locations list contains duplicate location(s).";

    private final List<JsonAdaptedLocation> locations = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLocationBook} with the given locations.
     */
    @JsonCreator
    public JsonSerializableLocationBook(@JsonProperty("locations") List<JsonAdaptedLocation> locations) {
        this.locations.addAll(locations);
    }

    /**
     * Converts a given {@code ReadOnlyLocationBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLocationBook}.
     */
    public JsonSerializableLocationBook(ReadOnlyLocationBook source) {
        locations.addAll(source.getLocationList().stream().map(JsonAdaptedLocation::new).collect(Collectors.toList()));
    }

    /**
     * Converts this location book into the model's {@code LocationBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LocationBook toModelType() throws IllegalValueException {
        LocationBook locationBook = new LocationBook();
        for (JsonAdaptedLocation jsonAdaptedLocation : locations) {
            Location location = jsonAdaptedLocation.toModelType();
            if (locationBook.hasLocation(location)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOCATION);
            }
            locationBook.addLocation(location);
        }
        return locationBook;
    }
}
