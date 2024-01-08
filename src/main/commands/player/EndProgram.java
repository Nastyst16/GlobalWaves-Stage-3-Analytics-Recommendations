package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Artists;
import main.commands.types.Merch;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;

import java.util.*;

public class EndProgram implements Command {
    private final String command;
    private final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
    @JsonIgnore
    private LinkedHashMap<String, Object> artistsMonetization;
    @JsonIgnore
    private ArrayList<Artist> sortedArtistsByNumberOfListens = new ArrayList<>();

    /**
     * executes the command.
     */
    public void execute() {

        //
        for (Artist artist : Artists.getArtists()) {
            sortedArtistsByNumberOfListens.add(artist);
        }

//        sorting alphabetically
        Collections.sort(sortedArtistsByNumberOfListens, new Comparator<Artist>() {
            @Override
            public int compare(final Artist o1, final Artist o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });


        Collections.sort(sortedArtistsByNumberOfListens, new Comparator<Artist>() {
            @Override
            public int compare(final Artist o1, final Artist o2) {
                return o2.getNumberOfListens() - o1.getNumberOfListens();
            }
        });

        this.setMonetizedArtists();
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

            double songRevenue = 0;
            double merchRevenue = 0;
            String mostProfitableSong = "N/A";

            for (Merch merch : artist.getMerchandise()) {
                merchRevenue += merch.getNumberSold() * merch.getPrice();
            }




            artistsMonetization.put("merchRevenue", merchRevenue);
            artistsMonetization.put("songRevenue", songRevenue);
            artistsMonetization.put("ranking", 0);
            artistsMonetization.put("mostProfitableSong", mostProfitableSong);

            result.put(artist.getUsername(), artistsMonetization);
        }

//        making a sum of merch revenue and song revenue and sort descending
        ArrayList<Map.Entry<String, Object>> sortedArtistsByRevenue = new ArrayList<>(result.entrySet());
        Collections.sort(sortedArtistsByRevenue, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(final Map.Entry<String, Object> o1, final Map.Entry<String, Object> o2) {
                LinkedHashMap<String, Object> artist1 = (LinkedHashMap<String, Object>) o1.getValue();
                LinkedHashMap<String, Object> artist2 = (LinkedHashMap<String, Object>) o2.getValue();

                double revenue1 = (double) artist1.get("merchRevenue") + (double) artist1.get("songRevenue");
                double revenue2 = (double) artist2.get("merchRevenue") + (double) artist2.get("songRevenue");

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
     * accepts a visitor.
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getCommand() {
        return command;
    }

    public LinkedHashMap<String, Object> getResult() {
        return result;
    }

    public LinkedHashMap<String, Object> getArtistsMonetization() {
        return artistsMonetization;
    }
}
