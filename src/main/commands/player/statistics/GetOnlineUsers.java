package main.commands.player.statistics;

import main.collections.Users;
import main.inputCommand.Command;
import main.SearchBar;
import main.users.User;

import java.util.ArrayList;

public final class GetOnlineUsers implements Command {
    private final String command;
    private final int timestamp;
    private ArrayList<String> result;

    /**
     * Method that executes the command
     */
    public void execute(final Object... params) {

        for (User u : Users.getInstance().getUsers()) {
            if (u.getOnline()) {
                result.add(u.getUsername());
            }
        }
    }

    /**
     * Constructor of the class, it sets the command and the timestamp
     * @param input the input command
     */
    public GetOnlineUsers(final SearchBar input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * Getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the result
     * @return the result
     */
    public ArrayList<String> getResult() {
        return result;
    }

    /**
     * Setter for the result
     * @param result the result
     */
    public void setResult(final ArrayList<String> result) {
        this.result = result;
    }
}
