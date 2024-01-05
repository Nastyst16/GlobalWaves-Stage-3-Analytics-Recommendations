package main.collections;

import fileio.input.LibraryInput;
import fileio.input.SongInput;
import main.commands.types.Song;

import java.util.ArrayList;

public final class Songs {
    private static final ArrayList<Song> EVERYSONG = new ArrayList<>();

    /**
     * default constructor
     */
    private Songs() {

    }

    /**
     * store the songs from the input
     * @param library the input
     */
    public static void storeSongs(final LibraryInput library) {
        ArrayList<SongInput> songInputs = library.getSongs();

//        storing songs
        for (SongInput songInput : songInputs) {
            Songs.addSong(new Song(songInput.getName(), songInput.getDuration(),
                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
                    songInput.getGenre(), songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * reset the songs after every test
     */
    public static void reset() {
        EVERYSONG.clear();
    }

    /**
     * add a song to the list
     * @param song the song to be added
     */
    public static void addSong(final Song song) {
        EVERYSONG.add(song);
    }

    /**
     * get the list of songs
     * @return the list of songs
     */
    public static ArrayList<Song> getSongs() {
        return EVERYSONG;
    }

    /**
     * remove a song from the list
     * @param song the song to be removed
     */
    public static void removeSong(final Song song) {
        EVERYSONG.remove(song);
    }
}
