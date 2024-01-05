package main.commands.types;

import fileio.input.EpisodeInput;

public class Episode implements Type {
    private String name;
    private int duration;
    private String description;
    private int secondsGone;

    /**
     * default constructor
     * for json file
     */
    public Episode() {
        this.name = null;
        this.duration = 0;
        this.description = null;
    }

    public Episode(final String name, final int duration, final String description) {
        this.name = name;
        this.duration = duration;
        this.description = description;
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
}
