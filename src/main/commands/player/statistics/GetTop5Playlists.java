package main.commands.player.statistics;

import main.collections.Playlists;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Playlist;
import main.SearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class GetTop5Playlists implements Command {
    private final String command;
    private final int timestamp;
    private final ArrayList<String> result;
    private static final int TOP_NR = 5;


    /**
     * Execute the command
     */
    public void execute() {
        this.searchTop5Playlists();
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public GetTop5Playlists(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
        result = new ArrayList<>();
    }

    /**
     * Search for the top 5 playlists
     */
    public void searchTop5Playlists() {

        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(Playlists.getPlaylists());
        Collections.sort(sortedPlaylists, Comparator.
                comparingInt(Playlist::getFollowers).reversed());

        int i = 0;
        while (i < TOP_NR && i < sortedPlaylists.size()) {
            result.add(sortedPlaylists.get(i).getName());
            i++;
        }
    }

    /**
     * Getter for the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     *
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }

}
