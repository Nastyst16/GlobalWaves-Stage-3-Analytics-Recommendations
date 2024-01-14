package main.collections;

import main.commands.types.Album;

import java.util.ArrayList;

public final class Albums {
    private static final Albums INSTANCE = new Albums();
    private final ArrayList<Album> everyAlbum = new ArrayList<>();

    /**
     * default constructor
     */
    private Albums() {

    }

    /**
     * get instance of the class
     */
    public static Albums getInstance() {
        return INSTANCE;
    }

    /**
     * resets the albums
     */
    public void reset() {
        everyAlbum.clear();
    }

    /**
     * adds an album to the list
     * @param album the album to add
     */
    public void addAlbum(final Album album) {
        everyAlbum.add(album);
    }

    /**
     * removes an album from the list
     * @param album the album to remove
     */
    public void removeAlbum(final Album album) {
        everyAlbum.remove(album);
    }

    /**
     * gets the list of albums
     * @return the list of albums
     */
    public ArrayList<Album> getAlbums() {
        return everyAlbum;
    }
}
