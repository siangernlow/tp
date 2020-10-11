package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.location.EditLocationCommand.EditLocationDescriptor;
import seedu.address.model.location.Location;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

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
        descriptor.setId(location.getId());
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

    /**
     * Sets the {@code id} of the {@code EditLocationDescriptor} that we are building.
     */
    public EditLocationDescriptorBuilder withId(Index id) {
        descriptor.setId(id);
        return this;
    }

    public EditLocationDescriptor build() {
        return descriptor;
    }
}
