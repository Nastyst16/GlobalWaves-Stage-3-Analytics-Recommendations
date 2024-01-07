package main.commands.player.statistics;

import main.SearchBar;
import main.collections.Artists;
import main.commands.searchBar.Search;
import main.commands.types.*;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.lang.reflect.Array;
import java.util.*;

public class Wrapped implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final Map<String, Object> result = new LinkedHashMap<>();



    /**
     * executes the command
     */
    public void execute(User user, Artist artist, Host host) {
        this.setWrapped(user, artist, host);
    }

    /**
     * setting wrapped
     */
    private void setWrapped(User currUser, Artist currArtist, Host currHost) {

        if (currUser != null) {
            wrappedUser(currUser);
        } else if (currArtist != null) {
            wrappedArtist(currArtist);
        } else if (currHost != null) {
            wrappedHost(currHost);
        }
    }

    private void wrappedUser(User currUser) {


//        printing the most 5 listened songs
        ArrayList<Artist> mostListenedArtists = new ArrayList<>();
        ArrayList<Song> mostListenedSongs = new ArrayList<>();
        ArrayList<Album> mostListenedAlbums = new ArrayList<>();
        ArrayList<Episode> mostListenedEpisodes = new ArrayList<>();


        for (Type t : currUser.getEverySong()) {
            mostListenedSongs.add((Song) t);
        }

        for (Album a : currUser.getEveryAlbum()) {
            mostListenedAlbums.add(a);
        }

        for (Podcast p : currUser.getEveryPodcast()) {
            for (Episode e : p.getEpisodesList()) {
                mostListenedEpisodes.add(e);
            }
        }

        for (Artist a : Artists.getArtists()) {
            mostListenedArtists.add(a);
        }

//        putting the number of listens for each artist
        for (Artist a : mostListenedArtists) {
            for (Song s : mostListenedSongs) {
                if (s.getArtist().equals(a.getUsername())) {
                    a.addNumberOfListens(s.getNumberOfListens());
                }
            }
        }



//        most listened genres based on the songs listened
        Map<String, Integer> tmpTopGenres = new LinkedHashMap<>();
        for (Song s : mostListenedSongs) {

            if (s.getName().equals("Ad Break") || s.getNumberOfListens() == 0) {
                continue;
            }

            if (tmpTopGenres.containsKey(s.getGenre())) {
                tmpTopGenres.put(s.getGenre(), tmpTopGenres.get(s.getGenre()) + s.getNumberOfListens());
            } else {
                tmpTopGenres.put(s.getGenre(), s.getNumberOfListens());
            }
        }


//        sorting the artists
        mostListenedArtists.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getUsername().compareTo(o2.getUsername());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

//        sorting the genres by number of listens
        tmpTopGenres = sortMapByValueDesc(tmpTopGenres);



//        sorting the songs
        mostListenedSongs.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

//        sorting the albums
        mostListenedAlbums.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

//        sorting the episodes
        mostListenedEpisodes.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });




        Map<String, Object> topArtists = new LinkedHashMap<>();
        Map<String, Object> topGenres = new LinkedHashMap<>();
        Map<String, Object> topSongs = new LinkedHashMap<>();
        Map<String, Object> topAlbums = new LinkedHashMap<>();
        Map<String, Object> topEpisodes = new LinkedHashMap<>();


        for (int i = 0; i < 5; i++) {
            if (i < mostListenedArtists.size() && mostListenedArtists.get(i).getNumberOfListens() != 0) {
                topArtists.put(mostListenedArtists.get(i).getUsername(), mostListenedArtists.get(i).getNumberOfListens());
            }

            if (i == 0 && tmpTopGenres.size() != 0) {

                Map.Entry<String, Integer> firstEntry = tmpTopGenres.entrySet().iterator().next();

                topGenres.put(firstEntry.getKey(), firstEntry.getValue());
            }

            if (i < mostListenedSongs.size() && mostListenedSongs.get(i).getNumberOfListens() != 0) {
                topSongs.put(mostListenedSongs.get(i).getName(), mostListenedSongs.get(i).getNumberOfListens());
            }

            if (i < mostListenedAlbums.size() && mostListenedAlbums.get(i).getNumberOfListens() != 0) {
                topAlbums.put(mostListenedAlbums.get(i).getName(), mostListenedAlbums.get(i).getNumberOfListens());
            }

            if (i < mostListenedEpisodes.size() && mostListenedEpisodes.get(i).getNumberOfListens() != 0) {
                topEpisodes.put(mostListenedEpisodes.get(i).getName(), mostListenedEpisodes.get(i).getNumberOfListens());
            }
        }



        result.put("topArtists", topArtists);
        result.put("topGenres", topGenres);
        result.put("topSongs", topSongs);
        result.put("topAlbums", topAlbums);
        result.put("topEpisodes", topEpisodes);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDesc(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        // Sortăm lista descrescător
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        // Construim un nou LinkedHashMap bazat pe lista sortată
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private void wrappedArtist(Artist currArtist) {

    }

    private void wrappedHost(Host currHost) {

    }


    public Wrapped(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }



    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getResult() {
        return result;
    }

}
