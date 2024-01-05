package main.commands.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public final class Podcast implements Type {
    private String name;
    @JsonIgnore
    private String owner;
    @JsonIgnore
    private List<Episode> episodesList;
    private ArrayList<String> episodes;
    @JsonIgnore
    private int lastRemainingEpisode;
    @JsonIgnore
    private int secondsGone;


    /**
     * default constructor
     */
    public Podcast() {
        this.name = null;
        this.owner = null;
        this.episodesList = null;
        this.episodes = null;
    }

    /**
     * constructor with parameters
     * @param name name of the podcast
     * @param owner owner of the podcast
     * @param episodesList list of episodesList
     */
    public Podcast(final String name, final String owner, final List<Episode> episodesList) {
        this.name = name;
        this.owner = owner;
        this.episodesList = episodesList;

        this.episodes = new ArrayList<>();
        for (Episode episode : episodesList) {
            this.episodes.add(episode.getName());
        }
    }

    /**
     * gets the name of the podcast
     */
    public String getName() {
        return name;
    }

    /**
     * gets the owner of the podcast
     */
    public String getOwner() {
        return owner;
    }

    /**
     * gets the list of episodesList
     */
    public List<Episode> getEpisodesList() {
        return episodesList;
    }

    @JsonIgnore
    /**
     * gets the duration of the podcast
     */
    public int getDuration() {
//        I wrote this because podcast implements type
        return 0;
    }

    /**
     * gets the last remaining episode
     */
    public int getLastRemainingEpisode() {
        return lastRemainingEpisode;
    }

    /**
     * sets the last remaining episode
     * @param lastRemainingEpisode last remaining episode
     */
    public void setLastRemainingEpisode(final int lastRemainingEpisode) {
        this.lastRemainingEpisode = lastRemainingEpisode;
    }

    /**
     * gets the duration of the podcast
     */
    public int getSecondsGone() {
        return this.secondsGone;
    }

    /**
     * sets the duration of the podcast
     * @param secondsGone duration of the podcast
     */
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * execute
     */
    @Override
    public void execute() {

    }

    /**
     * gets the list of episodesList names
     */
    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    /**
     * sets the name of the podcast
     * @param name name of the podcast
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * sets the owner of the podcast
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     * sets the list of episodesList
     * @param episodesList list of episodesList
     */
    public void setEpisodesList(final List<Episode> episodesList) {
        this.episodesList = episodesList;
    }

    /**
     * sets the list of episodesList names
     * @param episodes list of episodesList names
     */
    public void setEpisodes(final ArrayList<String> episodes) {
        this.episodes = episodes;
    }
}
