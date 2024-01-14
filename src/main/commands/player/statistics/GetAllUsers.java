package main.commands.player.statistics;

import main.collections.Artists;
import main.collections.Hosts;
import main.collections.Users;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public final class GetAllUsers implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;

    /**
     * Method that executes the command
     */
    public void execute(final Object... params) {

        for (User user : Users.getInstance().getUsers()) {
            this.result.add(user.getUsername());
        }
        for (Artist artist : Artists.getInstance().getArtists()) {
            this.result.add(artist.getUsername());
        }
        for (Host host : Hosts.getInstance().getHosts()) {
            this.result.add(host.getUsername());
        }

    }

    /**
     * Constructor of the class, it sets the command and the timestamp
     * @param input the input command
     */
    public GetAllUsers(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * Getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
