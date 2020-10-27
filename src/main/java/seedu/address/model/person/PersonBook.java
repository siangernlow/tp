package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.attribute.Id;

/**
 * Wraps all data at the person-book level
 * Duplicates are not allowed (by .isSamePerson and .isSameId comparison)
 */
public class PersonBook implements ReadOnlyPersonBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public PersonBook() {}

    /**
     * Creates a PersonBook using the Persons in the {@code toBeCopied}
     */
    public PersonBook(ReadOnlyPersonBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code PersonBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPersonBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the person book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the given {@code id} exists in the person book.
     */
    public boolean hasPersonId(Id id) {
        requireNonNull(id);
        return persons.containsPersonId(id);
    }

    /**
     * Returns a Person with the given Id from the person book.
     */
    public Person getPersonById(Id id) {
        return persons.getPersonById(id);
    }

    /**
     * Adds a person to the person book.
     * The person must not already exist in the person book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the person book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the person book.
     * The {@code editedPerson} must not have the same Id as another person in the person book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code PersonBook}.
     * {@code key} must exist in the person book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonBook // instanceof handles nulls
                && persons.equals(((PersonBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
