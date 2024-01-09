package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Artists;
import main.collections.Users;
import main.commands.types.Merch;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EndProgram implements Command {
    private final String command;
    private final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
    @JsonIgnore
    private LinkedHashMap<String, Object> artistsMonetization;
    @JsonIgnore
    private ArrayList<Artist> sortedArtistsByNumberOfListens = new ArrayList<>();
    @JsonIgnore
    private static final double TOTAL_MONEY = 1000000;

    /**
     * executes the command.
     */
    public void execute(final Object... params) {

        for (Artist artist : Artists.getArtists()) {
            sortedArtistsByNumberOfListens.add(artist);
        }

//        sorting by number of listens, if there are 0 listeners don't do anything
        Collections.sort(sortedArtistsByNumberOfListens, new Comparator<Artist>() {
            @Override
            public int compare(final Artist o1, final Artist o2) {
                return Integer.compare(o2.getNumberOfListens(), o1.getNumberOfListens());
            }
        });

//        sorting alphabetically
        Collections.sort(sortedArtistsByNumberOfListens, new Comparator<Artist>() {
            @Override
            public int compare(final Artist o1, final Artist o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

        this.calculateMonetizationBasedOnCurrPremiumUsers();
        this.setMonetizedArtists();
    }

    private void calculateMonetizationBasedOnCurrPremiumUsers() {

        for (User user : Users.getUsers()) {

            if (!user.isPremium()) {
                continue;
            }

//              total songs listened
            double totalSongsListened = 0;
            for (Song s : user.getEverySong()) {
                if (s.getNumberOfListens() != 0) {
                    totalSongsListened += 1;
                }
            }

            double songsListenedOfThisArtist = 0;
            for (Artist artist : Artists.getArtists()) {
                songsListenedOfThisArtist = 0;

                for (Song s : user.getEverySong()) {
                    if (s.getNumberOfListens() != 0 && s.getArtist().equals(artist.getUsername())) {
                        songsListenedOfThisArtist += 1;
                    }
                }

                if (songsListenedOfThisArtist != 0) {
                    double revenue = TOTAL_MONEY * songsListenedOfThisArtist / totalSongsListened;
                    artist.addSongRevenue(revenue);
                }
            }
        }
    }


    private void setMonetizedArtists() {

        for (Artist artist : sortedArtistsByNumberOfListens) {

//            if the artist has some merchandise bought
            boolean soldMerch = false;
            for (Merch merch : artist.getMerchandise()) {
                if (merch.getNumberSold() != 0) {
                    soldMerch = true;
                    break;
                }
            }

//            if the artist was not listened to, then the artist is not monetized
            if (artist.getNumberOfListens() == 0 && !soldMerch) {
                continue;
            }

            this.artistsMonetization = new LinkedHashMap<>();

            double merchRevenue = 0;
            String mostProfitableSong = "N/A";

            for (Merch merch : artist.getMerchandise()) {
                merchRevenue += merch.getNumberSold() * merch.getPrice();
            }


            artistsMonetization.put("merchRevenue", merchRevenue);
            artistsMonetization.put("songRevenue", artist.getSongRevenue());
            artistsMonetization.put("ranking", 0);
            artistsMonetization.put("mostProfitableSong", mostProfitableSong);

            result.put(artist.getUsername(), artistsMonetization);
        }

//        making a sum of merch revenue and song revenue and sort descending
        ArrayList<Map.Entry<String, Object>> sortedArtistsByRevenue =
                                new ArrayList<>(result.entrySet());
        Collections.sort(sortedArtistsByRevenue, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(final Map.Entry<String, Object> o1,
                               final Map.Entry<String, Object> o2) {

                LinkedHashMap<String, Object> artist1 =
                        (LinkedHashMap<String, Object>) o1.getValue();
                LinkedHashMap<String, Object> artist2 =
                        (LinkedHashMap<String, Object>) o2.getValue();

                double revenue1 = (double) artist1.get("merchRevenue")
                        + (double) artist1.get("songRevenue");
                double revenue2 = (double) artist2.get("merchRevenue")
                        + (double) artist2.get("songRevenue");

                return Double.compare(revenue2, revenue1);
            }
        });

        LinkedHashMap<String, Object> sortedArtistsByRevenueLinkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : sortedArtistsByRevenue) {
            sortedArtistsByRevenueLinkedHashMap.put(entry.getKey(), entry.getValue());
        }

        this.result.clear();
        this.result.putAll(sortedArtistsByRevenueLinkedHashMap);

//        setting the ranking
        int ranking = 0;
        for (Map.Entry<String, Object> entry : this.result.entrySet()) {
            ranking++;
            LinkedHashMap<String, Object> artist = (LinkedHashMap<String, Object>) entry.getValue();
            artist.put("ranking", ranking);
        }
    }

    /**
     * Constructor that sets the command to "end_program".
     * @param input the input given by the user
     */
    public EndProgram(final String input) {
        this.command = input;
    }

    /**
     * Getter for the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the result.
     * @return the result
     */
    public LinkedHashMap<String, Object> getResult() {
        return result;
    }
}
