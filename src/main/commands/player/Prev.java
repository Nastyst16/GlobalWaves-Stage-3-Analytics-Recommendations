package main.commands.player;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Type;
import main.SearchBar;
import main.users.User;

public final class Prev implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private String message;

    /**
     * Executes the command
     */
    public void execute(final User currUser) {

        this.setPrev(currUser);
    }

    /**
     * Accepts the visitor for the command
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
    public Prev(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Returns to the previous track
     *
     * @param currUser the user that called the command
     */
    public void setPrev(final User currUser) {

//        if the user is offline
        if (!currUser.getOnline()) {
            this.message = this.user + " is offline.";
            return;
        }

        Type currentType = currUser.getCurrentType();
        if (currUser.getCurrentType() == null) {
            this.message = "Please load a source before returning to the previous track.";
            return;
        }

//        if it's a song
        if (currUser.getTypeLoaded() == 0) {
            currentType.setSecondsGone(0);
        } else if (currUser.getTypeLoaded() == 2) {
//            prev for playlists

            if (currentType.getSecondsGone() > 0) {
                currentType.setSecondsGone(0);
            } else if (currentType.getSecondsGone() == 0) {
                if (currUser.isShuffle()) {

                    int prevIndex = currUser.getCurrentPlaylist().
                            getSongList().indexOf(currentType);
                    prevIndex = currUser.getShuffledIndices().indexOf(prevIndex) - 1;

                    if (prevIndex == -1) {
                        currentType.setSecondsGone(0);
                    } else {
                        prevIndex = currUser.getShuffledIndices().get(prevIndex);
                        currentType = currUser.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }

                } else {
//                    if it's the first song of the playlist
                    if (currUser.getCurrentPlaylist().getSongList().indexOf(currentType) == 0) {
                        currentType.setSecondsGone(0);
                    } else {
                        int prevIndex = currUser.getCurrentPlaylist().
                                getSongList().indexOf(currentType) - 1;
                        currentType = currUser.getCurrentPlaylist().getSongList().get(prevIndex);
                        currentType.setSecondsGone(0);
                    }
                }
            }
        } else if (currUser.getTypeLoaded() == 1) {
            if (currUser.getCurrentPodcast().getLastRemainingEpisode() == 0) {
                currentType.setSecondsGone(0);
            } else {
                int prevIndex = currUser.getCurrentPodcast().getLastRemainingEpisode() - 1;
                currentType = currUser.getCurrentPodcast().getEpisodesList().get(prevIndex);
            }
        }

        currUser.setCurrentType(currentType);
        currUser.setPaused(false);

        if (currUser.getCurrentType() != null) {
            this.message = "Returned to previous track successfully. The current track is "
                    + currentType.getName() + ".";
        }

        currUser.setCurrentType(currentType);
    }

    /**
     * gets the command
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * gets the user
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the timestamp
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     *
     * @param message the message to be set
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
