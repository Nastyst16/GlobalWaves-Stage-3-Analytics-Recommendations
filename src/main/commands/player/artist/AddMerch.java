package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Merch;
import main.users.Artist;
import main.users.Host;

public final class AddMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    @JsonIgnore
    private final int price;
    private String message;

    /**
     * executes the command AddMerch
     */
    public void execute(final User currUser, final Artist artist, final Host host) {
        this.setMerch(currUser, artist, host);
    }

    /**
     * constructor for AddMerch
     * @param input the input given
     */
    public AddMerch(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.price = input.getPrice();
    }

    /**
     * adds merchandise to the artist
     * @param currUser the user
     * @param artist the artist
     * @param host the host
     */
    public void setMerch(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || host != null) {
            this.setMessage(this.user + " is not an artist.");
            return;
        } else if (artist == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

        for (Merch merch : artist.getMerchandise()) {
            if (merch.getName().equals(this.name)) {
                this.setMessage(this.user + " has merchandise with the same name.");
                return;
            }
        }

        if (this.price < 0) {
            this.setMessage("Price for merchandise can not be negative.");
            return;
        }

        artist.getMerchandise().add(new Merch(this.user, this.name, this.description, this.price));
        this.setMessage(this.user + " has added new merchandise successfully.");
    }

    /**
     * accepts the visitor for the command
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
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
     * gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the price
     * @return the price
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
