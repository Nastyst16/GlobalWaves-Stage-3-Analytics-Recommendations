package main.collections;

import main.commands.types.Playlist;

import java.util.ArrayList;

public final class Playlists {
    private static final Playlists INSTANCE = new Playlists();
    private final ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * default constructor for the Playlists class
     */
    private Playlists() {

    }

    /**
     * method that returns the instance of the class
     */
    public static Playlists getInstance() {
        return INSTANCE;
    }

    /**
     * method that resets the playlists
     */
    public void reset() {
        playlists.clear();
    }

    /**
     * method that adds a playlist to the list of playlists
     * @param playlist the playlist to be added
     */
    public void addPlaylist(final Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * method that removes a playlist from the list of playlists
     * @param playlist the playlist to be removed
     */
    public void removePlaylist(final Playlist playlist) {
        playlists.remove(playlist);
    }

    /**
     * method that returns the list of playlists
     * @return the list of playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
