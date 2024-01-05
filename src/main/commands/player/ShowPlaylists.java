package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Playlist;
import main.SearchBar;
import main.users.User;

import java.util.ArrayList;

public final class ShowPlaylists implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<Playlist> result;

    /**
     * Execute the command.
     */
    public void execute(final User currUser) {

//                copying the playlists
        ArrayList<Playlist> copyList = new ArrayList<>();
        this.copyPlaylists(currUser, copyList);

        this.setResult(copyList);
    }


    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public ShowPlaylists(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.result = new ArrayList<>();
    }

    /**
     * Copy playlists.
     *
     * @param currentUser the current user
     * @param copyList    the copy list
     */
    public void copyPlaylists(final User currentUser, final ArrayList<Playlist> copyList) {

        for (int i = 0; i < currentUser.getPlayListList().size(); i++) {
            copyList.add(new Playlist(currentUser.getPlayListList().get(i)));
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
    public ArrayList<Playlist> getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(final ArrayList<Playlist> result) {
        this.result.clear();
        this.result.addAll(result);
    }


}
