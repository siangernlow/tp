package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attribute.Id;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PersonNotIdentifiableException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * This list also enforces that all elements have unique Ids.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains a person with the given Id.
     */
    public boolean containsPersonId(Id id) {
        requireNonNull(id);
        return internalList.stream().anyMatch(p -> p.getId().equals(id));
    }

    /**
     * Returns a person within the list that has the given Id.
     * The Id must belong to a person within the list.
     */
    public Person getPersonById(Id id) {
        requireNonNull(id);
        Optional<Person> person = internalList.stream().filter(p -> p.getId().equals(id)).findAny();
        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        } else {
            return person.get();
        }
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     * The person must not have same Id as another person in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        if (containsPersonId(toAdd.getId())) {
            throw new PersonNotIdentifiableException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        if (!target.isSameId(editedPerson) && containsPersonId(editedPerson.getId())) {
            throw new PersonNotIdentifiableException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons nor duplicate Ids.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        checkPersons(persons);

        internalList.setAll(persons);
    }

    /**
     * Checks if {@code persons} contains only persons with unique identities and Ids.
     * @throws DuplicatePersonException if there are persons with duplicate identities.
     * @throws PersonNotIdentifiableException if there are persons with duplicate Ids.
     */
    private void checkPersons(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    throw new DuplicatePersonException();
                }
                if (persons.get(i).isSameId(persons.get(j))) {
                    throw new PersonNotIdentifiableException();
                }
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
