package main.commands.monetization;

import main.SearchBar;
import main.commands.types.Merch;
import main.inputCommand.Command;
import main.users.User;

import java.util.ArrayList;

public class SeeMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<String> result;

    /**
     * executes the command
     */
    public void execute(Object... params) {
        this.seeMerch((User) params[1]);
    }

    /**
     * Method that sees the merch.
     */
    public void seeMerch(User user) {

        if (user == null) {
            this.result.add("The username " + this.user + " doesn't exist.");
        }

        for (Merch merch : user.getBoughtMerchandise()) {
            this.result.add(merch.getName());
        }
    }


    /**
     * Constructor for the SeeMerch command.
     * @param input the input given by the user
     */
    public SeeMerch(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * Getter for the command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for the user.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the message
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
