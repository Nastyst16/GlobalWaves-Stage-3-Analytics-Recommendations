package main.commands.player.user;

import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

public final class SwapOnlineOfline implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * Constructor of the SwapOnlineOfline class.
     * @param input the input given by the user
     */
    public SwapOnlineOfline(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = this.user + " has changed status successfully.";
    }

    /**
     * Executes the command.
     */
    public void execute(final Object... params) {
        User currUser = (User) params[1];
        Artist currArtist = (Artist) params[2];
        Host currHost = (Host) params[3];

        if (currArtist != null || currHost != null) {
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
