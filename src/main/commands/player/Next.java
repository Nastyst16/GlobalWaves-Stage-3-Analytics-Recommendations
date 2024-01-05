package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Type;
import main.SearchBar;
import main.users.User;

public final class Next implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * execute method for the next command
     * @param currUser the current user
     */
    public void execute(final User currUser) {

        currUser.setNext(true);
        this.setNext(currUser);
        currUser.setNext(false);
    }

    /**
     * accept method for the next command
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
    public Next(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /** next command method-helper
     *
     * @param currUser the current currUser
     */
    public void setNext(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (currUser.getTypeLoaded() == 2 && currUser.getRepeatStatus() == 0) {
            int index = currUser.getCurrentPlaylist().
                    getSongList().indexOf(currUser.getCurrentType());
            if (currUser.getCurrentPlaylist().getSongList().size() == index + 1
                    && currUser.getCurrentPlaylist().getSongList().size() == 1) {

                currUser.setCurrentType(null);
            }
        }
        if (currUser.getCurrentType() == null || currUser.getTypeLoaded() == -1) {
            this.message = "Please load a source before skipping to the next track.";
            return;
        }
        currUser.setNext(true);

        Type currentType = currUser.getCurrentType();

        currentType.setSecondsGone(currentType.getDuration());

        currUser.treatingRepeatStatus(currUser);
        currUser.setPaused(false);

        currUser.setNext(false);

        if (currUser.getCurrentType() != null) {
            this.message = "Skipped to next track successfully. The current track is "
                    + currUser.getCurrentType().getName() + ".";
        } else {
            this.message = "Please load a source before skipping to the next track.";
        }
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}
