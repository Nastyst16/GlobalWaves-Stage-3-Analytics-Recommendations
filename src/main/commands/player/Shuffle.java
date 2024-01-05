package main.commands.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Type;
import main.SearchBar;
import main.users.User;

import java.util.Collections;
import java.util.Random;

public class Shuffle implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int seed;
    private String message;


    /**
     * Execute the command.
     */
    public void execute(final SearchBar input, final User currUser) {

        currUser.setShuffleSeed(input.getSeed());

        this.settingShuffle(currUser);
    }


    /**
     * Accept method for visitor pattern.
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor
     * @param input the input
     */
    public Shuffle(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.seed = input.getSeed();
    }

    /**
     * settingShuffle
     * @param currUser the current user
     */
    public void settingShuffle(final User currUser) {

//        if the currUser is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        Type currentType = currUser.getCurrentType();

        if (currentType != null && currUser.getTypeLoaded() == 2) {

            if (!currUser.isShuffle()) {
                this.message = "Shuffle function activated successfully.";
                currUser.setShuffle(true);

                currUser.getOriginalIndices().clear();
                currUser.getShuffledIndices().clear();


                for (int i = 0; i < currUser.getCurrentPlaylist().getSongList().size(); i++) {
                    currUser.getOriginalIndices().add(i);
                }
                currUser.getShuffledIndices().addAll(currUser.getOriginalIndices());

                Random rand = new Random(this.seed);
                Collections.shuffle(currUser.getShuffledIndices(), rand);

            } else {
                this.message = "Shuffle function deactivated successfully.";
                currUser.setShuffle(false);

                currUser.getOriginalIndices().clear();
                currUser.getShuffledIndices().clear();

            }
            currUser.setShuffleSeed(this.seed);

        } else if (currentType != null && currUser.getTypeLoaded() != 2) {
            this.message = "The loaded source is not a playlist or an album.";
        } else if (currentType == null) {
            this.message = "Please load a source before using the shuffle function.";
        }
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }


    /**
     * Gets seed.
     *
     * @return the seed
     */
    @JsonIgnore
    public int getSeed() {
        return seed;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
