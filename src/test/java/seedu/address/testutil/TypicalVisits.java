package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
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
    public static final Visit SEVENTH_VISIT = new VisitBuilder().withPersonId("5")
            .withLocationId("6").withDate("2020-09-13").build();
    public static final Visit EIGHTH_VISIT = new VisitBuilder().withPersonId("4")
            .withLocationId("1").withDate("2020-09-13").build();

    // Manually added
    public static final Visit NINTH_VISIT = new VisitBuilder().withPersonId("6")
            .withLocationId("2").withDate("2020-09-14").build();
    public static final Visit TENTH_VISIT = new VisitBuilder().withPersonId("4")
            .withLocationId("5").withDate("2020-09-14").build();
    public static final Visit ELEVENTH_VISIT = new VisitBuilder().withPersonId("6")
            .withLocationId("3").withDate("2020-09-14").build();
    public static final Visit TWELFTH_VISIT = new VisitBuilder().withPersonId("5")
            .withLocationId("2").withDate("2020-09-14").build();
    public static final Visit THIRTEENTH_VISIT = new VisitBuilder().withPersonId("4")
            .withLocationId("2").withDate("2020-09-14").build();
    public static final Visit FOURTEENTH_VISIT = new VisitBuilder().withPersonId("5")
            .withLocationId("3").withDate("2020-09-14").build();

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

    /**
     * Returns a {@code VisitBook} with all the all visits.
     */
    public static VisitBook getWholeVisitBook() {
        VisitBook vb = new VisitBook();
        for (Visit visit : getAllVisits()) {
            vb.addVisit(visit);
        }
        return vb;
    }

    /**
     * Returns a {@code VisitBook} with less than 60% visits are infected visits.
     */
    public static VisitBook getLessThanSixtyPercentVisitBook() {
        VisitBook vb = new VisitBook();
        for (Visit visit : getLessThanSixtyPercentInfectedVisits()) {
            vb.addVisit(visit);
        }
        return vb;
    }

    /**
     * Returns a {@code VisitBook} with more than 60% visits are infected visits.
     */
    public static VisitBook getMoreThanSixtyPercentVisitBook() {
        VisitBook vb = new VisitBook();
        for (Visit visit : getMoreThanSixtyPercentInfectedVisits()) {
            vb.addVisit(visit);
        }
        return vb;
    }

    public static List<Visit> getTypicalVisits() {
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, THIRD_VISIT, FOURTH_VISIT,
                FIFTH_VISIT, SIXTH_VISIT, SEVENTH_VISIT, EIGHTH_VISIT));
    }

    public static List<Index> getLocationIdsOfTypicalVisits() {
        return new ArrayList<>(Arrays.asList(INDEX_SECOND, INDEX_THIRD, INDEX_FIRST, INDEX_FOURTH,
                INDEX_FIFTH, INDEX_SECOND, INDEX_SIXTH, INDEX_FIRST));
    }

    public static List<Visit> getAllVisits() {
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, THIRD_VISIT, FOURTH_VISIT,
                FIFTH_VISIT, SIXTH_VISIT, SEVENTH_VISIT, EIGHTH_VISIT, NINTH_VISIT, TENTH_VISIT,
                ELEVENTH_VISIT, TWELFTH_VISIT, THIRTEENTH_VISIT, FOURTEENTH_VISIT));
    }

    /**
     * Returns a list of {@code Visit} with 4 visits to location with id 2, 3 visits to location with id 3,
     * 2 visits to location with id 5 and 1 visit to location with id 4.
     */
    public static List<Visit> getVisitsForTest() {
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, FOURTH_VISIT, FIFTH_VISIT,
                SIXTH_VISIT, THIRTEENTH_VISIT, NINTH_VISIT, TENTH_VISIT, ELEVENTH_VISIT, FOURTEENTH_VISIT));
    }

    /**
     * Returns a list of unique {@code LocationId} in the list of {@code Visit} provided
     * by {@code getVisitsForTest}
     */
    public static List<Index> getLocationsIdsFromVisitsForTest() {
        return new ArrayList<>(Arrays.asList(INDEX_SECOND, INDEX_THIRD, INDEX_FIFTH, INDEX_FOURTH));
    }

    /**
     * Returns a list of {@code Visit} where less than 60% of the Visits in this list consist of Person
     * that is infected
     */
    public static List<Visit> getLessThanSixtyPercentInfectedVisits() {
        // FIRST_VISIT ,SECOND_VISIT and THIRD_VISIT are non-infected Visits. The remaining Visits are
        // infected Visits.
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, THIRD_VISIT, NINTH_VISIT,
                TENTH_VISIT, THIRTEENTH_VISIT, FOURTEENTH_VISIT));
    }

    /**
     * Returns a list of {@code Visit} where more than 60% of the Visits in this list consist of Person
     * that is infected
     */
    public static List<Visit> getMoreThanSixtyPercentInfectedVisits() {
        // FIRST_VISIT and SECOND_VISIT are non-infected Visits. The remaining Visits are infected Visits.
        return new ArrayList<>(Arrays.asList(FIRST_VISIT, SECOND_VISIT, THIRTEENTH_VISIT, NINTH_VISIT,
                TENTH_VISIT, ELEVENTH_VISIT, TWELFTH_VISIT, FOURTEENTH_VISIT));
    }
}
