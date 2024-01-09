package main.commands.monetization;

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

    /**
     * executes the command
     */
    public void execute(Object... params) {
        this.buyPremium((User) params[1]);
    }

    private void buyPremium(User user) {

        if (user.isPremium()) {
            this.setMessage(this.user + " cancelled the subscription successfully.");
            this.setMonetization(user);
            user.setPremium(false);
        } else {
            this.setMessage(this.user + " is not a premium user.");
        }

    }

    /**
     * sets the monetization
     */
    private void setMonetization(User user) {

//        total songs listened
        double totalSongsListened = 0;
        for (Song s : user.getEverySong()) {
            if (s.getNumberOfListens() != 0) {
                totalSongsListened += 1;
            }
        }

        double songsListenedOfThisArtist = 0;
        for (Artist artist : Artists.getArtists()) {
            songsListenedOfThisArtist = 0;

            for (Song s : user.getEverySong()) {
                if (s.getNumberOfListens() != 0 && s.getArtist().equals(artist.getUsername())) {
                    songsListenedOfThisArtist += 1;
                }
            }

            if (songsListenedOfThisArtist != 0) {
                double revenue = 1000000 * songsListenedOfThisArtist / totalSongsListened;
                artist.addSongRevenue(revenue);
            }
        }
    }


    /**
     * constructor
     */
    public CancelPremium(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
