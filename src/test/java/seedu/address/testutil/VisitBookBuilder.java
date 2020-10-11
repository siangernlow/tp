package seedu.address.testutil;

import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;

/**
 * A utility class to help with building Visitbook objects.
 * Example usage: <br>
 *     {@code VisitBook lb = new VisitBookBuilder().withPersonId("1").build();}
 */
public class VisitBookBuilder {

    private VisitBook visitBook;

    public VisitBookBuilder() {
        visitBook = new VisitBook();
    }

    public VisitBookBuilder(VisitBook visitBook) {
        this.visitBook = visitBook;
    }

    /**
     * Adds a new {@code Visit} to the {@code VisitBook} that we are building.
     */
    public VisitBookBuilder withVisit(Visit visit) {
        visitBook.addVisit(visit);
        return this;
    }

    public VisitBook build() {
        return visitBook;
    }
}
