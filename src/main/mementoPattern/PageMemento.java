package main.mementoPattern;

import main.commands.types.Playlist;
import main.commands.types.Song;

public class PageMemento {
    private final String currentPage;
    private final Object currentRecommendation;
    private final Playlist recommendedPlaylist;
    private final Song recommendedSong;
    private final String selectedPageOwner;
//    magic numbers
    private static final int CURRENT_PAGE = 0;
    private static final int CURRENT_RECOMM = 1;
    private static final int RECOMMENDED_PLAYLIST = 2;
    private static final int RECOMMENDED_SONG = 3;
    private static final int SELECTED_OWNER = 4;

    public PageMemento(final Object... params) {
        this.currentPage = (String) params[CURRENT_PAGE];
        this.currentRecommendation = params[CURRENT_RECOMM];
        this.recommendedPlaylist = (Playlist) params[RECOMMENDED_PLAYLIST];
        this.recommendedSong = (Song) params[RECOMMENDED_SONG];
        this.selectedPageOwner = (String) params[SELECTED_OWNER];
    }

    /**
     * get current page
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * get current recommendation
     */
    public Object getCurrentRecommendation() {
        return currentRecommendation;
    }

    /**
     * get recommended playlist
     */
    public Playlist getRecommendedPlaylist() {
        return recommendedPlaylist;
    }

    /**
     * get recommended song
     * @return recommended song
     */
    public Song getRecommendedSong() {
        return recommendedSong;
    }

    /**
     * get selected page owner
     */
    public String getSelectedPageOwner() {
        return selectedPageOwner;
    }
}
