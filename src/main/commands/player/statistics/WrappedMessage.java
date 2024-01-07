package main.commands.player.statistics;

import main.SearchBar;
import main.collections.Albums;
import main.collections.Artists;
import main.collections.Songs;
import main.collections.Users;
import main.commands.searchBar.Search;
import main.commands.types.*;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.lang.reflect.Array;
import java.util.*;

import static net.sf.saxon.query.QueryResult.serialize;

public class WrappedMessage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * executes the command
     */
    public void execute(User user, Artist artist, Host host) {
        this.setWrappedMessage(user, artist, host);
    }

    /**
     * setting wrapped
     */
    private void setWrappedMessage(User currUser, Artist currArtist, Host currHost) {
        this.setMessage("No data to show for user " + currUser.getUsername() + ".");
    }




    public WrappedMessage(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }



    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
