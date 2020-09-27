package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public class LocationListStub {
    private ObservableList<Location> internalList = FXCollections.observableArrayList();
    private final ObservableList<Location> list;

    public LocationListStub() {
        internalList.add(new Location("Clementi"));
        internalList.add(new Location("Bedok"));
        internalList.add(new Location("Orchard"));
        this.list = FXCollections.unmodifiableObservableList(internalList);
    }

    public ObservableList<Location> getList() {
        return list;
    }
}
