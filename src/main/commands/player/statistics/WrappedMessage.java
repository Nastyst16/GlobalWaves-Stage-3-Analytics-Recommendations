package main.commands.player.statistics;

import main.SearchBar;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

public class WrappedMessage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    private static final int HOST_PARAM = 3;

    /**
     * executes the command
     */
    public void execute(final Object... params) {
        this.setWrappedMessage((User) params[1], (Artist) params[2], (Host) params[HOST_PARAM]);
    }

    /**
     * setting wrapped
     */
    private void setWrappedMessage(final User currUser, final Artist currArtist,
                                   final Host currHost) {
        if (currUser != null) {
            this.setMessage("No data to show for user " + currUser.getUsername() + ".");
        } else if (currArtist != null) {
            this.setMessage("No data to show for artist " + currArtist.getUsername() + ".");
        } else if (currHost != null) {
            this.setMessage("No data to show for host " + currHost.getUsername() + ".");
        }
    }

    /**
     * constructor for the wrapped message
     */
    public WrappedMessage(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * gets the result
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
