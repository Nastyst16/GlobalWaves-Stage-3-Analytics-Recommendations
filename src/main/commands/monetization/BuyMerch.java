package main.commands.monetization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.SearchBar;
import main.collections.Artists;
import main.commands.types.Merch;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.User;

public class BuyMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    @JsonIgnore
    private String merchName;

    /**
     * executes the command
     */
    public void execute(final Object... params) {
        this.buyMerch((User) params[1]);
    }

    /**
     * Method that buys merch.
     */
    public void buyMerch(final User currUser) {

        if (currUser == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
        }

        if (currUser.getCurrentPage().getSelectedPageOwner() == "") {
            this.setMessage("Cannot buy merch from this page.");
        }

//        searching for the artist based on page
        Artist artist = null;
        for (Artist currArtist : Artists.getArtists()) {
            if (currArtist.getUsername().equals(currUser.
                    getCurrentPage().getSelectedPageOwner())) {
                artist = currArtist;
                break;
            }
        }

        if (artist == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        searching for the merchandise from this artist
        for (Merch merch : artist.getMerchandise()) {
            if (merch.getName().equalsIgnoreCase(this.getMerchName())) {
                currUser.addBoughtMerchandise(merch);
                merch.addSold();

                this.setMessage(this.user + " has added new merch successfully.");
                return;
            }
        }

        this.setMessage("The merch " + this.getMerchName() + " doesn't exist.");
    }


    /**
     * Constructor for the class BuyMerch
     */
    public BuyMerch(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.merchName = input.getName();
    }

    /**
     * Getter for the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the user.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * getter for the merch name
     */
    public String getMerchName() {
        return merchName;
    }

    /**
     * setter for the merch name
     */
    public void setMerchName(final String merchName) {
        this.merchName = merchName;
    }
}
