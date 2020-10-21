package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.attribute.Id;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.ReadOnlyVisitBook;
import seedu.address.model.visit.Visit;
import seedu.address.model.visit.VisitBook;

/**
 * Collectively uses data from the {@code Model} to produce statistics
 * The original data should not be modified in this class.
 */
public class InfoHandler {
    private static final double HUNDRED_IN_DOUBLE = 100.0;
    // Used as a flag to detect invalid ratios, such as dividing by zero.
    private static final double INVALID_RATIO = -1.0;
    private static final String COUNT_FORMAT = "%d";
    private static final String PERCENTAGE_FORMAT = "%.2f";
    private static final String INVALID_PERCENTAGE_STRING = "-%%";
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
        return model.getSortedPersonList();
    }

    public ObservableList<Location> getLocationList() {
        return model.getSortedLocationList();
    }

    public ObservableList<Visit> getVisitList() {
        return model.getSortedVisitList();
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
        // Check if dividing by zero
        if (totalPeople == 0) {
            return INVALID_RATIO;
        }
        return (double) totalInfected / totalPeople;
    }

    /**
     * Gets the percentage of people who are currently quarantined.
     *
     * @return the percentage of quarantined people.
     */
    public double getQuarantinedOverPeople() {
        int totalQuarantined = getTotalQuarantined();
        int totalPeople = getTotalPeople();
        // Check if dividing by zero
        if (totalPeople == 0) {
            return INVALID_RATIO;
        }
        return (double) totalQuarantined / totalPeople;
    }

    //============ Util ===========================================================================

    /**
     * Converts the given ratio into a percentage String.
     * Returns {@code INVALID_PERCENTAGE} if given ratio is -1.0
     * @param ratio
     * @return
     */
    public String getRatioAsPercentage(double ratio) {
        if (ratio == INVALID_RATIO) {
            return INVALID_PERCENTAGE_STRING;
        }
        double percentage = HUNDRED_IN_DOUBLE * ratio;
        return String.format(PERCENTAGE_FORMAT + "%%", percentage);
    }

    /**
     * Generates a list of visits that a person with a specified Id is associated with.
     * @param personId Id of the person.
     * @return List of visits that the person is associated with.
     */
    public VisitBook generateVisitsByPerson(Id personId) {
        ReadOnlyVisitBook tempVisitBook = model.getVisitBook();
        VisitBook visitsByPerson = new VisitBook();
        for (int i = 0; i < tempVisitBook.getVisitList().size(); i++) {
            if (tempVisitBook.getVisitList().get(i).getPerson().getId().equals(personId)) {
                visitsByPerson.addVisit(tempVisitBook.getVisitList().get(i));
            }
        }
        return visitsByPerson;
    }

    /**
     * Generates a list of location Ids that are associated with the visits in the given visit book.
     * @param visitBook List of visits.
     * @return List of location ids that are associated with the visits.
     */
    public List<Id> generateLocationIdsByVisitBook(VisitBook visitBook) {
        List<Id> locationIds = new ArrayList<>();
        for (int i = 0; i < visitBook.getVisitList().size(); i++) {
            locationIds.add(visitBook.getVisitList().get(i).getLocation().getId());
        }
        return locationIds;
    }

    /**
     * Generates a list of visits that are associated with the given list of location Ids.
     * @param locationIds List of location Ids.
     * @return List of visits that are associated with the location Ids.
     */
    public VisitBook generateVisitsByLocationIds(List<Id> locationIds) {
        ReadOnlyVisitBook tempVisitBook = model.getVisitBook();
        VisitBook associatedVisits = new VisitBook();
        for (Id locationId : locationIds) {
            for (int i = 0; i < tempVisitBook.getVisitList().size(); i++) {
                Visit visit = tempVisitBook.getVisitList().get(i);
                if (visit.getLocation().getId().equals(locationId)) {
                    associatedVisits.addVisit(visit);
                }
            }
        }
        return associatedVisits;
    }

    /**
     * Generates a list of location Ids that are associated with the visits in the given visit book. As this is an
     * intermediate method, it's purpose is to find other person ids associated visits that are associated with an
     * infected person. Hence, it does not include the original infected person.
     * @param visitBook List of visits.
     * @param personId Original infected person visit book is associated with.
     * @return List of location ids that are associated with the visits.
     */
    public List<Id> generatePersonIdsByVisitBook(VisitBook visitBook, Id personId) {
        List<Id> personIds = new ArrayList<>();
        for (Visit visit : visitBook.getVisitList()) {

            if (visit.getPerson().getId().equals(personId)) {
                continue;
            }
            personIds.add(visit.getPerson().getId());

            if (visit.getPerson().getId().equals(personId)) {
                continue;
            }
            personIds.add(visit.getPerson().getId());
        }
        return personIds;
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
            + PERCENTAGE_INFECTED + " %s" + "\n"
            + PERCENTAGE_QUARANTINED + " %s" + "\n",
            getTotalPeople(), getTotalLocations(), getTotalVisits(),
            getTotalInfected(), getTotalQuarantined(), getRatioAsPercentage(getInfectedOverPeople()),
            getRatioAsPercentage(getQuarantinedOverPeople())
        );
    }

    //============ High Risk Locations Related ====================================================

    /**
     * Returns a HashSet of ids of persons as given in the argument.
     */
    public static HashSet<Id> getIdHashSetFromPersonsList(List<Person> persons) {
        HashSet<Id> ids = new HashSet<>();
        for (Person person : persons) {
            ids.add(person.getId());
        }
        return ids;
    }

    /**
     * Returns an ArrayList of location ids of visits as given in the argument.
     */
    public static ArrayList<Id> getLocationIdsFromInfectedVisitList(List<Visit> visits) {
        HashMap<Id, Integer> infectedLocations = new HashMap<>();
        for (Visit visit : visits) {
            Id id = visit.getLocation().getId();
            if (infectedLocations.containsKey(id)) {
                infectedLocations.put(id, infectedLocations.get(id) + 1);
            } else {
                infectedLocations.put(id, 1);
            }
        }
        HashMap<Id, Integer> sortedInfectedLocations = sortByValues(infectedLocations);
        return new ArrayList<>(sortedInfectedLocations.keySet());
    }

    /**
     * Sort HashMap by value. Only Used by method getLocationIdsFromInfectedVisitList.
     */
    public static HashMap<Id, Integer> sortByValues(HashMap<Id, Integer> map) {
        List<Map.Entry<Id, Integer>> list = new LinkedList<>(map.entrySet());
        // Sort in decreasing order
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        HashMap<Id, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Id, Integer> entry : list) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Determines the number of high risk locations given all infected locations and total locations.
     */
    public static int getNumberOfHighRiskLocations(int numberOfInfectedLocations, int numberOfTotalLocations) {
        /*
        if number of infected locations is more than 60% of total locations,number of infected
        locations considered to be high risk will be 40% of total locations. Otherwise, all
        infected locations are considered to be high risk. This criterion needs further discussion
        and is subjected to change.
        */
        if (numberOfInfectedLocations > numberOfTotalLocations * 0.6) {
            return (int) (numberOfTotalLocations * 0.4);
        } else {
            return numberOfInfectedLocations;
        }
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
