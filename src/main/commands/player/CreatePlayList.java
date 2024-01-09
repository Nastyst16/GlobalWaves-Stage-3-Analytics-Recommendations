package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Playlists;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Playlist;
import main.SearchBar;
import main.users.Artist;
import main.users.Host;
import main.users.User;

public final class CreatePlayList implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String playlistName;
    private String message;
    private  Playlist playlist;

    /**
     * executes the command CreatePlayList
     */
    public void execute(final Object... params) {
        SearchBar input = (SearchBar) params[0];
        User currUser = (User) params[1];

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

//                verify if a playlist with the same name exists;
        String msg = "Playlist created successfully.";
        for (Playlist p : Playlists.getPlaylists()) {
            if (p.getName().equals(input.getPlaylistName())) {
                msg = "A playlist with the same name already exists.";
            }
        }

        this.setMessage(msg);

        if (!msg.equals("A playlist with the same name already exists.")) {
            currUser.addPlaylistToList(this.getPlaylist());
            Playlists.addPlaylist(this.getPlaylist());
        }

    }

    /**
     * constructor for the command CreatePlayList
     * @param input the input given
     */
    public CreatePlayList(final SearchBar input) {

        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = null;

        this.playlist = new Playlist(input.getPlaylistName(), user);
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
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * @param playlistName the playlistName to set
     */
    @JsonIgnore
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    /**
     * @param playlist the playlist to set
     */
    @JsonIgnore
    public void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }


}
