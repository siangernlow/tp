package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.VisitBook;
import seedu.address.model.visit.Visit;

/**
 * A utility class containing a list of {@code Visit} objects to be used in tests.
 */
public class TypicalVisits {
    public static final Visit FIRST_VISIT = new VisitBuilder().withPersonId("1")
            .withLocationId("2").withDate("2020-09-12").build();
    public static final Visit SECOND_VISIT = new VisitBuilder().withPersonId("2")
            .withLocationId("3").withDate("2020-09-12").build();
    public static final Visit THIRD_VISIT = new VisitBuilder().withPersonId("3")
            .withLocationId("1").withDate("2020-09-12").build();
    public static final Visit FOURTH_VISIT = new VisitBuilder().withPersonId("1")
            .withLocationId("4").withDate("2020-09-12").build();
    public static final Visit FIFTH_VISIT = new VisitBuilder().withPersonId("1")
            .withLocationId("5").withDate("2020-09-13").build();
    public static final Visit SIXTH_VISIT = new VisitBuilder().withPersonId("1")
            .withLocationId("2").withDate("2020-09-13").build();
    public static final Visit SEVENTH_VISIT = new VisitBuilder().withPersonId("4")
            .withLocationId("6").withDate("2020-09-13").build();

    private TypicalVisits() {} // prevents instantiation

    /**
     * Returns a {@code VisitBook} with all the typical visits.
     */
    public static VisitBook getTypicalVisitBook() {
        VisitBook vb = new VisitBook();
        for (Visit visit : getTypicalVisits()) {
            vb.addVisit(visit);
        }
        return vb;
    }

    public static List<Visit> getTypicalVisits() {
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, THIRD_VISIT,
                FOURTH_VISIT, FIFTH_VISIT, SIXTH_VISIT, SEVENTH_VISIT));
    }
}
