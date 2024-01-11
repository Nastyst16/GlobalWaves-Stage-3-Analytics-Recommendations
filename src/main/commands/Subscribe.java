package main.commands;

import main.SearchBar;
import main.collections.Artists;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.User;

public class Subscribe implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;


    /**
     * executes the Subscribe command
     */
    public void execute(final Object... params) {
        this.setSubscribe((User) params[1]);
    }

    /**
     * sets the message of the Subscribe command
     */
    public void setSubscribe(final User currUser) {

        if (currUser == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

        if (currUser.getCurrentPage().getSelectedPageOwner().equals("")
            || currUser.getCurrentPage().getSelectedPageOwner().equals("Home")) {
            this.message = "To subscribe you need to be on the page of an artist or host.";
        }

        for (Artist a : Artists.getArtists()) {
            if (a.getUsername().equals(currUser.getCurrentPage().getSelectedPageOwner())) {
                if (a.getNotificationService().containsObserver(currUser)) {
                    this.message = this.user + " unsubscribed from "
                            + currUser.getCurrentPage().getSelectedPageOwner() + " successfully.";
                    a.getNotificationService().removeObserver(currUser);
                    return;
                }
            }
        }

        if (currUser.getCurrentPage().getSelectedPageOwner().equals("")) {
            this.message = "User " + this.user + " has subscribed to you.";
        } else {
            this.message = this.user + " subscribed to "
                    + currUser.getCurrentPage().getSelectedPageOwner() + " successfully.";
            for (Artist a : Artists.getArtists()) {
                if (a.getUsername().equals(currUser.getCurrentPage().getSelectedPageOwner())) {
                    a.getNotificationService().addObserver(currUser);
                    break;
                }
            }
        }
    }

    /**
     * Constructor that sets the command to "subscribe".
     */
    public Subscribe(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
