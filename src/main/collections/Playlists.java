package main.collections;

import main.commands.types.Playlist;

import java.util.ArrayList;

public final class Playlists {
    private static final Playlists INSTANCE = new Playlists();
    private static final ArrayList<Playlist> PLAYLISTS = new ArrayList<>();

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
    public static void reset() {
        PLAYLISTS.clear();
    }

    /**
     * method that adds a playlist to the list of playlists
     * @param playlist the playlist to be added
     */
    public static void addPlaylist(final Playlist playlist) {
        PLAYLISTS.add(playlist);
    }

    /**
     * method that removes a playlist from the list of playlists
     * @param playlist the playlist to be removed
     */
    public static void removePlaylist(final Playlist playlist) {
        PLAYLISTS.remove(playlist);
    }

    /**
     * method that returns the list of playlists
     * @return the list of playlists
     */
    public static ArrayList<Playlist> getPlaylists() {
        return PLAYLISTS;
    }
}
