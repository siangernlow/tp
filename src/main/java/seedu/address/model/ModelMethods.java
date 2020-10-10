package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

public class ModelMethods {

    /**
     * Returns a HashSet of ids of persons as given in the argument.
     */
    public static HashSet<Index> getIdHashSetFromPersonsList(List<Person> persons) {
        HashSet<Index> ids = new HashSet<>();
        for (Person person : persons) {
            ids.add(person.getId());
        }
        return ids;
    }

    /**
     * Returns a ArrayList of location ids of visits as given in the argument.
     */
    public static ArrayList<Index> getLocationIdsFromInfectedVisitList(List<Visit> visits) {
        HashMap<Index, Integer> infectedLocations = new HashMap<>();
        for (Visit visit : visits) {
            Index id = visit.getLocationId();
            if (infectedLocations.containsKey(id)) {
                infectedLocations.put(id, infectedLocations.get(id) + 1);
            } else {
                infectedLocations.put(id, 1);
            }
        }
        HashMap<Index, Integer> sortedInfectedLocations = sortByValues(infectedLocations);
        return new ArrayList<>(sortedInfectedLocations.keySet());
    }

    /**
     * Sort HashMap by value. Only Used by method getIdeListFromLocationList.
     */
    public static HashMap<Index, Integer> sortByValues(HashMap<Index, Integer> map) {
        List<Map.Entry<Index, Integer>> list = new LinkedList<>(map.entrySet());
        // Sort in decreasing order
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        HashMap<Index, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Index, Integer> entry : list) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Determines the number of high risk locations given all infected locations and total locations.
     */
    public static int getNumberOfHighRiskLocations(int numberOfInfectedLocations, int numberOfTotalLocations) {
        // if number of infected locations is more than 60% of total locations,
        // number of infected locations considered to be high risk will be 40%
        // of total locations. Otherwise, all infected locations are considered
        // to be high risk. This criterion needs further discussion and is
        // subjected to change.
        if (numberOfInfectedLocations > numberOfTotalLocations * 0.6) {
            return (int) (numberOfTotalLocations * 0.4);
        } else {
            return numberOfInfectedLocations;
        }
    }

}
