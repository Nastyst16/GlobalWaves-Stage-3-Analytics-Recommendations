package main.commands.player;

import main.inputCommand.Command;
import main.commands.types.Song;
import main.SearchBar;
import main.users.User;

import java.util.ArrayList;

public final class ShowPreferredSongs implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result;


    /**
     * Execute method for visitor pattern
     */
    public void execute(final Object... params) {
        this.setResult((User) params[1]);
    }

    /**
     * Constructor
     * @param input the input
     */
    public ShowPreferredSongs(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        result = new ArrayList<>();
    }

    /**
     * Sets result.
     *
     * @param currUser the user
     */
    public void setResult(final User currUser) {
        if (!currUser.getLikedSongs().isEmpty()) {
            for (Song song : currUser.getLikedSongs()) {
                this.result.add(song.getName());
            }
        }
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
