package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Podcasts;
import main.collections.Users;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.commands.types.Podcast;
import main.users.Artist;
import main.users.Host;

public final class RemovePodcast implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;
    private static final int HOST_PARAM = 3;

    /**
     * execute method for visitor pattern
     */
    public void execute(final Object... params) {
        this.setRemovePodcast((User) params[1], (Artist) params[2], (Host) params[HOST_PARAM]);
    }

    /**
     * method that removes a podcast
     * @param currUser the current user
     * @param artist the artist
     * @param host the host
     */
    public void setRemovePodcast(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || artist != null) {
            this.setMessage(this.user + " is not an host.");
            return;
        } else if (host == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

//        verifying if the podcast exists
        boolean exists = false;

        for (Podcast podcast : host.getHostPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            this.setMessage(this.user + " doesn't have a podcast with the given name.");
            return;
        }

//        verifying if a users currently listens to the podcast
        for (User currentUser : Users.getUsers()) {
            if (currentUser.getCurrentType() != null) {
                for (Podcast podcast : host.getHostPodcasts()) {
                    this.setMessage(this.user + " can't delete this podcast.");
                    return;
                }
            }
        }

//        deleting everything related to the podcast
        for (Podcast p : host.getHostPodcasts()) {

            for (User u : Users.getUsers()) {

                u.setEveryPodcast(Podcasts.getPodcasts());
//                deleting also every user listened podcasts
                for (Podcast podcastToRemove : u.getPodcastsPlayed()) {
                    if (podcastToRemove.getName().equals(p.getName())) {

//                        removing the podcast from the user's listened podcasts
                        for (Podcast podcast : u.getPodcastsPlayed()) {
                            if (podcast.getName().equals(podcastToRemove.getName())) {
                                u.getPodcastsPlayed().remove(podcast);
                                break;
                            }
                        }

                        break;
                    }
                }
            }
        }

//        removing the podcast
        for (Podcast podcast : host.getHostPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                host.getHostPodcasts().remove(podcast);
                Podcasts.removePodcast(podcast);
                break;
            }
        }


        this.setMessage(this.user + " deleted the podcast successfully.");
    }

    /**
     * constructor for the RemovePodcast class
     * @param input the input
     */
    public RemovePodcast(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
