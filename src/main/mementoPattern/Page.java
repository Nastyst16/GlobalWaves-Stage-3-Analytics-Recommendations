package main.mementoPattern;

import main.commands.types.Playlist;
import main.commands.types.Song;

public class Page {
    private String currentPage;
    private Object currentRecommendation;
    private Playlist recommendedPlaylist;
    private Song recommendedSong;
    private String selectedPageOwner;

    public PageMemento save() {
        return new PageMemento(this.currentPage, this.currentRecommendation, this.recommendedPlaylist,
                this.recommendedSong, this.selectedPageOwner);
    }

    public void restore(final PageMemento memento) {
        this.currentPage = memento.getCurrentPage();
        this.currentRecommendation = memento.getCurrentRecommendation();
        this.recommendedPlaylist = memento.getRecommendedPlaylist();
        this.recommendedSong = memento.getRecommendedSong();
        this.selectedPageOwner = memento.getSelectedPageOwner();
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Object getCurrentRecommendation() {
        return currentRecommendation;
    }

    public void setCurrentRecommendation(Object currentRecommendation) {
        this.currentRecommendation = currentRecommendation;
    }

    public Playlist getRecommendedPlaylist() {
        return recommendedPlaylist;
    }

    public void setRecommendedPlaylist(Playlist recommendedPlaylist) {
        this.recommendedPlaylist = recommendedPlaylist;
    }

    public Song getRecommendedSong() {
        return recommendedSong;
    }

    public void setRecommendedSong(Song recommendedSong) {
        this.recommendedSong = recommendedSong;
    }

    public String getSelectedPageOwner() {
        return selectedPageOwner;
    }

    public void setSelectedPageOwner(String selectedPageOwner) {
        this.selectedPageOwner = selectedPageOwner;
    }
}
