package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;

/**
 * An Immutable VisitBook that is serializable to JSON format.
 */
@JsonRootName(value = "visitbook")
class JsonSerializableVisitBook {

    public static final String MESSAGE_DUPLICATE_VISIT = "Visits list contains duplicate visit(s).";

    private final List<JsonAdaptedVisit> visits = new ArrayList<JsonAdaptedVisit>();

    /**
     * Constructs a {@code JsonSerializableVisitBook} with the given visits.
     */
    @JsonCreator
    public JsonSerializableVisitBook(@JsonProperty("visits") List<JsonAdaptedVisit> visits) {
        this.visits.addAll(visits);
    }

    /**
     * Converts a given {@code ReadOnlyVisitBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableVisitBook}.
     */
    public JsonSerializableVisitBook(ReadOnlyVisitBook source) {

        visits.addAll(source.getVisitList().stream().map(JsonAdaptedVisit::new).collect(Collectors.toList()));
    }

    /**
     * Converts this visit book into the model's {@code VisitBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public VisitBook toModelType() throws IllegalValueException {
        VisitBook visitBook = new VisitBook();
        for (JsonAdaptedVisit jsonAdaptedVisit : visits) {
            Visit visit = jsonAdaptedVisit.toModelType();
            if (visitBook.hasVisit(visit)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VISIT);
            }
            visitBook.addVisit(visit);
        }
        return visitBook;
    }
}
