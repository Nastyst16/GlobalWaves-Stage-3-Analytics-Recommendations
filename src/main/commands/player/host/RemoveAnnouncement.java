package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.Host;

public final class RemoveAnnouncement implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;

    /**
     * execute method for visitor pattern
     * @param host the host
     */
    public void execute(final Host host) {
        removeAnnouncement(host);
    }

    /**
     * method that removes an announcement
     * @param host the host
     */
    public void removeAnnouncement(final Host host) {

//        verifying if the host exists
        if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the announcement exists
        for (int i = 0; i < host.getAnnouncements().size(); i++) {
            if (host.getAnnouncements().get(i).getName().equals(this.name)) {
                host.getAnnouncements().remove(i);
                this.message = this.user + " has successfully deleted the announcement.";
                return;
            }
        }

        this.message = this.user + " has no announcement with the given name.";

    }

    /**
     * constructor with parameters for RemoveAnnouncement
     * @param input the input
     */
    public RemoveAnnouncement(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }

    /**
     * accept method for visitor pattern
     * @param commandVisitor the command visitor
     */
    public void accept(final CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
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
     * getter for the name
     * @return the name
     */
    public String getName() {
        return name;
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
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
