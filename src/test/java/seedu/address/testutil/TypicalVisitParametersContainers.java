package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.DataGenerator;
import seedu.address.model.attribute.Id;
import seedu.address.model.visit.Visit;

/**
 * A utility class containing a list of {@code VisitParametersContainer} objects to be used in tests.
 */
public class TypicalVisitParametersContainers {
    public static final DataGenerator.VisitParametersContainer FIRST_VPC =
            new VpcBuilder().withPersonIndex(new Id("S1")).withLocationIndex(new Id("L1")).build();
    public static final DataGenerator.VisitParametersContainer SECOND_VPC =
            new VpcBuilder().withPersonIndex(new Id("S2")).withLocationIndex(new Id("L2")).build();
    public static final DataGenerator.VisitParametersContainer THIRD_VPC =
            new VpcBuilder().withPersonIndex(new Id("S3")).withLocationIndex(new Id("L3")).build();
    public static final DataGenerator.VisitParametersContainer FOURTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S4")).withLocationIndex(new Id("L4")).build();
    public static final DataGenerator.VisitParametersContainer FIFTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S5")).withLocationIndex(new Id("L5")).build();
    public static final DataGenerator.VisitParametersContainer SIXTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S6")).withLocationIndex(new Id("L6")).build();
    public static final DataGenerator.VisitParametersContainer SEVENTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S7")).withLocationIndex(new Id("L7")).build();
    public static final DataGenerator.VisitParametersContainer EIGHTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S8")).withLocationIndex(new Id("L8")).build();
    public static final DataGenerator.VisitParametersContainer NINTH_VPC =
            new VpcBuilder().withPersonIndex(new Id("S9")).withLocationIndex(new Id("L9")).build();

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
        Id personId = visit.getPerson().getId();
        Id locationId = visit.getLocation().getId();
        LocalDate date = visit.getDate();
        return new DataGenerator.VisitParametersContainer(personId, locationId, date);
    }

    public static class VpcBuilder {
        public static final Id DEFAULT_PERSON_INDEX = new Id("S1");
        public static final Id DEFAULT_LOCATION_INDEX = new Id("L1");
        private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public static final LocalDate DEFAULT_DATE = LocalDate.parse("2020-10-12", inputFormat);

        private Id personIndex;
        private Id locationIndex;
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
        public VpcBuilder withPersonIndex(Id personIndex) {
            this.personIndex = personIndex;
            return this;
        }

        /**
         * Sets the {@code locationIndex} of the {@code VpcBuilder} that we are building.
         * @param locationIndex The updated index.
         * @return The new {@code VpcBuilder}.
         */
        public VpcBuilder withLocationIndex(Id locationIndex) {
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
