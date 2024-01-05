package main.collections;

import main.commands.types.Album;

import java.util.ArrayList;

public final class Albums {
    private static final ArrayList<Album> EVERYALBUM = new ArrayList<>();

    /**
     * default constructor
     */
    private Albums() {

    }

    /**
     * resets the albums
     */
    public static void reset() {
        EVERYALBUM.clear();
    }

    /**
     * adds an album to the list
     * @param album the album to add
     */
    public static void addAlbum(final Album album) {
        EVERYALBUM.add(album);
    }

    /**
     * removes an album from the list
     * @param album the album to remove
     */
    public static void removeAlbum(final Album album) {
        EVERYALBUM.remove(album);
    }

    /**
     * gets the list of albums
     * @return the list of albums
     */
    public static ArrayList<Album> getAlbums() {
        return EVERYALBUM;
    }
}
