package seedu.address.ui.list;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.location.Location;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class LocationCard extends UiPart<Region> {

    private static final String FXML = "LocationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Location location;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label locationName;

    @FXML
    private Label address;

    @FXML
    private Label index;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public LocationCard(Location location, int displayedIndex) {
        super(FXML);
        this.location = location;
        index.setText(displayedIndex + ". ");
        id.setText("ID: " + location.getId().value);
        locationName.setText(location.getName().fullName);
        address.setText(location.getAddress().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LocationCard)) {
            return false;
        }

        // state check
        LocationCard card = (LocationCard) other;
        return index.getText().equals(card.index.getText())
                && location.equals(card.location);
    }
}
