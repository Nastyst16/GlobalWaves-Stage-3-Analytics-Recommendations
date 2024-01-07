package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Artists;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class EndProgram implements Command {
    private final String command;
    private final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
    @JsonIgnore
    private final LinkedHashMap<String, Object> artistsMonetization = new LinkedHashMap<>();

    /**
     * executes the command.
     */
    public void execute() {
        this.setMonetizedArtists();
    }

    private void setMonetizedArtists() {
        for (Artist artist : Artists.getArtists()) {

//            if the artist was not listened to, then the artist is not monetized
            if (artist.getNumberOfListens() == 0) {
                continue;
            }


            double songRevenue = 0;
            double merchRevenue = 0;
            int ranking = 1;
            String mostProfitableSong = "N/A";






            artistsMonetization.put("songRevenue", songRevenue);
            artistsMonetization.put("merchRevenue", merchRevenue);
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
