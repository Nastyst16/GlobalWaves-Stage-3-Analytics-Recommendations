package main.mementoPattern;

import main.commands.types.Playlist;
import main.commands.types.Song;

public class Page {
    private String currentPage;
    private Object currentRecommendation;
    private Playlist recommendedPlaylist;
    private Song recommendedSong;
    private String selectedPageOwner;

    /**
     * save the page
     */
    public PageMemento save() {
        return new PageMemento(this.currentPage, this.currentRecommendation,
                this.recommendedPlaylist, this.recommendedSong, this.selectedPageOwner);
    }

    /**
     * restore the page
     */
    public void restore(final PageMemento memento) {
        this.currentPage = memento.getCurrentPage();
        this.currentRecommendation = memento.getCurrentRecommendation();
        this.recommendedPlaylist = memento.getRecommendedPlaylist();
        this.recommendedSong = memento.getRecommendedSong();
        this.selectedPageOwner = memento.getSelectedPageOwner();
    }

    /**
     * get current page
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * set current page
     */
    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * get current recommendation
     */
    public Object getCurrentRecommendation() {
        return currentRecommendation;
    }

    /**
     * set current recommendation
     */
    public void setCurrentRecommendation(final Object currentRecommendation) {
        this.currentRecommendation = currentRecommendation;
    }

    /**
     * get recommended playlist
     */
    public Playlist getRecommendedPlaylist() {
        return recommendedPlaylist;
    }

    /**
     * set recommended playlist
     */
    public void setRecommendedPlaylist(final Playlist recommendedPlaylist) {
        this.recommendedPlaylist = recommendedPlaylist;
    }

    /**
     * get recommended song
     */
    public Song getRecommendedSong() {
        return recommendedSong;
    }

    /**
     * set recommended song
     */
    public void setRecommendedSong(final Song recommendedSong) {
        this.recommendedSong = recommendedSong;
    }

    /**
     * get selected page owner
     * @return selected page owner
     */
    public String getSelectedPageOwner() {
        return selectedPageOwner;
    }

    /**
     * set selected page owner
     * @param selectedPageOwner selected page owner
     */
    public void setSelectedPageOwner(final String selectedPageOwner) {
        this.selectedPageOwner = selectedPageOwner;
    }
}
