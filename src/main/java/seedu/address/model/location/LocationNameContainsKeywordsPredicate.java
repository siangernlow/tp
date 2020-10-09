package seedu.address.model.location;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Location}'s {@code Name} matches any of the keywords given.
 */
public class LocationNameContainsKeywordsPredicate implements Predicate<Location> {
    private final List<String> keywords;

    public LocationNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Location location) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(location.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LocationNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
