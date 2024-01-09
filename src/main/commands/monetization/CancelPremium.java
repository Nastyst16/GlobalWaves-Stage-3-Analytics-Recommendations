package main.commands.monetization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.SearchBar;
import main.collections.Artists;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.User;

public class CancelPremium implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    @JsonIgnore
    private final double MAX_MONEY = 1000000;

    /**
     * executes the command
     */
    public void execute(final Object... params) {
        this.buyPremium((User) params[1]);
    }

    /**
     * buys the premium
     */
    private void buyPremium(final User currUser) {

        if (currUser.isPremium()) {
            this.setMessage(this.user + " cancelled the subscription successfully.");
            this.setMonetization(currUser);
            currUser.setPremium(false);
        } else {
            this.setMessage(this.user + " is not a premium user.");
        }
    }

    /**
     * sets the monetization
     */
    private void setMonetization(final User currUser) {

//        total songs listened
        double totalSongsListened = 0;
        for (Song s : currUser.getEverySong()) {
            if (s.getNumberOfListens() != 0) {
                totalSongsListened += 1;
            }
        }

        double songsListenedOfThisArtist = 0;
        for (Artist artist : Artists.getArtists()) {
            songsListenedOfThisArtist = 0;

            for (Song s : currUser.getEverySong()) {
                if (s.getNumberOfListens() != 0 && s.getArtist().equals(artist.getUsername())) {
                    songsListenedOfThisArtist += 1;
                }
            }

            if (songsListenedOfThisArtist != 0) {
                double revenue = MAX_MONEY * songsListenedOfThisArtist / totalSongsListened;
                artist.addSongRevenue(revenue);
            }
        }
    }

    /**
     * constructor
     */
    public CancelPremium(final SearchBar input) {
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

    /**
     * sets the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
