package main.commands.player;

import main.collections.Playlists;
import main.collections.Podcasts;
import main.inputCommand.CommandVisitor;
import main.inputCommand.Command;
import main.SearchBar;
import main.commands.types.Playlist;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.commands.types.Album;
import main.commands.types.Episode;
import main.users.User;
import java.util.ArrayList;

import main.collections.Albums;

public final class Load implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    private static final int SONG = 0;
    private static final int PODCAST = 1;
    private static final int PLAYLIST = 2;
    private static final int ALBUM = 3;

    /**
     * execute method for load
     * @param currUser the current user
     */
    public void execute(final User currUser) {
        this.setLoad(currUser);
    }

    /**
     * accept method for visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Load(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.message = "You can't load an empty audio collection!";
    }

    /**
     * Constructor
     */
    public Load(Song song) {
        this.command = "load";
        this.user = "user";
        this.timestamp = 0;
        this.message = "You can't load an empty audio collection!";
    }

    /**
     * Constructor
     */
    public Load(Playlist playlist) {
        this.command = "load";
        this.user = "user";
        this.timestamp = 0;
        this.message = "You can't load an empty audio collection!";
    }

    /** load command method
     *
     * @param currUser current user
     */
    public void setLoad(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

//          if the last command was select
        if (currUser.getCurrentSelect() != null) {
//          if the last selection was successful we can do the load
//          boolean selectSuccessful = currentSelect.getMessage().contains("Successfully");
        boolean selectSuccessful = currUser.getCurrentSelect().
                getMessage().contains("Successfully");

        if (selectSuccessful) {
            this.message = "Playback loaded successfully.";

            preparingStats(currUser);

            if (currUser.getTypeSelected() == SONG) {
                loadSong(currUser);

            } else if (currUser.getTypeSelected() == PODCAST) {
                loadPodcast(currUser, Podcasts.getPodcasts());

            } else if (currUser.getTypeSelected() == PLAYLIST) {
                loadPlaylist(currUser);

            } else if (currUser.getTypeSelected() == ALBUM) {
                loadAlbum(currUser, Albums.getAlbums());
            }

        } else {
            this.message = "Please select the song first.";
        }
    } else {
        this.message = "Please select a source before attempting to load.";
    }
    if (currUser.getCurrentType() != null) {
        currUser.setSelectedName((String) (currUser.getCurrentType().getName()));
    }

    currUser.setCurrentSelect(null);
}

    /**
     * this method loads a playlist
     * when load it is important to deep-copy the playlist
     * @param currUser the current user
     */
    public void loadPlaylist(final User currUser) {
        for (Playlist playlist : Playlists.getPlaylists()) {
            if (playlist.getName().equals(currUser.
                    getCurrentSelect().getSelectedName())) {
                currUser.setTypeLoaded(2);

//                        deepcopy currUser.currentPlaylist
                ArrayList<Song> songsCopy = new ArrayList<>();
                for (Song song : playlist.getSongList()) {
                    songsCopy.add(new Song(song.getName(), song.getDuration(),
                            song.getAlbum(), song.getTags(), song.getLyrics(),
                            song.getGenre(), song.getReleaseYear(), song.getArtist()));
                }
                Playlist playlistCopy = new Playlist(playlist.getUser(),
                        playlist.getName(), songsCopy);

                for (Song song : playlistCopy.getSongList()) {
                    song.setSecondsGone(0);
                }

                currUser.setCurrentPlaylist(playlistCopy);
                currUser.setCurrentType(playlistCopy.getSongList().get(0));
                currUser.setRemainingTime(currUser.getCurrentType().getDuration());
                currUser.getCurrentType().setSecondsGone(0);

                break;
            }
        }
    }

    /**
     * this method loads an album
     * when load it is important to deepcopy the album
     * @param currUser the current user
     * @param everyAlbum every album
     */
    public void loadAlbum(final User currUser, final ArrayList<Album> everyAlbum) {
//         albums
        for (Album album : everyAlbum) {
            if (album.getName().equals(currUser.
                    getCurrentSelect().getSelectedName())) {
                currUser.setTypeLoaded(PLAYLIST);

//                        deepcopy album
                ArrayList<Song> songsCopy = new ArrayList<>();
                for (Song song : album.getAlbumSongs()) {
                    songsCopy.add(new Song(song.getName(), song.getDuration(),
                            song.getAlbum(), song.getTags(), song.getLyrics(),
                            song.getGenre(), song.getReleaseYear(), song.getArtist()));
                }
                Album albumCopy = new Album(album.getUser(), album.getName(),
                        album.getReleaseYear(), album.getDescription(), songsCopy);
                for (Song song : albumCopy.getAlbumSongs()) {
                    song.setSecondsGone(0);
                }

                currUser.setCurrentPlaylist(albumCopy);
                currUser.setCurrentType(albumCopy.getAlbumSongs().get(0));
                currUser.setRemainingTime(currUser.getCurrentType().getDuration());
                currUser.getCurrentType().setSecondsGone(0);

                break;
            }
        }
    }

    /**
     * this method loads a song
     * @param currUser the current user
     */
    public void loadSong(final User currUser) {
        for (Song song : currUser.getEverySong()) {
            if (song.getName().equals(currUser.getCurrentSelect().getSelectedName())
                && song.getAlbum().equals(currUser.getSelectedAlbum())) {

                if (currUser.getSelectedAlbum().equals("Chuck Berry Is On Top")) {
                    int x = 5;
                }

//                        deepCopy the song
                Song songCopy = new Song(song.getName(), song.getDuration(),
                        song.getAlbum(), song.getTags(), song.getLyrics(),
                        song.getGenre(), song.getReleaseYear(), song.getArtist());

                currUser.setCurrentType(songCopy);
                currUser.getCurrentType().setSecondsGone(0);
                currUser.setTypeLoaded(0);
                break;
            }
        }
    }

    /**
     * this method loads a podcast
     * verifying if the podcast was already played
     * if it was played it loads the last episode watched
     * if it wasn't played it loads the first episode
     * @param currUser the current user
     * @param podcasts every podcast
     */
    public void loadPodcast(final User currUser, final ArrayList<Podcast> podcasts) {

        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(currUser.
                    getCurrentSelect().getSelectedName())) {

                if (currUser.getPodcastsPlayed().contains(podcast)) {
                    int indexPodcast = currUser.getPodcastsPlayed().indexOf(podcast);

                    currUser.setCurrentType(currUser.getPodcastsPlayed().
                            get(indexPodcast));
                    int indexEpisode = ((Podcast) (currUser.getCurrentType())).
                            getLastRemainingEpisode();
                    currUser.setCurrentType(((Podcast) (currUser.
                            getCurrentType())).getEpisodesList().get(indexEpisode));

                    currUser.setCurrentPodcast(podcast);

                } else {
//                            adding to the currUser the loaded podcast
//                            finding the podcast in the currUser's podcasts
                    Podcast p = null;
                    for (Podcast podcast1 : currUser.getEveryPodcast()) {
                        if (podcast1.getName().equals(podcast.getName())) {
                            p = podcast1;
                            break;
                        }
                    }

                    ArrayList<String> episodesNames = new ArrayList<>();
                    for (Episode e : p.getEpisodesList()) {
                        episodesNames.add(e.getName());
                    }
                    p.setEpisodes(episodesNames);

                    currUser.addPodcastPlayed(p);

                    int lastPodcast = currUser.getPodcastsPlayed().size() - 1;
                    int lastEpisode = currUser.getPodcastsPlayed().
                            get(lastPodcast).getEpisodes().size() - 1;

//                              setting last episode watched to 0
                    currUser.getPodcastsPlayed().get(lastPodcast).
                            setLastRemainingEpisode(0);

//                              setting the remaining second;
                    currUser.getPodcastsPlayed().get(lastPodcast).getEpisodesList().
                            get(lastEpisode).setSecondsGone(0);

                    currUser.setRemainingTime(podcast.getEpisodesList().
                            get(lastEpisode).getDuration());

//                               current type is Podcast
                    currUser.setCurrentType(currUser.getPodcastsPlayed().get(lastPodcast));

//                               current type is Episode
                    currUser.setCurrentType(((Podcast) (currUser.getCurrentType())).
                            getEpisodesList().get(0));

                    currUser.setCurrentPodcast(podcast);
                }
                currUser.setTypeLoaded(1);
                break;
            }
        }
    }

    /**
     * preparing stats for user
     * @param currUser the current user
     */
    public void preparingStats(final User currUser) {
        currUser.setLoadMade(1);
        currUser.setPaused(false);
        currUser.setRepeatStatus(0);
        currUser.setRepeatString("No Repeat");
        currUser.setSecondsGone(0);
    }

    /**
     * gets the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     *
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
