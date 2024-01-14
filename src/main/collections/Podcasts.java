package main.collections;

import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import main.commands.types.Episode;
import main.commands.types.Podcast;

import java.util.ArrayList;

public final class Podcasts {
    private static final Podcasts INSTANCE = new Podcasts();
    private final ArrayList<Podcast> podcasts = new ArrayList<>();

    /**
     * default constructor.
     */
    private Podcasts() {

    }

    /**
     * get the instance of the class.
     */
    public static Podcasts getInstance() {
        return INSTANCE;
    }

    /**
     * Stores the podcasts from the input file.
     * @param library the input file
     */
    public void storePodcasts(final LibraryInput library) {
        ArrayList<PodcastInput> podcastInputs = library.getPodcasts();

//        storing podcasts
        for (PodcastInput podcastInput : podcastInputs) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput));
                episodes.get(episodes.size() - 1).setOwner(podcastInput.getOwner());
            }
            Podcasts.getInstance().addPodcast(new Podcast(podcastInput.getName(),
                    podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Resets the list of podcasts.
     */
    public void reset() {
        podcasts.clear();
    }

    /**
     * Adds a podcast to the list of podcasts.
     * @param podcast the podcast to be added
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * gets the list of podcasts.
     * @return the list of podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Removes a podcast from the list of podcasts.
     * @param podcast the podcast to be removed
     */
    public void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }
}
