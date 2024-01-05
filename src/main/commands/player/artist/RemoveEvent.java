package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Event;
import main.users.Artist;
import main.users.Host;

public final class RemoveEvent implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;

    /**
     * executes the RemoveEvent command
     * @param currUser the user that executes the command
     * @param artist the artist that executes the command
     * @param host the host that executes the command
     */
    public void execute(final User currUser, final Artist artist,
                        final Host host) {
        this.setRemoveEvent(currUser, artist, host);
    }

    /**
     * executes the RemoveEvent command
     * @param currUser the user that executes the command
     * @param artist the artist that executes the command
     * @param host the host that executes the command
     */
    public void setRemoveEvent(final User currUser, final Artist artist,
                               final Host host) {

        if (currUser != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the event we want to remove exists
        boolean exists = false;

        for (Event e : artist.getEvents()) {
            if (e.getName().equals(this.name)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.setMessage(this.user + " doesn't have an event with the given name.");
            return;
        }


        for (int i = 0; i < artist.getEvents().size(); i++) {
            if (artist.getEvents().get(i).getName().equals(this.name)) {
                artist.getEvents().remove(i);
                this.setMessage(this.user + " deleted the event successfully.");
                return;
            }
        }

        this.setMessage(this.user + " doesn't have an event with the given name.");
    }


    public RemoveEvent(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }

    /**
     * accepts a visitor for the command
     * @param visitor the visitor
     */
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * returns the message of the command
     * @return the message
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user of the command
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp of the command
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the name of the event
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the message of the command
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message of the command
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
