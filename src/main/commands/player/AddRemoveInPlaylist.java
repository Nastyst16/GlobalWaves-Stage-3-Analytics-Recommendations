package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Song;
import main.SearchBar;
import main.users.User;

/**
 * Represents a command to add or remove a song in a playlist.
 * Implements the {@link Command} interface for execution.
 */
public final class AddRemoveInPlaylist implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int playlistId;
    private String message;


    /**
     * Executes the command to add or remove a song in a playlist.
     * calls the setMessage method to set the message based on the execution of the command.
     */
    public void execute(final SearchBar input, final User currUser) {
//        setting message
        this.setMessage(currUser, input.getPlaylistId());
    }

    /**
     * Accepts a visitor to this command
     * and calls the visit method of the visitor.
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * constructor for the AddRemoveInPlaylist class
     * @param input the input from the user
     */
    public AddRemoveInPlaylist(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.playlistId = input.getPlaylistId();
    }

    /**
     * Sets the message based on the execution of the command.
     *
     * @param currUser The current user issuing the command.
     * @param index       The index of the playlist in the user's playlist list.
     */
    public void setMessage(final User currUser, final int index) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (currUser.getTypeLoaded() == 1) {
            this.message = "The loaded source is not a song.";
            return;
        }

        if (currUser.getCurrentType() == null) {
            this.message = "Please load a source before adding to or removing from the playlist.";
        } else {
            if (index > currUser.getPlayListList().size()) {
                this.message = "The specified playlist does not exist.";
            } else {
                if (currUser.getTypeSelected() == 2
                        || currUser.getTypeSelected() == 1) {
                    this.message = "The loaded source is not a song.";
                } else {
                    this.message = currUser.getPlayListList().get(index - 1).
                            addRemoveSong((Song) currUser.getCurrentType());
                }
            }
        }
    }

    /**
     * Gets the command string associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The command string.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the user associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The user string.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the timestamp associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the playlist ID associated with this {@code AddRemoveInPlaylist} instance.
     *
     * @return The playlist ID.
     */
    @JsonIgnore
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     * Gets the message associated with the execution of the command.
     *
     * @return The message string.
     */
    public String getMessage() {
        return message;
    }

}
