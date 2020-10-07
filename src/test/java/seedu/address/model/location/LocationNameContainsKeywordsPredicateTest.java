package seedu.address.model.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LocationBuilder;

public class LocationNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LocationNameContainsKeywordsPredicate firstPredicate =
                new LocationNameContainsKeywordsPredicate(firstPredicateKeywordList);
        LocationNameContainsKeywordsPredicate secondPredicate =
                new LocationNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LocationNameContainsKeywordsPredicate firstPredicateCopy =
                new LocationNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_locationNameContainsKeywords_returnsTrue() {
        // One keyword
        LocationNameContainsKeywordsPredicate predicate =
                new LocationNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new LocationBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new LocationNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new LocationBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new LocationNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new LocationBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new LocationNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new LocationBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LocationNameContainsKeywordsPredicate predicate =
                new LocationNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LocationBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new LocationNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new LocationBuilder().withName("Alice Bob").build()));

        // Keywords match address, but does not match name
        predicate = new LocationNameContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertFalse(predicate.test(new LocationBuilder().withName("Alice").withAddress("Main Street").build()));
    }
}
