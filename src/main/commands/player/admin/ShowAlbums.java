package main.commands.player.admin;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.commands.types.Album;
import main.users.Artist;

import java.util.ArrayList;

public final class ShowAlbums implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private ArrayList<Album> result;


    /**
     * executes the ShowAlbums command and sets the result
     * @param artist the artist that executes the command
     */
    public void execute(final Artist artist) {
        this.setShowAlbums(artist.getAlbums());
    }

    /**
     * sets the result of the ShowAlbums command
     * searches for the albums of the user and adds them to the result
     * @param userAlbums the albums of the user
     */
    public void setShowAlbums(final ArrayList<Album> userAlbums) {
        for (Album album : userAlbums) {
            this.result.add(album);
        }
    }

    /**
     * constructor for the ShowAlbums command
     * @param input the input given by the user
     */
    public  ShowAlbums(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * accepts the visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * gets the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the result
     * @return the result
     */
    public ArrayList<Album> getResult() {
        return result;
    }

    /**
     * sets the result
     * @param result the result
     */
    public void setResult(final ArrayList<Album> result) {
        this.result = result;
    }
}
