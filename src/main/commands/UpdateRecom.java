package main.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.SearchBar;
import main.collections.Artists;
import main.collections.Songs;
import main.collections.Users;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.util.*;
import java.util.stream.Collectors;

public class UpdateRecom implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String recommendationType;
    private String message;

    public UpdateRecom(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.recommendationType = input.getRecommendationType();
    }

    /**
     * Method that accepts a visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Method that executes the command
     */
    public void execute(User user, Artist artist, Host host) {

        if (user == null) {
            this.setMessage(user.getUsername() + " does not exist!");
        }

        if (artist != null || host != null) {
            this.setMessage(this.user + " is not a normal user.");
        }

        String recommendationType = this.getRecommendationType();

        if (recommendationType.equals("random_song")) {
//            this.setRandomSong(user);
        } else if (recommendationType.equals("random_playlist")) {
//            this.setRandomPlaylist(user);
        } else if (recommendationType.equals("fans_playlist")) {
            this.setFansPlaylist(user);
        }

    }

    /**
     * Method that sets a random song as recommendation
     */
    public void setFansPlaylist(User user) {
        Playlist recommendationPlaylist = new Playlist();

        LinkedHashMap<User, Integer> top5FansCurrentSongArtist = new LinkedHashMap<>();

        Artist currentSongArtist = null;
//        searching for the artist of the current song
        for (Artist a : Artists.getArtists()) {
            if (a.getUsername().equals(((Song) user.getCurrentType()).getArtist())) {
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
        if (top5FansCurrentSongArtist.size() > 5) {
            int i = 0;
            for (Map.Entry<User, Integer> entry : top5FansCurrentSongArtist.entrySet()) {
                if (i >= 5) {
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

                if (i >= 5) {
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

        recommendationPlaylist.setUser(user.getUsername());
        recommendationPlaylist.setName(currentSongArtist.getUsername() + " Fan Club recommendations");

        user.setRecommendedPlaylist(recommendationPlaylist);
//        user.setRecommendedSongs(user.getLikedSongs());

        this.setMessage("The recommendations for user " + user.getUsername()
                + " have been updated successfully.");

        user.setCurrentRecommendation(recommendationPlaylist);
    }

    public static LinkedHashMap<User, Integer> sortMapByValuesDescending(LinkedHashMap<User, Integer> inputMap) {
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
