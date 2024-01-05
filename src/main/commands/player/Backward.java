package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class Backward implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int SECONDS_TO_FORWARD = 90;

    /**
     * Executes the command.
     */
    public void execute(final User currUser) {
        this.setBackward(currUser);
    }

    /**
     * Accepts a visitor for the command.
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor.
     * @param input the input
     */
    public Backward(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * goes back 90 seconds in the current track
     * @param currUser the current user
     */
    public void setBackward(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        if (currUser.getTypeLoaded() != 1) {
            this.setMessage("The loaded source is not a podcast.");
            return;
        }
        if (currUser.getCurrentType() == null) {
            this.setMessage("Please load a source before returning to the previous track.");
            return;
        }
        currUser.getCurrentType().setSecondsGone(currUser.
                getCurrentType().getSecondsGone() - SECONDS_TO_FORWARD);
        this.setMessage("Rewound successfully.");
    }

    /**
     * Gets the command string.
     *
     * @return The command string.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user string.
     *
     * @return The user string.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp.
     *
     * @return The timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message The message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
