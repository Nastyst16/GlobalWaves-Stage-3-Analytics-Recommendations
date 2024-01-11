package main.commands.types;

import fileio.input.EpisodeInput;
import main.users.User;

import java.util.LinkedHashMap;

public class Episode implements Type {
    private String name;
    private int duration;
    private String description;
    private int secondsGone;
    private int numberOfListens;
    private String owner;

    /**
     * default constructor
     * for json file
     */
    public Episode() {
        this.name = null;
        this.duration = 0;
        this.description = null;
        this.numberOfListens = 0;
        this.setOwner("");
    }

    public Episode(final String name, final int duration,
                   final String description, final String owner) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.owner = owner;
    }

    /**
     * Constructor for Episode
     * @param episodeInput
     */
    public Episode(final EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        this.description = episodeInput.getDescription();
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * listens to an episode
     */
    @Override
    public void listen(final User user) {
        Episode currEpisode = this;

//        searching the podcast
        for (Podcast p : user.getPodcastsPlayed()) {

            boolean found = false;

            for (Episode e : p.getEpisodesList()) {
                if (e.getName().equals(currEpisode.getName())) {
                    e.addListen();
                    p.addListen();

                    LinkedHashMap<Episode, Integer> map = new LinkedHashMap<>();
                    map.put(e, 1);

                    user.addLisenedEpisode(map);

                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    /**
     * Getter for duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter for secondsGone
     * @return secondsGone
     */
    public int getSecondsGone() {
        return secondsGone;
    }

    /**
     * Setter for secondsGone
     * @param secondsGone secondsGone of the episode
     */
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Setter for name
     * @param name name of the episode
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setter for duration
     * @param duration duration of the episode
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * Setter for description
     * @param description description of the episode
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * execute
     */
    @Override
    public void execute() {

    }

    /**
     * get number of listens
     * @return
     */
    public int getNumberOfListens() {
        return numberOfListens;
    }

    /**
     * add listen
     */
    @Override
    public void addListen() {
        numberOfListens++;
    }

    /**
     * add number of listens
     * @param nOfListens number of listens
     */
    public void addNumberOfListens(final int nOfListens) {
        this.numberOfListens += nOfListens;
    }

    /**
     * set number of listens
     */
    public void setNumberOfListens(final int i) {
        this.numberOfListens = i;
    }

    /**
     * get owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * set owner
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
