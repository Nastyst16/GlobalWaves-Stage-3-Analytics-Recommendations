package main.collections;

import main.users.Artist;

import java.util.ArrayList;

public final class Artists {
    private static final Artists INSTANCE = new Artists();
    private static final ArrayList<Artist> artists = new ArrayList<>();

    /**
     * Default constructor.
     */
    private Artists() {
    }

    /**
     * Gets the instance of the class.
     */

    /**
     * Resets the list of artists.
     */
    public static void reset() {
        artists.clear();
    }

    /**
     * Adds an artist to the list of artists.
     * @param artist the artist to be added
     */
    public static void addArtist(final Artist artist) {
        artists.add(artist);
    }

    /**
     * gets the list of artists.
     * @return the list of artists
     */
    public static ArrayList<Artist> getArtists() {
        return artists;
    }

    /**
     * Gets an artist by username.
     * @param username the username of the artist
     * @return the artist
     */
    public static Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
}
