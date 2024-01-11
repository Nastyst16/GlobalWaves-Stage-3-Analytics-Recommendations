package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.SearchBar;
import main.commands.searchBar.Select;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.commands.types.Type;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

public class LoadRecomm implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;
    @JsonIgnore
    private static final int HOST_PARAM = 3;

    /**
     * execute the command and change the page
     */
    public void execute(final Object... params) {
        this.setLoadRecomm((SearchBar) params[0], (User) params[1],
                (Artist) params[2], (Host) params[HOST_PARAM]);
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
    public void setLoadRecomm(final SearchBar input, final User currUser,
                              final Artist artist, final Host host) {
        if (currUser == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        if (currUser.getCurrentRecommendation() == null) {
            this.setMessage(this.user + " has no previous recommendation.");
            return;
        }

        Select select = new Select(currUser.getCurrentRecommendation());
        currUser.setCurrentSelect(select);

        if (currUser.getCurrentRecommendation() instanceof Song) {
            Load load = new Load((Song) currUser.getCurrentRecommendation());
            load.execute(input, currUser, artist, host);
        } else {
            Load load = new Load((Playlist) currUser.getCurrentRecommendation());
            load.execute(input, currUser, artist, host);
        }

        Type currentType = currUser.getCurrentType();
        currentType.listen(currUser);

        this.setMessage("Playback loaded successfully.");
    }

    /**
     * sets the message
     */
    public void setMessage(final String message) {
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
