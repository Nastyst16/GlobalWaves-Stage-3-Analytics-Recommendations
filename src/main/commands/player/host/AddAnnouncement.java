package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Announcement;
import main.users.Artist;
import main.users.Host;

public final class AddAnnouncement implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    private String message;

    /**
     * execute method for AddAnnouncement command
     * @param currUser the user that is executing the command
     * @param artist the artist that is executing the command
     * @param host the host that is executing the command
     */
    public void execute(final User currUser, final Artist artist, final Host host) {
        addAnnouncement(currUser, artist, host);
    }

    /**
     * constructor for AddAnnouncement command
     * @param input the input command
     */
    public AddAnnouncement(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
    }

    /**
     * method that adds an announcement to a host
     * @param currUser the user that is executing the command
     * @param artist the artist that is executing the command
     * @param host the host that is executing the command
     */
    public void addAnnouncement(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || artist != null) {
            this.message = this.user + " is not a host.";
            return;
        } else if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if an announcement with the same name exists;
        for (int i = 0; i < host.getAnnouncements().size(); i++) {
            if (host.getAnnouncements().get(i).getName().equals(this.name)) {
                this.setMessage("The host " + this.user
                        + " already has an announcement with the same name.");
                return;
            }
        }

        host.getAnnouncements().add(new Announcement(this.name, this.description, this.user));
        this.message = this.user + " has successfully added new announcement.";
    }

    /**
     * accept method for AddAnnouncement command
     * @param commandVisitor the command visitor
     */
    public void accept(final CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    /**
     * getter for command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
