package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;

public final class SwitchVisibility implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int id;
    private String message;

    /**
     * This method is used to execute the command.
     */
    public void execute(final User currUser) {
        this.setVisibility(currUser);
    }

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * This constructor is used to create a new SwitchVisibility command.
     * @param input the input
     */
    public SwitchVisibility(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.id = input.getPlaylistId();
    }

    /**
     * This method is used to set the visibility of a playlist.
     * @param user the current user
     */
    public void setVisibility(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        if (this.id > currUser.getPlayListList().size()) {
            this.message = "The specified playlist ID is too high.";
            return;
        }
        if (currUser.getPlayListList().get(this.id - 1).getVisibility().equals("public")) {
            currUser.getPlayListList().get(this.id - 1).setVisibility("private");
            this.message = "Visibility status updated successfully to private.";
        } else {
            currUser.getPlayListList().get(this.id - 1).setVisibility("public");
            this.message = "Visibility status updated successfully to public.";
        }
    }

    /**
     * This method is used to get the command of the command.
     * @return the command of the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * This method is used to get the user of the command.
     * @return the user of the command
     */
    public String getUser() {
        return user;
    }

    /**
     * This method is used to get the timestamp of the command.
     * @return the timestamp of the command
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * This method is used to get the message of the command.
     * @return the message of the command
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method is used to get the id of the command.
     * @return the id of the command
     */
    @JsonIgnore
    public int getId() {
        return id;
    }

}
