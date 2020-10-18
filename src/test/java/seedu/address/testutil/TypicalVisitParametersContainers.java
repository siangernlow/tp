package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_NINTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_TENTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.DataGenerator;
import seedu.address.model.visit.Visit;

/**
 * A utility class containing a list of {@code VisitParametersContainer} objects to be used in tests.
 */
public class TypicalVisitParametersContainers {
    public static final DataGenerator.VisitParametersContainer FIRST_VPC =
            new VpcBuilder().withPersonIndex(INDEX_TENTH).withLocationIndex(INDEX_FIFTH).build();
    public static final DataGenerator.VisitParametersContainer SECOND_VPC =
            new VpcBuilder().withPersonIndex(INDEX_SIXTH).withLocationIndex(INDEX_SEVENTH).build();
    public static final DataGenerator.VisitParametersContainer THIRD_VPC =
            new VpcBuilder().withPersonIndex(INDEX_FIRST).withLocationIndex(INDEX_EIGHTH).build();
    public static final DataGenerator.VisitParametersContainer FOURTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_SEVENTH).withLocationIndex(INDEX_TENTH).build();
    public static final DataGenerator.VisitParametersContainer FIFTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_NINTH).withLocationIndex(INDEX_TENTH).build();
    public static final DataGenerator.VisitParametersContainer SIXTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_FIRST).withLocationIndex(INDEX_THIRD).build();
    public static final DataGenerator.VisitParametersContainer SEVENTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_TENTH).withLocationIndex(INDEX_SEVENTH).build();
    public static final DataGenerator.VisitParametersContainer EIGHTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_NINTH).withLocationIndex(INDEX_FIFTH).build();
    public static final DataGenerator.VisitParametersContainer NINTH_VPC =
            new VpcBuilder().withPersonIndex(INDEX_SECOND).withLocationIndex(INDEX_THIRD).build();

    private TypicalVisitParametersContainers() {} // prevents instantiation

    public static List<DataGenerator.VisitParametersContainer> getTypicalVpc() {
        return new ArrayList<>(Arrays.asList(FIRST_VPC, SECOND_VPC, THIRD_VPC, FOURTH_VPC, FIFTH_VPC, SIXTH_VPC,
                SEVENTH_VPC, EIGHTH_VPC, NINTH_VPC));
    }

    public static List<DataGenerator.VisitParametersContainer> getVisitsAsVpc(List<Visit> visits) {
        assert visits.size() > 0;

        List<DataGenerator.VisitParametersContainer> visitParametersContainerList = new ArrayList<>();
        for (Visit visit : visits) {
            visitParametersContainerList.add(getVisitAsVpc(visit));
        }
        return visitParametersContainerList;
    }

    private static DataGenerator.VisitParametersContainer getVisitAsVpc(Visit visit) {
        Index personIndex = visit.getPerson().getId();
        Index locationIndex = visit.getLocation().getId();
        LocalDate date = visit.getDate();
        return new DataGenerator.VisitParametersContainer(personIndex, locationIndex, date);
    }

    public static class VpcBuilder {
        public static final Index DEFAULT_PERSON_INDEX = INDEX_FIRST;
        public static final Index DEFAULT_LOCATION_INDEX = INDEX_FIRST;
        private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public static final LocalDate DEFAULT_DATE = LocalDate.parse("2020-10-12", inputFormat);

        private Index personIndex;
        private Index locationIndex;
        private LocalDate date;

        /**
         * Creates a default VpcBuilder using the default values.
         */
        public VpcBuilder() {
            this.personIndex = DEFAULT_PERSON_INDEX;
            this.locationIndex = DEFAULT_LOCATION_INDEX;
            this.date = DEFAULT_DATE;
        }

        /**
        * Sets the {@code personIndex} of the {@code VpcBuilder} that we are building.
        * @param personIndex The updated index.
        * @return The new {@code VpcBuilder}.
        */
        public VpcBuilder withPersonIndex(Index personIndex) {
            this.personIndex = personIndex;
            return this;
        }

        /**
         * Sets the {@code locationIndex} of the {@code VpcBuilder} that we are building.
         * @param locationIndex The updated index.
         * @return The new {@code VpcBuilder}.
         */
        public VpcBuilder withLocationIndex(Index locationIndex) {
            this.locationIndex = locationIndex;
            return this;
        }

        /**
         * Sets the {@code date} of the {@code VpcBuilder} that we are building.
         * @param date The updated date.
         * @return The new {@code VpcBuilder}.
         */
        public VpcBuilder withDate(String date) {
            this.date = LocalDate.parse(date, inputFormat);
            return this;
        }

        public DataGenerator.VisitParametersContainer build() {
            return new DataGenerator.VisitParametersContainer(personIndex, locationIndex, date);
        }
    }
}
