package main.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.SearchBar;
import main.collections.Artists;
import main.collections.Songs;
import main.collections.Users;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateRecom implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String recommendationType;
    private String message;
    private static final int MAX_TOP = 5;
    private static final int TOP_3 = 3;
    private static final int SECONDS = 30;
    private static final int HOST_PARAM = 3;

    public UpdateRecom(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.recommendationType = input.getRecommendationType();
    }

    /**
     * Method that executes the command
     */
    public void execute(final Object... params) {
        User currUser = (User) params[1];
        Artist currArtist = (Artist) params[2];
        Host currHost = (Host) params[HOST_PARAM];

        if (currUser == null) {
            this.setMessage(currUser.getUsername() + " does not exist!");
        }

        if (currArtist != null || currHost != null) {
            this.setMessage(this.user + " is not a normal user.");
        }

        String recommType = this.getRecommendationType();

        if (recommType.equals("random_song")) {
            this.setRandomSong(currUser);
        } else if (recommType.equals("random_playlist")) {
            this.setRandomPlaylist(currUser);
        } else if (recommType.equals("fans_playlist")) {
            this.setFansPlaylist(currUser);
        }

    }

    /**
     * Method that sets a random playlist as recommendation
     */
    public void setRandomPlaylist(final User currUser) {
        Playlist recommendationPlaylist = new Playlist();

        ArrayList<Song> everySongFromLikedSongsPlaylistsFollowedPlaylists = new ArrayList<>();
//        adding every song from the liked songs
        for (Song s : currUser.getLikedSongs()) {
            everySongFromLikedSongsPlaylistsFollowedPlaylists.add(s);
        }

//        adding every song from the playlists
        for (Playlist p : currUser.getPlayListList()) {
            for (Song s : p.getSongList()) {
//                if the song already exists in the list
                if (everySongFromLikedSongsPlaylistsFollowedPlaylists.contains(s)) {
                    continue;
                }
                everySongFromLikedSongsPlaylistsFollowedPlaylists.add(s);
            }
        }

//        adding every song from the followed playlists
        for (Playlist p : currUser.getFollowedPlaylists()) {
            for (Song s : p.getSongList()) {
//                if the song already exists in the list
                if (everySongFromLikedSongsPlaylistsFollowedPlaylists.contains(s)) {
                    continue;
                }
                everySongFromLikedSongsPlaylistsFollowedPlaylists.add(s);
            }
        }

        Map<String, Integer> userGenres = new HashMap<>();

        for (Song s : everySongFromLikedSongsPlaylistsFollowedPlaylists) {
            if (userGenres.containsKey(s.getGenre())) {
                userGenres.put(s.getGenre(), userGenres.get(s.getGenre()) + 1);
            } else {
                userGenres.put(s.getGenre(), 1);
            }
        }

//        sorting every song based on the total number of likes
        ArrayList<Song> everySongSortedByLikes = new ArrayList<>(Songs.getSongs());

        for (User u : Users.getUsers()) {
            for (Song s : u.getLikedSongs()) {
                int songIndex = everySongSortedByLikes.indexOf(s);
                everySongSortedByLikes.get(songIndex).setNumberOfLikes(everySongSortedByLikes.
                        get(songIndex).getNumberOfLikes() + 1);
            }
        }

//        sorting the songs descending based on the number of likes
        everySongSortedByLikes.sort((o1, o2) -> {
            if (o1.getNumberOfLikes() == o2.getNumberOfLikes()) {
                return o1.getName().compareTo(o2.getName());
            }
            return o2.getNumberOfLikes() - o1.getNumberOfLikes();
        });


//        removing the songs with 0 likes
        everySongSortedByLikes.removeIf(s -> s.getNumberOfLikes() == 0);

//        putting in recommendedPlaylist the first 5 songs from first genre
//        the first 3 songs from the second genre
//        the first 2 songs from the third genre
//        if there are not enough songs we don't add them
        int i = 0;
        int j = 0;
        int k = 0;
        for (Song s : everySongSortedByLikes) {
            if (i >= MAX_TOP && j >= TOP_3 && k >= 2) {
                break;
            }
            if (userGenres.containsKey(s.getGenre())) {
                if (userGenres.get(s.getGenre()) >= MAX_TOP && i < MAX_TOP) {
                    recommendationPlaylist.addSong(s);
                    i++;
                } else if (userGenres.get(s.getGenre()) >= TOP_3 && j < TOP_3) {
                    recommendationPlaylist.addSong(s);
                    j++;
                } else if (userGenres.get(s.getGenre()) >= 2 && k < 2) {
                    recommendationPlaylist.addSong(s);
                    k++;
                }
            }
        }

        recommendationPlaylist.setName(this.user + "'s recommendations");
        recommendationPlaylist.setUser(this.user);

        currUser.setRecommendedPlaylist(recommendationPlaylist);
        currUser.setCurrentRecommendation(recommendationPlaylist);

        this.setMessage("The recommendations for user " + currUser.getUsername()
                + " have been updated successfully.");
    }

    /**
     * Method that sets a random song as recommendation
     */
    public void setRandomSong(final User currUser) {

        if (currUser.getCurrentType().getSecondsGone() < SECONDS) {
            this.setMessage("The user has to listen to the current song for at least 30 seconds.");
            return;
        }

        int seed = currUser.getCurrentType().getSecondsGone();

        Random random = new Random(seed);

        String currentGenre = ((Song) currUser.getCurrentType()).getGenre();

        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : Songs.getSongs()) {
            if (s.getGenre().equals(currentGenre)) {
                songs.add(s);
            }
        }

