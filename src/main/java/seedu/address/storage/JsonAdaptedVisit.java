package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Email;
import seedu.address.model.attribute.Id;
import seedu.address.model.attribute.InfectionStatus;
import seedu.address.model.attribute.Name;
import seedu.address.model.attribute.Phone;
import seedu.address.model.attribute.QuarantineStatus;
import seedu.address.model.location.Location;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * Jackson-friendly version of {@link Visit}.
 */
public class JsonAdaptedVisit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final String namePerson;
    private final String phone;
    private final String email;
    private final String addressPerson;
    private final String quarantineStatus;
    private final String infectionStatus;
    private final String idPerson;
    private final String nameLocation;
    private final String addressLocation;
    private final String idLocation;
    private final String dateOfVisit;

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("namePerson") String namePerson,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("addressPerson") String addressPerson,
                            @JsonProperty("quarantineStatus") String quarantineStatus,
                            @JsonProperty("infectionStatus") String infectionStatus,
                            @JsonProperty("idPerson") String idPerson,
                            @JsonProperty("nameLocation") String nameLocation,
                            @JsonProperty("addressLocation") String addressLocation,
                            @JsonProperty("idLocation") String idLocation,
                            @JsonProperty("dateOfVisit") String date) {
        this.namePerson = namePerson;
        this.phone = phone;
        this.email = email;
        this.addressPerson = addressPerson;
        this.quarantineStatus = quarantineStatus;
        this.infectionStatus = infectionStatus;
        this.idPerson = idPerson;
        this.nameLocation = nameLocation;
        this.addressLocation = addressLocation;
        this.idLocation = idLocation;
        this.dateOfVisit = date;
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        namePerson = source.getPerson().getName().fullName;
        phone = source.getPerson().getPhone().value;
        email = source.getPerson().getEmail().value;
        addressPerson = source.getPerson().getAddress().value;
        quarantineStatus = source.getPerson().getQuarantineStatus().toString();
        infectionStatus = source.getPerson().getInfectionStatus().toString();
        idPerson = source.getPerson().getId().value;
        nameLocation = source.getLocation().getName().fullName;
        addressLocation = source.getLocation().getAddress().value;
        idLocation = source.getLocation().getId().value;
        dateOfVisit = source.getDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        if (namePerson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(namePerson)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(namePerson);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (addressPerson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(addressPerson)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(addressPerson);

        if (quarantineStatus == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, QuarantineStatus.class.getSimpleName()));
        }
        if (!QuarantineStatus.isValidQuarantineStatus(quarantineStatus)) {
            throw new IllegalValueException(QuarantineStatus.MESSAGE_CONSTRAINTS);
        }
        final QuarantineStatus modelQuarantineStatus = new QuarantineStatus(quarantineStatus);

        if (infectionStatus == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InfectionStatus.class.getSimpleName())
            );
        }
        if (!InfectionStatus.isValidInfectionStatus(infectionStatus)) {
            throw new IllegalValueException(InfectionStatus.MESSAGE_CONSTRAINTS);
        }
        final InfectionStatus modelInfectionStatus = new InfectionStatus(infectionStatus);

        if (idPerson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }
        if (!Id.isValidId(idPerson)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        final Id modelIdPerson = new Id(idPerson);

        final Person modelPerson = new Person(modelIdPerson, modelName, modelPhone, modelEmail, modelAddress,
                modelQuarantineStatus, modelInfectionStatus);

        if (nameLocation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(nameLocation)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelNameLocation = new Name(nameLocation);

        if (addressLocation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(addressLocation)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddressLocation = new Address(addressLocation);

        if (idLocation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }
        if (!Id.isValidId(idLocation)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        final Id modelIdLocation = new Id(idLocation);

        final Location modelLocation = new Location(modelIdLocation, modelNameLocation, modelAddressLocation);

        if (dateOfVisit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        if (dateOfVisit.trim().equals("")) {
            throw new IllegalValueException("Please enter the correct date format");
        }

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate modelDate = LocalDate.parse(dateOfVisit, inputFormat);
        return new Visit(modelPerson, modelLocation, modelDate);
    }
}
