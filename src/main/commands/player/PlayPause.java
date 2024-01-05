package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class PlayPause implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * Executes the command
     */
    public void execute(final User currUser) {
        this.setPlayPause(currUser);
    }

    /**
     * Accepts the visitor for the command
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
    public PlayPause(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = "Please load a source before attempting to pause or resume playback.";
    }

    /**
     * Pauses or resumes playback
     *
     * @param currUser the user that called the command
     */
    public void setPlayPause(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (!currUser.isPaused() && currUser.getTypeLoaded() != -1) {
            this.message = "Playback paused successfully.";

            currUser.setPaused(true);

        } else if (currUser.isPaused() && currUser.getTypeLoaded() != -1) {
            this.message = "Playback resumed successfully.";

            currUser.setPaused(false);
        }
    }

    /**
     * gets the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     *
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
