package main.inputCommand;

public interface Command {

    /**
     * Accepts a visitor
     * this is used for the visitor pattern
     * accept is Overridden in all the commands
     *
     * @param visitor the visitor to be accepted
     */
    void accept(CommandVisitor visitor);
}
