package main.commands.player;

import main.SearchBar;
import main.commands.searchBar.Select;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

public class LoadRecomm implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * execute the command and change the page
     */
    public void execute(final Object... params) {
        this.setLoadRecomm((SearchBar) params[0], (User) params[1],
                (Artist) params[2], (Host) params[3]);
    }

    /**
     * constructor for the ChangePage command
     * @param input
     */
    public LoadRecomm(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * sets the load recommendation
     */
    public void setLoadRecomm(final SearchBar input, User user,
                              Artist artist, Host host) {
        if (user == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        if (user.getCurrentRecommendation() == null) {
            this.setMessage(this.user + " has no previous recommendation.");
            return;
        }

        Select select = new Select(user.getCurrentRecommendation());
        user.setCurrentSelect(select);

        if (user.getCurrentRecommendation() instanceof Song) {
            Load load = new Load((Song) user.getCurrentRecommendation());
            load.execute(input, user, artist, host);
        } else {
            Load load = new Load((Playlist) user.getCurrentRecommendation());
            load.execute(input, user, artist, host);
        }

        user.getListenable().listen(user.getCurrentType(), user);

        this.setMessage("Playback loaded successfully.");
    }

    /**
     * sets the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * gets the command
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
}