//        if there are no songs with the same genre
        if (songs.size() == 0) {
            this.setMessage("There are no songs with the same genre as the current song.");
            return;
        }

//        picking the song based on the seed
        int index = random.nextInt(songs.size());
        Song randomSong = songs.get(index);

        currUser.setRecommendedSongs(randomSong);
        currUser.setCurrentRecommendation(randomSong);

        this.setMessage("The recommendations for user " + currUser.getUsername()
                + " have been updated successfully.");
    }

    /**
     * Method that sets a random song as recommendation
     */
    public void setFansPlaylist(final User currUser) {
        Playlist recommendationPlaylist = new Playlist();

        LinkedHashMap<User, Integer> top5FansCurrentSongArtist = new LinkedHashMap<>();

        Artist currentSongArtist = null;
//        searching for the artist of the current song
        for (Artist a : Artists.getArtists()) {
            if (a.getUsername().equals(((Song) currUser.getCurrentType()).getArtist())) {
                currentSongArtist = a;
                break;
            }
        }

        int numberOfLikes;
        for (User u : Users.getUsers()) {
            numberOfLikes = 0;

            for (Song s : u.getLikedSongs()) {
                if (s.getArtist().equals(currentSongArtist.getUsername())) {
                    numberOfLikes++;
                }
            }

            if (numberOfLikes > 0) {
                top5FansCurrentSongArtist.put(u, numberOfLikes);
            }

        }

//        sorting descending based on the integer
        top5FansCurrentSongArtist = sortMapByValuesDescending(top5FansCurrentSongArtist);

//        keeping only the first 5 users and removing the rest
        if (top5FansCurrentSongArtist.size() > MAX_TOP) {
            int i = 0;
            for (Map.Entry<User, Integer> entry : top5FansCurrentSongArtist.entrySet()) {
                if (i >= MAX_TOP) {
                    top5FansCurrentSongArtist.remove(entry.getKey());
                }
                i++;
            }
        }

        ArrayList<Song> everySong = Songs.getSongs();
//        sorting descending everySong based on the number of likes
        everySong.sort((o1, o2) -> {
            if (o1.getNumberOfLikes() == o2.getNumberOfLikes()) {
                return o1.getName().compareTo(o2.getName());
            }
            return o2.getNumberOfLikes() - o1.getNumberOfLikes();
        });

        ArrayList<ArrayList<Song>> top5SongsEveryUser = new ArrayList<>();

        for (Map.Entry<User, Integer> entry : top5FansCurrentSongArtist.entrySet()) {
            ArrayList<Song> top5Songs = new ArrayList<>();
            int i = 0;
            for (Song s : everySong) {

                if (s.getNumberOfLikes() == 0) {
                    break;
                }

                if (i >= MAX_TOP) {
                    break;
                }
                if (s.getArtist().equals(currentSongArtist.getUsername())) {
                    top5Songs.add(s);
                    i++;
                }
            }
            top5SongsEveryUser.add(top5Songs);
        }

//        adding the songs to the playlist
        for (ArrayList<Song> top5Songs : top5SongsEveryUser) {
            for (Song s : top5Songs) {
                if (recommendationPlaylist.getSongs().contains(s.getName())) {
                    continue;
                }
                recommendationPlaylist.addSong(s);
            }
        }

        recommendationPlaylist.setUser(currUser.getUsername());
        recommendationPlaylist.setName(currentSongArtist.getUsername()
                + " Fan Club recommendations");

        currUser.setRecommendedPlaylist(recommendationPlaylist);

        this.setMessage("The recommendations for user " + currUser.getUsername()
                + " have been updated successfully.");

        currUser.setCurrentRecommendation(recommendationPlaylist);
    }

    /**
     * Method that sorts a map by values descending
     */
    public static LinkedHashMap<User, Integer> sortMapByValuesDescending(final LinkedHashMap<User,
            Integer> inputMap) {

        List<Map.Entry<User, Integer>> sortedList = inputMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        LinkedHashMap<User, Integer> sortedMap = new LinkedHashMap<>();
        sortedList.forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        return sortedMap;
    }

    /**
     * Getter for the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Getter for the recommendation type
     */
    public String getRecommendationType() {
        return recommendationType;
    }
}
