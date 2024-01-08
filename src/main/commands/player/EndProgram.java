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

        int ranking = 0;

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
            ranking++;
            String mostProfitableSong = "N/A";

            for (Merch merch : artist.getMerchandise()) {
                merchRevenue += merch.getNumberSold() * merch.getPrice();
            }




            artistsMonetization.put("merchRevenue", merchRevenue);
            artistsMonetization.put("songRevenue", songRevenue);
            artistsMonetization.put("ranking", ranking);
            artistsMonetization.put("mostProfitableSong", mostProfitableSong);

            result.put(artist.getUsername(), artistsMonetization);
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
