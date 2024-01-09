package main.commands;

import main.SearchBar;
import main.inputCommand.Command;
import main.users.User;

import java.util.ArrayList;
import java.util.Map;

public class GetNotifications implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private ArrayList<Map<String, String>> notifications;

    /**
     * constructor for GetNotifications
     * @param input the input
     */
    public GetNotifications(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        notifications = new ArrayList<>();
    }

    /**
     * executes the GetNotifications command
     */
    public void execute(final Object... params) {
        this.setNotifications((User) params[1]);
    }

    /**
     * sets the message of the GetNotifications command
     */
    public void setNotifications(final User currUser) {

        if (currUser == null) {
            return;
        }

//        deep copy of the notifications
        for (Map<String, String> notification : currUser.getNotifications()) {
            notifications.add(notification);
        }
        currUser.getNotifications().clear();
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
     * gets the notifications
     */
    public ArrayList<Map<String, String>> getNotifications() {
        return notifications;
    }
}
