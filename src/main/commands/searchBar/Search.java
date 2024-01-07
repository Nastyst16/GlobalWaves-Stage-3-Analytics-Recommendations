package main.commands.searchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Albums;
import main.collections.Artists;
import main.collections.Hosts;
import main.collections.Playlists;
import main.collections.Podcasts;
import main.commands.types.*;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

import java.lang.reflect.Array;
import java.util.*;

public class Search implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String type;
    private final Map<String, Object> filters;

    private String message;
    private ArrayList<String> results;
    @JsonIgnore
    private Song resultsType;
    @JsonIgnore
    private ArrayList<String> resultsAlbum;
    private static final int MAX_SIZE = 5;
    private static final int PODCAST = 1;
    private static final int PLAYLIST = 2;
    private static final int ALBUM = 3;
    private static final int ARTIST = 4;
    private static final int HOST = 5;

    /**
     * This method is used to execute the command.
     */
    public void execute(final User currUser) {

        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        this.setSearch(currUser);
    }


    /**
     * This method is used to accept the visitor.
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * This is the constructor of the Search class.
     * @param input the input
     */
    public Search(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.type = input.getType();
        this.filters = input.getFilters();

//        initialize the results array
        this.results = new ArrayList<>();
        this.resultsAlbum = new ArrayList<>();
    }


    /**
     * searching by song type
     * @param songs every song
     */
    public void searchingBySongType(final ArrayList<Song> songs) {
        String songPrefix = (String) (filters.get("name"));
        if (songPrefix != null) {
            songPrefix = songPrefix.toLowerCase();
        }

        String album = (String) (filters.get("album"));
        if (album != null) {
            album = album.toLowerCase();
        }

        String lyrics = (String) (filters.get("lyrics"));
//        converting to lowerCase
        if (lyrics != null) {
            lyrics = lyrics.toLowerCase();
        }
        String genre = (String) (filters.get("genre"));
        if (genre != null) {
            genre = genre.toLowerCase();
        }

        List<String> tags = (List<String>) (filters.get("tags"));
        if (tags != null) {
            for (int i = 0; i < tags.size(); i++) {
                tags.set(i, tags.get(i).toLowerCase());
            }
        }

        String releaseYear = (String) (filters.get("releaseYear"));
        String operator = null;
        int targetYear = 0;

        if (releaseYear != null) {
//              operator can be <, >, or =
            operator = releaseYear.substring(0, 1);
//              target year is the year that the user wants to compare to
            targetYear = Integer.parseInt(releaseYear.substring(1));
        }

        String artist = (String) (filters.get("artist"));

        ArrayList<String> result = new ArrayList<>();

        for (Song song : songs) {
            String songLyrics = song.getLyrics().toLowerCase();

//            if the song matches the filters, add it to the results
            if ((songPrefix == null || song.getName().toLowerCase().startsWith(songPrefix))
                    && (album == null || song.getAlbum().toLowerCase().equals(album))
                    && (lyrics == null || songLyrics.toLowerCase().contains(lyrics))
                    && (genre == null || song.getGenre().equalsIgnoreCase(genre))
                    && (tags == null || song.getTags().containsAll(tags))
                    && (artist == null || song.getArtist().equalsIgnoreCase(artist))
                    && (releaseYear == null || (operator.equals("<")
                    && song.getReleaseYear() < targetYear)
                    || (operator.equals(">") && song.getReleaseYear() > targetYear)
                    || (operator.equals("=") && song.getReleaseYear() == targetYear))) {
                result.add(song.getName());
                resultsAlbum.add(song.getAlbum());

                resultsType = song;
            }

//            maximum size of 5
            if (result.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(result);
        this.setMessage("Search returned " + result.size() + " results");
    }

    /**
     * searching by podcast type
     */
    public void searchingByPodcastType() {

        String podcastPrefix = (String) (filters.get("name"));
        String owner = (String) (filters.get("owner"));

        for (Podcast podcast : Podcasts.getPodcasts()) {
            if ((podcastPrefix == null || podcast.getName().startsWith(podcastPrefix))
                    && (owner == null || podcast.getOwner().equals(owner))) {
                results.add(podcast.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by playlist type
     */
    public void searchingByPlaylistType() {
        String owner = (String) (filters.get("owner"));
        String name = (String) (filters.get("name"));

        for (Playlist playlist : Playlists.getPlaylists()) {
            if (playlist.getVisibility().equals("private") && !playlist.getUser().equals(user)) {
                continue;
            }
            if ((owner == null || playlist.getUser().equals(owner))
                && (name == null || playlist.getName().startsWith(name))) {
                results.add(playlist.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);

        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by album type
     * @param albums every album
     */
    public void searchingByAlbum(final ArrayList<Album> albums) {
        String name = (String) (filters.get("name"));
        String owner = (String) (filters.get("owner"));
        String description = (String) (filters.get("description"));

        for (Album album : albums) {

            if (album.getName().equalsIgnoreCase("greatest hits II")) {
                int x = 5;
            }


            if ((name == null || album.getName().startsWith(name))
                    && (owner == null || album.getUser().equals(owner))
                    && (description == null || album.getDescription().equals(description))) {
                results.add(album.getName());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }


    /**
     * searching by artist type
     */
    public void searchingByArtist() {
        String name = (String) (filters.get("name"));

        for (Artist artist : Artists.getArtists()) {
            if (artist.getUsername().startsWith(name)) {
                results.add(artist.getUsername());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }

    /**
     * searching by host type
     */
    public void searchingByHost() {
        String name = (String) (filters.get("name"));

        for (Host host : Hosts.getHosts()) {
            if (host.getUsername().startsWith(name)) {
                results.add(host.getUsername());
            }

            if (results.size() == MAX_SIZE) {
                break;
            }
        }

        this.setResults(results);
        this.setMessage("Search returned " + results.size() + " results");
    }


    /**
     * This method is used to set the search.
     * @param currUser the current user
     */
    public void setSearch(final User currUser) {

//                if only type is songs:
        if (this.type.equals("song")) {
            this.searchingBySongType(currUser.getEverySong());
            currUser.setTypeFoundBySearch(0);
            currUser.setSearchedSong(this.resultsType);
        }

//                if only type is podcasts:
        if (this.type.equals("podcast")) {
            this.searchingByPodcastType();
            currUser.setTypeFoundBySearch(PODCAST);
        }

//                if only type is playlist:
        if (this.type.equals("playlist")) {
            this.searchingByPlaylistType();
            currUser.setTypeFoundBySearch(PLAYLIST);
        }

//        if only type is album
        if (this.type.equals("album")) {
            this.searchingByAlbum(Albums.getAlbums());
            currUser.setTypeFoundBySearch(ALBUM);
        }

//        if only type is artist:
        if (this.type.equals("artist")) {
            this.searchingByArtist();
            currUser.setTypeFoundBySearch(ARTIST);
        }

//        if only type is album
        if (this.type.equals("host")) {
            this.searchingByHost();
            currUser.setTypeFoundBySearch(HOST);
        }


        currUser.setCurrentSearch(this);
        currUser.setTypeSelected(-1);
        currUser.setCurrentType(null);
        currUser.setCurrentPodcast(null);
        currUser.setCurrentPlaylist(null);

        currUser.setTypeLoaded(-1);
        currUser.setShuffle(false);


        currUser.setTypeLoaded(-1);
        currUser.setRepeatString("No Repeat");
    }


    /**
     * This method is used to get the user of the command.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * This method is used to get the timestamp of the command.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method is used to get the results of the command.
     * @return the results
     */
    public ArrayList<String> getResults() {
        return results;
    }

    /**
     * This method is used to set the results of the command.
     * @param results the results
     */
    public void setResults(final ArrayList<String> results) {
        this.results = results;
    }

    /**
     * This method is used to get the command of the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * This method is used to get the type of the command.
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return type;
    }

    /**
     * This method is used to get the filters of the command.
     * @return the filters
     */
    @JsonIgnore
    public Map<String, Object> getFilters() {
        return filters;
    }

    public String getResultsAlbum(final int index) {
        return resultsAlbum.get(index);
    }

    public void setResultsAlbum(ArrayList<String> resultsAlbum) {
        this.resultsAlbum = resultsAlbum;
    }
}
