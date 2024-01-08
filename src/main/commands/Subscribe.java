package main.commands;

import lombok.Getter;
import lombok.Setter;
import main.SearchBar;
import main.collections.Artists;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
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
    public void execute(User user) {
        this.setSubscribe(user);
    }

    /**
     * sets the message of the Subscribe command
     */
    public void setSubscribe(User user) {

        if (user == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

        if (user.getSelectedPageOwner().equals("")
            || user.getSelectedPageOwner().equals("Home")) {
            this.message = "To subscribe you need to be on the page of an artist or host.";
        }

        for (Artist a : Artists.getArtists()) {
            if (a.getUsername().equals(user.getSelectedPageOwner())) {
                if (a.getNotificationService().containsObserver(user)) {
                    this.message = this.user + " unsubscribed from " + user.getSelectedPageOwner() + " successfully.";
                    a.getNotificationService().removeObserver(user);
                    return;
                }
            }
        }

        if (user.getSelectedPageOwner().equals("")) {
            this.message = "User " + this.user + " has subscribed to you.";
        } else {
            this.message = this.user + " subscribed to " + user.getSelectedPageOwner() + " successfully.";
            for (Artist a : Artists.getArtists()) {
                if (a.getUsername().equals(user.getSelectedPageOwner())) {
                    a.getNotificationService().addObserver(user);
                    break;
                }
            }
        }
    }

    /**
     * accepts the command
     */
    public void accept(final CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
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
