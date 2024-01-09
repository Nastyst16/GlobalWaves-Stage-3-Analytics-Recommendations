package main.commandFactory;

import main.SearchBar;
import main.inputCommand.Command;

public interface Factory {
    Command createCommand(String command, SearchBar input);
}
