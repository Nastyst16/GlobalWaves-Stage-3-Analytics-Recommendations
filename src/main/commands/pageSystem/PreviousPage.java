package main.commands.pageSystem;

import main.SearchBar;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.User;

public class PreviousPage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * execute the command and change the page
     * calls the setPreviousPage method
     */
    public void execute(final User currUser) {
        this.setPreviousPage(currUser);
    }

    /**
     * constructor for the ChangePage command
     * @param input the input from the user
     */
    public PreviousPage(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * changes the page of the user if the user is online
     *
     * @param currUser the user that is trying to change the page
     */
    public void setPreviousPage(final User currUser) {

        if (currUser == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        if (currUser.getPreviousPages().isEmpty()) {
            this.setMessage(this.user + " has no previous pages.");
            return;
        }

        currUser.addNextPage(currUser.getCurrentPage(), currUser.getCurrentRecommendation());
        currUser.setCurrentPage(currUser.getPreviousPages().lastEntry().getKey());
        currUser.setCurrentRecommendation(currUser.getPreviousPages().lastEntry().getValue());
        currUser.getPreviousPages().remove(currUser.getCurrentPage());

        this.setMessage("The user " + this.user + " has navigated successfully to the previous page.");
    }

    /**
     * Accepts a visitor to perform some action on the current command.
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
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for the timestamp.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
