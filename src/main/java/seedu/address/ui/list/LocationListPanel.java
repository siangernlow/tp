package seedu.address.ui.list;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.location.Location;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class LocationListPanel extends UiPart<Region> {
    private static final String FXML = "LocationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LocationListPanel.class);

    @FXML
    private ListView<Location> locationListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public LocationListPanel(ObservableList<Location> locationList) {
        super(FXML);
        locationListView.setItems(locationList);
        locationListView.setCellFactory(listView -> new LocationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class LocationListViewCell extends ListCell<Location> {
        @Override
        protected void updateItem(Location location, boolean empty) {
            super.updateItem(location, empty);

            if (empty || location == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LocationCard(location, getIndex() + 1).getRoot());
            }
        }
    }

}
