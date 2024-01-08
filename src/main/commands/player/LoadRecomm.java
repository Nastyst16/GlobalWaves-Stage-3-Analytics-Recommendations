package main.commands.player;

import main.SearchBar;
import main.commands.searchBar.Select;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.User;

public class LoadRecomm implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * execute the command and change the page
     */
    public void execute(User user) {
        this.setLoadRecomm(user);
    }

    /**
     * constructor for the ChangePage command
     * @param input
     */
    public LoadRecomm(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * sets the load recommendation
     */
    public void setLoadRecomm(User user) {
        if (user == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        if (user.getCurrentRecommendation() == null) {
            this.setMessage(this.user + " has no previous recommendation.");
            return;
        }

        Select select = new Select(user.getCurrentRecommendation());
        user.setCurrentSelect(select);

        if (user.getCurrentRecommendation() instanceof Song) {
            Load load = new Load((Song) user.getCurrentRecommendation());
            load.execute(user);
        } else {
            Load load = new Load((Playlist) user.getCurrentRecommendation());
            load.execute(user);
        }

        user.getListenable().listen(user.getCurrentType(), user);

        this.setMessage("Playback loaded successfully.");
    }

    /**
     * accepts a visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * sets the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * gets the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }
}
