package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFECTION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUARANTINE_STATUS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_INFECTED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_LOCATIONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_QUARANTINED;
import static seedu.address.model.ModelPredicate.PREDICATE_SHOW_ALL_VISITS;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    // Used as a flag to detect invalid ratios, such as dividing by zero.
    public static final double INVALID_RATIO = -1.0;
    public static final String INVALID_PERCENTAGE_STRING = "-";

    private static final double HUNDRED_IN_DOUBLE = 100.0;
    private static final String COUNT_FORMAT = "%d";
    private static final String PERCENTAGE_FORMAT = "%.2f";
    private static final int NUM_DAYS_IN_TWO_WEEKS = 14;

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
        Optional<Predicate<? super Person>> lastUsedPersonPredicate = model.getPersonPredicate();
        updateModelPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int totalPeople = getPersonList().size();
        model.updateFilteredPersonList(lastUsedPersonPredicate.orElse(PREDICATE_SHOW_ALL_PERSONS));

        return totalPeople;
    }

    public int getTotalLocations() {
        Optional<Predicate<? super Location>> lastUsedLocationPredicate = model.getLocationPredicate();
        model.updateFilteredLocationList(PREDICATE_SHOW_ALL_LOCATIONS);
        int totalLocations = getLocationList().size();
        model.updateFilteredLocationList(lastUsedLocationPredicate.orElse(PREDICATE_SHOW_ALL_LOCATIONS));

        return totalLocations;
    }

    public int getTotalVisits() {
        Optional<Predicate<? super Visit>> lastUsedVisitPredicate = model.getVisitPredicate();
        model.updateFilteredVisitList(PREDICATE_SHOW_ALL_VISITS);
        int totalVisits = getVisitList().size();
        model.updateFilteredVisitList(lastUsedVisitPredicate.orElse(PREDICATE_SHOW_ALL_VISITS));

        return totalVisits;
    }

    /**
     * Retrieves the total amount of people who are currently infected.
     *
     * @return the number of people infected currently
     */
    public int getTotalInfected() {
        Optional<Predicate<? super Person>> lastUsedPersonPredicate = model.getPersonPredicate();
        updateModelPersonList(PREDICATE_SHOW_ALL_INFECTED);
        int totalInfected = getPersonList().size();
        model.updateFilteredPersonList(lastUsedPersonPredicate.orElse(PREDICATE_SHOW_ALL_PERSONS));

        return totalInfected;
    }

    /**
     * Retrieves the total amount of people who are currently quarantined.
     *
     * @return the number of people quarantined currently.
     */
    public int getTotalQuarantined() {
        Optional<Predicate<? super Person>> lastUsedPersonPredicate = model.getPersonPredicate();
        updateModelPersonList(PREDICATE_SHOW_ALL_QUARANTINED);
        int totalQuarantined = getPersonList().size();
        model.updateFilteredPersonList(lastUsedPersonPredicate.orElse(PREDICATE_SHOW_ALL_PERSONS));

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
     * @param ratio given ratio
     * @return percentage representation of ratio
     */
    public String getRatioAsPercentage(double ratio) {
        if (ratio == INVALID_RATIO || ratio > 1 || ratio < 0) {
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
            Visit visit = tempVisitBook.getVisitList().get(i);
            if (isVisitByPersonId(visit, personId)) {
                visitsByPerson.addVisit(tempVisitBook.getVisitList().get(i));
            }
        }
        return visitsByPerson;
    }

    /**
     * Checks if a visit is valid. It is considered valid if it meets 2 criteria.
     * 1. It is by a person with a specified id.
     * 2. It must also be within 2 weeks of the current date.
     * Note: 2 weeks refers to a 14 day period that is inclusive of the current date, but exclusive of the date 14
     * days ago.
     * @param visit Visit being checked.
     * @param personId Id of the person.
     * @return A boolean of whether a visit is by a person with a specified id.
     */
    private boolean isVisitByPersonId(Visit visit, Id personId) {
        boolean isPersonValid = visit.getPerson().getId().equals(personId);
        LocalDate currentDate = LocalDate.now();
        LocalDate visitDate = visit.getDate();
        boolean isDateValid = (currentDate.isAfter(visitDate) || currentDate.isEqual(visitDate))
                && ChronoUnit.DAYS.between(visitDate, currentDate) < NUM_DAYS_IN_TWO_WEEKS;
        return isPersonValid && isDateValid;
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
     * Generates a list of visits that happened on the same day as the given visits. This is an intermediate method for
     * the generatePeopleCommand. After the visits of an infected person have been found, this method finds all other
     * visits by other people that happened on the same day as the infected person's visits. A visit is considered
     * to have happened on the same day if the location and date of visit are the same.
     * @param visitBook Visits provided.
     * @return List of visits that are associated with the given visits.
     */
    public VisitBook generateOtherVisitsThatHappenedOnSameDay(VisitBook visitBook) {
        ReadOnlyVisitBook tempVisitBook = model.getVisitBook();
        VisitBook otherVisits = new VisitBook();
        for (Visit givenVisit : visitBook.getVisitList()) {
            for (int i = 0; i < tempVisitBook.getVisitList().size(); i++) {
                Visit visit = tempVisitBook.getVisitList().get(i);
                if (isAddableVisitThatHappenedOnSameDay(visit, givenVisit, otherVisits)) {
                    otherVisits.addVisit(visit);
                }
            }
        }
        return otherVisits;
    }

    /**
     * Checks if another visit can be added to the list of visits that is associated with the a specified visit.
     * The visit is considered addable if it meets 3 criteria.
     * 1. The location of the visit is the same as the specified visit's.
     * 2. The date of the visit is the same as the specified visit's.
     * 3. The visit is not already included in the list of visits (This is to prevent duplicates).
     * This method is only used inside generateAssociatedVisits and it was to prevent conditional statements from
     * becoming too deeply nested.
     * @param visit Visit to be checked.
     * @param givenVisit Visit the above parameter is being checked against.
     * @param associatedVisits List of visits already affected by the given visit.
     * @return A boolean of whether the visit can be added or not.
     */
    private boolean isAddableVisitThatHappenedOnSameDay(Visit visit, Visit givenVisit, VisitBook associatedVisits) {
        boolean isLocationValid = visit.getLocation().getId().equals(givenVisit.getLocation().getId());
        boolean isDateValid = visit.getDate().isEqual(givenVisit.getDate());
        boolean isDuplicate = associatedVisits.getVisitList().contains(visit);
        return isLocationValid && isDateValid && !isDuplicate;
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
        assert numberOfInfectedLocations <= numberOfTotalLocations;
        if (numberOfInfectedLocations > numberOfTotalLocations * 0.6) {
            return (int) (numberOfTotalLocations * 0.4);
        } else {
            return numberOfInfectedLocations;
        }
    }

    //=============== Exporting to CSV ============================================================

    /**
     * Converts the stored persons list in VirusTracker to a String of
     * attributes in the format to be added to CSV files.
     *
     * @return A string representation of the persons list.
     */
    public String getPersonListAsString() {
        List<Person> personList = getPersonList();
        StringBuilder personListAsString = new StringBuilder();
        for (Person person : personList) {
            List<String> attributes = extractPersonAttributes(person);
            personListAsString.append(convertListToCsvFormat(attributes));
        }
        return personListAsString.toString();
    }

    /**
     * Converts the stored locations list in VirusTracker to a String of
     * attributes in the format to be added to CSV files.
     *
     * @return A string representation of the locations list.
     */
    public String getLocationListAsString() {
        List<Location> locationList = getLocationList();
        StringBuilder locationListAsString = new StringBuilder();
        for (Location location : locationList) {
            List<String> attributes = extractLocationAttributes(location);
            locationListAsString.append(convertListToCsvFormat(attributes));
        }
        return locationListAsString.toString();
    }


    /**
     * Converts the stored visits list in VirusTracker to a String of
     * attributes in the format to be added to CSV files.
     *
     * @return A string representation of the visits list.
     */
    public String getVisitListAsString() {
        List<Visit> visitList = getVisitList();
        StringBuilder visitListAsString = new StringBuilder();
        for (Visit visit : visitList) {
            List<String> attributes = extractVisitAttributes(visit);
            visitListAsString.append(convertListToCsvFormat(attributes));
        }
        return visitListAsString.toString();
    }

    /**
     * Converts the given list of attributes to a String representing a single row
     * in the CSV file.
     *
     * @param attributes The list of attributes
     * @return The string representing the list of attributes
     */
    private String convertListToCsvFormat(List<String> attributes) {
        if (attributes.size() == 0) {
            return "";
        }

        StringBuilder attributeString = new StringBuilder();

        for (String attribute : attributes) {
            // Possible empty strings
            if (attribute.isEmpty()) {
                continue;
            }
            // If the field contains commas, enclose the field with quotation marks
            if (attribute.contains(",")) {
                attributeString.append('"').append(attribute).append('"').append(",");
            } else {
                attributeString.append(attribute).append(",");
            }
        }
        // To specify a new row in the CSV file
        attributeString.append("\n");
        // Remove trailing comma in last field
        attributeString.deleteCharAt(attributeString.toString().length() - 2);

        return attributeString.toString();
    }

    /**
     * Converts a {@code Person} into a list of attributes.
     *
     * @param person The person to extract the attributes from.
     * @return A list of attributes describing the person.
     */
    private List<String> extractPersonAttributes(Person person) {
        List<String> attributes = new ArrayList<>();

        attributes.add(PREFIX_PERSON_ID + person.getIdAsString());
        attributes.add(PREFIX_NAME + person.getNameAsString());
        attributes.add(PREFIX_PHONE + person.getPhoneAsString());
        attributes.add(PREFIX_EMAIL + person.getEmailAsString());
        attributes.add(PREFIX_ADDRESS + person.getAddressAsString());
        attributes.add(PREFIX_QUARANTINE_STATUS + person.getQuarantineStatusAsString());
        attributes.add(PREFIX_INFECTION_STATUS + person.getInfectionStatusAsString());

        return attributes;
    }

    /**
     * Converts a {@code Location} into a list of attributes.
     *
     * @param location The location to extract the attributes from.
     * @return A list of attributes describing the location.
     */
    private List<String> extractLocationAttributes(Location location) {
        List<String> attributes = new ArrayList<>();

        attributes.add(PREFIX_LOCATION_ID + location.getIdAsString());
        attributes.add(PREFIX_NAME + location.getNameAsString());
        attributes.add(PREFIX_ADDRESS + location.getAddressAsString());

        return attributes;
    }

    /**
     * Converts a {@code Visit} into a list of attributes.
     *
     * @param visit The vist to extract the attributes from.
     * @return A list of attributes describing the visit.
     */
    private List<String> extractVisitAttributes(Visit visit) {
        List<String> attributes = new ArrayList<>();

        attributes.add(PREFIX_PERSON_ID + visit.getPersonIdAsString());
        attributes.add(PREFIX_LOCATION_ID + visit.getLocationIdAsString());
        attributes.add(PREFIX_DATE + visit.getDateAsString());

        return attributes;
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
