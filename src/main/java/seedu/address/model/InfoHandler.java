package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Collectively uses data from the {@code Model} to produce statistics
 * The original data should not be modified in this class.
 */
public class InfoHandler {
    private static final double HUNDRED_IN_DOUBLE = 100.0;
    private static final String COUNT_FORMAT = "%d";
    private static final String PERCENTAGE_FORMAT = "%.2f";

    // Headers for summary display
    private static final String TOTAL_PEOPLE_HEADER = "Total number of people:";
    private static final String TOTAL_LOCATIONS_HEADER = "Total number of locations:";
    private static final String TOTAL_VISITS_HEADER = "Total number of visits:";
    private static final String TOTAL_INFECTED_HEADER = "Total number of infected people:";
    private static final String TOTAL_QUARANTINED_HEADER = "Total number of quarantined people:";
    private static final String PERCENTAGE_INFECTED = "Percentage of people infected:";
    private static final String PERCENTAGE_QUARANTINED = "Percentage of people quarantined:";

    private final Model model;

    /**
     * Initializes an InfoHandler with the given Model
     */
    public InfoHandler(Model model) {
        requireAllNonNull(model);

        this.model = model;
    }

    //=========== List Accessors ==============================================================

    public ObservableList<Person> getPersonList() {
        return model.getFilteredPersonList();
    }

    public ObservableList<Location> getLocationList() {
        return model.getFilteredLocationList();
    }

    public ObservableList<Visit> getVisitList() {
        return model.getFilteredVisitList();
    }

    /**
     * Filters the people list in the model with the given predicate.
     * In any operation, this method should be called twice: once to apply the
     * predicate, and another time to remove the predicate after the operation
     * is completed.
     *
     * @param predicate The predicate to filter the list against.
     */
    public void updateModelPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        model.updateFilteredPersonList(predicate);
    }

    //=========== Get total counts ==================================================================

    public int getTotalPeople() {
        return getPersonList().size();
    }

    public int getTotalLocations() {
        return getLocationList().size();
    }

    public int getTotalVisits() {
        return getVisitList().size();
    }

    /**
     * Retrieves the total amount of people who are currently infected.
     *
     * @return the number of people infected currently
     */
    public int getTotalInfected() {
        updateModelPersonList(PREDICATE_SHOW_ALL_INFECTED);
        int totalInfected = getPersonList().size();
        updateModelPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return totalInfected;
    }

    /**
     * Retrieves the total amount of people who are currently quarantined.
     *
     * @return the number of people quarantined currently.
     */
    public int getTotalQuarantined() {
        updateModelPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        int totalQuarantined = getPersonList().size();
        updateModelPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return totalQuarantined;
    }

    //=========== Get percentages ==================================================================

    /**
     * Gets the percentage of people who are currently infected.
     *
     * @return the percentage of infected people.
     */
    public double getInfectedOverPeople() {
        int totalInfected = getTotalInfected();
        int totalPeople = getTotalPeople();
        return HUNDRED_IN_DOUBLE * totalInfected / totalPeople;
    }

    /**
     * Gets the percentage of people who are currently quarantined.
     *
     * @return the percentage of quarantined people.
     */
    public double getQuarantinedOverPeople() {
        int totalQuarantined = getTotalQuarantined();
        int totalPeople = getTotalPeople();
        return HUNDRED_IN_DOUBLE * totalQuarantined / totalPeople;
    }

    //============ Summary ========================================================================

    /**
     * Gets a summary of the current statistics.
     */
    public String getStatistics() {
        return String.format(
            TOTAL_PEOPLE_HEADER + " " + COUNT_FORMAT + "\n"
            + TOTAL_LOCATIONS_HEADER + " " + COUNT_FORMAT + "\n"
            + TOTAL_VISITS_HEADER + " " + COUNT_FORMAT + "\n"
            + TOTAL_INFECTED_HEADER + " " + COUNT_FORMAT + "\n"
            + TOTAL_QUARANTINED_HEADER + " " + COUNT_FORMAT + "\n"
            + PERCENTAGE_INFECTED + " " + PERCENTAGE_FORMAT + "\n"
            + PERCENTAGE_QUARANTINED + " " + PERCENTAGE_FORMAT + "\n",
            getTotalPeople(), getTotalLocations(), getTotalVisits(),
            getTotalInfected(), getTotalQuarantined(), getInfectedOverPeople(),
            getQuarantinedOverPeople()
        );
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof InfoHandler)) {
            return false;
        }

        // state check
        InfoHandler other = (InfoHandler) obj;
        return model.equals(other.model);
    }
}
