package main.commands.searchBar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.collections.Albums;
import main.collections.Playlists;
import main.commands.types.Album;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.commands.types.Playlist;
import main.SearchBar;
import main.users.User;

import java.util.ArrayList;

public class Select implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final int itemNumber;
    private String selectedName;
    private String message;

//    creating macros for the selected type
    private static final int SONG = 0;
    private static final int PODCAST = 1;
    private static final int PLAYLIST = 2;
    private static final int ALBUM = 3;
    private static final int ARTIST = 4;
    private static final int HOST = 5;

    /**
     * Execute.
     */
    public void execute(final User currUser) {
        this.setSelect(currUser);
    }

    /**
     * Accept command
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Constructor for Select command.
     * @param input the input
     */
    public Select(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.itemNumber = input.getItemNumber();
        this.message = "Please conduct a search before making a selection.";
    }

    /**
     * Sets select.
     *
     * @param currUser   the current user
     */
    public void setSelect(final User currUser) {

//                if the last command was search
        if (currUser.getCurrentSearch() != null) {
            ArrayList<String> resultsPrevSearch = currUser.getCurrentSearch().getResults();

//                    getting index for setting the message
            int index = this.itemNumber;

//                    make this be a method in select class
            if (index > resultsPrevSearch.size()) {
                this.message = "The selected ID is too high.";
                currUser.setCurrentSelect(null);
                currUser.setCurrentType(null);
            } else {
                String name = resultsPrevSearch.get(index - 1);


                if (currUser.getTypeFoundBySearch() == ARTIST) {
                    currUser.setCurrentPage("Artist");
                    currUser.setSelectedPageOwner(name);
                    this.setMessage("Successfully selected " + name + "'s page.");
                } else if (currUser.getTypeFoundBySearch() == HOST) {
                    currUser.setCurrentPage("Host");
                    currUser.setSelectedPageOwner(name);
                    this.setMessage("Successfully selected " + name + "'s page.");
                } else {
                    this.setMessage("Successfully selected " + name + ".");
                }

                currUser.setSelectedName(name);
                this.setSelectedName(name);

                if (currUser.getTypeFoundBySearch() == SONG) {
                    currUser.setTypeSelected(SONG);

                    currUser.setSelectedAlbum(currUser.getCurrentSearch().getResultsAlbum(index - 1));

                    if (currUser.getSelectedName().equalsIgnoreCase("Johnny b. Goode")) {
                        int x = 5;
                    }


                    for (Album album : Albums.getAlbums()) {
                        if (album.getName().equals(currUser.getSelectedAlbum())) {
                            currUser.setSelectedPlaylist(album);
                            break;
                        }
                    }


                } else if (currUser.getTypeFoundBySearch() == PODCAST) {
                    currUser.setTypeSelected(PODCAST);
                } else if (currUser.getTypeFoundBySearch() == PLAYLIST) {
                    currUser.setTypeSelected(PLAYLIST);

                    for (Playlist playlist : Playlists.getPlaylists()) {
                        if (playlist.getName().equals(name)
                            && playlist.getName().equals(currUser.getCurrentSearch().getResultsAlbum(index))) {
                            currUser.setSelectedPlaylist(playlist);
                            break;
                        }
                    }

//                    albums
                } else if (currUser.getTypeFoundBySearch() == ALBUM) {
                    currUser.setTypeSelected(ALBUM);
                    for (Album album : Albums.getAlbums()) {
                        if (album.getName().equals(name)) {
                            currUser.setSelectedPlaylist(album);
                            break;
                        }
                    }

//                    artists
                } else if (currUser.getTypeFoundBySearch() == ARTIST) {
                    currUser.setTypeSelected(ARTIST);

//                    hosts
                } else if (currUser.getTypeFoundBySearch() == HOST) {
                    currUser.setTypeSelected(HOST);
                }
                currUser.setCurrentSelect(this);
            }
        }
        currUser.setCurrentSearch(null);
        currUser.setTypeFoundBySearch(-1);
    }

    /**
     * Gets command.
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets user.
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets timestamp.
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Gets item number.
     * @return the item number
     */
    @JsonIgnore
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     * Gets message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets selected name.
     * @return the selected name
     */
    @JsonIgnore
    public String getSelectedName() {
        return selectedName;
    }

    /**
     * Sets selected name.
     * @param selectedName the selected name
     */
    public void setSelectedName(final String selectedName) {
        this.selectedName = selectedName;
    }

}
