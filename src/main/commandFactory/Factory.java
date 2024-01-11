package main.commandFactory;

import main.SearchBar;
import main.inputCommand.Command;

public interface Factory {
    /**
     * creates a command
     * @return
     */
    Command createCommand(String command, SearchBar input);
}
