package main.commands.monetization;

import main.SearchBar;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.User;

public class BuyMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * executes the command
     */
    public void execute(User user) {
        this.buyMerch(user);
    }

    /**
     * Method that buys merch.
     */
    public void buyMerch(User user) {


    }


    /**
     * Constructor for the class BuyMerch
     */
    public BuyMerch(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Method that accepts a visitor.
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
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
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
