package main.commands.player.admin;

import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.commands.types.Podcast;
import main.users.Host;

import java.util.ArrayList;

public final class ShowPodcasts implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final ArrayList<Podcast> result;

    /**
     * executes the command and sets the result
     * calls the setHost method
     * @param host the host that will be set
     */
    public void execute(final Host host) {
        this.setHost(host);
    }

    /**
     * sets the result to the host's podcasts
     * @param host
     */
    private void setHost(final Host host) {

        for (Podcast podcast : host.getHostPodcasts()) {
            this.result.add(podcast);
        }
    }

    /**
     * constructor for the ShowPodcasts command
     * @param input the input that was given
     */
    public ShowPodcasts(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();

        this.result = new ArrayList<>();
    }

    /**
     * accepts a visitor and calls the visit method
     * @param commandVisitor the visitor
     */
    public void accept(final CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the result
     * @return the result that was obtained
     */
    public ArrayList<Podcast> getResult() {
        return result;
    }
}
