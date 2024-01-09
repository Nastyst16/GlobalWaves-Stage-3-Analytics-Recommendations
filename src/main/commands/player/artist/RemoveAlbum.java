package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Albums;
import main.collections.Songs;
import main.collections.Users;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.Iterator;

import static main.users.Host.HOST_PARAM;

public final class RemoveAlbum implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;

    /**
     * executes the RemoveAlbum command
     */
    public void execute(final Object... params) {
        this.setRemoveAlbum((User) params[1], (Artist) params[2], (Host) params[HOST_PARAM]);
    }

    /**
     * sets the message of the RemoveAlbum command
     * @param currUser the current user
     * @param artist the artist
     * @param host the host
     */
    public void setRemoveAlbum(final User currUser, final Artist artist, final Host host) {


        if (currUser != null || host != null) {
            this.setMessage(this.user + " is not an artist.");
            return;
        } else if (artist == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        verifying if the album exists
        Album albumToRemove = null;
        boolean exists = false;
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                albumToRemove = album;
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.setMessage(this.user + " doesn't have an album with the given name.");
            return;
        }


//        verifying if a users currently listens to the album
        for (User currentUser : Users.getUsers()) {
            if (currentUser.getCurrentType() != null && currentUser.getCurrentPlaylist() != null) {
                if (currentUser.getCurrentPlaylist().getName().equals(this.name)) {
                    this.setMessage(this.user + " can't delete this album.");
                    return;
                }

//                if the current playlist contains some of the songs we want to delete
                for (Song s : currentUser.getCurrentPlaylist().getSongList()) {

                    for (Song albumSong : albumToRemove.getAlbumSongs()) {
                        if (s.getName().equals(albumSong.getName())
                                && s.getAlbum().equals(albumSong.getAlbum())) {
                            this.setMessage(this.user + " can't delete this album.");
                            return;
                        }
                    }
                }
            }
        }


//        removing the album
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                artist.getAlbums().remove(album);
                Albums.removeAlbum(album);

//                deleting also every song from the album
                Songs.getSongs().removeAll(album.getAlbumSongs());
                for (User u : Users.getUsers()) {
                    u.getLikedSongs().removeAll(album.getAlbumSongs());

                    for (Playlist playlist : u.getPlayListList()) {

                        Iterator<Song> iterator = playlist.getSongList().iterator();
                        while (iterator.hasNext()) {
                            Song song = iterator.next();
                            for (Song albumSong : album.getAlbumSongs()) {
                                if (song.getName().equals(albumSong.getName())
                                        && song.getAlbum().equals(albumSong.getAlbum())) {
                                    iterator.remove();
                                    playlist.getSongs().remove(song.getName());

                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        this.setMessage(this.user + " deleted the album successfully.");
    }

    /**
     * constructor for the RemoveAlbum command
     * @param input the input given by the user
     */
    public RemoveAlbum(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }

    /**
     * gets the message of the command
     * @return the message
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user of the command
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the message of the command
     * @return the message
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the name of the event
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the message of the command
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message of the command
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
