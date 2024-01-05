package main.users;

import main.commands.types.Album;
import main.commands.types.Event;
import main.commands.types.Merch;

import java.util.ArrayList;

public class Artist {

    private final String username;
    private final int age;
    private final String city;
    private final ArrayList<Album> albums;
    private final ArrayList<Event> events;
    private final ArrayList<Merch> merchandise;

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
}
