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

import static net.sf.saxon.query.QueryResult.serialize;

public class Wrapped implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private Map<String, Object> result = new LinkedHashMap<>();

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

////        printing most listened songs
//        if (currUser.getUsername().equals("jack29"))
//        for (Song s : mostListenedSongs) {
//
//            if (s.getArtist().equals("James Brown") && s.getNumberOfListens() != 0)
//                System.out.println(s.getName() + " " + s.getNumberOfListens());
//        }
//        delete the above code
//        for (Artist a : Artists.getArtists()) {
//            int x = 5;
//        }



        for (Song s : mostListenedSongs) {
            for (Album a : Albums.getAlbums()) {
                if (s.getAlbum().equals(a.getName()) && s.getNumberOfListens() != 0) {

                    boolean exists = false;

                    for (Album album : mostListenedAlbums) {
                        if (album.getName().equals(a.getName())) {
                            album.addNumberOfListens(s.getNumberOfListens());
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        mostListenedAlbums.add(new Album(a.getName(), a.getName(), a.getReleaseYear(),
                                a.getDescription(), a.getAlbumSongs()));

                        mostListenedAlbums.get(mostListenedAlbums.size() - 1).addNumberOfListens(s.getNumberOfListens());
                    }
                }
            }
        }

        for (Podcast p : currUser.getEveryPodcast()) {
            for (Episode e : p.getEpisodesList()) {

                if (e.getNumberOfListens() == 0) {
                    continue;
                }

                mostListenedEpisodes.add(new Episode(e.getName(), e.getDuration(), e.getDescription()));
            }
        }

        for (Artist a : Artists.getArtists()) {
            mostListenedArtists.add(new Artist(a));
        }

//        putting the number of listens for each artist
        for (Artist a : mostListenedArtists) {

            for (Song s : currUser.getEverySong()) {

//                if (a.getUsername().equals("James Brown") && s.getNumberOfListens() != 0)
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
                tmpTopGenres.put(s.getGenre(), tmpTopGenres.get(s.getGenre()) + s.getNumberOfListens());
            } else {
                tmpTopGenres.put(s.getGenre(), s.getNumberOfListens());
            }
        }

//        most listened albums
//        for (Album a : mostListenedAlbums) {
//            for (Song s : mostListenedSongs) {
//                if (s.getAlbum().equals(a.getName())) {
//                    a.addNumberOfListens(s.getNumberOfListens());
//                }
//            }
//        }


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

//        removing the artists with 0 listens
        for (int i = 0; i < mostListenedArtists.size(); i++) {
            if (mostListenedArtists.get(i).getNumberOfListens() == 0) {
                mostListenedArtists.remove(i);
                i--;
            }
        }

        if (mostListenedArtists.size() == 0
            && tmpTopGenres.size() == 0
            && mostListenedSongs.size() == 0
            && mostListenedAlbums.size() == 0
            && mostListenedEpisodes.size() == 0) {
//            this.result.put("message", "No data to show for user " + currUser.getUsername() + ".");

            result = null;

            return;
        }


        Map<String, Object> topArtists = new LinkedHashMap<>();
        Map<String, Object> topGenres = new LinkedHashMap<>();
        Map<String, Object> topSongs = new LinkedHashMap<>();
        Map<String, Object> topAlbums = new LinkedHashMap<>();
        Map<String, Object> topEpisodes = new LinkedHashMap<>();


        for (int i = 0; i < 5; i++) {
            if (i < mostListenedArtists.size() && mostListenedArtists.get(i).getNumberOfListens() != 0) {
                topArtists.put(mostListenedArtists.get(i).getUsername(), mostListenedArtists.get(i).getNumberOfListens());
            }

            if (i < tmpTopGenres.size() && tmpTopGenres.size() != 0) {

                topGenres.put((String) tmpTopGenres.keySet().toArray()[i],
                        tmpTopGenres.get(tmpTopGenres.keySet().toArray()[i]));
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
