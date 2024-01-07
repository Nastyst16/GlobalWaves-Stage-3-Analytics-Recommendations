package main.commands.monetization;

import main.SearchBar;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.User;

public class BuyPremium implements Command {

    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * executes the command
     */
    public void execute(User user) {
        this.buyPremium(user);
    }

    private void buyPremium(User user) {
        if (user.isPremium()) {
            this.setMessage(this.user + " is already a premium user.");
        } else {
            this.setMessage(this.user + " bought the subscription successfully.");
            user.setPremium(true);
        }
    }

    /**
     * accepts a visitor
     */
    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * constructor
     */
    public BuyPremium(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
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

    public void setMessage(final String message) {
        this.message = message;
    }
}
