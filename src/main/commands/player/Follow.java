package main.commands.player;

import main.collections.Playlists;
import main.inputCommand.Command;
import main.commands.types.Playlist;
import main.SearchBar;
import main.users.User;
import java.util.ArrayList;

public final class Follow implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Executes the command to follow or unfollow a playlist
     */
    public void execute(final Object... params) {
        this.setFollow((User) params[1]);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Follow(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Follows or unfollows a playlist
     * @param currUser the current user
     */
    public void setFollow(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        if (currUser.getCurrentSelect() == null) {
            this.setMessage("Please select a source before following or unfollowing.");
            return;
        }
        if (currUser.getTypeSelected() != 2) {
            this.setMessage("The selected source is not a playlist.");
            return;
        }
        if (currUser.getUsername().equals(currUser.getSelectedPlaylist().getUser())) {
            this.setMessage("You cannot follow or unfollow your own playlist.");
            return;
        }

        ArrayList<Playlist> everyPlaylist = Playlists.getPlaylists();

        int indexPlaylist = everyPlaylist.indexOf(currUser.getSelectedPlaylist());

        if (currUser.getFollowedPlaylists().contains(currUser.getSelectedPlaylist())) {

            everyPlaylist.get(indexPlaylist).decrementFollowers();
            currUser.getFollowedPlaylists().remove(currUser.getSelectedPlaylist());
            this.setMessage("Playlist unfollowed successfully.");

        } else {
            everyPlaylist.get(indexPlaylist).incrementFollowers();
            currUser.getFollowedPlaylists().add(currUser.getSelectedPlaylist());
            this.setMessage("Playlist followed successfully.");
        }
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}
