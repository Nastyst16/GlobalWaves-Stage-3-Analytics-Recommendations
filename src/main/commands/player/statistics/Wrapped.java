package main.commands.player.statistics;

import main.SearchBar;
import main.collections.Albums;
import main.collections.Artists;
import main.collections.Songs;
import main.collections.Users;
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

//        most listened albums
        for (Album a : mostListenedAlbums) {
            for (Song s : mostListenedSongs) {
                if (s.getAlbum().equals(a.getName())) {
                    a.addNumberOfListens(s.getNumberOfListens());
                }
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
        Collections.sort(mostListenedSongs, Comparator.comparing(Song::getName));

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

        ArrayList<Album> mostListenedAlbums = new ArrayList<>();
        ArrayList<String> fansWithMostListens = new ArrayList<>();
        Map<String, Integer> tmpTopFans = new LinkedHashMap<>();
        int listeners = 0;

        for (Album a : currArtist.getAlbums()) {

            mostListenedAlbums.add(a);
        }



        Map<String, Integer> tmpTopSongs = new LinkedHashMap<>();
        for (Song s : Songs.getSongs()) {
            if (s.getArtist().equals(currArtist.getUsername())) {

                tmpTopSongs.put(s.getName(), 0);
            }
        }

//        putting in tmpTopFans every user
        for (User u : Users.getUsers()) {
            tmpTopFans.put(u.getUsername(), 0);
        }

        for (User u : Users.getUsers()) {
            boolean listenedByThisUser = false;


            for (Song s : u.getEverySong()) {
                if (s.getArtist().equals(currArtist.getUsername()) && s.getNumberOfListens() != 0) {
                    tmpTopFans.put(u.getUsername(), tmpTopFans.get(u.getUsername()) + s.getNumberOfListens());
                    listenedByThisUser = true;

//                    adding the number of listens to the song
                    tmpTopSongs.put(s.getName(), (int) tmpTopSongs.get(s.getName()) + s.getNumberOfListens());
                }
            }

            if (listenedByThisUser) {
                listeners++;
            }
        }

        tmpTopFans = sortMapByValueDesc(tmpTopFans);
        tmpTopSongs = sortMapByKey(tmpTopSongs);
        tmpTopSongs = sortMapByValueDesc(tmpTopSongs);

//        sorting the albums
        mostListenedAlbums.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

        for (Map.Entry<String, Integer> entry : tmpTopFans.entrySet()) {
            if (entry.getValue() != 0) {
                fansWithMostListens.add(entry.getKey());
            }
        }

        Map<String, Object> topFans = new LinkedHashMap<>();
        Map<String, Object> topAlbums = new LinkedHashMap<>();

        for (int i = 0; i < 5; i++) {
            if (i < fansWithMostListens.size()) {
                topFans.put(fansWithMostListens.get(i), tmpTopFans.get(fansWithMostListens.get(i)));
            }

            if (i < mostListenedAlbums.size() && mostListenedAlbums.get(i).getNumberOfListens() != 0) {
                topAlbums.put(mostListenedAlbums.get(i).getName(), mostListenedAlbums.get(i).getNumberOfListens());
            }
        }

//        putting topFans in "fansWithMostListens"
        fansWithMostListens.clear();
        for (Map.Entry<String, Object> entry : topFans.entrySet()) {
            fansWithMostListens.add(entry.getKey());
        }


        Map<String, Object> topSongs = new LinkedHashMap<>();
//        copying the top 5 songs
        int i = 0;
        for (Map.Entry<String, Integer> entry : tmpTopSongs.entrySet()) {
            if (i == 5) {
                break;
            }

            if (entry.getValue() == 0) {
                i++;
                continue;
            }

            topSongs.put(entry.getKey(), entry.getValue());
            i++;
        }




        result.put("topAlbums", topAlbums);
        result.put("topSongs", topSongs);
        result.put("topFans", fansWithMostListens);
        result.put("listeners", listeners);
    }

    private void wrappedHost(Host currHost) {

    }

    // Method to sort a Map alphabetically by keys
    public static Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());

        // Sort the list alphabetically based on keys
        entryList.sort(Map.Entry.comparingByKey());

        // Construct a new LinkedHashMap based on the sorted list
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
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
