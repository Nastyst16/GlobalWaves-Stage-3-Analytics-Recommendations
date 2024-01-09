package main.inputCommand;

import main.SearchBar;
import main.users.Artist;
import main.users.Host;
import main.users.User;

public interface Command {
    void execute(Object... params);
}
