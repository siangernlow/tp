package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLocations.BENSON_LOCATION;
import static seedu.address.testutil.TypicalLocations.CARL_LOCATION;
import static seedu.address.testutil.TypicalLocations.IDA_LOCATION;
import static seedu.address.testutil.TypicalLocations.getTypicalLocationBook;
import static seedu.address.testutil.TypicalLocations.getUnorderedTypicalLocationBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVisits.getLessThanSixtyPercentVisitBook;
import static seedu.address.testutil.TypicalVisits.getMoreThanSixtyPercentVisitBook;
import static seedu.address.testutil.TypicalVisits.getTypicalVisitBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.location.Location;

public class ModelPredicateTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalLocationBook(),
                new UserPrefs(), getTypicalVisitBook());
    }

    @Test
    public void getPredicateForHighRiskLocations() {
        // Infected Locations(3) are less than 60% of total locations(7)
        model.setVisitBook(getLessThanSixtyPercentVisitBook());
        Predicate<Location> actualPredicate = ModelPredicate.getPredicateForHighRiskLocations(model);
        model.setLocationBook(getUnorderedTypicalLocationBook());
        model.updateFilteredLocationList(actualPredicate);
        ObservableList<Location> actualList = model.getFilteredLocationList();

        ObservableList<Location> expectedList = FXCollections.observableArrayList();
        expectedList.add(IDA_LOCATION);
        expectedList.add(BENSON_LOCATION);
        expectedList.add(CARL_LOCATION);

        assertEquals(expectedList, actualList);

        // Infected Locations(6) are more than 60% of total locations(8)
        model.setVisitBook(getMoreThanSixtyPercentVisitBook());
        actualPredicate = ModelPredicate.getPredicateForHighRiskLocations(model);
        model.updateFilteredLocationList(actualPredicate);
        actualList = model.getFilteredLocationList();

        expectedList.clear();
        expectedList.add(IDA_LOCATION);
        expectedList.add(BENSON_LOCATION);
        expectedList.add(CARL_LOCATION);

        assertEquals(expectedList, actualList);
    }
}
