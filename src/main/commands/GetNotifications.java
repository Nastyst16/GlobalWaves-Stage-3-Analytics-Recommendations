package main.commands;

import main.SearchBar;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.util.ArrayList;
import java.util.Map;

public class GetNotifications implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private ArrayList<Map<String, String>> notifications;

    public GetNotifications(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        notifications = new ArrayList<>();
    }

    /**
     * executes the GetNotifications command
     */
    public void execute(Object... params) {
        this.setNotifications((User) params[1]);
    }

    /**
     * sets the message of the GetNotifications command
     */
    public void setNotifications(User user) {

        if (user == null) {
            return;
        }

//        deep copy of the notifications
        for (Map<String, String> notification : user.getNotifications()) {
            notifications.add(notification);
        }
        user.getNotifications().clear();
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public ArrayList<Map<String, String>> getNotifications() {
        return notifications;
    }
}
