package main.inputCommand;

public interface Command {
    /**
     * executes the command
     */
    void execute(Object... params);
}
