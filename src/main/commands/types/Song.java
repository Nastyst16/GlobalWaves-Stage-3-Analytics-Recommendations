package main.commands.types;

import main.collections.Artists;
import main.users.Artist;
import main.users.User;

import java.util.List;

public class Song implements Type {
    private String name;
    private int duration;
    private String album;
    private List<String> tags;
    private String lyrics;
    private String genre;
    private int releaseYear;
    private String artist;
    private int secondsGone;
    private int numberOfLikes;
    private int numberOfListens;

    /**
     * constructor
     */
    public Song() {

    }

    /**
     * constructor
     * @param name
     */
    public Song(final String name) {
        this.name = name;
    }

    /**
     * main constructor used for creating a song
     * @param name
     * @param duration
     * @param album
     * @param tags
     * @param lyrics
     * @param genre
     * @param releaseYear
     * @param artist
     */
    public Song(final String name, final int duration, final String album,
                final List<String> tags, final String lyrics, final String genre,
                final int releaseYear, final String artist) {

        this.name = name;
        this.duration = duration;
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
    }

    /**
     * overrides the listen method
     * implemented using decorator pattern
     */
    @Override
    public void listen(final User user) {

        this.addListen();
        for (Song s : user.getEverySong()) {
            if (s.getName().equals(this.getName())
                    && s.getAlbum().equals(this.getAlbum())) {

                s.addListen();
            }
        }

        for (Artist a : Artists.getInstance().getArtists()) {
            if (a.getUsername().equals(this.artist)) {
                a.addListen();
                break;
            }
        }
    }

    /**
     * get name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * get duration
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * set duration
     * @param duration
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * get album
     * @return
     */
    public String getAlbum() {
        return album;
    }

    /**
     * set album
     * @param album
     */
    public void setAlbum(final String album) {
        this.album = album;
    }

    /**
     * get tags
     * @return
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * set tags
     * @param tags
     */
    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    /**
     * get lyrics
     * @return
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * set lyrics
     * @param lyrics
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * get genre
     * @return
     */
    public String getGenre() {
        return genre;
    }

    /**
     * set genre
     * @param genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * get release year
     * @return
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * set release year
     * @param releaseYear
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * get artist
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     * set artist
     * @param artist
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * get seconds gone
     * @return
     */
    @Override
    public int getSecondsGone() {
        return secondsGone;
    }

    /**
     * set seconds gone
     * @param secondsGone
     */
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * get number of likes
     * @return
     */
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * set number of likes
     * @param numberOfLikes
     */
    public void setNumberOfLikes(final int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
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
     * add number of listeners
     */
    public void addNumberOfListeners(final int nr) {
        numberOfListens += nr;
    }
}
