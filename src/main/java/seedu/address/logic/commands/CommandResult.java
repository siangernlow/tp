package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static final String SWITCH_NONE = "No switch";
    public static final String SWITCH_TO_VIEW_ALL_PEOPLE = "Switch to view all people";
    public static final String SWITCH_TO_VIEW_ALL_LOCATIONS = "Switch to view all locations";
    public static final String SWITCH_TO_VIEW_ALL_VISITS = "Switch to view all visits";
    public static final String SWITCH_TO_VIEW_ALL_INFECTED = "Switch to view all infected";
    public static final String SWITCH_TO_VIEW_ALL_QUARANTINED = "Switch to view all quarantined";
    public static final String SWITCH_TO_VIEW_STATISTICS = "Switch to view statistics";

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final String switchState;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String switchState) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.switchState = switchState;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, SWITCH_NONE);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    public String getSwitchState() {
        return switchState;
    }
}
