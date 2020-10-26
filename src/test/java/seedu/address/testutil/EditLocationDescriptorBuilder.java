package seedu.address.testutil;

import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.model.attribute.Address;
import seedu.address.model.attribute.Name;
import seedu.address.model.location.Location;


/**
 * A utility class to help with building EditLocationDescriptor objects.
 */
public class EditLocationDescriptorBuilder {

    private EditLocationDescriptor descriptor;

    public EditLocationDescriptorBuilder() {
        descriptor = new EditLocationDescriptor();
    }

    public EditLocationDescriptorBuilder(EditLocationDescriptor descriptor) {
        this.descriptor = new EditLocationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLocationDescriptor} with fields containing {@code location}'s details
     */
    public EditLocationDescriptorBuilder(Location location) {
        descriptor = new EditLocationDescriptor();
        descriptor.setName(location.getName());
        descriptor.setAddress(location.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditLocationDescriptor} that we are building.
     */
    public EditLocationDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditLocationDescriptor} that we are building.
     */
    public EditLocationDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditLocationDescriptor build() {
        return descriptor;
    }
}
