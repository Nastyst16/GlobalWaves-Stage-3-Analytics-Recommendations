package main.mementoPattern;

import main.commands.types.Playlist;
import main.commands.types.Song;

public class PageMemento {
    private final String currentPage;
    private final Object currentRecommendation;
    private final Playlist recommendedPlaylist;
    private final Song recommendedSong;
    private final String selectedPageOwner;

    public PageMemento(Object... params) {
        this.currentPage = (String) params[0];
        this.currentRecommendation = params[1];
        this.recommendedPlaylist = (Playlist) params[2];
        this.recommendedSong = (Song) params[3];
        this.selectedPageOwner = (String) params[4];
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public Object getCurrentRecommendation() {
        return currentRecommendation;
    }

    public Playlist getRecommendedPlaylist() {
        return recommendedPlaylist;
    }

    public Song getRecommendedSong() {
        return recommendedSong;
    }

    public String getSelectedPageOwner() {
        return selectedPageOwner;
    }
}
