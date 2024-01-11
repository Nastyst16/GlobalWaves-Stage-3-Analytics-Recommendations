package main.commands.pageSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.commands.types.Episode;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.SearchBar;
import main.mementoPattern.PageMemento;
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
    public void execute(final Object... params) {
        User currUser = (User) params[1];

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

        PageMemento memento1 = currUser.getCurrentPage().save();
        if (currUser.getPageCareTaker().getCurrentMemento() == null) {
            currUser.getPageCareTaker().setMemento(memento1);
        }
        currUser.getPageCareTaker().addPageCurrentMemento(memento1);

        currUser.getPageCareTaker().clearNextPages();

        if (this.getNextPage().equals("Home") || this.getNextPage().equals("LikedContent")) {

            currUser.getCurrentPage().setCurrentPage(this.getNextPage());
            currUser.getCurrentPage().setSelectedPageOwner("");
            this.setMessage(this.user + " accessed " + this.getNextPage() + " successfully.");
        }

        if (this.getNextPage().equals("Artist") || this.getNextPage().equals("Host")) {

            currUser.getCurrentPage().setCurrentPage(this.getNextPage());

            if (this.getNextPage().equals("Artist")) {
                currUser.getCurrentPage().setSelectedPageOwner(((Song) currUser.
                        getCurrentType()).getArtist());
            } else {
                currUser.getCurrentPage().setSelectedPageOwner(((Episode) currUser.
                        getCurrentType()).getOwner());
            }

            this.setMessage(this.user + " accessed " + this.getNextPage() + " successfully.");
        }

        PageMemento memento2 = currUser.getCurrentPage().save();
        currUser.getPageCareTaker().setMemento(memento2);

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
