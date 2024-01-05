package main;

import main.commands.types.Episode;
import main.commands.types.Song;

import java.util.ArrayList;
import java.util.Map;

public class SearchBar {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Map<String, Object> filters;
    private int itemNumber;
    private String playlistName;
    private int playlistId;
    private int seed;


//    etapa 2
    private String nextPage;
    private String description;
    private String name;
    private String date;
    private int price;
    private int age;
    private String city;
    private int releaseYear;
    private ArrayList<Song> songs;
    private ArrayList<Episode> episodes;







    /**
     * default constructor
     */
    public SearchBar() {

    }

    /** get command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /** set command
     * @param command the command to set
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /** get username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /** set username
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /** get timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /** set timestamp
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /** get type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /** set type
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /** get filters
     * @return the filters
     */
    public Map<String, Object> getFilters() {
        return filters;
    }

    /** set filters
     * @param filters the filters to set
     */
    public void setFilters(final Map<String, Object> filters) {
        this.filters = filters;
    }

    /** get item number
     * @return the itemNumber
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /** set item number
     * @param itemNumber the itemNumber to set
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /** get playlist name
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /** set playlist name
     * @param playlistName the playlistName to set
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /** get playlist id
     * @return the playlistId
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /** set playlist id
     * @param playlistId the playlistId to set
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }


//    etapa 2


    /**
     * sets seed
     * @param seed
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }

    /**
     * gets next page
     * @return next page
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     * sets next page
     * @param nextPage
     */
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * gets description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * gets date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * gets price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets age
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * sets age
     * @param age
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * gets city
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * sets city
     * @param city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * gets release year
     * @return
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * sets release year
     * @param releaseYear
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * gets songs
     * @return
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * sets songs
     * @param songs
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * gets episodes
     * @return
     */
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * sets episodes
     * @param episodes
     */
    public void setEpisodes(final ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
