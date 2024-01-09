package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Albums;
import main.collections.Songs;
import main.collections.Users;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.commands.types.Song;
import main.commands.types.Album;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

import static main.users.Host.HOST_PARAM;

public class AddAlbum implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final ArrayList<Song> albumSongs;
    private String message;


    /**
     * Constructor that sets the command to "add_album".
     * @param input the input given by the user
     */
    public AddAlbum(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.releaseYear = input.getReleaseYear();
        this.description = input.getDescription();
        this.albumSongs = input.getSongs();
    }

    /**
     * Method that executes the command
     * and adds a new album to the artist's albums.
     */
    public void execute(final Object... params) {
        this.addAlbum((User) params[1], (Artist) params[2], (Host) params[HOST_PARAM]);
    }

    /**
     * Method that adds a new album to the artist's albums.
     */
    public void addAlbum(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || host != null) {
            this.setMessage(this.user + " is not an artist.");
            return;
        } else if (artist == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        verifying if the album already exists
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(this.name)) {
                this.setMessage(this.user + " has another album with the same name.");
                return;
            }
        }

//        verifying if the album has duplicate songs
        for (int i = 0; i < albumSongs.size(); ++i) {
            for (int j = i + 1; j < albumSongs.size(); ++j) {
                if (albumSongs.get(i).getName().equals(albumSongs.get(j).getName())) {
                    this.setMessage(this.user + " has the same song at least twice in this album.");
                    return;
                }
            }
        }

        for (Song song : albumSongs) {
            Songs.addSong(song);
        }
        for (User u : Users.getUsers()) {

//            deep copy
            for (Song s : albumSongs) {
                u.getEverySong().add(new Song(s.getName(), s.getDuration(),
                        s.getAlbum(), s.getTags(), s.getAlbum(),
                        s.getGenre(), s.getReleaseYear(), s.getArtist()));

            }
        }

        Albums.addAlbum(new Album(this.user, this.name, this.releaseYear,
                this.description, this.albumSongs));


        for (User u : Users.getUsers()) {
            u.getEveryAlbum().add(new Album(this.user, this.name, this.releaseYear,
                    this.description, this.albumSongs));
        }


        artist.getAlbums().add(Albums.getAlbums().
                get(Albums.getAlbums().size() - 1));
        this.setMessage(this.user + " has added new album successfully.");

        artist.getNotificationService().notifyUsers("New Album",
                "New Album from " + this.user + ".");
    }

    /**
     * Method that returns the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the username
     * @return the username
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the name
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return name;
    }

    /**
     * getter for the release year
     * @return the release year
     */
    @JsonIgnore
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * getter for the description
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
