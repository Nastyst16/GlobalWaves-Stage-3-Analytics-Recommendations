package main.commands.pageSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class ChangePage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String nextPage;
    private String message;

    /**
     * execute the command and change the page
     * calls the setChangePage method
     */
    public void execute(final User currUser) {

        this.setChangePage(currUser);
    }

    /**
     * constructor for the ChangePage command
     * @param input the input from the user
     */
    public ChangePage(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.nextPage = input.getNextPage();
        this.message = input.getUsername() + " is trying to access a non-existent page.";
    }

    /**
     * changes the page of the user if the user is online
     *
     * @param currUser the user that is trying to change the page
     */
    public void setChangePage(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.setMessage(this.user + " is offline.");
            return;
        }

        if (this.getNextPage().equals("Home") || this.getNextPage().equals("LikedContent")) {

            currUser.setCurrentPage(this.getNextPage());
            currUser.setSelectedPageOwner("");
            this.setMessage(this.user + " accessed " + this.getNextPage() + " successfully.");
        }
    }

    /**
     * accept method for the visitor pattern
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
     * gets the next page
     * @return the next page
     */
    public String getNextPage() {
        return nextPage;
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
