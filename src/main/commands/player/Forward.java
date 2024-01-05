package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class Forward implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int SECONDS_TO_FORWARD = 90;

    /**
     * Executes the command
     */
    public void execute(final User currUser) {

        this.setForward(currUser);
    }

    /**
     * Accept method for the visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Forward(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Skips forward 90 seconds
     * @param currUser the current user
     */
    public void setForward(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (currUser.getCurrentType() == null) {
            this.message = "Please load a source before attempting to forward.";
            return;
        }
        if (currUser.getTypeLoaded() != 1) {
            this.message = "The loaded source is not a podcast.";
            return;
        }

        currUser.getCurrentType().setSecondsGone(currUser.
                getCurrentType().getSecondsGone() + SECONDS_TO_FORWARD);
        this.message = "Skipped forward successfully.";
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}
