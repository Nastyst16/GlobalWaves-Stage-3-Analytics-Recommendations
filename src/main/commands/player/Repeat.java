package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class Repeat implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Executes the command
     */
    public void execute(final User currUser) {

        currUser.setRepeatStatus(this.setRepeatMessage(currUser,
                currUser.getRepeatStatus(), currUser.getTypeLoaded()));
    }

    /**
     * Constructor for repeat
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Repeat constructor
     * @param input the input
     */
    public Repeat(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Sets the repeat status of the current user
     * @param currUser the current user
     * @param repStatus the current repeat status
     * @param typeLoaded the type of source loaded
     * @return the new repeat status
     */
    public int setRepeatMessage(final User currUser, final int repStatus, final int typeLoaded) {
        int repeatStatus = repStatus;

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return -1;
        }

        if (currUser.getCurrentType() == null) {
            this.message = "Please load a source before setting the repeat status.";
            return -1;
        }

        if (typeLoaded == 0 || typeLoaded == 1) {
            if (repeatStatus == 0) {
                this.message = "Repeat mode changed to repeat once.";
                repeatStatus = 1;
                currUser.setRepeatString("Repeat Once");
            } else if (repeatStatus == 1) {
                this.message = "Repeat mode changed to repeat infinite.";
                repeatStatus = 2;
                currUser.setRepeatString("Repeat Infinite");
            } else if (repeatStatus == 2) {
                this.message = "Repeat mode changed to no repeat.";
                repeatStatus = 0;
                currUser.setRepeatString("No Repeat");
            }
        } else {
            if (repeatStatus == 0) {
                this.message = "Repeat mode changed to repeat all.";
                repeatStatus = 1;
                currUser.setRepeatString("Repeat All");
            } else if (repeatStatus == 1) {
                this.message = "Repeat mode changed to repeat current song.";
                repeatStatus = 2;
                currUser.setRepeatString("Repeat Current Song");
            } else if (repeatStatus == 2) {
                this.message = "Repeat mode changed to no repeat.";
                repeatStatus = 0;
                currUser.setRepeatString("No Repeat");
            }
        }

        return repeatStatus;
    }

    /**
     * Gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
