package main.users;

import main.commands.types.Announcement;
import main.commands.types.Podcast;

import java.util.ArrayList;

public class Host {

    private final String username;
    private final int age;
    private final String city;
    private final ArrayList<Podcast> hostPodcasts;
    private final ArrayList<Announcement> announcements;


    public Host(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;

        this.hostPodcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
    }


    /**
     * gets the username of the host
     * @return the username of the host
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the age of the host
     * @return the age of the host
     */
    public int getAge() {
        return age;
    }

    /**
     * gets the city of the host
     * @return the city of the host
     */
    public String getCity() {
        return city;
    }

    /**
     * gets the podcasts of the host
     * @return the podcasts of the host
     */
    public ArrayList<Podcast> getHostPodcasts() {
        return hostPodcasts;
    }

    /**
     * gets the announcements of the host
     * @return the announcements of the host
     */
    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }
}
