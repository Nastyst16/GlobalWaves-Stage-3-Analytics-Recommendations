package main.users;

import main.commands.types.Album;
import main.commands.types.Event;
import main.commands.types.Merch;
import main.notificationsObserver.NotificationService;
import main.notificationsObserver.Observer;

import java.util.ArrayList;

public class Artist {

    private final String username;
    private final int age;
    private final String city;
    private final ArrayList<Album> albums;
    private final ArrayList<Event> events;
    private final ArrayList<Merch> merchandise;
    private int numberOfListens = 0;
//    private ArrayList<User> subscribers = new ArrayList<>();
    private final NotificationService notificationService = new NotificationService();
    private double songRevenue = 0;

    public Artist(Artist artist) {
        this.username = artist.username;
        this.age = artist.age;
        this.city = artist.city;
        this.albums = artist.albums;
        this.events = artist.events;
        this.merchandise = artist.merchandise;
    }

    public Artist(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merchandise = new ArrayList<>();
    }


    /**
     * gets the albums of the artist
     * @return the albums of the artist
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * gets the username of the artist
     * @return the username of the artist
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the age of the artist
     * @return the age of the artist
     */
    public int getAge() {
        return age;
    }

    /**
     * gets the city of the artist
     * @return the city of the artist
     */
    public String getCity() {
        return city;
    }

    /**
     * gets the events of the artist
     * @return the events of the artist
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * gets the merchandise of the artist
     * @return the merchandise of the artist
     */
    public ArrayList<Merch> getMerchandise() {
        return merchandise;
    }

    /**
     * gets the number of listens of the artist
     * @return the number of listens of the artist
     */
    public int getNumberOfListens() {
        return numberOfListens;
    }

    /**
     * add one listen
     */
    public void addListen() {
        numberOfListens++;
    }

    /**
     * adding likes to the artist
     */
    public void addNumberOfListens(int newListens) {
        this.numberOfListens += newListens;
    }

    /**
     * gets the notificationservice of the artist
     */
    public NotificationService getNotificationService() {
        return notificationService;
    }

    /**
     * gets the song revenue of the artist
     */
    public double getSongRevenue() {
        return songRevenue;
    }

    /**
     * adds revenue to the artist
     */
    public void addSongRevenue(double revenue) {
        this.songRevenue += revenue;
    }
}
