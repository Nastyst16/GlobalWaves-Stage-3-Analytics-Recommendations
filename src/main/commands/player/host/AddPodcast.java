package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Podcasts;
import main.collections.Users;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Episode;
import main.commands.types.Podcast;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public final class AddPodcast implements Command {

    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final ArrayList<Episode> episodes;
    private String message;

    /**
     * execute method for visitor pattern
     * @param currUser the current user
     * @param artist the artist
     * @param host the host
     */
    public void execute(final User currUser, final Artist artist, final Host host) {
        addPodcast(currUser, artist, host);
    }

    /**
     * constructor with parameters for AddPodcast
     * @param input the input
     */
    public AddPodcast(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.episodes = input.getEpisodes();
    }

    /**
     * method that adds a podcast
     * @param currUser the user
     * @param artist the artist
     * @param host the host
     */
    public void addPodcast(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || artist != null) {
            this.setMessage(this.user + " is not a host.");
            return;
        } else if (host == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        verifying if the podcast already exists
        for (Podcast podcast : Podcasts.getPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                this.setMessage(this.user + " has another podcast with the same name.");
                return;
            }
        }

//        adding the podcast
        Podcasts.addPodcast(new Podcast(this.name, this.user, this.episodes));
        host.getHostPodcasts().add(new Podcast(this.name, this.user, this.episodes));

        for (User u : Users.getUsers()) {

            ArrayList<Episode> episodesCopy = new ArrayList<>();
            for (Episode e : this.episodes) {
                episodesCopy.add(new Episode(e.getName(), e.getDuration(), e.getDescription()));
            }

            u.getEveryPodcast().add(new Podcast(this.name, this.user, episodesCopy));
            ArrayList<String> episodesNames = new ArrayList<>();
            for (Episode e : episodesCopy) {
                episodesNames.add(e.getName());
            }

        }

        this.setMessage(this.user + " has added new podcast successfully.");
    }

    /**
     * accept method for visitor pattern
     * @param visitor the command visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the episodes
     * @return the episodes
     */
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
