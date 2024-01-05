package main.commands.pageSystem;

import main.collections.Artists;
import main.collections.Hosts;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Song;
import main.commands.types.Playlist;
import main.commands.types.Album;
import main.commands.types.Merch;
import main.commands.types.Event;
import main.commands.types.Podcast;
import main.commands.types.Episode;
import main.commands.types.Announcement;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;
import java.util.Comparator;

public final class PrintCurrentPage implements Command {
    private final String user;
    private final String command;
    private final int timestamp;
    private String message;

    private static final int MAX_RESULTS = 5;

    /**
     * executes the command
     * calls the setPrintCurrPage method
     */
    public void execute(final User currUser) {
        this.setPrintCurrPage(currUser);
    }

    /**
     * sets the message to be printed for the current page
     *
     * @param currUser the current user
     */
    public void setPrintCurrPage(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

//        if the current page is Home
        if (currUser.getCurrentPage().equals("Home")) {
            printHome(currUser);

//          if the current page is LikedContent
        } else if (currUser.getCurrentPage().equals("LikedContent")) {
            printLikedContent(currUser);

//          if the current page is Artist
        } else if (currUser.getCurrentPage().equals("Artist")) {
            printArtist(currUser);

//          if the current page is Host
        } else if (currUser.getCurrentPage().equals("Host")) {
            printHost(currUser);
        }
    }

    /**
     * prints the home page of the user (liked songs and followed playlists)
     * @param currUser the current user
     */
    private void printHome(final User currUser) {

        ArrayList<String> likedSongs = new ArrayList<>();
        ArrayList<String> followedPlaylists = new ArrayList<>();


        ArrayList<Song> sortedSongsByNoLikes = new ArrayList<>(currUser.getLikedSongs());

        sortedSongsByNoLikes.sort(Comparator.comparingInt(Song::getNumberOfLikes).reversed());


        int i = 0;
        for (Song song : sortedSongsByNoLikes) {
            likedSongs.add(song.getName());

            i++;
            if (i == MAX_RESULTS) {
                break;
            }
        }
        for (Playlist playlist : currUser.getFollowedPlaylists()) {
            followedPlaylists.add(playlist.getName());
        }

        this.message = "Liked songs:\n\t" + likedSongs + "\n\n"
                + "Followed playlists:\n\t" + followedPlaylists;
    }

    /**
     * prints the liked content of the user (liked songs and followed playlists)
     * @param currUser the current user
     */
    private void printLikedContent(final User currUser) {

        StringBuilder likedSongs = new StringBuilder();

        for (Song song : currUser.getLikedSongs()) {
            likedSongs.append(song.getName())
                    .append(" - ")
                    .append(song.getArtist());

            if (currUser.getLikedSongs().indexOf(song) != currUser.getLikedSongs().size() - 1) {
                likedSongs.append(", ");
            }
        }

        StringBuilder followedPlaylists = new StringBuilder();
        for (Playlist playlist : currUser.getFollowedPlaylists()) {
            followedPlaylists.append(playlist.getName())
                    .append(" - ")
                    .append(playlist.getUser());

            if (currUser.getFollowedPlaylists().indexOf(playlist)
                    != currUser.getFollowedPlaylists().size() - 1) {
                followedPlaylists.append(", ");
            }
        }

        this.message = "Liked songs:\n\t[" + likedSongs + "]\n\n"
                + "Followed playlists:\n\t[" + followedPlaylists + "]";
    }

    /**
     * prints the artist page of the user (albums, merchandise and events)
     *
     * @param currUser the current user
     */
    private void printArtist(final User currUser) {

        Artist currentArtist = Artists.getArtist(currUser.getSelectedPageOwner());

        ArrayList<String> albumsByName = new ArrayList<>();
        StringBuilder merchByName = new StringBuilder();
        StringBuilder eventsBuilder = new StringBuilder();

        for (Album album : currentArtist.getAlbums()) {
            albumsByName.add(album.getName());
        }
        for (Merch merch : currentArtist.getMerchandise()) {
            merchByName.append(merch.getName())
                    .append(" - ")
                    .append(merch.getPrice())
                    .append(":\n\t")
                    .append(merch.getDescription());

            if (currentArtist.getMerchandise().indexOf(merch)
                    != currentArtist.getMerchandise().size() - 1) {
                merchByName.append(", ");
            }
        }

        for (Event event : currentArtist.getEvents()) {
            // if it's the last event -> to avoid the ", " at the end of the last event
            eventsBuilder.append(event.getName())
                    .append(" - ")
                    .append(event.getDate())
                    .append(":\n\t")
                    .append(event.getDescription());

            if (currentArtist.getEvents().indexOf(event)
                    != currentArtist.getEvents().size() - 1) {
                eventsBuilder.append(", ");
            }
        }

        this.message = "Albums:\n\t" + albumsByName + "\n\n"
                + "Merch:\n\t[" + merchByName + "]\n\n"
                + "Events:\n\t[" + eventsBuilder.toString() + "]";
    }

    /**
     * prints the host page of the user (podcasts and announcements)
     *
     * @param currUser the current user
     */
    private void printHost(final User currUser) {

        Host currentHost = Hosts.getHost(currUser.getSelectedPageOwner());

        StringBuilder podcastsByName = new StringBuilder();
        StringBuilder announcementsByName = new StringBuilder();

        for (Podcast podcast : currentHost.getHostPodcasts()) {
            podcastsByName.append(podcast.getName())
                    .append(":\n\t[");

            for (Episode episode : podcast.getEpisodesList()) {
                podcastsByName.append(episode.getName())
                        .append(" - ")
                        .append(episode.getDescription());

                if (podcast.getEpisodesList().indexOf(episode)
                        != podcast.getEpisodes().size() - 1) {
                    podcastsByName.append(", ");
                }
            }

            if (currentHost.getHostPodcasts().indexOf(podcast)
                    != currentHost.getHostPodcasts().size() - 1) {
                podcastsByName.append("]\n, ");
            }
        }
        podcastsByName.append("]");

        for (Announcement announcement : currentHost.getAnnouncements()) {
            announcementsByName.append(announcement.getName())
                    .append(":\n\t")
                    .append(announcement.getDescription())
                    .append("\n");

            if (currentHost.getAnnouncements().indexOf(announcement)
                    != currentHost.getAnnouncements().size() - 1) {
                announcementsByName.append(", ");
            }
        }

        this.message = "Podcasts:\n\t[" + podcastsByName + "\n]\n\n"
                + "Announcements:\n\t[" + announcementsByName + "]";
    }


    public PrintCurrentPage(final SearchBar input) {
        this.user = input.getUsername();
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }

    /**
     * accepts the visitor for the visitor pattern
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * get the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
