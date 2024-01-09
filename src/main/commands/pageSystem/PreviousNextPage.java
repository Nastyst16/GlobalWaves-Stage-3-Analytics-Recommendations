package main.commands.pageSystem;

import main.SearchBar;
import main.commands.types.Playlist;
import main.commands.types.Song;
import main.inputCommand.Command;
import main.users.User;

public class PreviousNextPage implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * execute the command and change the page
     * calls the setPreviousPage method
     */
    public void execute(final Object... params) {
        User currUser = (User) params[1];

        if (this.command.equals("previousPage")) {
            this.setPreviousPage(currUser);
        } else if (this.command.equals("nextPage")) {
            this.setNextPage(currUser);
        }
    }

    private void setNextPage(final User currUser) {
        if (currUser == null) {
            this.setMessage(this.user + " doesn't exist.");
            return;
        }

        if (currUser.getNextPages().isEmpty()) {
            this.setMessage("There are no pages left to go forward.");
            return;
        }

        currUser.addPreviousPage(currUser.getCurrentPage());
        currUser.addPreviousPage(currUser.getCurrentRecommendation());
        currUser.addPreviousPage(currUser.getRecommendedPlaylist());
        currUser.addPreviousPage(currUser.getRecommendedSongs());
        currUser.addPreviousPage(currUser.getSelectedPageOwner());

        currUser.setSelectedPageOwner((String) currUser.popNextPage());
        currUser.setRecommendedSongs((Song) currUser.popNextPage());
        currUser.setRecommendedPlaylist((Playlist) currUser.popNextPage());
        currUser.setCurrentRecommendation(currUser.popNextPage());
        currUser.setCurrentPage((String) currUser.popNextPage());

        this.setMessage("The user " + this.user + " has navigated successfully to the next page.");
    }

    /**
     * constructor for the ChangePage command
     * @param input the input from the user
     */
    public PreviousNextPage(final SearchBar input) {
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

        currUser.addNextPage(currUser.getCurrentPage());
        currUser.addNextPage(currUser.getCurrentRecommendation());
        currUser.addNextPage(currUser.getRecommendedPlaylist());
        currUser.addNextPage(currUser.getRecommendedSongs());
        currUser.addNextPage(currUser.getSelectedPageOwner());

        currUser.setSelectedPageOwner((String) currUser.popPreviousPage());
        currUser.setRecommendedSongs((Song) currUser.popPreviousPage());
        currUser.setRecommendedPlaylist((Playlist) currUser.popPreviousPage());
        currUser.setCurrentRecommendation(currUser.popPreviousPage());
        currUser.setCurrentPage((String) currUser.popPreviousPage());

        this.setMessage("The user " + this.user
                + " has navigated successfully to the previous page.");
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
