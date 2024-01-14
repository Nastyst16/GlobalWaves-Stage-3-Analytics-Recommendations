package main.commands.player.statistics;

import main.SearchBar;
import main.collections.Artists;
import main.collections.Podcasts;
import main.collections.Songs;
import main.collections.Users;
import main.collections.Albums;
import main.commands.types.Song;
import main.commands.types.Album;
import main.commands.types.Episode;
import main.commands.types.Podcast;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class Wrapped implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private Map<String, Object> result = new LinkedHashMap<>();
    private static final int MAX_TOP = 5;
    private static final int HOST_PARAM = 3;

    /**
     * executes the command
     */
    public void execute(final Object... params) {
        this.setWrapped((User) params[1], (Artist) params[2], (Host) params[HOST_PARAM]);
    }

    /**
     * setting wrapped
     */
    private void setWrapped(final User currUser, final Artist currArtist, final Host currHost) {

        if (currUser != null) {
            wrappedUser(currUser);
        } else if (currArtist != null) {
            wrappedArtist(currArtist);
        } else if (currHost != null) {
            wrappedHost(currHost);
        }
    }

    /**
     * setting the wrapped for the user
     */
    private void wrappedUser(final User currUser) {

//        printing the most 5 listened songs
        ArrayList<Artist> mostListenedArtists = new ArrayList<>();
        ArrayList<Song> mostListenedSongs = new ArrayList<>();
        ArrayList<Album> mostListenedAlbums = new ArrayList<>();
        ArrayList<Episode> mostListenedEpisodes = new ArrayList<>();

//        deep copying the songs
        for (Song s : currUser.getEverySong()) {

            if (s.getNumberOfListens() == 0) {
                continue;
            }

            mostListenedSongs.add(new Song(s.getName(), s.getDuration(), s.getAlbum(), s.getTags(),
                    s.getLyrics(), s.getGenre(), s.getReleaseYear(), s.getArtist()));
        }

        for (Song t : currUser.getEverySong()) {

            boolean exists = false;
//            verifying if a song with the same name exists:
            for (Song s : mostListenedSongs) {

                if (s.getName().equals(t.getName())) {
                    s.addNumberOfListeners(t.getNumberOfListens());
                    exists = true;
                    break;
                }

            }
            if (exists) {
                continue;
            }
        }

        userCalculatingMostListenedAlbums(currUser, mostListenedAlbums);

        for (Podcast p : currUser.getEveryPodcast()) {
            for (Episode e : p.getEpisodesList()) {

                if (e.getNumberOfListens() == 0) {
                    continue;
                }

                mostListenedEpisodes.add(new Episode(e.getName(), e.getDuration(),
                        e.getDescription(), e.getOwner()));
                mostListenedEpisodes.get(mostListenedEpisodes.size() - 1).
                        addNumberOfListens(e.getNumberOfListens());
            }
        }

        for (Artist a : Artists.getInstance().getArtists()) {
            mostListenedArtists.add(new Artist(a));
        }

//        putting the number of listens for each artist
        for (Artist a : mostListenedArtists) {
            for (Song s : currUser.getEverySong()) {
                if (s.getArtist().equals(a.getUsername())) {
                    a.addNumberOfListens(s.getNumberOfListens());
                }
            }
        }

//        most listened genres based on the songs listened
        Map<String, Integer> tmpTopGenres = new LinkedHashMap<>();
        for (Song s : currUser.getEverySong()) {
            if (s.getName().equalsIgnoreCase("ad break")
                || s.getNumberOfListens() == 0) {
                continue;
            }

            if (tmpTopGenres.containsKey(s.getGenre())) {
                tmpTopGenres.put(s.getGenre(), tmpTopGenres.get(s.getGenre())
                        + s.getNumberOfListens());
            } else {
                tmpTopGenres.put(s.getGenre(), s.getNumberOfListens());
            }
        }

//        sorting the mostlistenedepisode by number of listens descending
        mostListenedEpisodes.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

//        sorting the artists
        mostListenedArtists.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getUsername().compareTo(o2.getUsername());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

//        sorting the genres by number of listens
        tmpTopGenres = sortDesc(tmpTopGenres);
        sortingEverythingUser(mostListenedArtists, mostListenedSongs,
                mostListenedAlbums, mostListenedEpisodes);

        if (mostListenedArtists.size() == 0
            && tmpTopGenres.size() == 0
            && mostListenedSongs.size() == 0
            && mostListenedAlbums.size() == 0
            && mostListenedEpisodes.size() == 0) {

            result = null;
            return;
        }

        Map<String, Object> topArtists = new LinkedHashMap<>();
        Map<String, Object> topGenres = new LinkedHashMap<>();
        Map<String, Object> topSongs = new LinkedHashMap<>();
        Map<String, Object> topAlbums = new LinkedHashMap<>();
        Map<String, Object> topEpisodes = new LinkedHashMap<>();

//        storing the top 5 for every map
        userStoreResultsTop5(mostListenedArtists, mostListenedSongs, mostListenedAlbums,
                mostListenedEpisodes, tmpTopGenres, topArtists, topGenres, topSongs,
                topAlbums, topEpisodes);

        result.put("topArtists", topArtists);
        result.put("topGenres", topGenres);
        result.put("topSongs", topSongs);
        result.put("topAlbums", topAlbums);
        result.put("topEpisodes", topEpisodes);
    }

    /**
     * Method to sort a Map by values in descending order
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDesc(final Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        // sort the list in descending order
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        // Construct a new LinkedHashMap based on the sorted list
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private void wrappedArtist(final Artist currArtist) {

        ArrayList<Album> mostListenedAlbums = new ArrayList<>();
        ArrayList<String> fansWithMostListens = new ArrayList<>();
        Map<String, Integer> tmpTopFans = new LinkedHashMap<>();
        int listeners = 0;

        for (Album a : Albums.getInstance().getAlbums()) {
            if (a.getUser().equals(currArtist.getUsername())) {

//                deep copying the album
                mostListenedAlbums.add(new Album(a.getName(), a.getName(), a.getReleaseYear(),
                        a.getDescription(), a.getAlbumSongs()));
                mostListenedAlbums.get(mostListenedAlbums.size() - 1).addNumberOfListens(0);
            }
        }

        for (User u : Users.getInstance().getUsers()) {
            for (Song s : u.getEverySong()) {
                if (!s.getArtist().equals(currArtist.getUsername())) {
                    continue;
                }

                for (Album a : mostListenedAlbums) {
                    if (a.getName().equals(s.getAlbum())) {
                        a.addNumberOfListens(s.getNumberOfListens());
                        break;
                    }
                }
            }
        }

        Map<String, Integer> tmpTopSongs = new LinkedHashMap<>();
        for (Song s : Songs.getInstance().getSongs()) {
            if (s.getArtist().equals(currArtist.getUsername())) {

                tmpTopSongs.put(s.getName(), 0);
            }
        }

//        putting in tmpTopFans every user
        for (User u : Users.getInstance().getUsers()) {
            tmpTopFans.put(u.getUsername(), 0);
        }

        for (User u : Users.getInstance().getUsers()) {
            boolean listenedByThisUser = false;


            for (Song s : u.getEverySong()) {
                if (s.getArtist().equals(currArtist.getUsername())
                        && s.getNumberOfListens() != 0) {

                    tmpTopFans.put(u.getUsername(), tmpTopFans.get(u.getUsername())
                            + s.getNumberOfListens());
                    listenedByThisUser = true;

//                    adding the number of listens to the song
                    tmpTopSongs.put(s.getName(), (int) tmpTopSongs.get(s.getName())
                            + s.getNumberOfListens());
                }
            }

            if (listenedByThisUser) {
                listeners++;
            }
        }

        tmpTopFans = sortDesc(tmpTopFans);
        tmpTopSongs = sortMapByKey(tmpTopSongs);
        tmpTopSongs = sortDesc(tmpTopSongs);

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

        for (int i = 0; i < MAX_TOP; i++) {
            if (i < fansWithMostListens.size()) {
                topFans.put(fansWithMostListens.get(i), tmpTopFans.get(fansWithMostListens.get(i)));
            }

            if (i < mostListenedAlbums.size()
                    && mostListenedAlbums.get(i).getNumberOfListens() != 0) {

                topAlbums.put(mostListenedAlbums.get(i).getName(),
                        mostListenedAlbums.get(i).getNumberOfListens());
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
            if (i == MAX_TOP) {
                break;
            }

            if (entry.getValue() == 0) {
                i++;
                continue;
            }

            topSongs.put(entry.getKey(), entry.getValue());
            i++;
        }

        if (topAlbums.size() == 0
            && topFans.size() == 0
            && topSongs.size() == 0
            && listeners == 0) {

            result = null;

            return;
        }

        result.put("topAlbums", topAlbums);
        result.put("topSongs", topSongs);
        result.put("topFans", fansWithMostListens);
        result.put("listeners", listeners);
    }

    private void wrappedHost(final Host currHost) {

        ArrayList<Episode> mostListenedEpisodes = new ArrayList<>();
        int listeners = 0;


        ArrayList<Podcast> mostListenedPodcasts = new ArrayList<>();
        mostListenedPodcasts = Podcasts.getInstance().getPodcasts();

        boolean found = false;

//        deep copying the episodes
        for (User u : Users.getInstance().getUsers()) {

            boolean listener = false;

            for (Episode e : u.getLisenedEpisodes().keySet()) {
                if (e.getOwner().equals(currHost.getUsername())) {


                    listener = true;
                    found = false;
//                    adding the number of listens in the array
//                    if already exists in the array just update it
                    for (Episode episode : mostListenedEpisodes) {
                        if (episode.getName().equals(e.getName())) {
                            episode.addNumberOfListens(u.getLisenedEpisodes().get(e));
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        mostListenedEpisodes.add(new Episode(e.getName(), e.getDuration(),
                                e.getDescription(), e.getOwner()));
                        mostListenedEpisodes.get(mostListenedEpisodes.size() - 1).
                                addNumberOfListens(u.getLisenedEpisodes().get(e));
                    }
                }
            }

            if (listener) {
                listeners++;
                listener = false;
            }

        }

        Map<String, Object> topEpisodes = new LinkedHashMap<>();
        for (int i = 0; i < MAX_TOP; i++) {
            if (i < mostListenedEpisodes.size() && mostListenedEpisodes.get(i).
                    getNumberOfListens() != 0) {

                topEpisodes.put(mostListenedEpisodes.get(i).getName(),
                        mostListenedEpisodes.get(i).getNumberOfListens());
            }
        }

        result.put("topEpisodes", topEpisodes);
        result.put("listeners", listeners);
    }

    /**
     * Method to sort a Map alphabetically by keys
     */
    public static Map<String, Integer> sortMapByKey(final Map<String, Integer> map) {
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

    /**
     * sorting everything for user wrapped
     */
    private void sortingEverythingUser(final ArrayList<Artist> mostListenedArtists,
                                       final ArrayList<Song> mostListenedSongs,
                                       final ArrayList<Album> mostListenedAlbums,
                                       final ArrayList<Episode> mostListenedEpisodes) {

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

//        removing the artists with 0 listens
        for (int i = 0; i < mostListenedArtists.size(); i++) {
            if (mostListenedArtists.get(i).getNumberOfListens() == 0) {
                mostListenedArtists.remove(i);
                i--;
            }
        }

//        removing the albums with 0 listens
        for (int i = 0; i < mostListenedAlbums.size(); i++) {
            if (mostListenedAlbums.get(i).getNumberOfListens() == 0) {
                mostListenedAlbums.remove(i);
                i--;
            }
        }
    }

    /**
     * calculating the most listened albums for user wrapped
     */
    private void userCalculatingMostListenedAlbums(final User currUser,
                                                   final ArrayList<Album> mostListenedAlbums) {
        for (Artist artist : Artists.getInstance().getArtists()) {
            for (Album a : artist.getAlbums()) {

//                checking if the user listened to this album
//                and calculating the number of listens for this user
                boolean listenedToThisAlbum = false;
                int numberOfListens = 0;

                for (Song s : currUser.getEverySong()) {
                    if (s.getAlbum().equals(a.getName())) {
                        listenedToThisAlbum = true;
                        numberOfListens += s.getNumberOfListens();
                    }
                }

                if (!listenedToThisAlbum) {
                    continue;
                }

//                if there is an album with the same name just add the listens
                boolean exists = false;
                for (Album album : mostListenedAlbums) {
                    if (album.getName().equals(a.getName())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    mostListenedAlbums.add(new Album(a.getName(), a.getName(), a.getReleaseYear(),
                            a.getDescription(), a.getAlbumSongs()));

                    mostListenedAlbums.get(mostListenedAlbums.size() - 1).
                            addNumberOfListens(numberOfListens);
                }

            }
        }
    }

    /**
     * user store results top 5
     */
    private void userStoreResultsTop5(final ArrayList<Artist> mostListenedArtists,
                                      final ArrayList<Song> mostListenedSongs,
                                      final ArrayList<Album> mostListenedAlbums,
                                      final ArrayList<Episode> mostListenedEpisodes,
                                      final Map<String, Integer> tmpTopGenres,
                                      final Map<String, Object> topArtists,
                                      final Map<String, Object> topGenres,
                                      final Map<String, Object> topSongs,
                                      final Map<String, Object> topAlbums,
                                      final Map<String, Object> topEpisodes) {
        for (int i = 0; i < MAX_TOP; i++) {
            if (i < mostListenedArtists.size() && mostListenedArtists.get(i).
                    getNumberOfListens() != 0) {
                topArtists.put(mostListenedArtists.get(i).getUsername(),
                        mostListenedArtists.get(i).getNumberOfListens());
            }

            if (i < tmpTopGenres.size() && tmpTopGenres.size() != 0) {

                topGenres.put((String) tmpTopGenres.keySet().toArray()[i],
                        tmpTopGenres.get(tmpTopGenres.keySet().toArray()[i]));
            }

            if (i < mostListenedSongs.size() && mostListenedSongs.get(i).
                    getNumberOfListens() != 0) {
                topSongs.put(mostListenedSongs.get(i).getName(),
                        mostListenedSongs.get(i).getNumberOfListens());
            }

            if (i < mostListenedAlbums.size() && mostListenedAlbums.get(i).
                    getNumberOfListens() != 0) {
                topAlbums.put(mostListenedAlbums.get(i).getName(),
                        mostListenedAlbums.get(i).getNumberOfListens());
            }

            if (i < mostListenedEpisodes.size() && mostListenedEpisodes.get(i).
                    getNumberOfListens() != 0) {
                topEpisodes.put(mostListenedEpisodes.get(i).getName(),
                        mostListenedEpisodes.get(i).getNumberOfListens());
            }
        }
    }

    /**
     * constructor for the wrapped
     */
    public Wrapped(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the result
     * @return the result
     */
    public Map<String, Object> getResult() {
        return result;
    }
}
