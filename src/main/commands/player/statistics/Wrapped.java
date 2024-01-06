package main.commands.player.statistics;

import main.SearchBar;
import main.commands.searchBar.Search;
import main.commands.types.Episode;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.commands.types.Type;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Wrapped implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result = new ArrayList<>();


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
        ArrayList<Song> mostListenedSongs = new ArrayList<>();
        ArrayList<Episode> mostListenedEpisodes = new ArrayList<>();
        ArrayList<Podcast> mostListenedPodcasts = new ArrayList<>();

        for (Type t : currUser.getEverySong()) {
            mostListenedSongs.add((Song) t);
        }

//        for (Type t : currUser.getEveryEpisode()) {
//            mostListenedEpisodes.add((Episode) t);
//        }

        for (Type t : currUser.getEveryPodcast()) {
            mostListenedPodcasts.add((Podcast) t);
        }



//        sorting the songs
        mostListenedSongs.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });

////        sorting the episodes
//        mostListenedEpisodes.sort((o1, o2) -> {
//            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
//                return o1.getName().compareTo(o2.getName());
//            }
//
//            return o2.getNumberOfListens() - o1.getNumberOfListens();
//        });

//        sorting the podcasts
        mostListenedPodcasts.sort((o1, o2) -> {
            if (o1.getNumberOfListens() == o2.getNumberOfListens()) {
                return o1.getName().compareTo(o2.getName());
            }

            return o2.getNumberOfListens() - o1.getNumberOfListens();
        });



        for (int i = 0; i < 5; i++) {
            if (i < mostListenedSongs.size()) {
                result.add(mostListenedSongs.get(i).getName());
            }
        }

//        for (int i = 0; i < 5; i++) {
//            if (i < mostListenedEpisodes.size()) {
//                result.add(mostListenedEpisodes.get(i).getName());
//            }
//        }
//
//        for (int i = 0; i < 5; i++) {
//            if (i < mostListenedPodcasts.size()) {
//                result.add(mostListenedPodcasts.get(i).getName());
//            }
//        }


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

    public ArrayList<String> getResult() {
        return result;
    }

}
