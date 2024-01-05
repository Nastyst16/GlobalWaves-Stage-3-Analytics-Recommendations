package main.commands.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Album extends Playlist {
    private final String user;
    private final String name;
    private final int releaseYear;
    private final String description;
    private final ArrayList<Song> albumSongs;
    private final ArrayList<String> songs;
    @JsonIgnore
    private String visibility;
    @JsonIgnore
    private int followers;


    public Album(final String user, final String name, final int releaseYear,
                 final String description, final ArrayList<Song> albumSongs) {
        super(user, name, albumSongs);
        this.user = user;
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.albumSongs = albumSongs;

        this.songs = new ArrayList<>();

        for (Song song : albumSongs) {
            this.songs.add(song.getName());
        }
    }




    /**
     * getter for the user that created the album
     * @return the user that created the album
     */
    @JsonIgnore
    public String getUser() {
        return user;
    }


    /**
     * getter for the name of the album
     * @return the name of the album
     */
    public String getName() {
        return name;
    }


    /**
     * getter for the release year of the album
     * @return the release year of the album
     */
    @JsonIgnore
    public int getReleaseYear() {
        return releaseYear;
    }


    /**
     * getter for the description of the album
     * @return the description of the album
     */
    @JsonIgnore
    public String getDescription() {
        return description;
    }


    /**
     * getter for the songs of the album
     * @return the songs of the album
     */
    @JsonIgnore
    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }

    /**
     * getter for the songs of the album
     * @return the songs of the album
     */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /**
     * getter for the visibility of the album
     * @return the visibility of the album
     */
    @Override
    public String getVisibility() {
        return visibility;
    }

    /**
     * setter for the visibility of the album
     * @param visibility the visibility of the album
     */
    @Override
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    /**
     * getter for the followers of the album
     * @return the followers of the album
     */
    @Override
    public int getFollowers() {
        return followers;
    }

    /**
     * setter for the followers of the album
     * @param followers the followers of the album
     */
    @Override
    public void setFollowers(final int followers) {
        this.followers = followers;
    }
}
