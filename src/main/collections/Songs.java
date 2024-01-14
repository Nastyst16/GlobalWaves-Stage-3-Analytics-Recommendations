package main.collections;

import fileio.input.LibraryInput;
import fileio.input.SongInput;
import main.commands.types.Song;

import java.util.ArrayList;

public final class Songs {
    private static final Songs INSTANCE = new Songs();
    private final ArrayList<Song> everySong = new ArrayList<>();

    /**
     * default constructor
     */
    private Songs() {

    }

    /**
     * get the instance of the class
     */
    public static Songs getInstance() {
        return INSTANCE;
    }

    /**
     * store the songs from the input
     * @param library the input
     */
    public void storeSongs(final LibraryInput library) {
        ArrayList<SongInput> songInputs = library.getSongs();

//        storing songs
        for (SongInput songInput : songInputs) {
            Songs.getInstance().addSong(new Song(songInput.getName(), songInput.getDuration(),
                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
                    songInput.getGenre(), songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * reset the songs after every test
     */
    public void reset() {
        everySong.clear();
    }

    /**
     * add a song to the list
     * @param song the song to be added
     */
    public void addSong(final Song song) {
        everySong.add(song);
    }

    /**
     * get the list of songs
     * @return the list of songs
     */
    public ArrayList<Song> getSongs() {
        return everySong;
    }

    /**
     * remove a song from the list
     * @param song the song to be removed
     */
    public void removeSong(final Song song) {
        everySong.remove(song);
    }
}
