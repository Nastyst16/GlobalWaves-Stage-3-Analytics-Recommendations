package main.commands.player.user;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

public final class SwitchConnectionStatus implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * Constructor of the SwitchConnectionStatus class.
     * @param input the input given by the user
     */
    public SwitchConnectionStatus(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = this.user + " has changed status successfully.";
    }

    /**
     * Executes the command.
     * @param currUser the current user
     * @param artist the current artist
     * @param host the current host
     */
    public void execute(final User currUser, final Artist artist, final Host host) {

        if (artist != null || host != null) {
            this.setMessage(this.user + " is not a normal user.");
            return;
        }

        if (currUser == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

        if (!currUser.getOnline()) {
            currUser.setOnline(true);
        } else {
            currUser.setOnline(false);
        }
    }

    /**
     * Accepts a visitor to perform some action on the current command.
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * Gets the command of the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the username of the command.
     * @return the username
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp of the command.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message of the command.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the command.
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
