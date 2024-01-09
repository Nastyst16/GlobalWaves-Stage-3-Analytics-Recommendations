package main.commands.monetization;

import main.SearchBar;
import main.inputCommand.Command;
import main.users.User;

public class BuyPremium implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * executes the command
     */
    public void execute(final Object... params) {
        this.buyPremium((User) params[1]);
    }

    private void buyPremium(final User currUser) {
        if (currUser.isPremium()) {
            this.setMessage(this.user + " is already a premium user.");
        } else {
            this.setMessage(this.user + " bought the subscription successfully.");
            currUser.setPremium(true);
        }
    }

    /**
     * constructor
     */
    public BuyPremium(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
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
     * gets the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
