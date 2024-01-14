package main.commands.player.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Users;
import main.collections.Artists;
import main.collections.Hosts;
import main.collections.Songs;
import main.collections.Podcasts;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;
import main.users.Artist;
import main.users.Host;

public final class AddUser implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String type;
    private final int age;
    private final String city;
    private String message;

    /**
     * execute method for AddUser command,
     * calls the addUser method and adds the user to the list of users
     */
    public void execute(final Object... params) {
        this.addUser();
    }

    /**
     * constructor for AddUser command
     * @param input the input from the user
     */
    public  AddUser(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.type = input.getType();
        this.age = input.getAge();
        this.city = input.getCity();
    }

    /**
     * method that adds a user to the list of users
     * we check if the username is already taken
     * if it is, we return a message
     * if it isn't, we add the user to the list of corresponding users
     */
    public void addUser() {

        for (User u : Users.getInstance().getUsers()) {
            if (u.getUsername().equals(this.user)) {
                this.setMessage("The username " + this.user + " is already taken.");
                return;
            }
        }
        for (Artist artist : Artists.getInstance().getArtists()) {
            if (artist.getUsername().equals(this.user)) {
                this.setMessage("The username " + this.user + " is already taken.");
                return;
            }
        }
        for (Host host : Hosts.getInstance().getHosts()) {
            if (host.getUsername().equals(this.user)) {
                this.setMessage("The username " + this.user + " is already taken.");
                return;
            }
        }

        if (this.type.equals("user")) {

            Users.getInstance().addUser(new User(user, age, city, Songs.getInstance().getSongs(),
                    Podcasts.getInstance().getPodcasts()));

        } else if (this.type.equals("artist")) {

            Artists.getInstance().addArtist(new Artist(user, age, city));

        } else if (this.type.equals("host")) {

            Hosts.getInstance().addHost(new Host(user, age, city));
        }
        this.setMessage("The username " + user + " has been added successfully.");
    }

    /**
     * gettter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the username
     * @return the username
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
     * getter for the type
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return type;
    }

    /**
     * getter for the age
     * @return the age
     */
    @JsonIgnore
    public int getAge() {
        return age;
    }

    /**
     * getter for the city
     * @return the city
     */
    @JsonIgnore
    public String getCity() {
        return city;
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
